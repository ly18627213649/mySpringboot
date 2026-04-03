package com.example.demo;

import com.example.DemoApplication;
import com.example.Exception.ServiceException;
import com.example.proto.ResultCode;
import com.example.app.mapper.UserLoginMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;

/**
 * 多线程事务回滚
 * 背景: 有一个大数据量插入操作,需要先做一下其它修改,再插入,由于插入数据可能会很多，用到多线程去拆分数据并行处理来提高响应时间，如果有一个线程执行失败，则全部回滚
 *       如果主线程需要先执行一些修改数据库的操作，当子线程在进行处理出现异常时，主线程修改的数据需要回滚
 */
@SpringBootTest(classes = { ThreadCallback.class, DemoApplication.class})
public class ThreadCallback {

    /**
     * 算法:平均拆分list方法.
     * @param source 需要拆分的list
     * @param n 拆几份
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n){
        List<List<T>> result=new ArrayList<List<T>>();
        int remaider = source.size()%n;
        int number = source.size()/n;
        int offset=0;//偏移量
        for(int i=0;i<n;i++){
            List<T> value=null;
            if(remaider>0){
                value = source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value = source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * 懒汉式单例: 线程池配置
     * @version V1.0
     */
    static class ExecutorConfig {

        private static int maxPoolSize = Runtime.getRuntime().availableProcessors();
        private volatile static ExecutorService executorService;

        public static ExecutorService getThreadPool() {
            if (executorService == null){
                synchronized (ExecutorConfig.class){
                    if (executorService == null){
                        executorService =  newThreadPool();
                    }
                }
            }
            return executorService;
        }

        private static  ExecutorService newThreadPool(){
            int queueSize = 500;
            int corePool = Math.min(5, maxPoolSize);
            return new ThreadPoolExecutor(corePool, maxPoolSize, 10000L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingQueue<>(queueSize),new ThreadPoolExecutor.AbortPolicy());
        }
        private ExecutorConfig(){}
    }

    /**
     * 获取sqlSession
     */
    @Component
    class SqlContext {
        @Resource
        private SqlSessionTemplate sqlSessionTemplate;

        public SqlSession getSqlSession(){
            SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
            return sqlSessionFactory.openSession();
        }
    }

    /**
     * 业务服务
     */
    @Service
    class MoreThreadService1 {

        @Resource
        private SqlContext sqlContext;

        /**
         * 保存业务,主线程删除操作,子线程新增操作
         * @param listData
         * @throws SQLException
         */
        public void save(List<Map<String,String>> listData) throws SQLException {

            // 获取数据库连接,获取会话(内部自有事务)
            SqlSession sqlSession = sqlContext.getSqlSession();
            Connection connection = sqlSession.getConnection();
            try {
                // 设置手动提交
                connection.setAutoCommit(false);
                //获取mapper
                UserLoginMapper userLoginMapper = sqlSession.getMapper(UserLoginMapper.class);
                //先做删除操作
                userLoginMapper.delete("123");
                //获取执行器
                ExecutorService executorService = ExecutorConfig.getThreadPool();
                List<Callable<Integer>> callableList  = new ArrayList<>();
                //拆分list
                List<List<Map<String,String>>> lists = averageAssign(listData, 3);
//                AtomicBoolean atomicBoolean = new AtomicBoolean(true);
                for (int i =0;i<lists.size();i++){
//                    if (i == lists.size()-1){
//                        atomicBoolean.set(false);
//                    }
                    List<Map<String,String>> list  = lists.get(i);
                    //使用返回结果的callable去执行,
                    Callable<Integer> callable = () -> {
                        // 执行批量新增
                        return userLoginMapper.saveBatch(list);
                    };
                    callableList.add(callable);
                }
                //执行子线程
                List<Future<Integer>> futures = executorService.invokeAll(callableList);
                for (Future<Integer> future : futures) {
                    //如果有一个执行不成功,则全部回滚
                    if (future.get() <= 0){
                        connection.rollback();
                        return;
                    }
                }
                connection.commit();
                System.out.println("添加完毕");
            }catch (Exception e){
                connection.rollback();
                throw new ServiceException(ResultCode.APP_ERROR,"出现异常");
            }finally {
                connection.close();
            }
        }
    }


    @Resource
    private MoreThreadService1 moreThreadService;
    /**
     * 测试
     */
    @Test
    public  void MoreThreadTest2() throws InterruptedException {
        int size = 10;
        List<Map<String,String>> list = new ArrayList<>(size);
        for (int i = 0; i<size;i++){
            Map<String,String> map = new HashMap<>();
            map.put("username","测试"+i);
            map.put("password","123456"+i);
            map.put("bgr","ly"+i);
            map.put("bgsj",new SimpleDateFormat("yyyy-mm-dd HH24:mm:ss").format(new Date()));

            list.add(map);
        }
        try {
            moreThreadService.save(list);
            System.out.println("添加成功");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

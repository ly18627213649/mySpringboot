package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.proto.ResponseResult;
import com.example.util.*;
import com.example.util.vo.SysFieldVo;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 测试类
 *
 * @author liyang
 * @since  2019/11/13 10:11
 */
public class LiyangTest {

    public static void main(String[] args) {

    }

    /**
     * Base64 加密解密
     */
    @Test
    public void base64(){

        String s = Base64Util.base64Encoder("123456");
        String s2 = MD5Util.md5(s);
        System.out.println(s2);
        System.out.println(s);

        String s1 = Base64Util.base64Decode(s);
        System.out.println(s1);
    }

    @Test
    public void test1(){
        boolean b = Boolean.parseBoolean("1");
        System.out.println(b);
    }

    @Test
    public void test2(){
        long aLong = Long.parseLong("1566374493404.9412");
        System.out.println(aLong);
    }

    /**
     * 验证密码强度, 最少8为, 包含数字和字母
     */
    @Test
    public void checkPassword(){
        Pattern p = Pattern.compile("^(?=.*?[a-z])(?=.*?[0-9]).{8,}$");
        Matcher m = p.matcher("1234567p");
        System.out.println(m.find());
    }

    /**
     * 日历格式
     */
    @Test
    public void calendar(){

        long timestamp = 1568710785374L;

        Calendar calendar = Calendar.getInstance(); // 日历对象

        calendar.setTimeInMillis(timestamp);

        int year = calendar.get(Calendar.YEAR);    //获取年
        int month = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = calendar.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int first = calendar.getActualMinimum(calendar.DAY_OF_MONTH);    //获取本月最小天数
        int last = calendar.getActualMaximum(calendar.DAY_OF_MONTH);    //获取本月最大天数
        int time = calendar.get(Calendar.HOUR_OF_DAY);       //获取当前小时
        int min = calendar.get(Calendar.MINUTE);          //获取当前分钟
        int xx = calendar.get(Calendar.SECOND);          //获取当前秒

        System.out.println("当前时间："+year + "-" + month + "-"+ day + " "+time + ":" + min +":" + xx);
        System.out.println("第一天和最后天：" + first +"," + last);

        // 时间格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String curTime = sdf.format(calendar.getTime());

        System.out.println("当前日期====:" + curTime);
    }

    /**
     * TimeUtils 工具
     */
    @Test
    public void time(){

        long currTime = System.currentTimeMillis();

        // 毫秒 --> 秒
        long seconds = TimeUnit.MILLISECONDS.toSeconds(currTime - 1568948984016L);

        System.out.println(seconds);
    }

    /**
     * 遍历map
     */
    @Test
    public void map(){
        Map<String,Long> map = new HashMap<>();

        map.put("a",0L);
        map.put("b",1L);
        map.put("c",2L);

        Map<String,Long> map1 = new HashMap<>();

        // 遍历map集合
        for (Map.Entry<String, Long> stringLongEntry : map.entrySet()) {

            Long otherReadTime = stringLongEntry.getValue();

            if (otherReadTime == 0){
                map.put(stringLongEntry.getKey(),9L);
            }
        }

        System.out.println(map.toString());
    }

    /**
     * AES 加密解密
     */
    @Test
    public void AES_encryp_decryp(){

        String content = "我爱北京天安门";
        String key = "XEMjQbym7+z/P64OZyQEGw==";

        // AES 加密与解密
        String encrypt = AESUtil.encrypt(key,content);

        String decrypt = AESUtil.decrypt(key,content).trim();

        System.out.println("原文: "+content);
        System.out.println("加密后: "+ encrypt);
        System.out.println("解密后: "+ decrypt);

    }

    /**
     * lang3  ArrayUtils , 集合转String
     */
    @Test
    public void ArrayUtils_toString(){
        // 拼接sql
        String tableName = "OA_TEST";
        String keyName = "id";

        List list = new ArrayList( );
        list.add( "Foo" );
        list.add( "Blah" );

        // 集合转为 String ,
        String array = ArrayUtils.toString(list.toArray(), ",");

        String sql = "delete from " + tableName + " where " + keyName + " in ("+ ArrayUtils.toString(list.toArray(), ",")+")";

        System.out.println( array );

    }

    @Test
    public void mapTest(){

        List<SysFieldVo> fields1 = new ArrayList<>();

        List<SysFieldVo> fields2 = new ArrayList<>();

        for (int i = 1; i<3;i++){
            SysFieldVo field1 = new SysFieldVo();
            field1.setField(i+"");
            field1.setDataType("int");
            field1.setLen(0);
            field1.setPot(0);
            field1.setDescribe("字段"+i);
            fields1.add(field1);
        }

        for (int i = 1; i<2;i++){
            SysFieldVo field1 = new SysFieldVo();
            field1.setField(i+"");
            field1.setDataType("int");
            field1.setLen(1);
            field1.setPot(1);
            field1.setDescribe("字段"+i);
            fields2.add(field1);
        }


        Map<String, List> listDiff = ListUtil.findListDiff(fields1, fields2);
    }

    /**
     *  获取系统信息
     */
    @Test
    public void System(){
        Properties properties = System.getProperties();
        for (Object o:properties.keySet()){
            String value = (String) properties.get(o);
            System.out.println(o+"::"+value);
        }
        String osName = System.getProperty("os.name");
        System.out.println(osName);
    }

    /**
     * html 转义和反转义,例; 富文本编辑器
     */
    @Test
    public void StringEscapeUtils(){
        String str = StringEscapeUtils.unescapeHtml4("&lt;p&gt;【产品名称】艾酷维多种维生素锌软糖&lt;/p&gt;");
        System.out.println(str);
        String str2 = StringEscapeUtils.escapeHtml4("<p>【产品名称】艾酷维多种维生素锌软糖</p>");
        System.out.println(str2);
    }


    @Test
    public void StringTest(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("field","ds");
        jsonObject.put("describe","dsff");

        SysFieldVo t = JacksonUtil.json2Bean(jsonObject.toString(), SysFieldVo.class);
        System.out.println(t.toString());
    }

    /**
     * 时间转为 cron 表达式
     */
    @Test
    public void formtCornString(){
        String str = DateTimeUtil.format(new Date(), "ss mm HH dd MM ? yyyy");
        System.out.println(str);
    }

    @Test
    public void remove(){
        Map<String,Map<String,String>> map = new HashMap<>();
        Map<String,String> map1 = new HashMap<>();
        map1.put("a","aa");
        map1.put("b","bb");
        map1.put("c","cc");

        map.put("1",map1);

        map.get("1").remove("d");
    }

    /**
     * 明感词检查
     */
    @Test
    public void SensitiveWordTest(){

        ArrayList words = new ArrayList<>();
        words.add("回家");
        words.add("我的");
        words.add("爱我");
        words.add("难过");
        String test = "你说你会难过我不相信,你真的是比我还要爱你,我真的没有天分";

        SensitiveWordUtil sensitiveWordUtil = new SensitiveWordUtil(words);

        // 检查文本是否包含敏感词
        boolean b = sensitiveWordUtil.isContaintSensitiveWord(test, 2);
        System.out.println(b);

        // 检查文本中明感词内容
        Set<String> set = sensitiveWordUtil.getSensitiveWord(test, 2);
        for (String str:set){
            System.out.println(str);
        }
    }

}

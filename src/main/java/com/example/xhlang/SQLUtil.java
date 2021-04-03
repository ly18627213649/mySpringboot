package com.example.xhlang;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 针对SQL的相关工具集
 *
 * @author Tommy
 * @since 2019/2/14 16:37
 */
public class SQLUtil {

    protected static Logger log = LoggerFactory.getLogger(SQLUtil.class);

    public SQLUtil() {
    }

    /**
     * 自动赋值
     *
     * @author Tommy
     * @since 2019/2/14 16:39
     */
    public static void autoSetSQLByList(PreparedStatement ps, List<Object> paramsList) throws SQLException {
        if (ps != null && paramsList != null && !paramsList.isEmpty()) {

            // 取得总数
            int size = paramsList.size();

            for (int i = 0; i < size; ++i) {
                /* 逐个参数进行类型判断，来使用ps进行相应类型参数的设置 */

                // 取得当前的参数
                Object params = paramsList.get(i);

                // 设置索引
                int index = i + 1;

                // null类型处理
                if (params == null) {
                    ps.setNull(index, 0);
                    // 其它类型的处理
                } else {
                    // 取得字段类型
                    String type = paramsList.get(i).getClass().getName();
                    // int类型
                    if (type.equals("java.lang.Integer")) {
                        ps.setInt(index, (Integer) params);
                        // long类型
                    } else if (type.equals("java.lang.Long")) {
                        ps.setLong(index, (Long) params);
                        // double类型
                    } else if (type.equals("java.lang.Double")) {
                        ps.setDouble(index, (Double) params);
                        // float类型
                    } else if (type.equals("java.lang.Float")) {
                        ps.setFloat(index, (Float) params);
                        // bytej类型
                    } else if (type.equals("java.lang.Byte")) {
                        ps.setByte(index, (Byte) params);
                        // boolean类型
                    } else if (type.equals("java.lang.Boolean")) {
                        ps.setBoolean(index, (Boolean) params);
                        // short类型
                    } else if (type.equals("java.lang.Short")) {
                        ps.setShort(index, (Short) params);
                        //string类型
                    } else if (type.equals("java.lang.String")) {
                        ps.setString(index, params.toString());
                        //date类型
                    } else {
                        if (!type.equals("java.util.Date")) {
                            throw new IllegalArgumentException("未定义的参数类型，请检查重试！");
                        }
                        // 强转为date类型
                        Date _date = (Date) params;
                        // 以timestamp进行参数设置
                        ps.setTimestamp(index, new Timestamp(_date.getTime()));
                    }
                }
            }

        }
    }

    /**
     * 通过rs对结果进行封装
     *
     * @author Tommy
     * @since 2019/2/14 16:40
     */
    public static Object resultSetToObject(ResultSet rs, Class clazz) throws Exception {

        Object returnValue = null;

        // 生成list对象
        List<Object> list = resultSetToObjectList(rs, clazz);
        // 防守判断
        if (!list.isEmpty()) {
            // 取得第一个对象
            returnValue = list.get(0);
        }

        return returnValue;
    }

    /**
     * 通过rs对结果进行封装
     *
     * @author Tommy
     * @since 2019/2/14 16:41
     */
    public static List<Object> resultSetToObjectList(ResultSet rs, Class clazz) throws Exception {
        ArrayList returnValue = new ArrayList();

        try {
            // 加载类
            Class myClass = Class.forName(clazz.getName());
            // 取得meta信息
            ResultSetMetaData meta = rs.getMetaData();
            // 取得总字段数
            int count = meta.getColumnCount();
            // 遍历每一条记录
            while (rs.next()) {
                // 遍历每一条记录
                Object obj = ClassUtil.newInstance(clazz);
                // 遍历每一条记录的每一列
                for (int i = 1; i <= count; ++i) {
                    // 取得列的类型
                    int type = meta.getColumnType(i);
                    // 取得列名
                    String columnName = meta.getColumnName(i);
                    // 取得类方法
                    Method method = getMethodByColumn(myClass, "set", columnName);

                    try {
                        switch (type) {
                            case -16:
                                method.invoke(obj, rs.getNString(i));
                                break;
                            case -9:
                                method.invoke(obj, rs.getString(i));
                                break;
                            case -7:
                                method.invoke(obj, rs.getByte(i));
                                break;
                            case -6:
                                method.invoke(obj, rs.getInt(i));
                                break;
                            case -5:
                                method.invoke(obj, rs.getLong(i));
                                break;
                            case -4:
                                method.invoke(obj, rs.getBinaryStream(i));
                                break;
                            case -3:
                                method.invoke(obj, rs.getBinaryStream(i));
                                break;
                            case -2:
                                method.invoke(obj, rs.getBinaryStream(i));
                                break;
                            case -1:
                                method.invoke(obj, rs.getString(i));
                                break;
                            case 0:
                                throw new Exception("ResultSetToObject时,NULL 类型未定义!");
                            case 1:
                                method.invoke(obj, rs.getString(i));
                                break;
                            case 2:
                                method.invoke(obj, rs.getDouble(i));
                                break;
                            case 3:
                                method.invoke(obj, rs.getDouble(i));
                                break;
                            case 4:
                                method.invoke(obj, rs.getInt(i));
                                break;
                            case 5:
                                method.invoke(obj, rs.getInt(i));
                                break;
                            case 6:
                                method.invoke(obj, rs.getFloat(i));
                                break;
                            case 7:
                                method.invoke(obj, rs.getInt(i));
                                break;
                            case 8:
                                method.invoke(obj, rs.getDouble(i));
                                break;
                            case 12:
                                method.invoke(obj, rs.getString(i));
                                break;
                            case 16:
                                method.invoke(obj, rs.getBoolean(i));
                                break;
                            case 70:
                                method.invoke(obj, rs.getTimestamp(i));
                                break;
                            case 91:
                                Date date = rs.getDate(i);
                                method.invoke(obj, date != null ? new Date(date.getTime()) : null);
                                break;
                            case 92:
                                method.invoke(obj, rs.getTime(i));
                                break;
                            case 93:
                                Timestamp timestamp = rs.getTimestamp(i);
                                method.invoke(obj, timestamp != null ? new Date(timestamp.getTime()) : null);
                                break;
                            case 1111:
                                throw new Exception("ResultSetToObject时,OTHER 类型未定义!");
                            case 2000:
                                method.invoke(obj, rs.getObject(i));
                                break;
                            case 2001:
                                throw new Exception("ResultSetToObject时,DISTINCT 类型未定义!");
                            case 2002:
                                throw new Exception("ResultSetToObject时,STRUCT 类型未定义!");
                            case 2003:
                                method.invoke(obj, rs.getArray(i));
                                break;
                            case 2004:
                                // 取得数据
                                Blob blob = rs.getBlob(i);
                                // 读成字符串
                                String blobStr = blobToString(blob);
                                method.invoke(obj, blobStr);
                                break;
                            case 2005:
                                // 取得数据
                                Clob clob = rs.getClob(i);
                                // 读成字符串
                                String clobStr = clobToString(clob);
                                method.invoke(obj, clobStr);
                                break;
                            case 2006:
                                method.invoke(obj, rs.getRef(i));
                                break;
                            default:
                                throw new Exception("ResultSetToObject时,没有找到 java.sql.Types 与之对应的类型!");
                        }
                    } catch (NullPointerException var17) {
                        log.error("ResultSetToObject 出错: 找不到对象，或Bean对象中不存在 " + columnName + " 方法！", var17);
                        throw var17;
                    }
                }

                returnValue.add(obj);
            }

            return returnValue;
        } catch (Exception var18) {
            log.error("ResultSetToObject出错", var18);
            throw var18;
        }
    }

    /**
     * 通过rs对结果进行封装
     *
     * @author Tommy
     * @since 2019/2/14 16:40
     */
    public static Map<String, Object> resultSetToMap(ResultSet rs) {
        Map<String, Object> returnValue = null;
        // 生成list对象
        List<Map<String, Object>> list = resultSetToMapList(rs);
        // 防守判断
        if (!list.isEmpty()) {
            // 取得第一个对象
            returnValue = (Map) list.get(0);
        }

        return returnValue;
    }

    /**
     * 通过rs对结果进行封装
     *
     * @author Tommy
     * @since 2019/2/14 16:41
     */
    public static List<Map<String, Object>> resultSetToMapList(ResultSet rs) {
        ArrayList returnValue = new ArrayList();

        try {
            // 取得meta信息
            ResultSetMetaData meta = rs.getMetaData();
            // 取得总字段数
            int count = meta.getColumnCount();
            // 遍历每一条记录
            while (rs.next()) {
                // 初始化map
                Map<String, Object> map = new HashMap();
                // 遍历每一条记录的每一列
                for (int i = 1; i <= count; ++i) {
                    int type = meta.getColumnType(i);
                    Object columnValue;
                    if (type == 2005) {
                        columnValue = clobToString(rs.getClob(i));
                    } else if (type == 2004) {
                        columnValue = blobToString(rs.getBlob(i));
                    } else {
                        columnValue = rs.getObject(i);
                    }

                    String columnName = meta.getColumnName(i);
                    map.put(columnName, columnValue);
                }

                returnValue.add(map);
            }
        } catch (Exception var9) {
            log.error("ResultSetToMap出错", var9);
        }

        return returnValue;
    }

    /**
     * 取得类方法
     *
     * @author Tommy
     * @since 2019/2/14 17:50
     */
    public static Method getMethodByColumn(Class objClass, String methodType, String column) {
        Method method = null;
        // 生成方法名称
        String methodName = getMethodName(methodType, column);
        // 取得类的所有方法
        Method[] methods = objClass.getMethods();
        // 取得方法总数
        int count = methods.length;
        // 遍历
        for (int i = 0; i < count; ++i) {
            // 取得当前方法
            Method m = methods[i];
            // 判断是否匹配，如若匹配，则跳出
            if (m.getName().equals(methodName)) {
                method = m;
                break;
            }
        }

        return method;
    }

    /**
     * 取得方法名称
     *
     * @author Tommy
     * @since 2019/2/14 17:51
     */
    public static String getMethodName(String methodType, String column) {
        return column == null ? null : methodType + StringUtils.capitalize(column);
    }

    /**
     * 把clob对象转为String类型
     *
     * @author Tommy
     * @since 2019/3/6 9:23
     */
    public static String clobToString(Clob clob) {
        String returnValue = "";
        // 定义
        Reader is = null;
        BufferedReader br = null;

        try {
            // 取得流
            is = clob.getCharacterStream();
            // 读为BufferedReader对象
            br = new BufferedReader(is);
            // 定义拼装对象
            StringBuffer sb = new StringBuffer();
            // 定义行
            String line;
            // 循环读取
            while ((line = br.readLine()) != null) {
                // 进行拼装
                sb.append(line);
            }
            // 读完之后，转换为string对象
            returnValue = sb.toString();
        } catch (IOException | SQLException var18) {
            log.error(String.format("把Clob对象转换为String时出错：%s", var18));
        } finally {
            // 防守判断
            if (is != null) {
                try {
                    is.close();
                } catch (IOException var17) {
                    log.error(String.format("关闭is时出错：%s", var17));
                }
            }

            if (br != null) {
                try {
                    br.close();
                } catch (IOException var16) {
                    log.error(String.format("关闭br时出错：%s", var16));
                }
            }

        }

        return returnValue;
    }

    /**
     * 把blob对象转换为String
     *
     * @author Tommy
     * @since 2019/4/16 19:34
     */
    public static String blobToString(Blob blob) {
        String returnValue = "";
        // 防守判断
        if (blob == null) {
            return returnValue;
        } else {
            // 定义数据流
            InputStream is = null;

            try {
                // 读取数据
                is = blob.getBinaryStream();
                // 创建byte数组
                byte[] b = new byte[is.available()];
                // 开始读取
                is.read(b, 0, b.length);
                // 转为string
                returnValue = new String(b);
            } catch (SQLException | IOException var12) {
                log.error(String.format("把Blob对象转换为String时出错：%s", var12));
            } finally {
                // 防守判断
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException var11) {
                        log.error(String.format("(BlobToString)关闭is时出错：%s", var11));
                    }
                }

            }

            return returnValue;
        }
    }
}

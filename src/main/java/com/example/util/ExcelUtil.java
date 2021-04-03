package com.example.util;/*
package com.xinhoo.xhpfp.utils;

import com.xinhoo.xhpfp.entity.vo.ExcelVOAttribute;
import com.xinhoo.xhpfp.utils.classz.BeanHelper;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

*/
/**
 * ExcelUtil 导入/导出 工具
 *
 * @author liyang
 * @since  2019/11/8 9:40
 *//*

public class ExcelUtil {

    protected static LogUtil logUtil = LogUtil.getLog(ExcelUtil.class);

    // 私有化构造方法
    private ExcelUtil(){}

    // 返回当前对象单例
    public static ExcelUtil me(){

        return single.INSTANCE.instance;
    }

    // 枚举
    private enum single{

        INSTANCE;

        private ExcelUtil instance;

        single(){ instance = new ExcelUtil();}

    }

    */
/**
     *  从excel 中导入数据,保存到list中
     *  @param cls javaBean 类型,需和excel文件每行数据的列数相同且一一对应
     *  @param is 文件输入流
     *  @param excelFileName 原始文件名
     *
     * @author liyang
     * @since  2019/11/8 11:28
     *//*

    public <T> List<Object> importDataFromExcel(Class<T> cls, InputStream is, String excelFileName){

        List<Object> list = new ArrayList();

        try {
            //创建工作簿
            Workbook workbook = this.createWorkbook(is, excelFileName);

            if (workbook == null){
                return null;
            }

            //创建工作表sheet
            Sheet sheet = this.getSheet(workbook, 0);

            //获取sheet中数据的行数
            int rows = sheet.getPhysicalNumberOfRows();

            //获取表头单元格个数(列数)
            int cells = sheet.getRow(0).getPhysicalNumberOfCells();

            //利用反射，给JavaBean的属性
            Field[] fields = cls.getDeclaredFields();

            // 将有注解的field 存入map<Integer,Field>
            Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
            for (int i = 0; i < fields.length; i++) {

                // 获取属性对象
                Field field = fields[i];

                // 将有注解的field存放到map中.
                if (field.isAnnotationPresent(ExcelVOAttribute.class)) {

                    // 获取注解
                    ExcelVOAttribute excelVOAttribute = field.getAnnotation(ExcelVOAttribute.class);

                    // 获取列号
                    int columnIndex = excelVOAttribute.columnIndex();

                    // 设置类的私有字段属性可访问.
                    field.setAccessible(true);

                    fieldsMap.put(columnIndex, field);
                }
            }

            //第一行为标题栏，从第二行开始取数据
            for (int i = 1; i < rows; i++) {

               JSONObject jsonObject = new JSONObject();

                // 得到每一行
                Row row = sheet.getRow(i);

                int index = 0;
                // 遍历每一行,从第一列开始取每个单元格值
                while (index < cells) {

                    // 得到每一个单元格
                    Cell cell = row.getCell(index);
                    if (null == cell) {
                        cell = row.createCell(index);
                    }
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    // 取单元格值
                    String value = null == cell.getStringCellValue() ? "" : cell.getStringCellValue();

                    // 从map中取属性对象
                    Field field = fieldsMap.get(index);
                    // 属性名称
                    String fieldName = field.getName();

                    jsonObject.put(fieldName,this.setFieldValueByType(field,value));

                    index++;
                }

                // 将map转为Bean
                Object object = JSONObject.toBean(jsonObject,cls);

                if (object != null){

                    list.add(object);
                }
            }
        } catch (Exception e) {
            logUtil.error("读取excel 数据失败",e);
        }finally{
            try {
                is.close();//关闭流
            } catch (Exception e2) {
                logUtil.error("关闭inputStream流失败",e2);
            }
        }
        return list;

    }

    */
/**
     * 导出数据到excel
     *
     * @param list  导出的数据,一个T对应一行数据
     * @param sheetName 工作表名
     *
     * @author liyang
     * @since  2019/11/8 10:41
     *//*

    public <T> void exportDataToExcel(List<T> list, String sheetName, OutputStream os, String excelFileName){

        Workbook workbook = null;

        // 创建文件对象
        if (excelFileName.endsWith(".xls")){
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }

        // 生成一个表格sheet
        Sheet sheet = workbook.createSheet(sheetName);

        // 设置表格默认列宽15个字节
        sheet.setDefaultColumnWidth(15);

        // 生成一个单元格样式
        CellStyle style = this.getCellStyle(workbook);

        // 生成一个字体
        Font font = this.getFont(workbook);

        // 把字体应用到当前样式
        style.setFont(font);

        //创建表头行
        Row row = sheet.createRow(0);
        row.setHeight((short)300);
        Cell cell = null;

        // 获取实体所有属性
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        // 列索引
        int columnIndex = 0;
        // 列名字
        String columnName = "";
        // 自定义注解
        ExcelVOAttribute excelVOAttribute;

        // 为表头行赋值
        for (int i = 0; i < fields.length; i++) {
            // 获取属性对象
            Field field = fields[i];

            // 设置类的私有字段属性可访问.
            field.setAccessible(true);

            // 判断是否是注解
            if (field.isAnnotationPresent(ExcelVOAttribute.class)){

                // 获取注解
                excelVOAttribute = field.getAnnotation(ExcelVOAttribute.class);

                // 获取列索引号
                columnIndex = excelVOAttribute.columnIndex();

                // 获取列名字
                columnName = excelVOAttribute.columnName();

            }

            // 创建单元格,赋值和样式
            cell = row.createCell(columnIndex);
            cell.setCellStyle(style);
            cell.setCellValue(columnName);
        }

        // 将数据放入sheet表格中
        for (int i = 0; i < list.size(); i++) {

            // 创建行, 因为标头已设定,从1开始
            row = sheet.createRow(i+1);

            T t = list.get(i);
            try {
                // 循环列
                for (int j = 0; j < fields.length; j++) {

                    // 获取属性对象
                    Field field = fields[i];

                    // 判断是否是注解
                    if (field.isAnnotationPresent(ExcelVOAttribute.class)){

                        // 获取注解
                        excelVOAttribute = field.getAnnotation(ExcelVOAttribute.class);

                        // 获取列索引号
                        columnIndex = excelVOAttribute.columnIndex();
                    }

                    // 反射获取对象属性值
                    Object value = field.get(t);
                    if (value == null){value = "";}

                    // 创建每一行的单元格, 并赋值/样式
                    cell = row.createCell(columnIndex);
                    cell.setCellValue(value.toString());
                    cell.setCellStyle(style);

                }
            } catch (Exception e) {
                logUtil.error("为sheet赋值时失败",e);
            }
        }

        try {
            workbook.write(os);
        } catch (Exception e) {
            logUtil.error("导出数据到excel,写出失败",e);
        }finally{
            try {
                os.flush();
                os.close();
            } catch (IOException e) {
                logUtil.error("导出数据到excel关闭流失败",e);
            }
        }
    }

    */
/**
     * 根据文件输入流, 创建excel的文档对象
     *
     * @author liyang
     * @since  2019/11/8 9:42
     *//*

    private Workbook createWorkbook(InputStream is, String excelFileName) throws IOException {

        if (excelFileName.endsWith(".xls")){

            return new HSSFWorkbook(is);
        } else if (excelFileName.endsWith(".xlsx")){
            return new XSSFWorkbook(is);
        }
        return null;
    }

    */
/**
     * 根据sheet索引号获取对应的sheet表单
     *
     * @author liyang
     * @since  2019/11/8 9:48
     *//*

    private Sheet getSheet(Workbook workbook, int sheetIndex){

        Sheet sheet = workbook.getSheetAt(sheetIndex);

        //合并单元格://起始列号 结束行号 起始列号 终止列号
        //CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 0, (short)11);
        //sheet.addMergedRegion(region1);

        return sheet;
    }

    */
/**
     * 获取单元格格式
     *
     * @author liyang
     * @since  2019/11/8 10:53
     *//*

    private CellStyle getCellStyle(Workbook workbook){
        // 创建单元格格式对象
        CellStyle style = workbook.createCellStyle();

        // 设置图案颜色
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        // 设置图案背景色
        style.setFillBackgroundColor(HSSFColor.RED.index);
        // //设置图案样式(空白)
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 右边框
        style.setBorderRight(BorderStyle.THIN);

        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 设置自动换行
        style.setWrapText(true);

        return style;
    }

    */
/**
     * 生成字体样式
     *
     * @author liyang
     * @since  2019/11/8 10:54
     *//*

    private Font getFont(Workbook workbook){
        // 创建  excel字体 对象
        Font font = workbook.createFont();

        // 设置字体名称
        font.setFontName("仿宋_GB2312");
        // 设置字体颜色
        font.setColor(HSSFColor.WHITE.index);
        // 设置字体大小
        font.setFontHeightInPoints((short)12);
        // 设置黑体加粗
        font.setBold(true);

        return font;
    }

    */
/**
     * 判断一个对象所有属性是否有值，如果一个属性有值(非空)，则返回true
     *
     * @author liyang
     * @since  2019/11/8 10:30
     *//*

    private boolean isHasValues(Object object){
        Field[] fields = object.getClass().getDeclaredFields();
        boolean flag = false;
        for (Field field : fields) {
            String fieldName = field.getName();
            String methodName = "getCache" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getMethod;
            try {
                getMethod = object.getClass().getMethod(methodName);
                Object obj = getMethod.invoke(object);
                if (obj != null) {
                    flag = true;
                    break;
                }
            } catch (Exception e) {
                logUtil.error("判断对象属性有没有值时,反射时出错!", e);
            }
        }
        return flag;
    }

    */
/**
     * 设置属性对象的类型值
     *
     * @author liyang
     * @since  2019/11/11 16:40
     *//*

    public static Object setFieldValueByType(Field field,Object value){

        // 属性类型
        Class<?> type = field.getType();

        if (Integer.class == type || int.class == type){

            value = CommUtil.null2Int(value);

        } else if (Double.class == type || double.class == type){

            value = CommUtil.null2Double(value);
        }
        else if (Boolean.class == type || boolean.class == type){

            value = CommUtil.null2Boolean(value);
        }
        else if (String.class == type){

            value = CommUtil.null2String(value);
        }
        else if (Long.class == type || long.class == type){

            value = CommUtil.null2Long(value);

        }
        else if (Float.class == type ){

            value = CommUtil.null2Float(value);
        }
        else if (Short.class == type || short.class == type){

            value = CommUtil.null2Short(value);
        }

        return value;
    }
}
*/

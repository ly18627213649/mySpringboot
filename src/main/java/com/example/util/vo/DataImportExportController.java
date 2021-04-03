package com.example.util.vo;/*
package com.xinhoo.xhpfp.controller;

import com.xinhoo.xhpfp.config.ApplicationProp;
import com.xinhoo.xhpfp.dao.Dao;
import com.xinhoo.xhpfp.entity.base.Data;
import com.xinhoo.xhpfp.entity.base.DataTable;
import com.xinhoo.xhpfp.entity.base.ResultModel;
import com.xinhoo.xhpfp.entity.base.impl.DataTableImpl;
import com.xinhoo.xhpfp.entity.enums.ErrorCodeMessage;
import com.xinhoo.xhpfp.entity.sys.SysConfigControl;
import com.xinhoo.xhpfp.utils.ExcelUtil;
import com.xinhoo.xhpfp.utils.JsonUtil;
import com.xinhoo.xhpfp.utils.LogUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

*/
/**
 * 数据导入测试
 *
 * @author liyang
 * @since  2019/11/11 10:24
 *//*

@Controller
@RequestMapping(value = "/file")
public class DataImportExportController {

    protected LogUtil logUtil = LogUtil.getLog(DataImportExportController.class);

    @Autowired
    private Dao dao;

    private final String DB_NAME = "NW_BASE";
    */
/**
     * excel 导入数据测试
     *
     * @author liyang
     * @since  2019/11/18 10:43
     *//*

*/
/*    @PostMapping(value = "/importData")
    @ResponseBody
    public ResultModel dataImport3(@RequestParam("file") MultipartFile file,String className,String bareTableName){

        try {
            // 防守判断
            if (StringUtils.isBlank(className) || StringUtils.isBlank(bareTableName)){
                return new ResultModel(ErrorCodeMessage.XH_000006);
            }

            // 拼接表名
            String tableName = DB_NAME.concat(".").concat(bareTableName);

            // 防守判断
            if (file.isEmpty()){
                return new ResultModel(ErrorCodeMessage.XH_000006);
            }

            // 获得原始文件名
            String originalFilename = file.getOriginalFilename();

            // 发射实例化对象
            Class<?> aClass = Class.forName(className);

            // 获取excel 文件数据
            List<Object> list = ExcelUtil.me().importDataFromExcel(aClass, file.getInputStream(), originalFilename);

            // 创建data 集合
            DataTable dataTable = new DataTableImpl();

            for (Object object: list){

                // 转为data
                Data data = JsonUtil.json2Data(JSONObject.fromObject(object,JsonUtil.getJsonConfig()), tableName, Boolean.TRUE);

                dataTable.addData(data);
            }

            // 插入数据库
            dao.saveDataTable(dataTable);

            // 返回
            return ResultModel.success();

        } catch (IOException | ClassNotFoundException e) {
            logUtil.error("数据导入出错",e);
            return new ResultModel(ErrorCodeMessage.XH_000002);
        }
    }*//*


    @PostMapping(value = "/importData")
    @ResponseBody
    public ResultModel dataImport(HttpServletRequest request, HttpServletResponse response){
        try {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            servletFileUpload.setHeaderEncoding("utf-8");
            // 得到所有上传表单对象集合
            List<FileItem> items = servletFileUpload.parseRequest(new ServletRequestContext(request));

            // 遍历表单对象集合,取出参数
            String className = "";
            String bareTableName = "";
            String fileName = "";
            FileItem fileItem = null;
            for (FileItem item : items) {
                // 取出用户提交内容
                if (item.isFormField()){        // item是否是简单的表单字段
                    if(item.getFieldName().equals("className")){
                        className = item.getString("utf-8");
                    }else if(item.getFieldName().equals("bareTableName")){
                        bareTableName = item.getString("utf-8");
                    }
                }
                else {
                        // 取出文件
                        if(item.getFieldName().equals("file")){
                            fileName = item.getName();  // 文件名
                           fileItem = item;
                        }
                    }
            }

            // 防守判断
            if (StringUtils.isBlank(className) || StringUtils.isBlank(bareTableName) || fileItem == null){
                return new ResultModel(ErrorCodeMessage.XH_000006);
            }

            // 导入excel数据
            // 发射实例化对象
            Class<?> aClass = Class.forName(className);
            // 获取excel 文件数据
            List<Object> list = ExcelUtil.me().importDataFromExcel(aClass, fileItem.getInputStream(), fileName);

            // 创建data 集合
            DataTable dataTable = new DataTableImpl();
            for (Object object: list){
                // 拼接表名
                String tableName = DB_NAME.concat(".").concat(bareTableName);
                Data data = JsonUtil.json2Data(JSONObject.fromObject(object,JsonUtil.getJsonConfig()),tableName, Boolean.TRUE);
                dataTable.addData(data);
            }
            // 插入数据库
            dao.saveDataTable(dataTable);

            // 返回
            return ResultModel.success();

        } catch (IOException | ClassNotFoundException e) {
            logUtil.error("数据导入出错",e);
            return new ResultModel(ErrorCodeMessage.XH_000002);
        } catch (FileUploadException e) {
            return new ResultModel(ErrorCodeMessage.XH_000002);
        }
    }

    */
/**
     * 导出数据测试
     *
     * @author liyang
     * @since  2019/11/18 10:43
     *//*

    @ResponseBody
    @PostMapping(value = "/excelExport")
    public ResultModel dataExport (){

        FileOutputStream fileOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream("C:\\Users\\liyang\\Desktop\\测试.xlsx");

            SysConfigControl sysConfigControl = new SysConfigControl();

            sysConfigControl.setControlID(1);
            sysConfigControl.setControlSort("普通控件");
            sysConfigControl.setControlName("单文本框");
            sysConfigControl.setControlHtml("<div><input type='text' class='layui-input' autocomplete='off'/></div>");
            sysConfigControl.setControlIcon("icon-textBox");

            List list = new ArrayList();
            list.add(sysConfigControl);

            // 导出excel文件数据
            ExcelUtil.me().exportDataToExcel(list,"sheet",fileOutputStream,"测试.xlsx");

            return ResultModel.success();
        } catch (IOException e) {
            logUtil.error("导出数据失败",e);
            return new ResultModel(ErrorCodeMessage.XH_000002);
        } finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
*/

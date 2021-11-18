package com.example.util.vo;

import com.example.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * 数据导入测试
 *
 * @author liyang
 * @since 2019/11/11 10:24
 */

@Controller
@RequestMapping(value = "/file")
public class DataImportExportController {

    private Logger log = LoggerFactory.getLogger(DataImportExportController.class);


    /**
     * excel 导入数据测试
     * @param file  excel文件
     * @param className 全类名
     * @author liyang
     * @since 2019/11/18 10:43
     */
    @PostMapping(value = "/importData")
    @ResponseBody
    public Map<String,String> dataImport3(@RequestParam("file") MultipartFile file, String className) {
        Map<String,String> result = new HashMap<>();
        try {
            // 防守判断
            if (StringUtils.isBlank(className) ) {
                result.put("code","XH_000006");result.put("msg","参数为null");
                return result;
            }


            // 防守判断
            if (file.isEmpty()) {
                result.put("code","XH_000006");result.put("msg","excel文件为null");
                return result;
            }

            // 获得原始文件名
            String originalFilename = file.getOriginalFilename();

            // 发射实例化对象
            Class<?> aClass = Class.forName(className);

            // 获取excel 文件数据
            List<?> list = ExcelUtil.me().importDataFromExcel(aClass, file.getInputStream(), originalFilename);

            for (Object object : list) {

                // TODO 插入数据库;

            }

            // 返回
            result.put("code","200");result.put("msg","成功");
            return result;

        } catch (IOException | ClassNotFoundException e) {
            log.error("数据导入出错", e);
            result.put("code","000006");result.put("msg","导入Excel异常");
            return result;
        }
    }


    @PostMapping(value = "/importData")
    @ResponseBody
    public Map<String,String> dataImport(HttpServletRequest request, HttpServletResponse response) {
        Map<String,String> result = new HashMap<>();
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
                if (item.isFormField()) {        // item是否是简单的表单字段
                    if (item.getFieldName().equals("className")) {
                        className = item.getString("utf-8");
                    } else if (item.getFieldName().equals("bareTableName")) {
                        bareTableName = item.getString("utf-8");
                    }
                } else {
                    // 取出文件
                    if (item.getFieldName().equals("file")) {
                        fileName = item.getName();  // 文件名
                        fileItem = item;
                    }
                }
            }

            // 防守判断
            if (StringUtils.isBlank(className) || StringUtils.isBlank(bareTableName) || fileItem == null) {
                result.put("code","XH_000006");result.put("msg","参数为null");
                return result;
            }

            // 导入excel数据
            // 发射实例化对象
            Class<?> aClass = Class.forName(className);
            // 获取excel 文件数据
            List<?> list = ExcelUtil.me().importDataFromExcel(aClass, fileItem.getInputStream(), fileName);


            for (Object object : list) {
                // TODO 插入数据库;
            }


            // 返回
            result.put("code","200");result.put("msg","成功");
            return result;

        } catch (IOException | ClassNotFoundException e) {
            log.error("数据导入出错", e);
            result.put("code","000002");result.put("msg","导入Excel异常");
            return result;
        }
    }


    /**
     * 导出数据测试
     *
     * @author liyang
     * @since 2019/11/18 10:43
     */

    @ResponseBody
    @PostMapping(value = "/excelExport")
    public Map<String,String> dataExport() {

        Map<String,String> result = new HashMap<>();

        FileOutputStream fileOutputStream = null;

        try {

            fileOutputStream = new FileOutputStream("C:\\Users\\liyang\\Desktop\\测试.xlsx");

            SysFieldVo sysFieldVo = new SysFieldVo();

            sysFieldVo.setDataType("String");
            sysFieldVo.setDescribe("姓名");
            sysFieldVo.setField("name");
            sysFieldVo.setLen(32);
            sysFieldVo.setPot(0);

            List list = new ArrayList();
            list.add(sysFieldVo);

            // 导出excel文件数据
            ExcelUtil.me().exportDataToExcel(list, "sheet", fileOutputStream, "测试.xlsx");

            // 返回
            result.put("code","200");result.put("msg","成功");
            return result;
        } catch (IOException e) {
            log.error("导出数据失败", e);
            result.put("code","000002");result.put("msg","导出数据到Excel异常");
            return result;
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


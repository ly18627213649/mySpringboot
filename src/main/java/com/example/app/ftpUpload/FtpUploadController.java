package com.example.app.ftpUpload;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FtpUploadController {

    /**
     * 上传文件到FTP, 线程上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/uploadFtp")
    public Map<String,String> uploadFileToFTP(@RequestParam("file")MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis()+".mp4";
        String ip = "192.168.145.1";
        String user = "liyang";
        String pwd = "123456";
        String port = "21";

        Ftpupload ftpupload = new Ftpupload("/",fileName,file.getInputStream(),ip,user,pwd,port);
        ftpupload.start();
        System.out.println(ftpupload.path+fileName);

        Map<String,String> map = new HashMap<>();
        map.put("msg","成功");
        map.put("code","200");
        return map;
    }
}

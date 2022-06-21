package com.example.yw.ftpUpload;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * FTP操作辅助类.
 *
 * @author ZCY
 * @version 1.1, 2017/07/06
 */
public class FTPUtil {

    private static final Logger log = LoggerFactory.getLogger(FTPUtil.class);

    private FTPClient ftp = null;
    private String ipAddr;
    private String port;
    private String userName;
    private String password;

    public FTPUtil() {
    }

    /**
     * 初始化FTP参数.
     *
     * @param ipAddr   IP地址
     * @param port     FTP端口号
     * @param userName 登录用户名
     * @param password 登录密码
     */
    public FTPUtil(String ipAddr, String port, String userName, String password) {
        ftp = new FTPClient();
        this.ipAddr = ipAddr;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 设置文件类型(0:文本文件/1:二进制文件).
     *
     * @param fileType 文件类型
     */
    public void setFileType(int fileType) {
        try {
            if (fileType == 0) {
                this.ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
            } else if (fileType == 1) {
                this.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            }
        } catch (IOException e) {
            log.error("设置文件类型错误:" + e.getMessage());
        } catch (Exception e) {
            log.error("ftp异常：" + e.getMessage());
        }
    }

    /**
     * 登录FTP.
     *
     * @return 是否登录成功
     */
    public boolean login() {
        int reply;
        try {
            ftp.connect(this.ipAddr, Integer.parseInt(this.port));
            ftp.login(this.userName, this.password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
        } catch (IOException e) {
            log.error("FTP登录失败:" + e.getMessage());
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    return false;
                }
            }
        }
        ftp.setControlEncoding("GBK");
        this.setFileType(1);
        log.info("成功登录服务器:" + this.ipAddr);
        return true;
    }

    /**
     * 移动至指定目录，如果目录不存在则自动创建.
     *
     * @param path 指定路径
     */
    private boolean moveToDirectory(String path) {
        try {
            boolean flag = false;
            log.info(this.ftp.printWorkingDirectory());
            StringTokenizer stk = new StringTokenizer(path, "/");
            String token;
            while (stk.hasMoreTokens()) {
                token = stk.nextToken();
                log.info("开始定位目录:" + token);
                flag = ftp.changeWorkingDirectory(token);
                if (flag == false) {
                    flag = ftp.makeDirectory(token);
                    if (flag == false) {
                        log.error("无法创建目录:" + token);
                        return false;
                    } else if (flag == true) {
                        flag = ftp.changeWorkingDirectory(token);
                        if (flag == false) {
                            log.error("无法移动至:" + token);
                            return false;
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.error("移动至指定目录失败：" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 上传文件到指定目录.
     *
     * @param path     指定路径
     * @param fileName 文件名称
     * @param stream   文件输入流
     * @return 是否上传成功
     */
    public boolean uploadFile(String path, String fileName, InputStream stream) {
        boolean flag = false;
        flag = this.moveToDirectory(path);
        if (flag == false) {
            return false;
        } else {
            try {
                flag = this.ftp.storeFile(fileName, stream);
                log.info("上传文件:" + fileName + " --> " + String.valueOf(flag));
            } catch (IOException e) {
                e.printStackTrace();
                log.error("上传文件失败:" + e.getMessage());
                return false;
            } finally {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("输入流关闭失败:" + e.getMessage());
                }
            }
        }
        return flag;
    }

    /**
     * 下载文件到输出流.
     *
     * @param path     指定路径
     * @param fileName 文件名称
     * @param stream   输出流
     * @return 是否下载成功
     */
    public boolean downloadFile(String path, String fileName, OutputStream stream) {
        boolean flag = this.moveToDirectory(path);
        if (flag == false) {
            return false;
        } else {
            try {
                flag = this.ftp.retrieveFile(fileName, stream);
                log.info("下载文件:" + fileName + " --> " + String.valueOf(flag));
            } catch (IOException e) {
                log.error("下载文件失败：" + e.getMessage());
                return false;
            } finally {
                try {
                    stream.flush();
                    stream.close();
                } catch (IOException e) {
                    log.error("输出流关闭失败:" + e.getMessage());
                }
            }
        }
        return flag;
    }


    public InputStream downloadFiles(String path, String fileName, OutputStream stream) {
        this.moveToDirectory(path);
        InputStream it = null;
        try {
            it = this.ftp.retrieveFileStream(new String(fileName.getBytes("gbk"), "ISO-8859-1"));
        } catch (IOException e) {
            log.error("输出流关闭失败:" + e.getMessage());
        }
        return it;
    }

    /**
     * 登出FTP服务器.
     *
     * @return 是否成功
     */
    public boolean loginout() {
        try {
            ftp.disconnect();
            log.info("成功登出FTP服务器:" + this.ipAddr);
            this.ftp = null;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据指定文件类型获取一个随机文件名
     *
     * @param fileType 文件后缀
     * @return 文件名称
     */
    public String getRandomFileName(String fileType) {
        SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmss");

        return sdf.format(new Date()) + String.valueOf((int) (Math.random() * 10000)) + "." + fileType;
    }

    /**
     * 获取存储路径.
     *
     * @param type 业务类型，参照DEF_FTP_DEFINE.ACCESSTYPE定义
     * @param unit 所属单位
     * @return 存储路径
     */
    public String getAccessPath(String type, String unit) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        return type + "/" + unit + "/" + sdf.format(new Date());
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

        String dwdm = "444222121";
        dwdm = dwdm.substring(7, dwdm.length() - 1);

        String s2 = stringToUnicode("测试");
        String s3 = new String(s2.getBytes("GBK"), "UTF-8");
        String s = URLDecoderString(s2);
        String name = java.net.URLEncoder.encode("测试", "UTF-8");
        System.out.println(name);

        FTPUtil ftp = new FTPUtil("192.168.1.6", "21", "vanda", "vanda");
        System.out.println("DD:" + ftp.getAccessPath("THLY", "440300121"));
        System.out.println("FN:" + ftp.getRandomFileName("mp3"));

        ftp.login();

        String c = "/";
        String[] aa = "aaa/bbb/ccc".split("/");

        //THLY/440300121/202103/011353143188.mp3
//		boolean flag = ftp.login();
//		if(flag == true){
//			InputStream stream = null;
//			try{
//				stream = new BufferedInputStream(new FileInputStream(new File("C:/123.ppt")));
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			if(stream != null){
//			ftp.setFileType(1);	
//			try {
//				flag = ftp.downloadFile("THLY/440300112/20101119", "123.ppt", new FileOutputStream(new File("C:ttt.ppt")));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//				
////			}			
//		}
//		ftp.loginout();
    }

    public static String stringToUnicode(String str) {
        StringBuffer sb = new StringBuffer();
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            sb.append("\\u" + Integer.toHexString(c[i]));
        }
        return sb.toString();
    }

}
package com.example.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 写入文件
 *
 * @author liyang
 * @since 2019/11/19 17:51
 */
public class WriteJs {

    protected static Logger logger = LoggerFactory.getLogger(WriteJs.class);

    /**
     * 创建文件夹
     */
    public static File createDir(String rootPath, String path) throws Exception {

        File file = new File(rootPath);
        if (!file.exists()) {
            file.mkdir();
        }

        String dirs[] = path.split("/");
        for (int i = 0; i < dirs.length; i++) {
            if (dirs[i] != null && !"".equals(dirs[i].trim())) {
                file = getDir(file, dirs[i]);
            }
        }
        return file;
    }

    /**
     * 创建目录  
     *
     * @param parentPath  
     * @param dirName     
     */
    public static File getDir(File parentPath, String dirName) throws IOException {
        File dir = new File(parentPath, dirName);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir;
    }

    /**
     * 创建文件
     *
     * @param dirPath   
     * @param fileName
     */
    public static File getFile(File dirPath, String fileName) throws IOException {
        File file = new File(dirPath, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 写入js文件
     *
     * @author liyang
     * @since 2019/11/19 16:27
     */
    public static void writeJs(String root, String path, String name, String html) throws IOException, Exception {
        // 创建目录
        File filePath = createDir(root, path.trim());
        // 创建文件
        File file = getFile(filePath, name.trim() + ".js");
        // 写文件
        writeFile(file, html);
    }

    /**
     * 写入文件
     *
     * @author liyang
     * @since 2019/11/19 16:27
     */
    public static void writeFile(File file, String html) throws Exception {

        if (StringUtils.isBlank(html)) {
            return;
        }

        // 参数有效性检测
        if (file == null || !file.isFile()) {
            throw new Exception("public static void appendFile(File tar) parameters error!");
        }

        // 判断文件是否可写
        if (!file.canWrite()) {
            throw new Exception(file.toString() + " write prohibited! ");
        }

        String js = html;

        // 写文件
        OutputStreamWriter out = null;
        try {
            out = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
            out.write(js);
            out.flush();
        } catch (IOException e) {
            logger.error("写入文件失败!", e);
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}

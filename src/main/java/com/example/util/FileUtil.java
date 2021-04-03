package com.example.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * 文件工具类
 *
 * @author liyang
 * @since 2019/11/19 17:33
 */
public class FileUtil {

    // 日志
    protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 清空文件夹
     *
     * @param path 文件夹路径
     * @author liyang
     * @since 2019/11/6 10:36
     */
    public static void deleteDir(String path) {

        // 取得文件夹
        File file = new File(path);

        // 判断是否待删除目录是否存在
        if (!file.exists()) {
            return;
        }

        //取得当前目录下所有文件和文件夹
        String[] content = file.list();

        // 判断
        if (content == null || content.length <= 0) {
            return;
        }

        // 进行遍历
        for (String name : content) {

            // 取得文件
            File temp = new File(path, name);

            // 判断是否是目录
            if (temp.isDirectory()) {

                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
            }

            //删除空目录
            temp.delete();
        }
    }

    /**
     * 写入文件
     *
     * @param filePath xxx/xxx.jsp
     * @param content  文件内容
     * @throws FileNotFoundException
     * @author liyang
     * @since 2019/11/6 10:36
     */
    public static void writer(String filePath, String content) throws FileNotFoundException {

        // 防守判断
        if (StringUtils.isBlank(filePath)) {
            return;
        }

        // 读取文件
        File file = new File(filePath);

        // 判断父路径是否存在
        if (!file.getParentFile().exists()) {

            // 不存在，则创建
            boolean mkdirs = file.getParentFile().mkdirs();
        }

        // 初始化文件对象
        PrintStream printStream = null;

        try {

            // 写入文件, 如果为true,则写入文件末尾而不是开头
            printStream = new PrintStream(new FileOutputStream(file, true));

            // 写入内容
            printStream.println(content);
        } catch (Exception e) {
            logger.error(String.format("创建printStream对象时出错：%s", e), e);
            throw e;
        } finally {
            if (printStream != null) {
                printStream.close();
            }
        }
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName 要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return Boolean.FALSE;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {

        File file = new File(fileName);

        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {

        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }

        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            System.out.println("删除目录失败：" + dir + "不存在!");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = FileUtil.deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (!flag) {
            System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        // 删除一个目录
        String dir = "D:/home/web/upload/upload/files";
        FileUtil.deleteDirectory(dir);
    }
}

package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class FileUtil {

    private static Logger log = LoggerFactory.getLogger(FileUtil.class);
    private static final String DEFAULT_CHARSET = "UTF-8";

    public FileUtil() {
    }

    public static boolean copyFile(String sourceFile, String targetFile) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else if (CommUtil.null2String(targetFile).equals("")) {
            throw new IllegalArgumentException("传入的targetFile不允许为空！");
        } else {
            return copyFile(new File(sourceFile), new File(targetFile));
        }
    }

    public static boolean copyFile(String sourceFile, File targetFile) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else if (CommUtil.null2String(targetFile.getAbsolutePath()).equals("")) {
            throw new IllegalArgumentException("传入的targetFile不允许为空！");
        } else {
            return copyFile(new File(sourceFile), targetFile);
        }
    }

    public static boolean copyFile(File sourceFile, String targetFile) {
        if (sourceFile == null) {
            throw new RuntimeException("文件复制时出错：源文件不存在");
        } else if (CommUtil.null2String(targetFile).equals("")) {
            throw new IllegalArgumentException("传入的targetFile不允许为空！");
        } else {
            return copyFile(sourceFile, new File(targetFile));
        }
    }

    public static boolean copyFile(File sourceFile, File targetFile) {
        boolean returnValue = false;
        if (sourceFile == null) {
            throw new RuntimeException("文件复制时出错：源文件不存在");
        } else if (targetFile == null) {
            throw new IllegalArgumentException("传入的targetFile不允许为空！");
        } else {
            FileChannel sourceChannel = null;
            FileChannel targetChannel = null;

            try {
                sourceChannel = (new FileInputStream(sourceFile)).getChannel();
                targetChannel = (new FileOutputStream(targetFile)).getChannel();
                targetChannel.transferFrom(sourceChannel, 0L, sourceChannel.size());
                returnValue = Boolean.TRUE;
            } catch (IOException var18) {
                log.error(String.format("复制文件时出错：%s", var18));
            } finally {
                if (sourceChannel != null) {
                    try {
                        sourceChannel.close();
                    } catch (IOException var17) {
                        log.error(String.format("关闭sourceChannel时出错：%s", var17));
                    }
                }

                if (targetChannel != null) {
                    try {
                        targetChannel.close();
                    } catch (IOException var16) {
                        log.error(String.format("关闭targetChannel时出错：%s", var16));
                    }
                }

            }

            return returnValue;
        }
    }

    public static String getFileExt(String sourceFile) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            return getFileExt(new File(sourceFile));
        }
    }

    public static String getFileExt(File sourceFile) {
        String fileName = sourceFile.getName();
        String returnValue = fileName.substring(fileName.lastIndexOf(".") + 1);
        return returnValue;
    }

    public static String replaceFileExt(String sourceFile, String suffix) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            return replaceFileExt(new File(sourceFile), suffix);
        }
    }

    public static String replaceFileExt(File sourceFile, String suffix) {
        String fileName = sourceFile.getName();
        String newFileName = fileName.substring(0, fileName.lastIndexOf(".") + 1);
        String returnValue = newFileName.concat(suffix);
        return returnValue;
    }

    public static String getFileMD5(String sourceFile) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            return getFileMD5(new File(sourceFile));
        }
    }

    public static String getFileMD5(File file) {
        String returnValue = "";
        FileInputStream fileInputStream = null;

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];

            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                md5.update(buffer, 0, length);
            }

            returnValue = (new BigInteger(1, md5.digest())).toString(16);
        } catch (IOException var16) {
            log.error(String.format("取得文件MD5值时出错：%s", var16));
        } catch (NoSuchAlgorithmException var17) {
            log.error(String.format("取得加密类型时出错：%s", var17));
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException var15) {
                log.error(String.format("关闭输入流时出错：%s", var15));
            }

        }

        return returnValue;
    }

    public static int countLines(String sourceFile) {
        return countLines(sourceFile, "UTF-8");
    }

    public static int countLines(String sourceFile, String encoding) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            return countLines(new File(sourceFile), encoding);
        }
    }

    public static int countLines(File file, String encoding) {
        int returnValue = 0;
        if (!file.exists()) {
            throw new RuntimeException("统计文件行数时出错：文件不存在");
        } else {
            FileInputStream fileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            try {
                fileInputStream = new FileInputStream(file);
                inputStreamReader = new InputStreamReader(fileInputStream, encoding);
                bufferedReader = new BufferedReader(inputStreamReader);

                for (LineNumberReader lnr = new LineNumberReader(bufferedReader); lnr.readLine() != null; ++returnValue) {
                }
            } catch (IOException var23) {
                log.error(String.format("对文件进行行数统计时出错：%s", var23));
            } finally {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException var22) {
                        log.error(String.format("关闭文件输入流时出错：%s", var22));
                    }
                }

                if (inputStreamReader != null) {
                    try {
                        inputStreamReader.close();
                    } catch (IOException var21) {
                        log.error(String.format("关闭流读取对象时出错：%s", var21));
                    }
                }

                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException var20) {
                        log.error(String.format("关闭bufferedReader时出错：%s", var20));
                    }
                }

            }

            return returnValue;
        }
    }

    public static List<String> lines(Path path, String encoding) {
        List<String> returnValue = null;
        if (path == null) {
            throw new IllegalArgumentException("传入的path参数不允许为空！");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);

            try {
                returnValue = Files.readAllLines(path, Charset.forName(encoding));
            } catch (IOException var4) {
                log.error(String.format("以列表的方式获取文件的所有行时出错：%s", var4));
            }

            return returnValue;
        }
    }

    public static List<String> lines(Path path) {
        if (path == null) {
            throw new IllegalArgumentException("传入的path参数不允许为空！");
        } else {
            List<String> returnValue = lines(path, "UTF-8");
            return returnValue;
        }
    }

    public static List<String> lines(File file, String encoding) {
        if (file == null) {
            throw new IllegalArgumentException("传入的file参数不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("以列表的方式获取文件的所有行时出错：文件不存在");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            Path path = file.toPath();
            List<String> returnValue = lines(path, encoding);
            return returnValue;
        }
    }

    public static List<String> lines(File file) {
        if (file == null) {
            throw new IllegalArgumentException("传入的file参数不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("以列表的方式获取文件的所有行时出错：文件不存在");
        } else {
            Path path = file.toPath();
            List<String> returnValue = lines(path, "UTF-8");
            return returnValue;
        }
    }

    public static List<String> lines(String filePath, String encoding) {
        if (CommUtil.null2String(filePath).equals("")) {
            throw new IllegalArgumentException("入参filePath不允许为空！");
        } else {
            return lines(new File(filePath), encoding);
        }
    }

    public static List<String> lines(String filePath) {
        if (CommUtil.null2String(filePath).equals("")) {
            throw new IllegalArgumentException("入参filePath不允许为空！");
        } else {
            return lines(new File(filePath), "UTF-8");
        }
    }

    public static boolean appendLine(String sourceFile, String content) {
        return appendLine(sourceFile, content, "UTF-8");
    }

    public static boolean appendLine(String sourceFile, String content, String encoding) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            return appendLine(new File(sourceFile), content, encoding);
        }
    }

    public static boolean appendLine(File file, String content) {
        return appendLine(file, content, "UTF-8");
    }

    public static boolean appendLine(File file, String content, String encoding) {
        boolean returnValue = false;
        boolean createFlag = false;
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else {
            if (!file.exists()) {
                createFiles(file.getAbsolutePath());
                createFlag = Boolean.TRUE;
            }

            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            String lineSeparator = System.getProperty("line.separator", "\n");
            RandomAccessFile randomFile = null;

            try {
                randomFile = new RandomAccessFile(file, "rw");
                long fileLength = randomFile.length();
                randomFile.seek(fileLength);
                if (createFlag) {
                    randomFile.write(content.getBytes(encoding));
                } else {
                    randomFile.write((lineSeparator + content).getBytes(encoding));
                }

                returnValue = Boolean.TRUE;
            } catch (IOException var17) {
                log.error(String.format("在文件末尾追加内容时出错：%s", var17));
            } finally {
                if (randomFile != null) {
                    try {
                        randomFile.close();
                    } catch (IOException var16) {
                        log.error(String.format("关闭RandomFile对象时出错：%s", var16));
                    }
                }

            }

            return returnValue;
        }
    }

    public static boolean write(String sourceFile, String content) {
        return write(sourceFile, content, "UTF-8");
    }

    public static boolean write(String sourceFile, String content, String encoding) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            return write(new File(sourceFile), content, encoding);
        }
    }

    public static boolean write(File file, String content) {
        return write(file, content, "UTF-8");
    }

    public static boolean write(File file, String content, String encoding) {
        boolean returnValue = false;
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else {
            if (!file.exists()) {
                String absolutePath = file.getAbsolutePath();
                createFiles(absolutePath);
            }

            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            RandomAccessFile randomFile = null;

            try {
                randomFile = new RandomAccessFile(file, "rw");
                randomFile.write(content.getBytes(encoding));
                returnValue = Boolean.TRUE;
            } catch (IOException var14) {
                log.error(String.format("将字符串以指定的编码写入到文件中时出错：%s", var14));
            } finally {
                if (randomFile != null) {
                    try {
                        randomFile.close();
                    } catch (IOException var13) {
                        log.error(String.format("关闭RandomFile对象时出错：%s", var13));
                    }
                }

            }

            return returnValue;
        }
    }

    public static boolean writeAppend(String sourceFile, String content) {
        return writeAppend(sourceFile, content, "UTF-8");
    }

    public static boolean writeAppend(String sourceFile, String content, String encoding) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            return writeAppend(new File(sourceFile), content, encoding);
        }
    }

    public static boolean writeAppend(File file, String content) {
        return writeAppend(file, content, "UTF-8");
    }

    public static boolean writeAppend(File file, String content, String encoding) {
        boolean returnValue = false;
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else {
            if (!file.exists()) {
                String absolutePath = file.getAbsolutePath();
                createFiles(absolutePath);
            }

            encoding = CommUtil.null2String(encoding).equals("") ? "UTF-8" : CommUtil.null2String(encoding);
            RandomAccessFile randomFile = null;

            try {
                randomFile = new RandomAccessFile(file, "rw");
                long fileLength = randomFile.length();
                randomFile.seek(fileLength);
                randomFile.write(content.getBytes(encoding));
                returnValue = Boolean.TRUE;
            } catch (IOException var15) {
                log.error(String.format("将字符串以追加的方式以指定的编码写入到文件中时出错：%s", var15));
            } finally {
                if (randomFile != null) {
                    try {
                        randomFile.close();
                    } catch (IOException var14) {
                        log.error(String.format("关闭RandomFile对象时出错：%s", var14));
                    }
                }

            }

            return returnValue;
        }
    }

    public static String getFileMimeType(String file) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String returnValue = fileNameMap.getContentTypeFor(file);
        return returnValue;
    }

    public static String getFileMimeType(File file) {
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else if (!file.exists()) {
            throw new RuntimeException("取得文件的Mime类型时出错：文件不存在");
        } else {
            String returnValue = getFileMimeType(file.getAbsoluteFile());
            return returnValue;
        }
    }

    public static Date getFileModifyTime(String sourceFile) {
        if (CommUtil.null2String(sourceFile).equals("")) {
            throw new IllegalArgumentException("传入的sourceFile不允许为空！");
        } else {
            return getFileModifyTime(new File(sourceFile));
        }
    }

    public static Date getFileModifyTime(File file) {
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else if (!file.exists()) {
            throw new RuntimeException("获取文件最后的修改时间时出错：文件不存在");
        } else {
            return DateUtil.parseByDateTime(file.lastModified());
        }
    }

    public static boolean createPaths(String paths) {
        if (CommUtil.null2String(paths).equals("")) {
            throw new IllegalArgumentException("传入的paths不允许为空！");
        } else {
            File dir = new File(paths);
            return dir.exists() ? true : dir.mkdirs();
        }
    }

    public static boolean createFiles(String filePath) {
        boolean returnValue = false;
        if (CommUtil.null2String(filePath).equals("")) {
            throw new IllegalArgumentException("传入的filePath不允许为空！");
        } else {
            File file = new File(filePath);
            File dir = file.getParentFile();
            if (!dir.exists()) {
                boolean mkdirs = createPaths(dir.getAbsolutePath());
                if (!mkdirs) {
                    throw new RuntimeException("创建文件支持多级目录时出错：创建目录出错");
                }
            }

            try {
                returnValue = file.createNewFile();
            } catch (IOException var5) {
                log.error(String.format("创建文件时出错：%s", var5));
            }

            return returnValue;
        }
    }

    public static boolean deleteFile(String deleteFile) {
        if (CommUtil.null2String(deleteFile).equals("")) {
            throw new IllegalArgumentException("传入的deleteFile不允许为空！");
        } else {
            return deleteFile(new File(deleteFile));
        }
    }

    public static boolean deleteFile(File file) {
        if (file == null) {
            throw new RuntimeException("入参file不允许为null");
        } else if (!file.exists()) {
            log.warn(String.format("删除一个文件时，文件未存在，可忽略此提醒！"));
            return true;
        } else if (!file.isFile()) {
            throw new RuntimeException("需要删除的对象非文件，无法进行删除操作！");
        } else {
            return file.delete();
        }
    }

    public static boolean deleteDir(String dirPath) {
        if (CommUtil.null2String(dirPath).equals("")) {
            throw new IllegalArgumentException("传入的删除目录路径不允许为空！");
        } else {
            return deleteDir(new File(dirPath));
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir == null) {
            throw new IllegalArgumentException("入参dir不允许为null");
        } else if (!dir.exists()) {
            log.warn(String.format("删除一个文件夹时，文件夹未存在，可忽略此提醒！"));
            return true;
        } else if (!dir.isDirectory()) {
            throw new RuntimeException("传入的file并非一个目录，无法进行删除操作");
        } else {
            List<File> files = listAllFile(dir);
            if (files.isEmpty()) {
                return dir.delete();
            } else {
                Iterator var2 = files.iterator();

                while (var2.hasNext()) {
                    File file = (File) var2.next();
                    if (file.isDirectory()) {
                        deleteDir(file);
                    } else {
                        deleteFile(file);
                    }
                }

                return dir.delete();
            }
        }
    }

    public static void copyDir(String sourcePath, String targetPath) {
        if (CommUtil.null2String(sourcePath).equals("")) {
            throw new IllegalArgumentException("传入的sourcePath不允许为空！");
        } else if (CommUtil.null2String(targetPath).equals("")) {
            throw new IllegalArgumentException("传入的targetPath不允许为空！");
        } else {
            File file = new File(sourcePath);
            copyDir(file, targetPath);
        }
    }

    public static void copyDir(File filePath, String targetPath) {
        copyDir(filePath, new File(targetPath));
    }

    public static void copyDir(File sourcePath, File targetPath) {
        if (sourcePath == null) {
            throw new IllegalArgumentException("入参sourcePath不允许为空！");
        } else if (!sourcePath.exists()) {
            throw new RuntimeException("复制目录时出错：源目录不存在");
        } else if (targetPath == null) {
            throw new IllegalArgumentException("入参targetPath不允许为空！");
        } else {
            if (!targetPath.exists()) {
                createPaths(targetPath.getAbsolutePath());
            }

            File[] files = sourcePath.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var3 = files;
                int var4 = files.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File file = var3[var5];
                    String path = file.getName();
                    if (file.isDirectory()) {
                        copyDir(file, targetPath + "/" + path);
                    } else {
                        copyFile(file, targetPath + "/" + path);
                    }
                }

            }
        }
    }

    public static List<File> listFileWithoutChild(String path) {
        if (CommUtil.null2String(path).equals("")) {
            throw new IllegalArgumentException("入参path不允许为空！");
        } else {
            File file = new File(path);
            return listFile(file, Boolean.FALSE);
        }
    }

    public static List<File> listFileWithoutChild(File file) {
        if (file == null) {
            throw new IllegalArgumentException("入参file不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("取得指定路径下的全部文件（不包含下级目录）时出错：目录不存在");
        } else {
            return listFile(file, Boolean.FALSE);
        }
    }

    public static List<File> listFile(String path) {
        if (CommUtil.null2String(path).equals("")) {
            throw new IllegalArgumentException("入参path不允许为空！");
        } else {
            File file = new File(path);
            return listFile(file, Boolean.TRUE);
        }
    }

    public static List<File> listFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("入参file不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("取得指定路径下的全部文件（不包含下级目录）时出错：目录不存在");
        } else {
            return listFile(file, Boolean.TRUE);
        }
    }

    public static List<File> listFile(File file, boolean child) {
        List<File> list = new ArrayList();
        if (file == null) {
            throw new IllegalArgumentException("入参file不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("罗列指定路径下的全部文件时出错：目录不存在");
        } else {
            File[] files = file.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                child = CommUtil.null2Boolean(child);
                File[] var4 = files;
                int var5 = files.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    File _file = var4[var6];
                    if (child && _file.isDirectory()) {
                        list.addAll(listFile(_file));
                    } else {
                        list.add(_file);
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public static List<File> listAllFile(File file) {
        List<File> list = new ArrayList();
        if (file == null) {
            throw new IllegalArgumentException("入参file不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("罗列指定路径下的全部文件时出错：目录不存在");
        } else {
            File[] files = file.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var3 = files;
                int var4 = files.length;

                for (int var5 = 0; var5 < var4; ++var5) {
                    File _file = var3[var5];
                    list.add(_file);
                    if (_file.isDirectory()) {
                        list.addAll(listAllFile(_file));
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public static List<File> listFileFilter(File file, FilenameFilter filter) {
        List<File> list = new ArrayList();
        if (file == null) {
            throw new IllegalArgumentException("入参file不允许为空！");
        } else if (!file.exists()) {
            throw new RuntimeException("罗列指定路径下的指定条件文件时出错：目录不存在");
        } else {
            File[] files = file.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var4 = files;
                int var5 = files.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    File _file = var4[var6];
                    if (_file.isDirectory()) {
                        list.addAll(listFileFilter(_file, filter));
                    } else if (filter.accept(_file.getParentFile(), _file.getName())) {
                        list.add(_file);
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public static List<File> listFileFilter(File dirPath, String suffix) {
        List<File> list = new ArrayList();
        if (dirPath == null) {
            throw new IllegalArgumentException("入参dirPath不允许为空！");
        } else if (!dirPath.exists()) {
            throw new RuntimeException("获取指定目录下的特点文件,通过后缀名过滤时出错：目录不存在");
        } else {
            File[] files = dirPath.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var4 = files;
                int var5 = files.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    File file = var4[var6];
                    if (file.isDirectory()) {
                        list.addAll(listFileFilter(file, suffix));
                    } else {
                        String fileExt = getFileExt(file);
                        if (fileExt.equalsIgnoreCase(suffix)) {
                            list.add(file);
                        }
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public static List<File> searchFile(File dirPath, String fileName) {
        List<File> list = new ArrayList();
        if (dirPath == null) {
            throw new IllegalArgumentException("入参dirPath不允许为空！");
        } else if (!dirPath.exists()) {
            throw new RuntimeException("获取指定目录下的特点文件,通过后缀名过滤时出错：目录不存在");
        } else {
            File[] files = dirPath.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var4 = files;
                int var5 = files.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    File file = var4[var6];
                    if (file.isDirectory()) {
                        list.addAll(searchFile(file, fileName));
                    } else {
                        String name = file.getName();
                        if (name.equals(fileName)) {
                            list.add(file);
                        }
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public static List<File> searchFileReg(File dirPath, String reg) {
        List<File> list = new ArrayList();
        if (dirPath == null) {
            throw new IllegalArgumentException("入参dirPath不允许为空！");
        } else if (!dirPath.exists()) {
            throw new RuntimeException("获取指定目录下的特点文件,通过后缀名过滤时出错：目录不存在");
        } else {
            File[] files = dirPath.listFiles();
            if (!Objects.isNull(files) && files.length > 0) {
                File[] var4 = files;
                int var5 = files.length;

                for (int var6 = 0; var6 < var5; ++var6) {
                    File file = var4[var6];
                    if (file.isDirectory()) {
                        list.addAll(searchFile(file, reg));
                    } else {
                        String name = file.getName();
                        if (CommUtil.isMatch(name, reg)) {
                            list.add(file);
                        }
                    }
                }

                return list;
            } else {
                return list;
            }
        }
    }

    public boolean saveFile(InputStream input, String targetPath) {
        FileOutputStream downloadFile = null;

        boolean returnValue;
        try {
            byte[] bytes = new byte[8192];
            downloadFile = new FileOutputStream(targetPath);

            int index;
            while ((index = input.read(bytes)) != -1) {
                downloadFile.write(bytes, 0, index);
                downloadFile.flush();
            }

            returnValue = true;
        } catch (IOException var19) {
            log.error(String.format("保存文件时出错：%s", var19));
            returnValue = false;
        } finally {
            if (downloadFile != null) {
                try {
                    downloadFile.close();
                } catch (IOException var18) {
                    log.error(String.format("关闭文件流时出错：%s", var18));
                }
            }

            if (input != null) {
                try {
                    input.close();
                } catch (IOException var17) {
                    log.error(String.format("关闭输入流时出错：%s", var17));
                }
            }

        }

        return returnValue;
    }
}

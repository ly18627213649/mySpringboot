package com.example.xhlang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {

    private static Logger log = LoggerFactory.getLogger(ImageUtil.class);

    public ImageUtil() {
    }

    public static BufferedImage imageMagnifyRatio(BufferedImage originalImage, Integer withdRatio, Integer heightRatio) {
        if (withdRatio <= 0) {
            withdRatio = 1;
        }

        if (heightRatio <= 0) {
            heightRatio = 1;
        }

        int width = originalImage.getWidth() * withdRatio;
        int height = originalImage.getHeight() * heightRatio;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver) null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage imageShrinkRatio(BufferedImage originalImage, Integer withdRatio, Integer heightRatio) {
        if (withdRatio <= 0) {
            withdRatio = 1;
        }

        if (heightRatio <= 0) {
            heightRatio = 1;
        }

        int width = originalImage.getWidth() / withdRatio;
        int height = originalImage.getHeight() / heightRatio;
        BufferedImage newImage = new BufferedImage(width, height, originalImage.getType());
        Graphics g = newImage.getGraphics();
        g.drawImage(originalImage, 0, 0, width, height, (ImageObserver) null);
        g.dispose();
        return newImage;
    }

    public static void reduceImageByRatio(String srcImagePath, String toImagePath, int widthRatio, int heightRatio) throws IOException {
        File file = new File(srcImagePath);
        enlargement(toImagePath, widthRatio, heightRatio, file);
    }

    public static void reduceImageEqualProportion(String srcImagePath, String toImagePath, int ratio) throws IOException {
        File file = new File(srcImagePath);
        enlargement(toImagePath, ratio, ratio, file);
    }

    public static void enlargementImageByRatio(String srcImagePath, String toImagePath, int widthRatio, int heightRatio) throws IOException {
        File file = new File(srcImagePath);
        enlargement(toImagePath, widthRatio, heightRatio, file);
    }

    public static void enlargementImageEqualProportion(String srcImagePath, String toImagePath, int ratio) throws IOException {
        File file = new File(srcImagePath);
        enlargement(toImagePath, ratio, ratio, file);
    }

    private static void enlargement(String toImagePath, int widthRatio, int heightRatio, File file) {
        try {
            FileOutputStream out = new FileOutputStream(toImagePath);
            Throwable var5 = null;

            try {
                String prefix = FileUtil.getFileExt(file);
                BufferedImage srcBuffer = ImageIO.read(file);
                BufferedImage imageBuffer = imageMagnifyRatio(srcBuffer, widthRatio, heightRatio);
                ImageIO.write(imageBuffer, prefix, out);
            } catch (Throwable var17) {
                var5 = var17;
                throw var17;
            } finally {
                if (out != null) {
                    if (var5 != null) {
                        try {
                            out.close();
                        } catch (Throwable var16) {
                            var5.addSuppressed(var16);
                        }
                    } else {
                        out.close();
                    }
                }

            }
        } catch (Exception var19) {
            log.error(String.format("读取文件时出错：%s", var19));
        }

    }
}

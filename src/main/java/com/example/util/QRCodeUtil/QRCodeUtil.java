package com.example.util.QRCodeUtil;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class QRCodeUtil {

    // 二维码宽度
    private static Integer width = 400;
    // 二维码高度
    private static Integer height = 400;

    /**
     * 获取二维码
     *
     * @param text      二维码路径
     * @param filePath  图片路径，如"E:/qrcode/1.png"
     */
    public static String getQRCoder(String text,String filePath) throws IOException, WriterException {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        File file = new File(filePath);

        // 判断父路径是否存在
        if (!file.getParentFile().exists()) {

            // 不存在，则创建
            boolean mkdirs = file.getParentFile().mkdirs();
        }

        MatrixToImageWriter.writeToFile(bitMatrix, "png", file);

        // 返回qrcode 路径
        return filePath;
    }

    /**
     * 解析指定路径下的二维码图片
     *
     * @param filePath 二维码图片路径
     */
    public static String parseQRCode(String filePath) throws IOException, NotFoundException {

        File file = new File(filePath);

        // 二维码不存在返回null
        if (!file.exists()){
            return null;
        }

        BufferedImage image = ImageIO.read(file);
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        Binarizer binarizer = new HybridBinarizer(source);
        BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");
        MultiFormatReader formatReader = new MultiFormatReader();
        Result result = formatReader.decode(binaryBitmap, hints);

//        System.out.println("result 为：" + result.toString());
//        System.out.println("resultFormat 为：" + result.getBarcodeFormat());
//        System.out.println("resultText 为：" + result.getText());

        // 返回二维码内容
        return result.getText();
    }
}

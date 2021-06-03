package com.example.StartWithTextract.BusinessLayer.CombineImgaes;

import com.amazonaws.util.IOUtils;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ImageConcat {
    public ImageConcat() {
    }
    public ByteBuffer concat(List<String> paths) {
        return ConcatUsingOpenCV(paths);
    }
    Mat dst;
    private ByteBuffer ConcatUsingOpenCV(List<String> paths) {
        dst = new Mat();
        List<Mat> fimages = makeHeightandWidthSame(paths);
        Core.hconcat(fimages, dst);
        ByteBuffer im = null;
        try {
            im = Mat2BufferedByteImage(dst);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return im;
    }
    private List<Mat> makeHeightandWidthSame(List<String> paths) {
        List<Mat> src = new ArrayList<>();
        int maxw = 0;
        int maxh = 0;
        for (String path : paths) {
            Mat img = Imgcodecs.imread(path);
            maxw = Math.max(img.width(), maxw);
            maxh = Math.max(img.height(), maxh);
            src.add(img);
        }
        List<Mat> fimages = new ArrayList<>();
        for (int i = 0; i < src.size(); i++) {
            Mat img = src.get(i);
            Mat res = new Mat();
            Core.copyMakeBorder(img, res, maxh - img.height(), 0, maxw - img.width(), 0, Core.BORDER_DEFAULT);
            fimages.add(res);
        }
        return fimages;
    }
//    public boolean istextPresent(Mat image) throws IOException, TesseractException {
//        Tesseract t = new Tesseract();
//        t.setDatapath("D:\\Java Spring-Boot Projects\\StartWithTextract\\tessdata");
//        Imgproc.cvtColor(image, image, Imgproc.COLOR_RGB2GRAY);
//        byte[] ba = Mat2BufferedByteImage(image).array();
//        ByteArrayInputStream in = new ByteArrayInputStream(ba);
//        BufferedImage bi = ImageIO.read(in);
//        String result = "";
//        result = t.doOCR(bi);
//        System.out.println("Tesaract has-" + result);
//        return result.length() > 0;
//    }
//    public boolean istextPresent(String path) throws IOException, TesseractException {
//        ITesseract t = new Tesseract();
//        t.setDatapath("D:\\Java Spring-Boot Projects\\StartWithTextract\\tessdata");
//        BufferedImage img = ImageIO.read(new File(path));
//        System.out.println(img);
//        String result = "";
//        System.out.println(path);
//        String res = t.doOCR(img);
//        System.out.println("Tessaract Text-" + res);
//        res = res.replaceAll("\\s", "");
//        return res.length() > 0;
//
//    }
    private ByteBuffer Mat2BufferedByteImage(Mat mat) throws IOException {
        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpeg", mat, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
        System.out.println("Size of Image ==>" + byteArray.length);
        while (byteArray.length > 10485760) {
            byteArray = CompressImage(byteArray);
        }

        InputStream in = new ByteArrayInputStream(byteArray);
        ByteBuffer imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(in));
        return imageBytes;
    }
    private byte[] CompressImage(byte[] arr) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(arr);
        BufferedImage image = ImageIO.read(in);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "JPG", baos);
        byte[] data = baos.toByteArray();
        System.out.println("SIze of Compressed Image -" + data.length);
        return data;
    }
}
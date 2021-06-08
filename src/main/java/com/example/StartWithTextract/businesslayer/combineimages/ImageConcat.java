package com.example.StartWithTextract.businesslayer.combineimages;

import com.amazonaws.util.IOUtils;
import com.example.StartWithTextract.businesslayer.Service;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ImageConcat {
    private Logger logger= LoggerFactory.getLogger(ImageConcat.class);
    public ImageConcat() {
    }
    public ByteBuffer concat(List<String> paths) {
        logger.trace("Concatenating the images in " +this.getClass().getSimpleName()+" class" );
        return ConcatUsingOpenCV(paths);
    }
    private Mat dst;
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
    private ByteBuffer Mat2BufferedByteImage(Mat mat) throws IOException {
        //Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpeg", mat, matOfByte);
        //Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();
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
        return data;
    }
}
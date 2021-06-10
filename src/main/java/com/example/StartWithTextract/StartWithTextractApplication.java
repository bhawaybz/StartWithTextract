package com.example.StartWithTextract;

import com.amazonaws.services.textract.model.Block;
import com.example.StartWithTextract.businesslayer.amazontextract.MyTextract;
import com.example.StartWithTextract.businesslayer.combineimages.ImageConcat;
import com.example.StartWithTextract.businesslayer.documents.*;
import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

@SpringBootApplication
@EnableCaching
public class StartWithTextractApplication {
    static HashMap<String, HashMap<String, ArrayList<String>>> data;
    static MyTextract textract;
    static ImageConcat imageConcat;

    public static void main(String[] args) {
        SpringApplication.run(StartWithTextractApplication.class, args);
        textract = new MyTextract();
        data = new HashMap<>();
        imageConcat = new ImageConcat();
        OpenCV.loadShared();
//        start();
//       checkForRandomImages();
    }

    //    private static void  checkForRandomImages(){
//                List<String> list=new ArrayList<>();
//        list.add("C:\\Users\\HP\\Pictures\\DSC_0016.jpeg");
//        list.add("C:\\Users\\HP\\Pictures\\DSC_0017.jpeg");
//       ImageConcat c=new ImageConcat();
//        Mat img = Imgcodecs.imread("D:\\DOWNLOADS\\selfieimg3.png");
//        try {
//            System.out.println(c.istextPresent("D:\\DOWNLOADS\\selfieimg3.png"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (TesseractException e) {
//            e.printStackTrace();
//        }
//    }
    private static void start() {
        String[] paths = {"VoterCard", "Aadhardata", "PassPort", "DlData"};
        for (String path : paths) {
            String currpath = "C:\\Users\\HP\\Desktop\\ZoomCar\\" + path;
            File[] files = new File(currpath).listFiles();
            for (int i = 0; i < files.length; i++) {
                File[] subfile = new File(currpath + "\\" + files[i].getName()).listFiles();
                System.out.println(files[i].getAbsolutePath());
                List<String> locations = new ArrayList<>();
                for (File iname : subfile) {
                    if (validFile(splitted(iname.getAbsolutePath()))) {
                        locations.add(iname.getAbsolutePath());
                        System.out.println(iname.getAbsolutePath());
                    }
                }
                if (locations.size() == 2) {
                    iterateForImageConcatenation(locations, files[i].getName(), path);
                } else if (locations.size() == 1 || locations.size() > 2) {
                    String id = files[i].getName();
                    HashMap<String, ArrayList<String>> map = data.getOrDefault(path, new HashMap<>());
                    ArrayList<String> list = map.getOrDefault(id, new ArrayList<>());

                    if (locations.size() > 2) {
                        list.add("Need manual Checking ");
                    } else {
                        list.add("Ony 1 Image is present ");
                    }

                    map.put(id, list);
                    data.put(path, map);
                }
            }
        }
        System.out.println(data);
    }

    static int i = 10;

    private static void printBufferedImage(ByteBuffer img) {

        byte[] b = img.array();
        ByteArrayInputStream in = new ByteArrayInputStream(b);


        try {
            BufferedImage image = ImageIO.read(in);
            ImageIO.write(image, "jpeg", new File("C:\\Users\\HP\\Desktop\\ZoomCar\\ConcatenatedImages\\image" + i + ".jpeg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        i++;
    }

    private static void iterate(String filePath) {
        String[] arr = splitted(filePath);
        String id = arr[arr.length - 2];
        String file = arr[arr.length - 3];
        String name = arr[arr.length - 1];
        if (validFile(arr)) {
            String document = callTextractandGetConfidence(filePath, id, name);
            updateMap(document, file, id);
        }
    }

    private static void iterateForImageConcatenation(List<String> paths, String id, String file) {
        System.out.println("Checking for " + file + " " + id);
        String ans = concatTheImages(paths, id);
        updateMap(ans, file, id);
    }

    private static void updateMap(String ans, String file, String id) {
        HashMap<String, ArrayList<String>> map = data.getOrDefault(file, new HashMap<>());
        ArrayList<String> confList = map.getOrDefault(id, new ArrayList<>());
        confList.add(ans);
        map.put(id, confList);
        data.put(file, map);
    }

    private static String concatTheImages(List<String> locations, String id) {
        ImageConcat c = new ImageConcat();
        ByteBuffer concatenatedImg = c.concat(locations);
        if (concatenatedImg == null) return "No text Detected";
        System.out.println(concatenatedImg);
        return callTextractwithBufferImage(concatenatedImg, id);
    }

    //    private static String concatWriteDelete(List<String> locations,String id){
////        String img="\\tempimg.jpeg";
//        ByteBuffer concatenatedImg=imageConcat.concat(locations);
//        // write the image at a particular path ;
//        imageConcat.writeImage(TempStoragePath);
//        System.out.println(concatenatedImg);
////        String ans= callTextractwithBufferImage(concatenatedImg,id);
//        String ans=callTextractandGetConfidence(TempStoragePath,id,"tempImg"+"->"+id);
//        imageConcat.DeleteImage(TempStoragePath);
//        return ans;
//    }
    private static String callTextractwithBufferImage(ByteBuffer concatImg, String id) {
        List<Block> list = textract.getBlockBuffered(concatImg);
        return parsed(list);
    }

    private static String callTextractandGetConfidence(String path, String id, String name) {
        List<Block> list = textract.getBlock(path);
        return parsed(list);

    }


    private static void analayze(String text1, AadharCard aadhar, Passport passport, VoterCard votercard, DrivingLicence dl) {
        ArrayList<String> list = updateText(text1);

        for (String text : list) {

            if (text.length() >= 3) {

                aadhar.analyzeDoc(text);
                passport.analyzeDoc(text);
                votercard.analyzeDoc(text);
                dl.analyzeDoc(text);


            }

        }

    }

    private static ArrayList<String> updateText(String text) {
        text = text.toUpperCase(Locale.ROOT);
        ArrayList<String> splittedAll = new ArrayList<>(Arrays.asList(text.split(" ")));

        if (splittedAll.size() == 1) return splittedAll;

        text = text.replaceAll("\\s", "");
        splittedAll.add(text);
        return splittedAll;
    }

    private static String parsed(List<Block> list) {

        AadharCard aadhar = new AadharCard();
        Passport passport = new Passport();
        VoterCard votercard = new VoterCard();
        DrivingLicence dl = new DrivingLicence();
        StringBuilder sb = new StringBuilder();
        for (Block block : list) {
            if (block.getBlockType().equals("LINE")) {
//                System.out.println("["+block.getId()+"]"+" "+ block.getText() +"--> "+ block.getConfidence()+", "+ block.getRelationships());
                if (block.getConfidence() > 70.0 && block.getText().length() >= 3) {
                    System.out.println("Confidence Greater(>) 70 ---" + block.getText());
//                    sb.append(block.getText());
                    analayze(block.getText(), aadhar, passport, votercard, dl);
                }
            }
        }
        return check_Max(aadhar, passport, votercard, dl);
    }

    private static String check_Max(AadharCard aadhar, Passport passport, VoterCard votercard, DrivingLicence dl) {
        int max = 0;
        String ans = "";
        if (aadhar.getConfidence() > max) {
            max = aadhar.getConfidence();
            ans = name(aadhar.getClass().getName());
        }
        if (votercard.getConfidence() > max) {
            max = votercard.getConfidence();
            ans = name(votercard.getClass().getName());
        }
        if (dl.getConfidence() > max) {
            max = dl.getConfidence();
            ans = name(dl.getClass().getName());
        }
        if (passport.getConfidence() > max) {
            max = passport.getConfidence();
            ans = name(passport.getClass().getName());
        }

        if (max <= 75) {
            return "Invalid Document";
        }
        System.out.println(ans);
        return ans;
    }

    private static String name(String s) {
        String[] arr = s.split("\\.");
        return arr[arr.length - 1];
    }

    private static String[] splitted(String s) {
        String cpath = s;
        cpath = cpath.replace("\\", "/");
        return cpath.split("/");
    }

    private static boolean validFile(String[] arr) {
        return !(arr[arr.length - 1].length() > 14 && arr[arr.length - 1].substring(0, 14).equals("textractResult"));
    }


}

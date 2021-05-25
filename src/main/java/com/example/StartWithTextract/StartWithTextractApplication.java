package com.example.StartWithTextract;

import com.amazonaws.services.textract.model.Block;
import com.example.StartWithTextract.AmazonTextract.MyTextract;
import com.example.StartWithTextract.Documents.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class StartWithTextractApplication {
    static HashMap<String, HashMap<String, ArrayList<String>>> data;
    static MyTextract textract;
    public static void main(String[] args) {
        SpringApplication.run(StartWithTextractApplication.class, args);
        textract=new MyTextract();
        data = new HashMap<>();
//        String[] paths = {"AadharData", "PassPort", "VoterCard", "DlData"};
        String[] paths={"InvalidPictures"};
        for (String path : paths) {
            System.out.println(path);
            try (Stream<Path> filePathStream = Files.walk(Paths.get("C:\\Users\\HP\\Desktop\\ZoomCar\\" + path))) {
                filePathStream.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        iterate(filePath.toString());
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//         callTextractandGetConfidence("C:\\Users\\HP\\Desktop\\ZoomCar\\DlData\\2555355\\image86.jpeg"," fdfdf","iamge86" );
        System.out.println(data);
    }
    public static void iterate(String filePath) {
        String[] arr = splitted(filePath);
        String id = arr[arr.length - 2];
        String file = arr[arr.length - 3];
        String name=arr[arr.length-1];
        if (validFile(arr)) {
            String document = callTextractandGetConfidence(filePath,id,name);
            HashMap<String, ArrayList<String>> map = data.getOrDefault(file, new HashMap<>());
            ArrayList<String> confList = map.getOrDefault(id, new ArrayList<>());
            confList.add(document);
            map.put(id, confList);
            data.put(file, map);
        }
    }
    public static String callTextractandGetConfidence(String path,String id,String name) {
        AadharCard aadhar = new AadharCard();
        VoterIdCard votercard = new VoterIdCard();
        PassPort passport = new PassPort();
        DrivingLicence dl = new DrivingLicence();
        AadharCardBack aadharb = new AadharCardBack();
        VoterIdCardBack votercardb = new VoterIdCardBack();
        PassPortBack passportb = new PassPortBack();
        DrivingLicenceBack dlb = new DrivingLicenceBack();

        List<Block> list = textract.getBlock(path);
       StringBuilder sb=new StringBuilder();
        for (Block block : list) {
            if (block.getBlockType().equals("LINE")) {
               sb.append(block.getText());
            }
        }
        String text=sb.toString();
        System.out.println( name + " has a Text --" + text);
        aadhar.analyzeAadharKey(text);
        passport.analyzePassportKey(text);
        votercard.analyzVoterIdKey(text);
        dl.analyzeDlKey(text);
//        aadharb.analyzeAadharKey(text);
//        passportb.analyzePassportKey(text);
//        votercardb.analyzVoterIdKey(text);
//        dlb.analyzeDlKey(text);

        return check_Max(aadhar, votercard, passport, dl,aadharb, votercardb, passportb, dlb);
    }
    private static String check_Max(AadharCard aadhar, VoterIdCard votercard, PassPort passport, DrivingLicence dl,AadharCardBack aadharb, VoterIdCardBack votercardb,
                                    PassPortBack passportb, DrivingLicenceBack dlb) {
        int max = 0;
        String ans = "";
        if (aadhar.getConfidence() > max) {
            max = aadhar.getConfidence();
            ans = aadhar.getString();
        }
        if (votercard.getConfidence() > max) {
            max = votercard.getConfidence();
            ans = votercard.getString();
        }
        if (dl.getConfidence() > max) {
            max = dl.getConfidence();
            ans = dl.getString();
        }
        if (passport.getConfidence() > max) {
            max = passport.getConfidence();
            ans = passport.getString();
        }

//        if (aadharb.getConfidence() > max) {
//            max = aadharb.getConfidence();
//            ans = aadharb.getString();
//        }
//        if (votercardb.getConfidence() > max) {
//            max = votercardb.getConfidence();
//            ans = votercardb.getString();
//        }
//        if (passportb.getConfidence() > max) {
//            max = passportb.getConfidence();
//            ans = passportb.getString();
//        }
//        if (dlb.getConfidence() > max) {
//            max = dlb.getConfidence();
//            ans = dlb.getString();
//        }
        if (max <=75) {
            return "Invalid Document";
        }
        System.out.println(ans);
        return ans;
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
//        String cpath = path;
//        cpath = cpath.replace("\\", "/");
//        String[] arr = cpath.split("/");
//        System.out.println(arr[arr.length - 1]);
//        if (arr[arr.length - 1].length() > 14 && arr[arr.length - 1].substring(0, 14).equals("textractResult")) {
//            return "TextDocument";
//        }
//        String id = arr[arr.length - 2];
//
//        String npath = "";
//        for (int i = 0; i < arr.length - 2; i++) {
//            npath += arr[i] + "\\";
//        }
//        npath += id + "\\textractResult" + i + ".txt";
//			 try {
//
//				 myWriter.write( "Text ---->" + block.getText());
//				 myWriter.write(System.getProperty( "line.separator" ));
//			 } catch (IOException e) {
//				 e.printStackTrace();
//			 }
//	 try {
//		 myWriter.close();
//	 } catch (IOException e) {
//		 e.printStackTrace();
//	 }
//	 File file = new File(npath);
//	 try {
//		 file.createNewFile();
//	 } catch (IOException e) {
//		 e.printStackTrace();
//	 }

//	 try {
//		 myWriter = new FileWriter(npath);
//	 } catch (IOException e) {
//		 e.printStackTrace();
//	 }
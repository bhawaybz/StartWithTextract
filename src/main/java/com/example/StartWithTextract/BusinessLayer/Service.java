package com.example.StartWithTextract.BusinessLayer;
import com.amazonaws.services.textract.model.Block;
import com.example.StartWithTextract.BusinessLayer.AmazonTextract.MyTextract;
import com.example.StartWithTextract.BusinessLayer.CombineImgaes.ImageConcat;
import com.example.StartWithTextract.BusinessLayer.Documents.AadharCard;
import com.example.StartWithTextract.BusinessLayer.Documents.DrivingLicence;
import com.example.StartWithTextract.BusinessLayer.Documents.Passport;
import com.example.StartWithTextract.BusinessLayer.Documents.VoterCard;
import nu.pattern.OpenCV;
import java.util.List;

public class Service {

    private MyTextract textract;
    private ImageConcat imageConcat;
    public Service(){
        textract= new MyTextract();
        imageConcat=new ImageConcat();
        OpenCV.loadShared();
    }
    public String getdata(String Path){
        return callTextractandGetConfidence(Path);
    }
    public String getDocumentType(String path){
        return "";
    }
    private  String callTextractandGetConfidence(String path) {
        List<Block> list = textract.getBlock(path);
        StringBuilder sb=parsed(list);
        String text=sb.toString();

        return analyzeAllDocuments(text);

    }
    private  String analyzeAllDocuments(String text){

        AadharCard aadhar =new AadharCard();
        Passport passport=new Passport();
        VoterCard votercard=new VoterCard();
        DrivingLicence dl=new DrivingLicence();
//        System.out.println( name + " has a Text --" + text);
        aadhar.analyzeDoc(text);
        passport.analyzeDoc(text);
        votercard.analyzeDoc(text);
        dl.analyzeDoc(text);

        return check_Max(aadhar, votercard, passport,dl);
    }
    private  StringBuilder parsed( List<Block> list){
        StringBuilder sb=new StringBuilder();
        for (Block block : list) {
            if (block.getBlockType().equals("LINE")) {
                if(block.getConfidence()>70.0)
                    sb.append(block.getText());
            }
        }
        return sb;
    }
    private  String check_Max(AadharCard aadhar, VoterCard votercard, Passport passport, DrivingLicence dl) {
        int max = 0;
        String ans = "";
        if (aadhar.getConfidence() > max) {
            max = aadhar.getConfidence();
            ans = aadhar.DocName();
        }
        if (votercard.getConfidence() > max) {
            max = votercard.getConfidence();
            ans = votercard.DocName();
        }
        if (dl.getConfidence() > max) {
            max = dl.getConfidence();
            ans = dl.DocName();
        }
        if (passport.getConfidence() > max) {
            max = passport.getConfidence();
            ans = passport.DocName();
        }
        System.out.println(ans);
        if (max <=75) {
            return "Invalid Document";
        }
        return ans;
    }

}

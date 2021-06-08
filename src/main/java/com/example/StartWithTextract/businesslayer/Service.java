package com.example.StartWithTextract.businesslayer;

import com.amazonaws.services.textract.model.Block;
import com.example.StartWithTextract.businesslayer.amazontextract.MyTextract;
import com.example.StartWithTextract.businesslayer.combineimages.ImageConcat;
import com.example.StartWithTextract.businesslayer.documents.AadharCard;
import com.example.StartWithTextract.businesslayer.documents.DrivingLicence;
import com.example.StartWithTextract.businesslayer.documents.Passport;
import com.example.StartWithTextract.businesslayer.documents.VoterCard;
import nu.pattern.OpenCV;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Cacheable
public class Service {
    private MyTextract textract;
    private AadharCard aadhar;
    private Passport passport;
    private VoterCard votercard;
    private DrivingLicence dl;
    private ImageConcat imageConcat;
    Logger logger = LoggerFactory.getLogger(Service.class);

    public Service() {
        textract = new MyTextract();
        imageConcat = new ImageConcat();
        OpenCV.loadShared();
    }

    public String getDocumentType(List<String> images) {
        return concatTheImages(images);
    }

    private String concatTheImages(List<String> images) {

        ImageConcat c = new ImageConcat();
        ByteBuffer concatenatedImg = c.concat(images);
        logger.trace("Images Concatenated and calling Textract");
        return callTextractwithByteBuffer(concatenatedImg);
    }

    private String callTextractwithByteBuffer(ByteBuffer concatImg) {
        List<Block> list = textract.getBlockBuffered(concatImg);
        logger.trace("Data Received form Textract");
        return parsed(list);
    }

    private String parsed(List<Block> list) {
        logger.trace("Parsing The Document data");
        long start = System.currentTimeMillis();
        aadhar = new AadharCard();
        passport = new Passport();
        votercard = new VoterCard();
        dl = new DrivingLicence();
        for (Block block : list) {
            if (block.getBlockType().equals("LINE")) {
                String text = block.getText();
                if (block.getConfidence() > 70.0 && text.length() >= 3) {
                    analyzeAllDocuments(text);
                }
            }
        }
        NumberFormat formatter = new DecimalFormat("#0.00000");
        logger.trace("Time taken to parse the document " + (formatter.format((System.currentTimeMillis() - start) / 1000d)) + " seconds");
        logger.trace("Getting the Result ");
        return checkMax();
    }

    private void analyzeAllDocuments(String text1) {
        ArrayList<String> list = updateText(text1);
        for (String text : list) {
            if (text.length() >=3) {
                aadhar.analyzeDoc(text);
                passport.analyzeDoc(text);
                votercard.analyzeDoc(text);
                dl.analyzeDoc(text);
            }
        }
    }

    private ArrayList<String> updateText(String text) {
        text = text.toUpperCase(Locale.ROOT);
        ArrayList<String> splittedAll = new ArrayList<>(Arrays.asList(text.split(" ")));
        if (splittedAll.size() == 1) return splittedAll;
        text = text.replaceAll("\\s", "");
        splittedAll.add(text);
        return splittedAll;
    }

    private String checkMax() {
        int max = 0;
        String ans = "";
        if (aadhar.getConfidence() > max) {
            max = aadhar.getConfidence();
            ans = aadhar.getClass().getSimpleName();
        }
        if (votercard.getConfidence() > max) {
            max = votercard.getConfidence();
            ans = votercard.getClass().getSimpleName();
        }
        if (dl.getConfidence() > max) {
            max = dl.getConfidence();
            ans = dl.getClass().getSimpleName();
        }
        if (passport.getConfidence() > max) {
            max = passport.getConfidence();
            ans = passport.getClass().getSimpleName();
        }
        System.out.println(ans);
        if (max <= 75) {
            return "Invalid Document";
        }
        return ans;
    }

}

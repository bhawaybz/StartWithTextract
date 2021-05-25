package com.example.StartWithTextract.AmazonTextract;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

public class MyTextract {

    private AmazonTextract client;
    public MyTextract(){
        Credentials cred=new Credentials();
        AmazonTextractClientBuilder cb = AmazonTextractClientBuilder.standard().withRegion(Regions.AP_SOUTH_1);
        cb.setCredentials(new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(cred.getAccessKey(), cred.getsecretKey())));
         client = cb.build();
    }
    public List<Block> getBlock(String path){
       return FindBlockUsingTextract(path);
    }
     private List<Block> FindBlockUsingTextract(String path){
         String document = path;
         ByteBuffer imageBytes = null;
         try (InputStream inputStream = new FileInputStream(new File(document))) {
             imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));
         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
         DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                 .withDocument(new Document()
                         .withBytes(imageBytes));
         DetectDocumentTextResult result = client.detectDocumentText(request);
         List<Block> l = result.getBlocks();
         return l;
     }
}

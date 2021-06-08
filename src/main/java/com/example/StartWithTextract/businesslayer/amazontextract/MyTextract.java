package com.example.StartWithTextract.businesslayer.amazontextract;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;
import com.amazonaws.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.List;

public class MyTextract {
    private AmazonTextract client;
    Logger logger= LoggerFactory.getLogger(MyTextract.class);
    public MyTextract(){
        AmazonTextractClientBuilder cb = AmazonTextractClientBuilder.standard();
         client = cb.build();
    }
    public List<Block> getBlock(String path){
       return FindBlockUsingTextract(path);
    }
    public List<Block> getBlockBuffered(ByteBuffer img){
        logger.trace("Fetching Images data in " + this.getClass().getSimpleName()+" class");
        return resuestAndgetList(img);
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
           return resuestAndgetList(imageBytes);
     }
     private List<Block> resuestAndgetList(ByteBuffer imageBytes ){

         DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                 .withDocument(new Document()
                         .withBytes(imageBytes));
         DetectDocumentTextResult result = client.detectDocumentText(request);
         return result.getBlocks();
     }
}

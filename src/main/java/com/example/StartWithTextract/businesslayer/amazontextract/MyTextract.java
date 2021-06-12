package com.example.StartWithTextract.businesslayer.amazontextract;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.AmazonTextractClientBuilder;
import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.DetectDocumentTextRequest;
import com.amazonaws.services.textract.model.DetectDocumentTextResult;
import com.amazonaws.services.textract.model.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.List;

public class MyTextract {
    private AmazonTextract client;
    private StringBuilder rawText;
    Logger logger = LoggerFactory.getLogger(MyTextract.class);

    public MyTextract() {
        rawText = new StringBuilder();
        AmazonTextractClientBuilder cb = AmazonTextractClientBuilder.standard();
        client = cb.build();
    }

    public List<Block> getBlockBuffered(ByteBuffer img) {
        logger.trace("Fetching Images data in " + this.getClass().getSimpleName() + " class");
        return resuestAndgetList(img);
    }

    private List<Block> resuestAndgetList(ByteBuffer imageBytes) {

        DetectDocumentTextRequest request = new DetectDocumentTextRequest()
                .withDocument(new Document()
                        .withBytes(imageBytes));
        DetectDocumentTextResult result = client.detectDocumentText(request);
        rawText.append(result.toString());
        return result.getBlocks();
    }

    public String getRawData() {
        return rawText.toString();
    }
}

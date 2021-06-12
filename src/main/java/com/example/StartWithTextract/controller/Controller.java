package com.example.StartWithTextract.controller;

import com.example.StartWithTextract.businesslayer.Service;
import com.example.StartWithTextract.model.DocumentModel;
import com.example.StartWithTextract.model.UrlModel;
import com.example.StartWithTextract.repository.DocumentRepository;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import org.slf4j.Logger;


@RestController
@RequestMapping("/apiClassifier")
public class Controller {
    private Service service;

    @Autowired
    private DocumentRepository repository;

    Logger logger = LoggerFactory.getLogger(Controller.class);


    @PostMapping("/getdocument")
    public String getDocumentType(@RequestBody UrlModel url) {
        service = new Service();
        logger.trace("got the Url " + url);
        String url1 = fetchUrlString(url.getUrl1());
        String url2 = fetchUrlString(url.getUrl2());
        DocumentModel modelMongo = repository.findbyUrl(url1, url2);
        if (modelMongo != null) {
            logger.trace(" Url already present in Database ");
            return modelMongo.getDocumentType();
        }
        ArrayList<String> list = new ArrayList<>(Arrays.asList(url.getUrl1(), url.getUrl2()));
        String documentType = service.getDocumentType(list);
        logger.trace("Document is: " + documentType);
        repository.save(new DocumentModel(new UrlModel(url1, url2), documentType, service.getRawText()));
        return documentType;
    }

    private String fetchUrlString(String url) {

        return url.split("\\?")[0];
    }


}

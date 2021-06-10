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

    @GetMapping("/type") // have to make it a post mapping
    public String getdata(@RequestParam Map<String, String> map) {
        service = new Service();

        String url1 = map.get("img1");
        String url2 = map.get("img2");
        String id = getonlyId(url1);
        logger.trace(url1);
        logger.trace(url2);
        Optional<DocumentModel> modelMongo = repository.findById(id);
        if (modelMongo.isPresent())
            return modelMongo.get().getDocumentType();

        String documentType = service.getDocumentType(new ArrayList<>(Arrays.asList(url1, url2)));

        repository.save(new DocumentModel(id, documentType));
        return documentType;
    }

    private String getonlyId(String url) {
        String[] arr = url.split("/");
        return arr[6];
    }

    @PostMapping("/getdocument")
    public String getDocumentType(@RequestBody UrlModel url) {

        logger.trace("got the Url " + url);
        String id = getCombineId(url.getUrl1(), url.getUrl2());

        Optional<DocumentModel> modelMongo = repository.findById(id);
        if (modelMongo.isPresent()) {
            logger.trace(id + " already present ");
            return modelMongo.get().getDocumentType();
        }


        String documentType = service.getDocumentType(new ArrayList<>(Arrays.asList(url.getUrl1(), url.getUrl2())));
        repository.save(new DocumentModel(id, documentType));

        return documentType;
    }

    private String getCombineId(String url1, String url2) {
        String[] url1arr = url1.split(".");
        String[] url2arr = url2.split(".");

        String id = getSingleId(url1arr) + "#" + getSingleId(url2arr);
        String s3bucket = url1arr[0].substring(8) + "." + url1arr[1];
        String region = url1arr[2];

        return id + s3bucket + region;
    }

    private String getSingleId(String[] arr) {
        String[] split = arr[4].split("/");
        return split[2];
    }

}

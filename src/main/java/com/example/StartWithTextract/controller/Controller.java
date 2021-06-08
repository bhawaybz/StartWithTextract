package com.example.StartWithTextract.controller;

import com.example.StartWithTextract.businesslayer.Service;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.Map;
import org.slf4j.Logger;

@RestController
@RequestMapping("/apiClassifier")
public class Controller {
    private Service service;
    Logger logger = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/getdocument")
    public String getdata(@RequestParam Map<String, String> map) {
        service = new Service();

        ArrayList<String> imgPaths = new ArrayList<>();
        String url1=map.get("img1");
        String url2=map.get("img2");
        imgPaths.add(url1);
        imgPaths.add(url2);
        logger.trace(url1);
        logger.trace(url2);

        return service.getDocumentType(imgPaths);
    }
}

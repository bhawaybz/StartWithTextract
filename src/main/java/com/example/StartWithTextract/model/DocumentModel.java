package com.example.StartWithTextract.model;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DocumentModel")
public class DocumentModel {


    @Id
    public String id;
    private String documentType;
    private String textractRawData;
    private UrlModel urlModel;

    public DocumentModel() {
    }

    public DocumentModel(UrlModel urlModel, String documentType, String textractRawData) {

        this.urlModel = urlModel;
        this.documentType = documentType;
        this.textractRawData = textractRawData;
    }

    public UrlModel getUrlModel() {
        return urlModel;
    }

    public void setUrlModel(UrlModel urlModel) {
        this.urlModel = urlModel;
    }

    public String getTextractRawData() {
        return textractRawData;
    }

    public void setTextractRawData(String textractRawData) {
        this.textractRawData = textractRawData;
    }

    @Override
    public String toString() {
        return this.id + " having Document Type : " + this.documentType;
    }

    public String getId() {
        return id;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

}

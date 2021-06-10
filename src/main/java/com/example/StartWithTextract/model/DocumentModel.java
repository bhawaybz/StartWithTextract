package com.example.StartWithTextract.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DocumentModel")
public class DocumentModel {

    public DocumentModel() {
    }

    @Id
    private String id;

    private String documentType;


    @Override
    public String toString(){
        return this.id+" having Document Type : "+ this.documentType;
    }

    public DocumentModel(String id, String documentType) {
        this.id = id;
        this.documentType = documentType;

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

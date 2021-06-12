package com.example.StartWithTextract.businesslayer.documents;

import com.example.StartWithTextract.businesslayer.validationalgos.ValidatePassport;

public class Passport extends Documents {
    public Passport() {
        super();
    }

    @Override
    public boolean validDocumentNumber(String text) {
        return (ValidatePassport.isValidPassport(text));
    }

    @Override
    protected void updateKeywords() {
        //*******************Front Side*******************//
        this.keywords.put(this.getClass().getSimpleName(), 60);
        this.keywords.put("REPUBLICOFINDIA", 40);
        //*****************************************************
        this.keywords.put("PLACEOFISSUE", 10);
        this.keywords.put("PASSPORTNO", 40);
        this.keywords.put("EMIGRATIONCHECKREQUIRED", 30);

    }

}

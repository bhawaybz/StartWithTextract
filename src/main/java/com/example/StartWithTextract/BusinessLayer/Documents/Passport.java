package com.example.StartWithTextract.BusinessLayer.Documents;

import com.example.StartWithTextract.BusinessLayer.ValidationAlgos.ValidatePassport;

public class Passport extends Documents {
    public Passport(){
        super();
    }
    @Override
    public String DocName() {
        return PASSPORT;
    }
    @Override
    public boolean validDocNumber(String text) {
        ValidatePassport validate =new ValidatePassport();
        if( !(this.keywordsDone.contains(PASSPORT)) && validate.isValidPassport(text) ){
            keywordsDone.add(PASSPORT);
            this.confidenceLevel+= keywords.get(PASSPORT);
            System.out.println("Passport Verified" + text);
           return true;
        }
        return false;
    }
    @Override
    protected void updateKeywords() {
        //*******************Front Side*******************//

        this.keywords.put(PASSPORT, 60);
        this.keywords.put("REPUBLICOFINDIA", 40);
        //*****************************************************
        keywords.put("PLACEOFISSUE", 10);
        keywords.put("PASSPORTNO", 40);
        keywords.put("EMIGRATIONCHECKREQUIRED",30);

    }
}

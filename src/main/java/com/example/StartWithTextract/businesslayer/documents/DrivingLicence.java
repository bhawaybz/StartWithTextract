package com.example.StartWithTextract.businesslayer.documents;

import com.example.StartWithTextract.businesslayer.validationalgos.ValidateLicence;

public class DrivingLicence extends Documents {


    public DrivingLicence() {
        super();
    }

    @Override
    public boolean validDocumentNumber(String text) {
        return ((text.length() == 16 || text.length() == 15) && ValidateLicence.isValidLicence(text));
    }

    @Override
    protected void updateKeywords() {
        //********************** FRONT SIDE ********************//
        this.keywords.put(this.getClass().getSimpleName(), 60);
        this.keywords.put("INDIANUNIONDRIVINGLICENCE", 40);
        this.keywords.put("LICENCINGAUTHORITY", 30);
        this.keywords.put("VALIDTILL", 5);
// *****this.************************************Back Side
        this.keywords.put("DRIVINGLICENCE", 40);
        this.keywords.put("Drive", 10);
        this.keywords.put("MCWG", 10);
        this.keywords.put("LMV", 10);
        this.keywords.put("TRANSPORT", 10);
        this.keywords.put("MOTOR", 10);
        this.keywords.put("CYCLE", 10);
        this.keywords.put("VEHICLE", 10);
        this.keywords.put("REFERENCENO", 5);
        this.keywords.put("GEAR", 10);
    }
}

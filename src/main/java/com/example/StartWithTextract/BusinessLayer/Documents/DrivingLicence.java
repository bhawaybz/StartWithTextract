package com.example.StartWithTextract.BusinessLayer.Documents;

import com.example.StartWithTextract.BusinessLayer.ValidationAlgos.ValidateLicence;

public class DrivingLicence extends Documents{

    public DrivingLicence(){
        super();
    }
    @Override
    public String DocName() {
        return DRIVINGLICENCE;
    }
    @Override
    public boolean validDocNumber(String text) {
        ValidateLicence validate =new ValidateLicence();
        if((text.length()==16 || text.length()==15) && validate.isValidLicence(text) && !(this.keywordsDone.contains(text))){
            keywordsDone.add(text);
            System.out.println("Validatd Licence Number -" + text);
            this.confidenceLevel+= keywords.get(DRIVINGLICENCE);
            return true;
        }
        return false;
    }
    @Override
    protected void updateKeywords() {
        //********************** FRONT SIDE ********************//
        keywords.put(DRIVINGLICENCE, 60);
        keywords.put("INDIANUNIONDRIVINGLICENCE", 30);
        keywords.put("LICENCINGAUTHORITY", 30);
        keywords.put("VALIDTILL", 5);
// *****************************************Back Side
        keywords.put("DRIVINGLICENCE",20);
        keywords.put("Drive",10);
        keywords.put("MCWG", 10);
        keywords.put("LMV", 10);
        keywords.put("TRANSPORT",10);
        keywords.put("MOTOR",10);
        keywords.put("CYCLE",10);
        keywords.put("VEHICLE",10);
        keywords.put("REFERENCE NO",5);
        keywords.put("GEAR",10);
    }
}

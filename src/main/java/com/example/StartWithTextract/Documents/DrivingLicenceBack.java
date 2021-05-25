package com.example.StartWithTextract.Documents;
import com.example.StartWithTextract.ValidationAlgos.ValidateLicence;
import com.example.StartWithTextract.ValidationAlgos.ValidatePassport;
import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class DrivingLicenceBack {
    private String licenceNumber;
    private boolean stateUsed;
    private int ConfidenceLevel;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private String passportNumber;
    private ValidateWithJaroWrinkler jw;
    public DrivingLicenceBack() {
        this.stateUsed=false;
        this.ConfidenceLevel = 0;
        this.keywords = new HashMap<>();
        this.KeywordsDone=new HashSet<>();
        this.updateKeywords();
        this.jw=new ValidateWithJaroWrinkler();
    }
    public void analyzeDlKey(String text) {
        calculatePassportConditions(text);
    }
    public String getString(){
        return " Driving Licence Back";
    }
    public int getConfidence(){
        return this.ConfidenceLevel;
    }
    private  void calculatePassportConditions(String check){

//        check = check.replaceAll("\\s", "");
        String x=check.toUpperCase(Locale.ROOT);
        StringBuilder sb=new StringBuilder(x);
        checkAllSubstrings(sb);
    }
    private void checkAllSubstrings(StringBuilder sb){
        ValidateLicence validate=new ValidateLicence();
        for(int i=0;i<sb.length();i++){
            for(int j=i+1;j<=sb.length();j++){
                StringBuilder xsubstr=new StringBuilder(sb.substring(i,j));
                String text=xsubstr.toString();
                if(validate.isValidLicence(text) && !(this.KeywordsDone.contains(text))){
                    KeywordsDone.add(text);

                    this.ConfidenceLevel+= keywords.get(licenceNumber);
                }else{
                    String getc= jw.checkParticularSubstring(text,this.keywords,this.KeywordsDone,this.stateUsed);
                    if(getc.length()>0){
//                        System.out.println("PassPort Text Considered" +text +" "+getc);
                        int cl=keywords.get(getc);
                        if(cl==50 && this.stateUsed==true){
                            break;
                        }else if(cl==50) {
                            this.stateUsed = true;
                        }
                        this.ConfidenceLevel+=cl;
                        this.KeywordsDone.add(getc);
                        break;
                    }
                }
            }
        }
    }
    private void updateKeywords() {
        licenceNumber = "liccenceNumber";
        keywords.put(licenceNumber, 50);
        keywords.put("DRIVINGLICENCE",20);
        keywords.put("Drive",20);
        keywords.put("MCWG", 20);
        keywords.put("LMV", 20);
        keywords.put("TRANSPORT",20);
        keywords.put("MOTOR CYCLE",20);
        keywords.put("VEHICLE",20);
        keywords.put("REFERENCE NO",20);
        keywords.put("GEAR",10);
    }

}

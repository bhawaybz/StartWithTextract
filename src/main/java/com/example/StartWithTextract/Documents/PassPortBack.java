package com.example.StartWithTextract.Documents;

import com.example.StartWithTextract.ValidationAlgos.ValidatePassport;
import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class PassPortBack {
    private int ConfidenceLevel;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private boolean stateUsed;
    private String passportNumber;
    private ValidateWithJaroWrinkler jw;
    public PassPortBack() {
        this.ConfidenceLevel = 0;
        this.keywords = new HashMap<>();
        this.stateUsed=false;
        this.KeywordsDone=new HashSet<>();
        this.updateKeywords();
        this.jw=new ValidateWithJaroWrinkler();
    }
    public void analyzePassportKey(String text) {
        calculatePassportConditions(text);
    }
    public String getString(){
        return " PassPort Back Side ";
    }
    public int getConfidence(){
        return this.ConfidenceLevel;
    }
    private  void calculatePassportConditions(String check){

        check = check.replaceAll("\\s", "");
        String x=check.toUpperCase(Locale.ROOT);
        StringBuilder sb=new StringBuilder(x);
        checkAllSubstrings(sb);
    }
    private void checkAllSubstrings(StringBuilder sb){
        ValidatePassport validate=new ValidatePassport();
        for(int i=0;i<sb.length();i++){
            for(int j=i+1;j<=sb.length();j++){
                StringBuilder xsubstr=new StringBuilder(sb.substring(i,j));
                String text=xsubstr.toString();
                if(validate.isValidPassport(text) && !(this.KeywordsDone.contains(text))){
                    KeywordsDone.add(text);
                    this.ConfidenceLevel+= keywords.get(passportNumber);
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
        //******************** BACK SIDE ********************//
        passportNumber = "MyNumber";
        keywords.put(passportNumber, 20);
        keywords.put("PLACEOFISSUE", 10);
        keywords.put("PASSPORTNO", 30);
        keywords.put("EMIGRATIONCHECKREQUIRED",40);

    }
}

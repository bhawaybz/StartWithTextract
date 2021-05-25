package com.example.StartWithTextract.Documents;

import com.example.StartWithTextract.ValidationAlgos.ValidatePassport;
import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class VoterIdCardBack {
    private int ConfidenceLevel;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private String passportNumber;
    private boolean stateUsed;
    private ValidateWithJaroWrinkler jw;
    public VoterIdCardBack() {
        this.ConfidenceLevel = 0;
        this.keywords = new HashMap<>();
        this.KeywordsDone=new HashSet<>();
        this.updateKeywords();
        this.stateUsed=false;
        this.jw=new ValidateWithJaroWrinkler();
    }
    public void analyzVoterIdKey(String text) {
        calculatePassportConditions(text);
    }
    public String getString(){
        return "Voter Id Card Back";
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
        for(int i=0;i<sb.length();i++){
            for(int j=i+1;j<=sb.length();j++){
                StringBuilder xsubstr=new StringBuilder(sb.substring(i,j));
                String text=xsubstr.toString();

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
    private void updateKeywords() {
        keywords.put("CONSTITUENCY", 30);
        keywords.put("ELECTORALREGISTRATIONOFFICER", 40);
        keywords.put("ASSEMBLY", 10);
        keywords.put("ELECTORAL",10);
        keywords.put("ELECTOR",10);

        // update for the last page
    }
}

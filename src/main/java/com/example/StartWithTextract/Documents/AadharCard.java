package com.example.StartWithTextract.Documents;

import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;
import com.example.StartWithTextract.ValidationAlgos.VerhoeffAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
public class AadharCard {
    private String AadharNumber;
    private boolean stateUsed ;
    private int ConfidenceLevel;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private String passportNumber;
    private ValidateWithJaroWrinkler jw;
    public AadharCard() {
        this.stateUsed=false;
        this.ConfidenceLevel = 0;
        this.keywords = new HashMap<>();
        this.KeywordsDone = new HashSet<>();
        this.updateKeywords();
        this.jw = new ValidateWithJaroWrinkler();
    }
    public void analyzeAadharKey(String text) {
        calculatePassportConditions(text);
    }
    public String getString() {
        return "AADHAR CARD";
    }
    public int getConfidence() {
        return this.ConfidenceLevel;
    }
    private void calculatePassportConditions(String check) {
        check = check.replaceAll("\\s", "");
        String x = check.toUpperCase(Locale.ROOT);
        StringBuilder sb = new StringBuilder(x);
        checkAllSubstrings(sb);
    }
    private void checkAllSubstrings(StringBuilder sb) {
        VerhoeffAlgorithm v = new VerhoeffAlgorithm();
        for (int i = 0; i < sb.length(); i++) {
            for (int j = i + 1; j <= sb.length(); j++) {
                StringBuilder xsubstr = new StringBuilder(sb.substring(i, j));
                String x = xsubstr.toString();
                if (x.length() == 12 && checkNum(x) && v.validateVerhoeff(x)) {
                    if (!KeywordsDone.contains(x)) {
                        ConfidenceLevel += keywords.get(AadharNumber);
                        KeywordsDone.add(x);
                        System.out.println("Aadhar number verified");
                    }
                } else {
                    String getc= jw.checkParticularSubstring(x,this.keywords,this.KeywordsDone,this.stateUsed);
                    if(getc.length()>0){
//                        System.out.println("PassPort Text Considered" +text +" "+getc);
                        int cl=keywords.get(getc);
                        if(cl==50 && this.stateUsed==true){
                            break;
                        }else if(cl==50) {
                            this.stateUsed = true;
                        }
                        this.ConfidenceLevel+=cl;
                        System.out.println("Aadhar has text" + getc);
                        this.KeywordsDone.add(getc);
                        break;
                    }
                }
            }
        }
    }
    private boolean checkNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                return false;
            }

        }
        return true;
    }

    private void updateKeywords() {
        //*****************FRONT*********************//
        AadharNumber = "AadharNumber";
        keywords.put(AadharNumber, 65);
        keywords.put("GOVERNMENTOFINDIA", 30);
        keywords.put("AADHAAR", 10);

        //*************************************************\
        keywords.put("UNIQUEIDENTIFICATIONAUTHORITYOFINDIA", 35);
//        keywords.put("ADDRESS", 0.1);
        String link = "www.uidai.gov.in";
        keywords.put(link.toUpperCase(Locale.ROOT), 20);
        keywords.put("ADDRESS",10);
        fillstates();
    }
    private void fillstates(){

        keywords.put("ANDHRA PRADESH",50);
        keywords.put("ARUNACHAL PRADESH",50);
        keywords.put("ASSAM",50);
        keywords.put("BIHAR",50);
        keywords.put("CHATTISHGARH",50);
        keywords.put("GOA",50);
        keywords.put("GUJARAT",50);
        keywords.put("HARYANA",50);
        keywords.put("HIMACHAL PRADESH",50);
        keywords.put("JAMMU AND KASHMIR",50);
        keywords.put("JHARKHAND",50);
        keywords.put("KARNATAKA",50);
        keywords.put("KERALA",50);
        keywords.put("MADHYA PRADESH",50);
        keywords.put("MAHARASHTRA",50);
        keywords.put("MANIPUR",50);
        keywords.put("MEGHALYA",50);
        keywords.put("MIZORAM",50);
        keywords.put("NAGALAND",50);
        keywords.put("ODISHA",50);
        keywords.put("PUNJAB",50);
        keywords.put("RAJASTHAN",50);
        keywords.put("SIKKIM",50);
        keywords.put("TAMIL NADU",50);
        keywords.put("TELANGANA",50);
        keywords.put("TRIPURA",50);
        keywords.put("UTTRAKHAND",50);
        keywords.put("UTTAR PRADESH",50);
        keywords.put("WEST BENGAL",50);
        keywords.put("ANDAMAN AND NICOBAR ISLAND",50);
        keywords.put("CHANDIGARH",50);
        keywords.put("DADRA AND NAGAR HAVELI",50);
        keywords.put("DAMAN AND DIU",50);
        keywords.put("DELHI",50);
        keywords.put("LAKSHADWEEP",50);
        keywords.put("PUDUCHERRY",50);
    }
}
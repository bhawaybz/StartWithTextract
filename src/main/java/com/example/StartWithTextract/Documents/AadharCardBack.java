package com.example.StartWithTextract.Documents;

import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;
import com.example.StartWithTextract.ValidationAlgos.VerhoeffAlgorithm;
import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class AadharCardBack {
    private boolean stateUsed;
    private String AadharNumber;
    private int ConfidenceLevel;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private String passportNumber;
    private ValidateWithJaroWrinkler jw;

    public AadharCardBack() {
        stateUsed=false;
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
//        keywords.put("AADHAAR", 0.4);
        AadharNumber = "AadharNumber";
        keywords.put(AadharNumber, 60);
        keywords.put("UNIQUEIDENTIFICATIONAUTHORITYOFINDIA", 20);
//        keywords.put("ADDRESS", 0.1);
        String link = "www.uidai.gov.in";
        keywords.put(link, 10);
        keywords.put("ADDRESS",10);
    }
}
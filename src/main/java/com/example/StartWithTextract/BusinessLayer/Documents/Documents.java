package com.example.StartWithTextract.BusinessLayer.Documents;

import com.example.StartWithTextract.BusinessLayer.ValidationAlgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public abstract class Documents {
    protected String DRIVINGLICENCE = "Driving Licence";
    protected String PASSPORT = "Passport";
    protected String VOTERCARD = "Voter Card";
    protected String AADHARCARD = "Aadhar Card";
    protected int confidenceLevel;
    protected HashMap<String, Integer> states;
    protected HashMap<String, Integer> keywords;
    protected HashSet<String> keywordsDone;
    private ValidateWithJaroWrinkler jw;
    private boolean stateUsed;

    public Documents() {
        this.confidenceLevel = 0;
        states = new HashMap<>();
        this.keywords = new HashMap<>();
        this.keywordsDone = new HashSet<>();
        this.stateUsed = false;
        this.jw = new ValidateWithJaroWrinkler();
        fillstates();
        updateKeywords();
    }

    protected abstract void updateKeywords();

    public int getConfidence() {
        return this.confidenceLevel;
    }

    public abstract String DocName();

    public void analyzeDoc(String text) {
        checkAllSubstring(new StringBuilder(updateText(text)));
    }

    private String updateText(String text) {
        text = text.replaceAll("\\s", "");
//        text = text.replaceAll("[-+^]*/", "");
        text = text.toUpperCase(Locale.ROOT);
        return text;
    }

    protected abstract boolean validDocNumber(String text);

    private void checkAllSubstring(StringBuilder sb) {

        for (int i = 0; i < sb.length(); i++) {
            for (int j = i + 1; j <= sb.length(); j++) {
                StringBuilder xsubstr = new StringBuilder(sb.substring(i, j));
                String text = xsubstr.toString();
                if (validDocNumber(text)) {
                    continue;
                } else {
                    String getc = jw.checkParticularSubstring(text, this.keywords, this.keywordsDone);
                    if (getc.length() > 0 ) {
                        if(isTextStateUsed(getc)){
                            continue;
                        }
                        else if(isTextState(getc) && stateUsed==false){
                            stateUsed=true;
                        }
                        increaseConfidence(getc);
                        break;
                    }
                }
            }
        }
    }

    private void increaseConfidence(String s){
        this.confidenceLevel += keywords.get(s);
        this.keywordsDone.add(s);
    }
    private boolean isTextState(String s){
        return keywords.get(s)==50;
    }
    private boolean isTextStateUsed(String s){
        return keywords.get(s)==50 && this.stateUsed==false;
    }
    private void fillstates() {

        keywords.put("ANDHRA PRADESH", 50);
        keywords.put("ARUNACHAL PRADESH", 50);
        keywords.put("ASSAM", 50);
        keywords.put("BIHAR", 50);
        keywords.put("CHATTISHGARH", 50);
        keywords.put("GOA", 50);
        keywords.put("GUJARAT", 50);
        keywords.put("HARYANA", 50);
        keywords.put("HIMACHAL PRADESH", 50);
        keywords.put("JAMMU AND KASHMIR", 50);
        keywords.put("JHARKHAND", 50);
        keywords.put("KARNATAKA", 50);
        keywords.put("KERALA", 50);
        keywords.put("MADHYA PRADESH", 50);
        keywords.put("MAHARASHTRA", 50);
        keywords.put("MANIPUR", 50);
        keywords.put("MEGHALYA", 50);
        keywords.put("MIZORAM", 50);
        keywords.put("NAGALAND", 50);
        keywords.put("ODISHA", 50);
        keywords.put("PUNJAB", 50);
        keywords.put("RAJASTHAN", 50);
        keywords.put("SIKKIM", 50);
        keywords.put("TAMIL NADU", 50);
        keywords.put("TELANGANA", 50);
        keywords.put("TRIPURA", 50);
        keywords.put("UTTRAKHAND", 50);
        keywords.put("UTTAR PRADESH", 50);
        keywords.put("WEST BENGAL", 50);
        keywords.put("ANDAMAN AND NICOBAR ISLAND", 50);
        keywords.put("CHANDIGARH", 50);
        keywords.put("DADRA AND NAGAR HAVELI", 50);
        keywords.put("DAMAN AND DIU", 50);
        keywords.put("DELHI", 50);
        keywords.put("LAKSHADWEEP", 50);
        keywords.put("PUDUCHERRY", 50);
    }
}

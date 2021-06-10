package com.example.StartWithTextract.businesslayer.documents;

import com.example.StartWithTextract.businesslayer.validationalgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;


public  abstract class Documents {
    protected int confidenceLevel;
    protected HashMap<String, Integer> states;
    protected HashMap<String, Integer> keywords;
    protected HashSet<String> keywordsDone;
    private boolean stateUsed;

    public Documents() {
        this.confidenceLevel = 0;
        this.states = new HashMap<>();
        this.keywords = new HashMap<>();
        this.keywordsDone = new HashSet<>();
        this.stateUsed = false;
        fillstates();
        updateKeywords();
    }

    protected abstract void updateKeywords();

    public int getConfidence() {
        return this.confidenceLevel;
    }

    public void analyzeDoc(String text) {
        checkAllSubstring(new StringBuilder(text));
    }

    protected abstract boolean validDocumentNumber(String text);

    protected void checkAllSubstring(StringBuilder text) {
        //System.out.println("Check all SUbstrings -" + text);
        if (!keywordsDone.contains(this.getClass().getSimpleName()) && validDocumentNumber(text.toString())) {
            increaseConfidence(this.getClass().getSimpleName());
            // System.out.println("Document Number Validated- " + text + " -for calss " +this.getClass().getSimpleName());
        } else {
            String getc = checkParticularSubstring(text);
            if (getc.length() > 0) {
                increaseConfidence(getc);
            }
        }
    }

    private boolean checkWhetherStateAndisUsed(String getc) {
        if (isTextStateAndUsed(getc)) {
            return true;
        } else if (isTextState(getc) && this.stateUsed == false) {
            this.stateUsed = true;
        }
        return false;
    }

    private String checkParticularSubstring(StringBuilder s) {
        //System.out.println("Check for a particular substring for class " + this.getClass().getSimpleName() + " " + s);
        double maxc = 0.0;
        String myKey = "";
        for (String keys : this.keywords.keySet()) {
            if (!this.keywordsDone.contains(keys)) {
                double jdis = ValidateWithJaroWrinkler.jaroDistance(keys, s.toString());
                if (jdis > maxc) {
                    maxc = jdis;
                    myKey = keys;
                }
            }
        }
        if (maxc <= 80.0) return "";
        return myKey;
    }

    private void increaseConfidence(String s) {

        if (checkWhetherStateAndisUsed(s)) return;
        this.confidenceLevel += keywords.get(s);
        this.keywordsDone.add(s);
    }

    private boolean isTextState(String s) {
        return keywords.get(s) == 50;
    }

    private boolean isTextStateAndUsed(String s) {
        return isTextState(s) && this.stateUsed == true;
    }

    private void fillstates() {

        keywords.put("ANDHRAPRADESH", 50);
        keywords.put("ARUNACHALPRADESH", 50);
        keywords.put("ASSAM", 50);
        keywords.put("BIHAR", 50);
        keywords.put("CHATTISHGARH", 50);
        keywords.put("GOA", 50);
        keywords.put("GUJARAT", 50);
        keywords.put("HARYANA", 50);
        keywords.put("HIMACHALPRADESH", 50);
        keywords.put("JAMMUANDKASHMIR", 50);
        keywords.put("JHARKHAND", 50);
        keywords.put("KARNATAKA", 50);
        keywords.put("KERALA", 50);
        keywords.put("MADHYAPRADESH", 50);
        keywords.put("MAHARASHTRA", 50);
        keywords.put("MANIPUR", 50);
        keywords.put("MEGHALYA", 50);
        keywords.put("MIZORAM", 50);
        keywords.put("NAGALAND", 50);
        keywords.put("ODISHA", 50);
        keywords.put("PUNJAB", 50);
        keywords.put("RAJASTHAN", 50);
        keywords.put("SIKKIM", 50);
        keywords.put("TAMILNADU", 50);
        keywords.put("TELANGANA", 50);
        keywords.put("TRIPURA", 50);
        keywords.put("UTTRAKHAND", 50);
        keywords.put("UTTARPRADESH", 50);
        keywords.put("WESTBENGAL", 50);
        keywords.put("ANDAMANANDNICOBARISLAND", 50);
        keywords.put("CHANDIGARH", 50);
        keywords.put("DADRAANDNAGARHAVELI", 50);
        keywords.put("DAMANANDDIU", 50);
        keywords.put("DELHI", 50);
        keywords.put("LAKSHADWEEP", 50);
        keywords.put("PUDUCHERRY", 50);
    }
}

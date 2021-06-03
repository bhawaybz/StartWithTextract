package com.example.StartWithTextract.BusinessLayer.Documents;

import com.example.StartWithTextract.BusinessLayer.ValidationAlgos.VerhoeffAlgorithm;

import java.util.Locale;

public class AadharCard extends Documents{
    public AadharCard(){
        super();
    }
    @Override
    public String DocName() {
        return AADHARCARD;
    }
    @Override
    protected boolean validDocNumber(String x) {
        VerhoeffAlgorithm v = new VerhoeffAlgorithm();
        if (x.length() == 12 && checkNum(x) && v.validateVerhoeff(x)) {
            if (!super.keywordsDone.contains(AADHARCARD)) {
                System.out.println("Aadhar Verified "+ x);
                confidenceLevel += keywords.get(AADHARCARD);
                keywordsDone.add(AADHARCARD);
                return true;
            }
        }
        return false;
    }
    private boolean checkNum(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (!(s.charAt(i) >= '0' && s.charAt(i) <= '9')) {
                return false;
            }
        }
        return true;
    }
    @Override
    protected void updateKeywords() {
        //*****************FRONT*********************//

        keywords.put(AADHARCARD, 60);
        keywords.put("GOVERNMENTOFINDIA", 30);
        keywords.put("AADHAAR", 10);

        //*************************************************\
        keywords.put("UNIQUEIDENTIFICATIONAUTHORITYOFINDIA", 35); // 20 to complete 200
        String link = "www.uidai.gov.in";
        keywords.put(link.toUpperCase(Locale.ROOT), 20);
    }


}

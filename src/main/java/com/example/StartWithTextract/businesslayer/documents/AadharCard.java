package com.example.StartWithTextract.businesslayer.documents;

import com.example.StartWithTextract.businesslayer.validationalgos.VerhoeffAlgorithm;

import java.util.Locale;

public class AadharCard extends Documents {


    public AadharCard() {
        super();

    }

    @Override
    protected boolean validDocumentNumber(String x) {

        return (x.length() == 12 && checkNum(x) && VerhoeffAlgorithm.validateVerhoeff(x));

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

        this.keywords.put(this.getClass().getSimpleName(), 60);
        this.keywords.put("GOVERNMENTOFINDIA", 30);
        this.keywords.put("AADHAAR", 10);

        //*************************************************
        this.keywords.put("UNIQUEIDENTIFICATIONAUTHORITYOFINDIA", 35);
        String link = "www.uidai.gov.in";
        this.keywords.put(link.toUpperCase(Locale.ROOT), 20);
    }
}

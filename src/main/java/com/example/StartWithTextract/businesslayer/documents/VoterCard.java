package com.example.StartWithTextract.businesslayer.documents;

import com.example.StartWithTextract.businesslayer.validationalgos.ValidateVotercard;

public class VoterCard extends Documents {
    public VoterCard() {
        super();
    }
    @Override
    public boolean validDocumentNumber(String text) {
        return (text.length() == 10 && ValidateVotercard.validate(text));
    }

    @Override
    protected void updateKeywords() {
        //*******************Front Side*******************//

        this.keywords.put(this.getClass().getSimpleName(), 60);
        this.keywords.put("IDENTITYCARD", 20);
        this.keywords.put("ELECTIONCOMMISSIONOFINDIA", 40); // dont know why not abke to identify this keyword
        this.keywords.put("ELECTOR", 40);
        this.keywords.put("FACSIMILESIGNATURE",30);
        //Facsimile Signature
//        ***************************************************
        this.keywords.put("CONSTITUENCY", 30);
        this.keywords.put("ELECTORALREGISTRATIONOFFICER", 40);
        this.keywords.put("ASSEMBLY", 10);

    }
}

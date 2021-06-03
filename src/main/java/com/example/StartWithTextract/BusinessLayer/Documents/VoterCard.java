package com.example.StartWithTextract.BusinessLayer.Documents;

import com.example.StartWithTextract.BusinessLayer.ValidationAlgos.ValidateVotercard;

public class VoterCard extends Documents{
    public VoterCard(){
        super();
    }
    @Override
    public String DocName() {
        return VOTERCARD;
    }

    @Override
    public boolean validDocNumber(String text) {
        ValidateVotercard v=new ValidateVotercard();
        if (text.length() == 10 &&!keywordsDone.contains(VOTERCARD) && v.validate(text)) {
            keywordsDone.add(VOTERCARD);
            this.confidenceLevel+=keywords.get(VOTERCARD);
            return true;
        }
        return false;
    }
    @Override
    protected void updateKeywords() {
        //*******************Front Side*******************//

        this.keywords.put(VOTERCARD,60);
        this.keywords.put("IDENTITYCARD", 20);
        this.keywords.put("ELECTIONCOMMISSIONOFINDIA",40);
        this.keywords.put("ELECTOR", 40);
//        ***************************************************
        keywords.put("CONSTITUENCY", 30);
        keywords.put("ELECTORALREGISTRATIONOFFICER", 40);
        keywords.put("ASSEMBLY", 10);

    }
}

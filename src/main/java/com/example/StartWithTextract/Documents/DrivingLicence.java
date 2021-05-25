package com.example.StartWithTextract.Documents;
import com.example.StartWithTextract.ValidationAlgos.ValidateLicence;
import com.example.StartWithTextract.ValidationAlgos.ValidatePassport;
import com.example.StartWithTextract.ValidationAlgos.ValidateWithJaroWrinkler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class DrivingLicence {
    private String licenceNumber;
    private int ConfidenceLevel;
    private HashMap<String,Integer> states;
    private HashMap<String, Integer> keywords;
    private HashSet<String> KeywordsDone;
    private String passportNumber;
    private ValidateWithJaroWrinkler jw;
    private boolean stateUsed;
    public DrivingLicence() {
        this.ConfidenceLevel = 0;
        states=new HashMap<>();
        this.keywords = new HashMap<>();
        this.KeywordsDone=new HashSet<>();
        this.updateKeywords();
        this.stateUsed=false;
        this.jw=new ValidateWithJaroWrinkler();
    }
    public void analyzeDlKey(String text) {
        calculatePassportConditions(text);
    }
    public String getString(){
        return " Driving Licence ";
    }
    public int getConfidence(){
        return this.ConfidenceLevel;
    }
    private  void calculatePassportConditions(String check){

//        check = check.replaceAll("\\s", "");
        String x=check.toUpperCase(Locale.ROOT);
        StringBuilder sb=new StringBuilder(x);
        checkAllSubstrings(sb);
    }
    private void checkAllSubstrings(StringBuilder sb){
        ValidateLicence validate=new ValidateLicence();
        for(int i=0;i<sb.length();i++){
            for(int j=i+1;j<=sb.length();j++){
                StringBuilder xsubstr=new StringBuilder(sb.substring(i,j));
                String text=xsubstr.toString();
                if(validate.isValidLicence(text) && !(this.KeywordsDone.contains(text))){
                    KeywordsDone.add(text);
//                     System.out.println("Validatd Licence Number -" + text);
                    this.ConfidenceLevel+= keywords.get(licenceNumber);
                }else{
                    String getc= jw.checkParticularSubstring(text,this.keywords,this.KeywordsDone,this.stateUsed);
                    if(getc.length()>0){

                        int cl=keywords.get(getc);
                        if(cl==50 && this.stateUsed==true){
                            System.out.println("break for "+getc);
                            continue;
                        }else if(cl==50) {
                            System.out.println("Mark True");
                            this.stateUsed = true;
                        }
                        System.out.println("Dl Text Considered" +text +" "+getc);
                        this.ConfidenceLevel+=cl;
                        this.KeywordsDone.add(getc);
                        break;
                    }
                }
            }
        }
    }
    private void updateKeywords() {
        //********************** FRONT SIDE ********************//
        licenceNumber = "licenceNumber";
        keywords.put(licenceNumber, 55);
        keywords.put("INDIAN UNION DRIVING LICENCE", 25);
        keywords.put("LICENCING AUTHORITY", 10);
        keywords.put("VALID TILL", 5);
// *****************************************Back Side
        keywords.put("DRIVING LICENCE",25);
        keywords.put("Drive",10);
        keywords.put("MCWG", 5);
        keywords.put("LMV", 5);
        keywords.put("TRANSPORT",15);
        keywords.put("MOTOR",15);
        keywords.put("CYCLE",10);
        keywords.put("VEHICLE",15);
        keywords.put("REFERENCE NO",10);
        keywords.put("GEAR",10);
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

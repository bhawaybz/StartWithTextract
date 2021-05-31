package com.example.StartWithTextract.ValidationAlgos;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateLicence {
    private HashSet<String> stateCodes;

    public ValidateLicence() {
        this.stateCodes = new HashSet<>();
        fillStatesCodes();
    }
    public boolean isValidLicence(String str) {
              return validLicence(str);
    }
    private boolean validLicence(String s) {
          if(!stateCodes.contains(s.substring(0,2)))return false;
           if(!isNumber(s.substring(2))) return false;
           if(!isYearPresent(s.substring(2))) return false;
        return true;
    }
  private boolean isNumber(String s){
        for(int i=0;i<s.length();i++){
            if(!Character.isDigit(s.charAt(i))){
                return false;
            }
        }
        return true;
  }
  private boolean isYearPresent(String s){
//          System.out.println("Checking for "+s);
        for(int i=0;i+4<=s.length() ;i++){
            int j=i+4;
                 int yr=Integer.parseInt(s.substring(i,j));
                if(yr>2000 && yr<=2021){
                    return true;
                }

        }
        return false;
  }
    private boolean isValidLicenseNo(String str) {

        String regex = "^(([A-Z]{2}[0-9]{2})"
                + "( )|([A-Z]{2}-[0-9]"
                + "{2}))((19|20)[0-9]"
                + "[0-9])[0-9]{7}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Find match between given string
        // and regular expression
        // uSing Pattern.matcher()

        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    private void fillStatesCodes() {
                stateCodes.add("AN");
                stateCodes.add("AP");
                stateCodes.add("AR");
                stateCodes.add("AS");
                stateCodes.add("BR");
                stateCodes.add("CH");
                stateCodes.add("DN");
                stateCodes.add("DD");
                stateCodes.add("DL");
                stateCodes.add("GA");
                stateCodes.add("GJ");
                stateCodes.add("HR");
                stateCodes.add("HP");
                stateCodes.add("JK");
                stateCodes.add("KA");
                stateCodes.add("KL");
                stateCodes.add("LD");
                stateCodes.add("MP");
                stateCodes.add("MH");
                stateCodes.add("MN");
                stateCodes.add("ML");
                stateCodes.add("MZ");
                stateCodes.add("NL");
                stateCodes.add("OR");
                stateCodes.add("PY");
                stateCodes.add("PN");
                stateCodes.add("RJ");
                stateCodes.add("SK");
                stateCodes.add("TN");
                stateCodes.add("TR");
                stateCodes.add("UP");
                stateCodes.add("WB");


    }
}

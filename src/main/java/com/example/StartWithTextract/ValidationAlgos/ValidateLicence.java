package com.example.StartWithTextract.ValidationAlgos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateLicence {
       public ValidateLicence(){}

    public boolean isValidLicence(String str){
           return isValidLicenseNo(str);
    }
    private  boolean isValidLicenseNo(String str)
    {
        // Regex to check valid
        // Indian driving license number
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
}

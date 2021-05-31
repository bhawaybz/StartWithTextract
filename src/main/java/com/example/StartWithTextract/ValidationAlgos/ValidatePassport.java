package com.example.StartWithTextract.ValidationAlgos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ValidatePassport {
    public ValidatePassport(){
    }
    public boolean isValidPassport(String text){
           return isValidPassportNo(text);
    }
    private static boolean isValidPassportNo(String str)
    {
        // Regex to check valid.
        // passport number of India
        String regex = "^[A-PR-WYa-pr-wy][1-9]\\d"
                + "\\s?\\d{4}[1-9]$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }
        // Find match between given string
        // and regular expression
        // using Pattern.matcher()
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }
}

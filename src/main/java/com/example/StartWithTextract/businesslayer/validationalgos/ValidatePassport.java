package com.example.StartWithTextract.businesslayer.validationalgos;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ValidatePassport {
    public ValidatePassport(){
    }
    public static boolean isValidPassport(String text){
           return isValidPassportNo(text);
    }
    private static boolean isValidPassportNo(String str)
    {
        String regex = "^[A-PR-WYa-pr-wy][1-9]\\d"
                + "\\s?\\d{4}[1-9]$";
        Pattern p = Pattern.compile(regex);
        if (str == null) {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
}

package com.example.StartWithTextract.BusinessLayer.ValidationAlgos;

public class ValidateVotercard {
    public ValidateVotercard(){};

     public boolean validate(String s){
         return isVoterCard(s);
     }
     private boolean isVoterCard(String s){

         String shouldCharacters=s.substring(0,3);
         String shouldNumbers=s.substring(3);

         return allCharacters(shouldCharacters) && allNumbers(shouldNumbers);
     }

     private boolean allCharacters(String s){

         for(char ch:s.toCharArray()){
             if(!Character.isAlphabetic(ch)) return false;
         }
         return true;
     }
     private boolean allNumbers(String s){

         for(char ch:s.toCharArray()){
             if(!Character.isDigit(ch)) return false;
         }
         return true;
     }
}

package com.example.StartWithTextract.BusinessLayer.ValidationAlgos;

import java.util.HashMap;
import java.util.HashSet;

public class ValidateWithJaroWrinkler {


    public ValidateWithJaroWrinkler(){

    }
    private double jaro_distance(String s1, String s2)
    {
        // If the Strings are equal
        if (s1.equals(s2))
            return 1.0;

        // Length of two Strings
        int len1 = s1.length(),
                len2 = s2.length();

        // Maximum distance upto which matching
        // is allowed
        int max_dist = (int) (Math.floor(Math.max(len1, len2) / 2) - 1);

        // Count of matches
        int match = 0;

        // Hash for matches
        int hash_s1[] = new int[s1.length()];
        int hash_s2[] = new int[s2.length()];

        // Traverse through the first String
        for (int i = 0; i < len1; i++)
        {
            for (int j = Math.max(0, i - max_dist);
                 j < Math.min(len2, i + max_dist + 1); j++)
                if (s1.charAt(i) == s2.charAt(j) && hash_s2[j] == 0)
                {
                    hash_s1[i] = 1;
                    hash_s2[j] = 1;
                    match++;
                    break;
                }
        }
        if (match == 0)
            return 0.0;

        // Number of transpositions
        double t = 0;

        int point = 0;

        // Count number of occurances
        // where two characters match but
        // there is a third matched character
        // in between the indices
        for (int i = 0; i < len1; i++)
            if (hash_s1[i] == 1)
            {

                // Find the next matched character
                // in second String
                while (hash_s2[point] == 0)
                    point++;

                if (s1.charAt(i) != s2.charAt(point++) )
                    t++;
            }

        t /= 2;
        double ans= (((double)match) / ((double)len1)
                + ((double)match) / ((double)len2)
                + ((double)match - t) / ((double)match))
                / 3.0;

        return ans*100;
    }
    public String checkParticularSubstring(String s, HashMap<String,Integer> keywords, HashSet<String> KeywordsDone){
        double maxc=0.0;
        String myKey="";
        for(String keys:keywords.keySet()){

            if(!KeywordsDone.contains(keys)){
                double jdis= jaro_distance(keys,s);

                if(jdis >maxc){
                    maxc=jdis;
                    myKey=keys;
                }
            }
        }

        if(maxc<=85.0 ) return "";
//        KeywordsDone.add(myKey);
//        return keywords.get(myKey);
         return myKey;
    }

}

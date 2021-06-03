package com.example.StartWithTextract.BusinessLayer.AmazonTextract;

public class Credentials {
    private String accessKey="";
    private String secretKey="";
     Credentials(){
         this.accessKey="AKIA6N6JM4WQTASBAB2Y";
         this.secretKey="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";
     }
     public String getAccessKey(){
         return this.accessKey;
     }
     public String getsecretKey(){
         return this.secretKey;
     }
}

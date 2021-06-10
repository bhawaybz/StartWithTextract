package com.example.StartWithTextract.model;

public class UrlModel {
    private String url1;
    private String url2;

    public UrlModel(String url1, String url2) {
        this.url1 = url1;
        this.url2 = url2;
    }

    @Override
    public String toString(){
        return "Url1- "+this.url1+" /n"+" Url2- "+this.url2+" ";
    }
    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }
}

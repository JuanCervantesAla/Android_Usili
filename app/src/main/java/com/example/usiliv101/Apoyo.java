package com.example.usiliv101;

public class Apoyo {
    String Id,Url,Id2,Url2;

    public Apoyo(){

    }

    public Apoyo(String id, String url,String id2, String url2) {
        Id = id;
        Url = url;
        Id2 = id2;
        Url2 = url2;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getId2() {
        return Id2;
    }

    public void setId2(String id2) {
        Id2 = id2;
    }

    public String getUrl2() {
        return Url2;
    }

    public void setUrl2(String url2) {
        Url2 = url2;
    }
}
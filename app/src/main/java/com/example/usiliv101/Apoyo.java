package com.example.usiliv101;

public class Apoyo {
    String Id,Url;

    public Apoyo(){

    }

    public Apoyo(String id, String url) {
        Id = id;
        Url = url;
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
}

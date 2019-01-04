package com.example.mosaab.newsreader;

public class news {

    private int id ;
    private String Title;
    private String Detial;

    public news() {
    }

    public news(int id, String title, String detial) {
        this.id = id;
        Title = title;
        Detial = detial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDetial() {
        return Detial;
    }

    public void setDetial(String detial) {
        Detial = detial;
    }
}

package com.example.mosaab.newsreader.Model;

import java.util.ArrayList;

public class TechCrunch {

    private String status;
    private float totalResults;
    ArrayList<TechCrunchArticles> articles = new ArrayList<>();




    public ArrayList<TechCrunchArticles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<TechCrunchArticles> articles) {
        this.articles = articles;
    }

// Getter Methods

    public String getStatus() {
        return status;
    }

    public float getTotalResults() {
        return totalResults;
    }

    // Setter Methods

    public void setStatus( String status ) {
        this.status = status;
    }

    public void setTotalResults( float totalResults ) {
        this.totalResults = totalResults;
    }
}

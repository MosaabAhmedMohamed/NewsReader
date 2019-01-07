package com.example.mosaab.newsreader.Model;

import java.util.ArrayList;

public class news_api {

    private String status;
    private float totalResults;
    ArrayList<NewsArticles> articles = new ArrayList<>();




    public ArrayList<NewsArticles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsArticles> articles) {
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

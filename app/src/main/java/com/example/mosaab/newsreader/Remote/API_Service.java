package com.example.mosaab.newsreader.Remote;

import com.example.mosaab.newsreader.Model.news_api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Service {


    @GET("top-headlines")
    Call<news_api> get_News_Json(@Query("sources") String sources, @Query("apiKey") String Key);

}

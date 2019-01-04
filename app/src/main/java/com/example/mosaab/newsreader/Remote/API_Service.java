package com.example.mosaab.newsreader.Remote;

import com.example.mosaab.newsreader.Model.TechCrunch;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Service {



    @GET("top-headlines")
    Call<TechCrunch> getTechCrunch_News(@Query("category") String Category, @Query("apiKey") String Key);

}

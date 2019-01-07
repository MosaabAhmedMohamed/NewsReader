package com.example.mosaab.newsreader.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.example.mosaab.newsreader.Common.Common;
import com.example.mosaab.newsreader.Model.LocalSavedAPIS;
import com.example.mosaab.newsreader.Model.NewsArticles;
import com.example.mosaab.newsreader.Model.news_api;
import com.example.mosaab.newsreader.Remote.API_Service;
import com.example.mosaab.newsreader.Remote.RetrofitClient;
import java.util.ArrayList;
import java.util.Iterator;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class loadDataScheduler extends BroadcastReceiver {

    public static final String TAG = "loadDataScheduler";

    private API_Service api_service;
    private ArrayList<LocalSavedAPIS> savedApis ;
    private ArrayList<NewsArticles> update_localNews_List;
    private Iterator<LocalSavedAPIS> iterator ;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (Common.isConnectedToInternet())
        {
            savedApis = new ArrayList<>();

            Paper.init(context);

            if (Paper.book().read(Common.SavedAPIS_Key) != null)
            {
                savedApis = Paper.book().read(Common.SavedAPIS_Key);
            }
            Log.d(TAG, "onReceive: ");
            iterator = savedApis.iterator();
            while (iterator.hasNext())
            {
                InitRetorFit();

                final LocalSavedAPIS item = iterator.next();
                api_service.get_News_Json(item.getApiSourceName(),Common.API_KEY)
                        .enqueue(new Callback<news_api>() {
                            @Override
                            public void onResponse(Call<news_api> call, Response<news_api> response) {
                                if (response.isSuccessful())
                                {
                                    Update_localData(response.body(),item);
                                }
                                else
                                {
                                    Log.d(TAG, "onResponse: "+response.message());
                                }
                            }

                            @Override
                            public void onFailure(Call<news_api> call, Throwable t) {
                                Log.d(TAG, "onFailure: "+t.getMessage());
                            }
                        });
            }
        }

    }

    private void InitRetorFit() {
        api_service = RetrofitClient.getInstance(Common.APIS_URL).create(API_Service.class);
        Log.d(TAG, "InitRetorFit: ");
    }

    private void Update_localData(news_api body, LocalSavedAPIS item) {
        news_api news_api = body;
        update_localNews_List = new ArrayList<>();
        update_localNews_List.addAll(news_api.getArticles());
        Paper.book().delete(item.getKeySavedData());
        Log.d(TAG, "onResponse: "+Paper.book().getAllKeys());
        Paper.book().write(item.getKeySavedData(),update_localNews_List);
        Log.d(TAG, "onResponse: "+Paper.book().getAllKeys());
        Log.d(TAG, "onResponse: "+Paper.book().read(item.getKeySavedData()));
    }


}

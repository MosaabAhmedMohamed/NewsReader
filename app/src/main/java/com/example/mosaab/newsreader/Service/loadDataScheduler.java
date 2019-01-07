package com.example.mosaab.newsreader.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.mosaab.newsreader.Remote.API_Service;

import io.paperdb.Paper;


public class loadDataScheduler extends BroadcastReceiver {

    public static final String TAG = "loadDataScheduler";

    private API_Service api_service;

    @Override
    public void onReceive(Context context, Intent intent) {

        Paper.init(context);

        
       // Paper.book().destroy();

        //api_service.get_News_Json()


    }
}

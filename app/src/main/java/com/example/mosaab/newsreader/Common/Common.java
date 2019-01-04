package com.example.mosaab.newsreader.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mosaab.newsreader.Remote.API_Service;
import com.example.mosaab.newsreader.Remote.RetrofitClient;

public class Common {

    public static final String TechCrunch = "Tech Crunch";
    public static final String TechCrunch_API_KEY ="6c29d74094a34bf5be92d544a0f86f30";
    public static final String TechCrunch_URL = "https://newsapi.org/v2/";

    public static final String Mashable = "Mashable";
    public static final String BusinessInsider = "Business Insider";




    public static boolean  isConnectedToInternet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if(connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if(info != null)
            {
                for (int i= 0;i<info.length;i++)
                {
                    if(info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }

            }
        }
        return false;
    }
}

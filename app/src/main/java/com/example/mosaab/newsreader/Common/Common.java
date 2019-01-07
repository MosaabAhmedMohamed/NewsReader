package com.example.mosaab.newsreader.Common;

import java.io.IOException;


public class Common {

    public static final String TechCrunch = "TechCrunch";
    public static final String TECHCrunchSource = "techcrunch";
    public static final String API_KEY ="6c29d74094a34bf5be92d544a0f86f30";

    public static final String Mashable = "Mashable";
    public static final String MashableSource = "mashable";

    public static final String BusinessInsider = "BusinessInsider";
    public static final String BusinessInsiderSource = "business-insider";

    public static final String SavedAPIS_Key = "SavedAPIS_Key";

    public static final String APIS_URL = "https://newsapi.org/v2/";



    public static boolean  isConnectedToInternet()
    {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}

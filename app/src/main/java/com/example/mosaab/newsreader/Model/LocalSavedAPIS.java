package com.example.mosaab.newsreader.Model;

public class LocalSavedAPIS {

    private String ApiSourceName,KeySavedData,TapsName;

    public LocalSavedAPIS(String apiSourceName, String keySavedData, String tapsName) {
        ApiSourceName = apiSourceName;
        KeySavedData = keySavedData;
        TapsName = tapsName;
    }

    public String getApiSourceName() {
        return ApiSourceName;
    }

    public void setApiSourceName(String apiSourceName) {
        ApiSourceName = apiSourceName;
    }

    public String getKeySavedData() {
        return KeySavedData;
    }

    public void setKeySavedData(String keySavedData) {
        KeySavedData = keySavedData;
    }

    public String getTapsName() {
        return TapsName;
    }

    public void setTapsName(String tapsName) {
        TapsName = tapsName;
    }
}

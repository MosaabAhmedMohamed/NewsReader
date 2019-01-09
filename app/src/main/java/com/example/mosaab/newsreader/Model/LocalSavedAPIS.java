package com.example.mosaab.newsreader.Model;

public class LocalSavedAPIS {

    private String ApiSourceName, KeySavedData, TapsName;
    private boolean IsWebview;

    public LocalSavedAPIS(String apiSourceName, String keySavedData, String tapsName, boolean isWebview) {
        ApiSourceName = apiSourceName;
        KeySavedData = keySavedData;
        TapsName = tapsName;
        IsWebview = isWebview;
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

    public boolean isWebview() {
        return IsWebview;
    }

    public void setWebview(boolean webview) {
        IsWebview = webview;
    }
}

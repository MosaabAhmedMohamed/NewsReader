package com.example.mosaab.newsreader.Model;

public class Providers {

    private String provider_name;
    private boolean Active_provider;

    public Providers(String provider_name, boolean active_provider) {
        this.provider_name = provider_name;
        Active_provider = active_provider;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public boolean isActive_provider() {
        return Active_provider;
    }

    public void setActive_provider(boolean active_provider) {
        Active_provider = active_provider;
    }
}

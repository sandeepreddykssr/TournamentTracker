package com.sandeepreddy.tournament.dto;

import com.google.gson.annotations.SerializedName;

public class FBPictureData {
    @SerializedName("is_silhouette")
    private boolean isSilhouette;
    @SerializedName("url")
    private String url;

    public boolean getIsSilhouette() {
        return isSilhouette;
    }

    public void setIsSilhouette(boolean isSilhouette) {
        this.isSilhouette = isSilhouette;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

package com.sandeepreddy.tournament.dto;

import com.google.gson.annotations.SerializedName;

public class FBPicture {
    @SerializedName("data")
    private FBPictureData pictureData;

    public FBPictureData getPictureData() {
        return pictureData;
    }

    public void setPictureData(FBPictureData pictureData) {
        this.pictureData = pictureData;
    }
}

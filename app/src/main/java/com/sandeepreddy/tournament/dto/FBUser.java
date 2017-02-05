package com.sandeepreddy.tournament.dto;


import com.google.gson.annotations.SerializedName;

/**
 * Created by sandeepreddy on 25/1/17.
 */

public class FBUser {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("picture")
    private FBPicture picture;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FBPicture getPicture() {
        return picture;
    }

    public void setPicture(FBPicture picture) {
        this.picture = picture;
    }
}

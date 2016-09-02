package com.test.androidtrainingtest.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Qushay on 30/08/2016.
 */
public class Question {
    @SerializedName("title")
    private String title;
    @SerializedName("image")
    private String image;

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}

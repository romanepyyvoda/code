package com.example.dreamcarfinder.CarObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Car Image resources
 */
public class CarMedia {

    //member variable
    @SerializedName("photo_links")
    @Expose
    private ArrayList<String> photo_links;

    public ArrayList<String> getPhoto_links() {
        return photo_links;
    }
}

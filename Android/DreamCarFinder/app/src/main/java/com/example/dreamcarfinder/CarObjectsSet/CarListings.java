package com.example.dreamcarfinder.CarObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * List of cars which got from api
 */
public class CarListings {

    //member variable
    @SerializedName("listings")
    @Expose
    private ArrayList<Car> cars;

    public ArrayList<Car> getCars() {
        return cars;
    }

    @Override
    public String toString() {
        return "CarListings{" +
                "cars=" + cars +
                '}';
    }
}

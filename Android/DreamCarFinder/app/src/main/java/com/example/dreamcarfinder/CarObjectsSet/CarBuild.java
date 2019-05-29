package com.example.dreamcarfinder.CarObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * To create a Car DataModel for Network communication
 */
public class CarBuild {

    //members variables
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("make")
    @Expose
    private String make;
    @SerializedName("model")
    @Expose
    private String model;
    @SerializedName("body_type")
    @Expose
    private String body_type;
    @SerializedName("vehicle_type")
    @Expose
    private String vehicle_type;
    @SerializedName("drivetrain")
    @Expose
    private String drivetrain;
    @SerializedName("fuel_type")
    @Expose
    private String fuel_type;
    @SerializedName("engine_size")
    @Expose
    private String engine_size;
    @SerializedName("doors")
    @Expose
    private String doors;
    @SerializedName("cylinders")
    @Expose
    private String cylinders;
    @SerializedName("made_in")
    @Expose
    private String made_in;
    @SerializedName("trim_r")
    @Expose
    private String trim_r;

    public String getYear() {
        return year;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    @Override
    public String toString() {
        return  "Year: " + year + '\n' +
                "Make: " + make + '\n' +
                "Model: " + model + '\n' +
                "Body Type: " + body_type + '\n' +
                "Vehicle Type: " + vehicle_type + '\n' +
                "Drivetrain: " + drivetrain + '\n' +
                "Fuel Type: " + fuel_type + '\n' +
                "Engine Size: " + engine_size + '\n' +
                "Doors: " + doors + '\n' +
                "Cylinders: " + cylinders + '\n' +
                "Made In: " + made_in + '\n' +
                "Trim: " + trim_r ;
    }
}

package com.example.dreamcarfinder.CarObjectsSet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * To create a Car DataModel for Network communication
 */
public class Car {

    // Member variables representing the title and information about the game.
    @SerializedName("build")
    @Expose
    private CarBuild build;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("dealer")
    @Expose
    private CarLocation location;
    @SerializedName("vdp_url")
    @Expose
    private String link;
    @SerializedName("media")
    @Expose
    private CarMedia media;


    public CarBuild getBuild() {
        return build;
    }

    public void setBuild(CarBuild build) {
        this.build = build;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public CarLocation getLocation() {
        return location;
    }

    public void setLocation(CarLocation location) {
        this.location = location;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public CarMedia getMedia() {
        return media;
    }

    public void setMedia(CarMedia media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Car{" +
                "build=" + build +
                ", price='" + price + '\'' +
                ", location=" + location +
                ", link='" + link + '\'' +
                ", media=" + media +
                '}';
    }
}

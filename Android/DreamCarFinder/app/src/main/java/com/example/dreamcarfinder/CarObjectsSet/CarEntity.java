package com.example.dreamcarfinder.CarObjectsSet;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


/**
 * Car DataModel for DB communication (create a table in DB)
 */

@Entity(tableName = "car_table")
public class CarEntity {

    //member variables
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "year")
    @Nullable
    private String year;
    @ColumnInfo(name = "make")
    @Nullable
    private String make;
    @ColumnInfo(name = "model")
    @Nullable
    private String model;
    @ColumnInfo(name = "price")
    @Nullable
    private String price;
    @ColumnInfo(name = "description")
    @Nullable
    private String description;
    @ColumnInfo(name = "weblink")
    @Nullable
    private String weblink;
    @ColumnInfo(name = "imglink")
    @Nullable
    private String imglink;
    @ColumnInfo(name = "location")
    @Nullable
    private String location;

    public CarEntity(int id,String year, String make, String model, String price, String description, String weblink, String imglink, String location){
        this.id = id;
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.description = description;
        this.weblink = weblink;
        this.imglink = imglink;
        this.location = location;
    }
    @Ignore
    public CarEntity(){}
    @Ignore
    public CarEntity(String year, String make, String model, String price, String description, String weblink, String imglink, String location){
        this.year = year;
        this.make = make;
        this.model = model;
        this.price = price;
        this.description = description;
        this.weblink = weblink;
        this.imglink = imglink;
        this.location = location;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWeblink() {
        return weblink;
    }

    public void setWeblink(String weblink) {
        this.weblink = weblink;
    }

    public String getImglink() {
        return imglink;
    }

    public void setImglink(String imglink) {
        this.imglink = imglink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "CarEntity{" +
                "id=" + id +
                ", year='" + year + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", weblink='" + weblink + '\'' +
                ", imglink='" + imglink + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

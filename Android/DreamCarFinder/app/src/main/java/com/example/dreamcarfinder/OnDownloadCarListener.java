package com.example.dreamcarfinder;

import com.example.dreamcarfinder.CarObjectsSet.Car;

import java.util.List;

public interface OnDownloadCarListener {

    /**
     *  Facilitates inserting data into recycler view when downloading is complete
     * @param carList
     */
    void onDownloadCarListener (List<Car> carList);
}

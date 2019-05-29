package com.example.dreamcarfinder;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

import java.util.List;

@Dao
public interface CarDAO {

    //Insert a certain car into DB
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CarEntity car);

    //Delete a certain car from DB
    @Delete
    void deleteCar(CarEntity car);

    //Get all cars from DB
    @Query("SELECT * from car_table ORDER BY id ASC")
    LiveData<List<CarEntity>> getAllCars();

}

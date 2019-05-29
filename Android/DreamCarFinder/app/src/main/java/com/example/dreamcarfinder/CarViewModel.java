package com.example.dreamcarfinder;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

import java.util.List;

/**
 * The CarViewModel provides the interface between the UI and the data layer of the app,
 * represented by the repository
 */
public class CarViewModel extends AndroidViewModel {

    //member variables
    private CarRepository mRepository;
    private LiveData<List<CarEntity>> mAllCars;

    public CarViewModel(Application application) {
        super(application);
        mRepository = new CarRepository(application);
        mAllCars = mRepository.getAllCars();
    }

    public LiveData<List<CarEntity>> getAllCars() {
        return mAllCars;
    }

    public void insert(CarEntity car) {
        mRepository.insert(car);
    }

    public void deleteCar(CarEntity car) {
        mRepository.deleteCar(car);
    }
}

package com.example.dreamcarfinder;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

import java.util.List;

public class CarRepository {

    private CarDAO mCarDao;
    private LiveData<List<CarEntity>> mAllCars;
    private CarAPI mCarAPI;

    CarRepository(Application application) {
        // DB
        CarRoomDatabase db = CarRoomDatabase.getDatabase(application);
        mCarDao = db.carDao();
        mAllCars = mCarDao.getAllCars();
    }

    public LiveData<List<CarEntity>> getAllCars() {
        return mAllCars;
    }

    public void insert(CarEntity car) {
        new insertAsyncTask(mCarDao).execute(car);
    }


    // Need to run off main thread
    public void deleteCar(CarEntity car) {
        new deleteCarAsyncTask(mCarDao).execute(car);
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a car into the database.
     */
    private static class insertAsyncTask extends AsyncTask<CarEntity, Void, Void> {

        private CarDAO mAsyncTaskDao;

        insertAsyncTask(CarDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CarEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    /**
     *  Delete a single car from the database.
     */
    private static class deleteCarAsyncTask extends AsyncTask<CarEntity, Void, Void> {
        private CarDAO mAsyncTaskDao;

        deleteCarAsyncTask(CarDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final CarEntity... params) {
            mAsyncTaskDao.deleteCar(params[0]);
            return null;
        }
    }
}

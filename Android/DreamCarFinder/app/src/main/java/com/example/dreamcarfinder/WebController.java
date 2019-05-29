package com.example.dreamcarfinder;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.dreamcarfinder.CarObjectsSet.Car;
import com.example.dreamcarfinder.CarObjectsSet.CarListings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class WebController {
    // Member variables
    static final String BASE_URL = "https://marketcheck-prod.apigee.net/v1/";
    private List<Car> mCarList = new ArrayList<>();
    static final String ERROR_MSG = "Error: check your network connection";
    static final String API_KEY = "FPgYHmiqEgAXnpEmLsUK5hx07AqM4nTZ";

    /**
     * Method gets List of all legislatures from OpenNorthAPI and
     * stores in mLegislature variable.
     */
    public void downloadAllCars(final Context context, final OnDownloadCarListener listener, String year, String make, String model, String price) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CarAPI api = retrofit.create(CarAPI.class);
        Call<CarListings> call = api.getAllCars(API_KEY,year, make, model, price);
        call.enqueue(new Callback<CarListings>() {
            @Override
            public void onResponse(Call<CarListings> call, Response<CarListings> response) {
                if(!response.isSuccessful()){
                    String error_msg = "Conn.error: " + response.code();
                    Toast toast = Toast.makeText(context, error_msg, Toast.LENGTH_LONG);
                    toast.show();
                }
                if(response.body() != null){
                    mCarList = response.body().getCars();
                }

                // passing a data to an activity whe downloading is complete
                listener.onDownloadCarListener(mCarList);

            }

            @Override
            public void onFailure(Call<CarListings> call, Throwable t) {
                Log.e(TAG, "onFailure: Something went wrong: " + t.getMessage());
                Toast toast = Toast.makeText(context, ERROR_MSG, Toast.LENGTH_LONG);
                toast.show();
                return;
            }
        });
    }
}

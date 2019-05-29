package com.example.dreamcarfinder;




import com.example.dreamcarfinder.CarObjectsSet.CarListings;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CarAPI {


    @GET("search?&rows=50")
    /**
     * Create query to get all cars as per search criteria
     */
    Call<CarListings> getAllCars(@Query("api_key") String key,
                                 @Query("year") String year,
                                 @Query("make") String make,
                                 @Query("model") String model,
                                 @Query("price_range") String price);
}

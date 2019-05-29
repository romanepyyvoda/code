package com.example.dreamcarfinder;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

/**
 * CarRoomDatabase. Includes code to create the database.
 * After the app creates the database, all further interactions
 * with it happen through the CarViewModel.
 */
@Database(entities = {CarEntity.class}, version = 1, exportSchema = false)
public abstract class CarRoomDatabase extends RoomDatabase {

    public abstract CarDAO carDao();

    private static CarRoomDatabase INSTANCE;

    public static CarRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CarRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CarRoomDatabase.class, "car_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();

                }
            }
        }
        return INSTANCE;
    }

}

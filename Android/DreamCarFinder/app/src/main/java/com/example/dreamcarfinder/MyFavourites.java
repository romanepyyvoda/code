package com.example.dreamcarfinder;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * To create my Favorites List
 */
public class MyFavourites extends AppCompatActivity {

    // Member variables.
    private CarViewModel mCarViewModel;
    private RecyclerView mRecyclerView;
    private List<CarEntity> mCarsData;
    private CarAdapterDB mAdapter;
    private GridLayoutManager mLayoutManager;
    private static int mPositionIndex;
    private static int mTopView;
    private Parcelable recyclerViewState;
    private final String KEY_RECYCLER_STATE = "recycler_state";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourites);

        int gridColumnCount = 1;

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerViewFavourites);

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(new
                GridLayoutManager(this, gridColumnCount));

        // Initialize the ArrayList that will contain the data.
        mCarsData = new ArrayList<>();

        // Initialize the adapter and set it to the RecyclerView.
        mAdapter = new CarAdapterDB(this, mCarsData);
        mRecyclerView.setAdapter(mAdapter);

        // Setup the CarViewModel
        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);
        // Get all the cars from the database
        // and associate them to the adapter
        mCarViewModel.getAllCars().observe(this, new Observer<List<CarEntity>>() {
            @Override
            public void onChanged(@Nullable List<CarEntity> carEntities) {
                // Update the cached copy of the cars in the adapter.
                if(carEntities == null){ return;}
                mCarsData = carEntities;
                initializeData();
            }
        });

        // Add the functionality to swipe items in the
        // RecyclerView to delete the swiped item.
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    // We are not implementing onMove() in this app.
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    // When the use swipes a car,
                    // delete that car from the database.
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        CarEntity myCar = mAdapter.getCarAtPosition(position);
                        Toast.makeText(MyFavourites.this,
                                "This car was removed from the favourites", Toast.LENGTH_LONG).show();

                        // Delete the word.
                        mCarViewModel.deleteCar(myCar);

                        mCarsData.remove(viewHolder.getAdapterPosition());
                        // Notify the adapter.
                        mAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });
        // Attach the item touch helper to the recycler view.
        helper.attachToRecyclerView(mRecyclerView);
    }

    /**
     * Initialize the cars data from resources.
     */
    public void initializeData() {

        mRecyclerView = findViewById(R.id.recyclerViewFavourites);

        int gridColumnCount = 1;

        if(mLayoutManager == null){
            mLayoutManager = new GridLayoutManager(this, gridColumnCount);
            if (mPositionIndex != -1) {
                mLayoutManager.scrollToPositionWithOffset(mPositionIndex, mTopView);
            }
        }

        // Set the Layout Manager.
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize the adapter and set it to the RecyclerView.
        if(mCarsData == null){ return; }
        mAdapter = new CarAdapterDB(this, mCarsData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if(recyclerViewState != null){
            recyclerViewState = mLayoutManager.onSaveInstanceState();
            outState.putParcelable(KEY_RECYCLER_STATE, recyclerViewState);

            mPositionIndex= mLayoutManager.findFirstVisibleItemPosition();
            View startView = mRecyclerView.getChildAt(0);
            mTopView = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

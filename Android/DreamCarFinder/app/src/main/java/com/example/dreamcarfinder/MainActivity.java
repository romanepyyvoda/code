package com.example.dreamcarfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.dreamcarfinder.CarObjectsSet.Car;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDownloadCarListener, MethodCaller {

    // Member variables.
    private RecyclerView mRecyclerView;
    private static List<Car> mCarsData;
    private CarsAdapterNetwork mAdapter;
    private static int mTopView;
    private static int mPositionIndex;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private Parcelable recyclerViewState;
    private GridLayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if  (mCarsData != null) {
            initializeData();
        }
        else {
            int gridColumnCount = 1;

            // Initialize the RecyclerView.
            mRecyclerView = findViewById(R.id.recyclerView);

            // Set the Layout Manager.
            mRecyclerView.setLayoutManager(new
                    GridLayoutManager(this, gridColumnCount));
            // Initialize the ArrayList that will contain the dat
            //
            mCarsData = new ArrayList<>();
            // Initialize the adapter and set it to the RecyclerView.
            mAdapter = new CarsAdapterNetwork(this, mCarsData, this);
            mRecyclerView.setAdapter(mAdapter);
        }
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
    public  void positionSaver(){
        mPositionIndex= mLayoutManager.findFirstVisibleItemPosition();
        View startView = mRecyclerView.getChildAt(0);
        mTopView = (startView == null) ? 0 : (startView.getTop() - mRecyclerView.getPaddingTop());
    }


    @Override
    /**
     * Create the main Menu
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    /**
     * Create Menu items in the main menu
     */
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_search) {
            unWrapSearch();
        }
        if (id == R.id.go_to_favourites) {

            Intent intent = new Intent(MainActivity.this,
                    MyFavourites.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Initialize the cars data from resources.
     */
    public void initializeData() {

        mRecyclerView = findViewById(R.id.recyclerView);

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
        mAdapter = new CarsAdapterNetwork(this, mCarsData, this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * The Method are collecting all fields to create a query
     * @param view
     */
    public void searchForCars(View view) {

        String make, model, priceFrom, priceTo, year, price = "";

        EditText makeET = (EditText) findViewById(R.id.make_field);
        make = makeET.getText().toString();
        EditText modelET = (EditText) findViewById(R.id.model_field);
        model = modelET.getText().toString();
        EditText yearET = (EditText) findViewById(R.id.year_field);
        year = yearET.getText().toString();
        EditText priceFromET = (EditText) findViewById(R.id.pricefrom_field);
        priceFrom = priceFromET.getText().toString();
        EditText priceToET = (EditText) findViewById(R.id.priceto_field);
        priceTo = priceToET.getText().toString();

        if (make.isEmpty()) {
            make = null;
        }
        if (model.isEmpty()) {
            model = null;
        }
        if (year.isEmpty()) {
            year = null;
        }
        if (!priceFrom.isEmpty() || !priceTo.isEmpty()) {
            if (priceFrom.isEmpty()) {
                priceFrom = "0";
            }
            if (priceTo.isEmpty()) {
                priceTo = "1000000";
            }
            price = priceFrom + "-" + priceTo;
        }
        else if(priceFrom.isEmpty() && priceTo.isEmpty()){ price = null;}

        WebController wc = new WebController();
        wc.downloadAllCars(this,this, year, make, model, price);

        wrapSearch();
    }

    @Override
    /**
     * Populate data into Car list when downloading is complete
     */
    public void onDownloadCarListener(List<Car> carList) {
        mCarsData = carList;
        //Initialize the cars data from resources.
        initializeData();
    }

    public void wrapSearchDrawer(View view) {
        wrapSearch();
    }

    /**
     * Wrap the search window in toolbar
     */
    public void  wrapSearch(){
        ConstraintLayout search_area = findViewById(R.id.car_search_main);
        search_area.setLayoutParams(new CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT, 0));
    }

    /**
     * Unwrap the search window in search car layout
     */
    public void  unWrapSearch(){
        ConstraintLayout search_area = findViewById(R.id.car_search_main);
        search_area.setLayoutParams(new CoordinatorLayout.LayoutParams(
                CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT));
        search_area.bringToFront();
    }
}

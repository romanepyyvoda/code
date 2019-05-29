package com.example.dreamcarfinder;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dreamcarfinder.CarObjectsSet.CarEntity;

/**
 * To display the details of selected car
 */
public class CarDescription extends AppCompatActivity {

    //variable members
    private String carsWebLink;
    private String carsLocation;
    private CarViewModel mCarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_description);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set up the WordViewModel.
        mCarViewModel = ViewModelProviders.of(this).get(CarViewModel.class);

        // Initialize the views.
        ImageView carsImage = findViewById(R.id.carImageDetail);
        TextView carsPrice = findViewById(R.id.priceDetail);
        TextView carsLocationText = findViewById(R.id.locationDetail);
        TextView carsDescription = findViewById(R.id.descriptionDetail);

        // Set the text from the Intent extra.
        carsPrice.setText(getIntent().getStringExtra("price"));
        carsLocationText.setText(getIntent().getStringExtra("location"));
        carsDescription.setText(getIntent().getStringExtra("description"));

        // Load the image using the Glide library and the Intent extra.
        String imgResource = getIntent().getStringExtra("image_resource");
        Glide.with(this).load(imgResource)
                .error(R.drawable.default_car)
                .into(carsImage);

        // Make overflowing description text scroll
        carsDescription.setMovementMethod(new ScrollingMovementMethod());

        // Set car's name into action bar
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        // Get a link for car's web page
        carsWebLink = getIntent().getStringExtra("link");

        //Get car's location
        carsLocation = getIntent().getStringExtra("location");
    }

    /**
     * Method opens car's web page when button is clicked
     * @param view
     */
    public void redirectToBrowser(View view) {
        // Parse the URI and create the intent.
        Uri webpage = Uri.parse(carsWebLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Find an activity to hand the intent and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    /**
     * Add a certain car into My Favorites list (DB) using a clickable button (drawable icon)
     * @param view
     */
    public void like(View view) {

        ImageView like = findViewById(R.id.likeButton);
        like.setImageResource(R.drawable.ic_like);
        Toast toast = Toast.makeText(this, "This car was added to the favourites", Toast.LENGTH_LONG);
        toast.show();

        addCarToDB();
    }

    /**
     * Display a car location using Google map
     * @param view
     */
    public void goToCarLocation(View view) {

        String loc = carsLocation;

        // Parse the location and create the intent.
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }

    /**
     * Add a certain car to DB
     */
    public  void addCarToDB(){
        CarEntity carEntity = new CarEntity(
                getIntent().getStringExtra("year"),
                getIntent().getStringExtra("make"),
                getIntent().getStringExtra("model"),
                getIntent().getStringExtra("price"),
                getIntent().getStringExtra("description"),
                getIntent().getStringExtra("link"),
                getIntent().getStringExtra("image_resource"),
                getIntent().getStringExtra("location"));

        try {
            mCarViewModel.insert(carEntity);
        } catch (Exception e) {
            // This will catch any exception, because they are all descended from Exception
            Log.e("TAG", "onFailure: Something went wrong: " + e.getMessage());
            return;
        }
    }
}

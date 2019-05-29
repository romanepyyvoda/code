package com.example.dreamcarfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

/**
 * To display animated greeting when an application starts
 */
public class Greeting extends AppCompatActivity {

    //member variable
    private static int GREETING_TIME_OUT = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greeting);

        //locating view to load GIF into
        ImageView bootLoader = findViewById(R.id.imageView_loader);
        //access raw GIF image
        Glide.with(this).load(R.raw.loader).into(bootLoader);

        // Delayed greeting animation
        Thread greetingThread = new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(GREETING_TIME_OUT);
                } catch (Exception e) {

                } finally {

                    Intent mainIntent = new Intent(Greeting.this,
                            MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };
        greetingThread.start();

    }
}

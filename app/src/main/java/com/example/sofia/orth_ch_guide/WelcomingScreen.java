package com.example.sofia.orth_ch_guide;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

/**
 * Created by Sofia on 28.10.2016.
 */
public class WelcomingScreen extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcoming_layout);
        image = (ImageView)findViewById(R.id.imageView);
        image.setImageResource(R.drawable.loading);

        Thread welcomeThread = new Thread() {

            @Override
            public void run() {
                try {
                    super.run();
                    sleep(10000); //Delay of 10 seconds
                } catch (Exception e) {

                } finally {

                    Intent i = new Intent(WelcomingScreen.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}

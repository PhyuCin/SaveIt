package com.example.saveit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.Random;

public class LaunchScreen extends Activity {
    Thread splashTread;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launchscreen);

        imageView = (ImageView) findViewById(R.id.splashscreenImage);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //put splashscreen images ids into a list
        int[] ids = new int[]{R.drawable.splashscreen, R.drawable.splashscreen2,
                R.drawable.splashscreen3};

        //randomly set splashscreen image to be displayed
        Random randomGenerator = new Random();
        int r = randomGenerator.nextInt(ids.length);
        this.imageView.setImageDrawable(getResources().getDrawable(ids[r]));

        //run splashscreen for a period of time before showing the main activity screen
        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(LaunchScreen.this,
                            MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    LaunchScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    LaunchScreen.this.finish();
                }

            }
        };
        splashTread.start();
    }
}


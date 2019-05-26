package com.example.saveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    private SharedPreferences preferences;
    private LinearLayout mainLayout;
    private int background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences("value", MODE_PRIVATE);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
    }

    // handles backgrounds
    @Override
    protected void onStart() {
        super.onStart();

        //change background
        background = preferences.getInt("background number", 0);
        if (background == 0) {
            mainLayout.setBackgroundResource(R.drawable.bgone);
        } else if (background == 1) {
            mainLayout.setBackgroundResource(R.drawable.bgtwo);
        } else if (background == 2) {
            mainLayout.setBackgroundResource(R.drawable.bgthree);
        }
    }

    //on click of the play button
    public void goToPlayMenu(View view){
        Intent intent = new Intent(this, PlayMenu.class );
        startActivity(intent);
    }

    //on click of the highscores button
    public void goToHighScores(View view){
        Intent intent = new Intent(this, HighScores.class );
        startActivity(intent);
    }

    //on click of the settings button
    public void goToSettings(View view){
        Intent intent = new Intent(this, Settings.class );
        startActivity(intent);
    }
}

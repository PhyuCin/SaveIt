package com.example.saveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    private ArrayList<Integer> highScores = new ArrayList<Integer>();

    // set background
    private int background;
    private SharedPreferences preferences;
    private LinearLayout settingsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        preferences = getSharedPreferences("value", MODE_PRIVATE);
        settingsLayout = (LinearLayout) findViewById(R.id.settingsLayout);


    }

    // handles backgrounds
    @Override
    protected void onStart() {
        super.onStart();

        //change background
        background = preferences.getInt("background number", 0);
        if (background == 0) {
            settingsLayout.setBackgroundResource(R.drawable.bgone);
        } else if (background == 1) {
            settingsLayout.setBackgroundResource(R.drawable.bgtwo);
        } else if (background == 2) {
            settingsLayout.setBackgroundResource(R.drawable.bgthree);
        }
    }

    //on click of background 1
    public void changeToBackground1(View view){
        preferences.edit()
                .putInt("background number", 0)
                .apply();
        settingsLayout.setBackgroundResource(R.drawable.bgone);
        Toast.makeText(this, "Background changed to pink", Toast.LENGTH_SHORT).show();
    }

    //on click of background 2
    public void changeToBackground2(View view){
        preferences.edit()
                .putInt("background number", 1)
                .apply();
        settingsLayout.setBackgroundResource(R.drawable.bgtwo);
        Toast.makeText(this, "Background changed to orange", Toast.LENGTH_SHORT).show();
    }

    //on click of background 3
    public void changeToBackground3 (View view){
        preferences.edit()
                .putInt("background number", 2)
                .apply();
        settingsLayout.setBackgroundResource(R.drawable.bgthree);
        Toast.makeText(this, "Background changed to blue", Toast.LENGTH_SHORT).show();
    }

    //on click of dog 1
    public void changeToDog1(View view){
        preferences.edit()
                .putInt("dog number", 0)
                .apply();
        Toast.makeText(this, "Dog changed to brown", Toast.LENGTH_SHORT).show();
    }

    //on click of dog 2
    public void changeToDog2(View view){
        preferences.edit()
                .putInt("dog number", 1)
                .apply();
        Toast.makeText(this, "Dog changed to black", Toast.LENGTH_SHORT).show();
    }

    //on click of dog 3
    public void changeToDog3 (View view){
        preferences.edit()
                .putInt("dog number", 2)
                .apply();
        Toast.makeText(this, "Dog changed to dark brown", Toast.LENGTH_SHORT).show();
    }

    //on click of lives 3
    public void changeLivesTo3(View view){
        preferences.edit()
                .putInt("number of lives", 3)
                .apply();
        Toast.makeText(this, "Number of lives changed to 3", Toast.LENGTH_SHORT).show();
    }

    //on click of lkives 5
    public void changeLivesTo5(View view){
        preferences.edit()
                .putInt("number of lives", 5)
                .apply();
        Toast.makeText(this, "Number of lives changed to 5", Toast.LENGTH_SHORT).show();
    }

    //on click of lives 10
    public void changeLivesTo10 (View view){
        preferences.edit()
                .putInt("number of lives", 10)
                .apply();
        Toast.makeText(this, "Number of lives changed to 10", Toast.LENGTH_SHORT).show();
    }


    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    //on click of reset high scores
    public void resetHighScores(View view){
        FileHelper.writeDataEasy(highScores, this);
        Toast.makeText(this, "High scores have been reset", Toast.LENGTH_SHORT).show();
    }
}
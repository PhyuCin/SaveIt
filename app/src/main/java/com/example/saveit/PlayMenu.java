package com.example.saveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class PlayMenu extends AppCompatActivity{

    private SharedPreferences preferences;
    private LinearLayout playMenuLayout;
    private int background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);

        preferences = getSharedPreferences("value", MODE_PRIVATE);
        playMenuLayout = (LinearLayout) findViewById(R.id.playMenuLayout);

    }

    // handles backgrounds
    @Override
    protected void onStart() {
        super.onStart();

        //change background
        background = preferences.getInt("background number", 0);
        if (background == 0) {
            playMenuLayout.setBackgroundResource(R.drawable.bgone);
        } else if (background == 1) {
            playMenuLayout.setBackgroundResource(R.drawable.bgtwo);
        } else if (background == 2) {
            playMenuLayout.setBackgroundResource(R.drawable.bgthree);
        }
    }

    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }


    //handling the three buttons
    public void toEasy(View view){
        Intent intent = new Intent(this, EasyGame.class );
        startActivity(intent);
    }

    //on click of back button
    public void toHard(View view){

        Intent intent = new Intent(this, HardGame.class );
        startActivity(intent);
    }
}
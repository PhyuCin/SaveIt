package com.example.saveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayMenu extends AppCompatActivity{
    private Button back;
    private ImageButton easy, normal, hard;


    private SharedPreferences preferences;

    private final int EASY = 2;
    private final int NORMAL = 4;
    private final int HARD = 6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);

        preferences = getSharedPreferences("choices", MODE_PRIVATE);

        back = (Button) findViewById(R.id.playMenuBack);

        easy = (ImageButton) findViewById(R.id.easyButton);
        normal  = (ImageButton) findViewById(R.id.normalButton);
        hard = (ImageButton) findViewById(R.id.hardButton);

        //handling the three buttons
        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit()
                        .putInt("choices", EASY)
                        .apply();
                back.setText(String.format("%d", preferences.getInt("choices", 0)));
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit()
                        .putInt("choices", NORMAL)
                        .apply();
                back.setText(String.format("%d", preferences.getInt("choices", 0)));
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferences.edit()
                        .putInt("choices", HARD)
                        .apply();
                back.setText(String.format("%d", preferences.getInt("choices", 0)));
            }
        });

    }

    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

}
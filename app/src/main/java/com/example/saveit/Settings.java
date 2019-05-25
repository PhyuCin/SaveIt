package com.example.saveit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {
    private ArrayList<Integer> highScores = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    //on click of reset high scores
    public void resetHighScores(View view){
        FileHelper.writeData(highScores, this);
        Toast.makeText(this, "High scores have been reset", Toast.LENGTH_SHORT).show();
    }
}
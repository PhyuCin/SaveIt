package com.example.saveit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Collections;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

package com.example.saveit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class PlayMenu extends AppCompatActivity{
    private Button back;
    private Button easy, normal, hard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);

        back = (Button) findViewById(R.id.playMenuBack);

        easy = (Button) findViewById(R.id.easyButton);
        normal  = (Button) findViewById(R.id.normalButton);
        hard = (Button) findViewById(R.id.hardButton);

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
    public void toNormal(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    //on click of back button
    public void toHard(View view){

        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }
}
package com.example.saveit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class HighScores extends AppCompatActivity {

    private TextView first, second, third, fourth, fifth;
    private TextView[] allTextViews;
    private ArrayList<Integer> highScores = new ArrayList<Integer>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);

        first = (TextView) findViewById(R.id.first);
        second = (TextView) findViewById(R.id.second);
        third = (TextView) findViewById(R.id.third);
        fourth = (TextView) findViewById(R.id.fourth);
        fifth = (TextView) findViewById(R.id.fifth);

        allTextViews = new TextView[]{first, second, third, fourth, fifth};

        highScores = FileHelper.readData(this);
        System.out.println(highScores);

        //show on screen
        int num = highScores.size();
        for (int i = 0; i < num; ++i) {
            allTextViews[i].setText((i + 1) + "     ---     " + highScores.get(i) + "pts");
        }
    }



    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }
}

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

    private TextView firstEasy, secondEasy, thirdEasy, fourthEasy, fifthEasy;
    private TextView[] allTextViews;
    private ArrayList<Integer> highScores = new ArrayList<Integer>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_scores);

        firstEasy = (TextView) findViewById(R.id.firstEasy);
        secondEasy = (TextView) findViewById(R.id.secondEasy);
        thirdEasy = (TextView) findViewById(R.id.thirdEasy);
        fourthEasy = (TextView) findViewById(R.id.fourthEasy);
        fifthEasy = (TextView) findViewById(R.id.fifthEasy);

        allTextViews = new TextView[]{firstEasy, secondEasy, thirdEasy, fourthEasy, fifthEasy};

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

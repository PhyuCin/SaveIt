package com.example.saveit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PlayMenu extends AppCompatActivity {
    private Button back;
    private ImageButton easy, normal, hard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmenu);

        back = (Button) findViewById(R.id.playMenuBack);
        easy = (ImageButton) findViewById(R.id.easyButton);
        normal  = (ImageButton) findViewById(R.id.normalButton);
        hard = (ImageButton) findViewById(R.id.hardButton);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setText("Easy");
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setText("Normal");
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setText("Hard");
            }
        });

    }

    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

}
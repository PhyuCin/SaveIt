package com.example.saveit;

import android.content.ClipData;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EasyGame extends AppCompatActivity {
    // Screen Size
    private int screenWidth;
    private int screenHeight;
    private int margin;

    // TextViews
    private TextView eqn1, eqn2, eqn3, eqn4, eqn5, eqn6;

    // Positions
    private float eqn1x, eqn1y, eqn2x, eqn2y, eqn3x, eqn3y, eqn4x, eqn4y, eqn5x, eqn5y, eqn6x, eqn6y;


    // Initialise Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    // manage equations
    private EquationsManager equationsManager = new EquationsManager();
    private String[][] equationsList;

    private Random rand = new Random();
    private int answer1Num = 0;
    private int answer2Num = 0;
    private String[] equationsSetOne;
    private String[] equationsSetTwo;

    //index for random equation to be selected
    private int randomIndex;

    // text views
    private TextView lives;
    private TextView scoresDisplay;
    private TextView answer1;
    private TextView answer2;


    // correct status
    private boolean isCorrect;
    private int score = 0;

    private int count = 0;

    // lives
    private int livesCount = 5;

    // drag id
    private int draggedTextView;

    // speeds
    private float speed1 = 0.05f;
    private float speed2 = 0.1f;
    private float speed3 = 0.15f;
    private float speed4 = 0.20f;
    private float speed5 = 0.25f;
    private float speed6 = 0.3f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game);

        eqn1 = (TextView) findViewById(R.id.easyEqn1);
        eqn2 = (TextView) findViewById(R.id.easyEqn2);
        eqn3 = (TextView) findViewById(R.id.easyEqn3);
        eqn4 = (TextView) findViewById(R.id.easyEqn4);
        eqn5 = (TextView) findViewById(R.id.easyEqn5);
        eqn6 = (TextView) findViewById(R.id.easyEqn6);

        equationsList = equationsManager.getAllEquations();

        lives = (TextView) findViewById(R.id.lives);
        scoresDisplay = (TextView) findViewById(R.id.scoresDisplay);

        lives.setText("Lives: " + livesCount);
        scoresDisplay.setText("Points: " + score);

        answer1 =(TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);

        // choose random answers for the two answer areas
        answer1Num = rand.nextInt(9);
        while(answer1Num == answer2Num)
            answer2Num = rand.nextInt(9);

        // set the two equations list
        equationsSetOne = equationsList[answer1Num];
        equationsSetTwo = equationsList[answer2Num];

        answer1.setText(String.format("%d", (answer1Num + 1)));
        answer2.setText(String.format("%d", (answer2Num + 1)));


        eqn1.setOnTouchListener(touchListener);
        eqn2.setOnTouchListener(touchListener);
        eqn3.setOnTouchListener(touchListener);
        eqn4.setOnTouchListener(touchListener);
        eqn5.setOnTouchListener(touchListener);
        eqn6.setOnTouchListener(touchListener);

        answer1.setOnDragListener(dragListener1);
        answer2.setOnDragListener(dragListener2);


        // Get screen size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = (size.x/3)*2;
        screenHeight = size.y;
        margin = size.x/6;

        // Move out of screen;
        eqn1.setX(-80.0f + margin);
        eqn1.setY(screenHeight + 80.0f);

        eqn2.setX(-80.0f + margin);
        eqn2.setY(screenHeight + 100.0f);

        eqn3.setX(-80.0f + margin);
        eqn3.setY(screenHeight + 120.0f);

        eqn4.setX(-80.0f + margin);
        eqn4.setY(screenHeight + 140.0f);

        eqn5.setX(-80.0f + margin);
        eqn5.setY(screenHeight + 160.0f);

        eqn6.setX(-80.0f + margin);
        eqn6.setY(screenHeight + 180.0f);

        // Start timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePosition();
                    }
                });
            }
        }, 0, 20);
    }

    // deals with position changing of textviews during the timer
    public void changePosition(){

        if (score % 5 == 0 && score != 0 && count == 0){
            speed1 += 0.05f;
            speed2 += 0.05f;
            speed3 += 0.05f;
            speed4 += 0.05f;
            speed5 += 0.05f;
            speed6 += 0.05f;
            ++count;
        }

        eqn1y += speed1;
        //for going out of the screen
        if(eqn1.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn1.setText(equationsSetOne[randomIndex]);

            eqn1x = (float) Math.floor(Math.random() *(screenWidth - eqn1.getWidth()));
            eqn1y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn1.setX(eqn1x + margin);
        eqn1.setY(eqn1y);



        eqn2y += speed2;
        //for going out of the screen
        if(eqn2.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn2.setText(equationsSetOne[randomIndex]);

            eqn2x = (float) Math.floor(Math.random() *(screenWidth - eqn2.getWidth()));
            eqn2y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn2.setX(eqn2x + margin);
        eqn2.setY(eqn2y);



        eqn3y += speed3;
        //for going out of the screen
        if(eqn3.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn3.setText(equationsSetOne[randomIndex]);

            eqn3x = (float) Math.floor(Math.random() *(screenWidth - eqn3.getWidth()));
            eqn3y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn3.setX(eqn3x + margin);
        eqn3.setY(eqn3y);



        eqn4y += speed4;
        //for going out of the screen
        if(eqn4.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn4.setText(equationsSetTwo[randomIndex]);

            eqn4x = (float) Math.floor(Math.random() *(screenWidth - eqn4.getWidth()));
            eqn4y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn4.setX(eqn4x + margin);
        eqn4.setY(eqn4y);



        eqn5y += speed5;
        //for going out of the screen
        if(eqn5.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn5.setText(equationsSetTwo[randomIndex]);

            eqn5x = (float) Math.floor(Math.random() *(screenWidth - eqn5.getWidth()));
            eqn5y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn5.setX(eqn5x + margin);
        eqn5.setY(eqn5y);


        eqn6y += speed6;
        //for going out of the screen
        if(eqn6.getY() > screenHeight){
            //choosing random eqn
            randomIndex = rand.nextInt(30);
            eqn6.setText(equationsSetTwo[randomIndex]);

            eqn6x = (float) Math.floor(Math.random() *(screenWidth - eqn6.getWidth()));
            eqn6y = -100.0f;
            lives.setText("Lives: " + livesCount);
        }

        eqn6.setX(eqn6x + margin);
        eqn6.setY(eqn6y);
    }


    // for click and drag
    View.OnTouchListener touchListener = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            draggedTextView = v.getId();
            v.startDrag(data, myShadowBuilder, v, 0);
            return true;
        }
    };

    View.OnDragListener dragListener1 = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch(dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    if(draggedTextView == R.id.easyEqn1){
                        eqn1.setX(-80.0f + margin);
                        eqn1.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    else if(draggedTextView == R.id.easyEqn2){
                        eqn2.setX(-80.0f + margin);
                        eqn2.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    else if(draggedTextView == R.id.easyEqn3){
                        eqn3.setX(-80.0f + margin);
                        eqn3.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    else if(draggedTextView == R.id.easyEqn4){
                        eqn4.setX(-80.0f + margin);
                        eqn4.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);
                    }
                    else if(draggedTextView == R.id.easyEqn5){
                        eqn5.setX(-80.0f + margin);
                        eqn5.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);
                    }
                    else if(draggedTextView == R.id.easyEqn6){
                        eqn6.setX(-80.0f + margin);
                        eqn6.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);
                    }

                    if (isCorrect){
                        ++score;
                        scoresDisplay.setText("Points: " + score);
                        count = 0;
                    }
                    break;

            }
            return true;
        }
    };

    View.OnDragListener dragListener2 = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch(dragEvent){
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                case DragEvent.ACTION_DROP:
                    if(draggedTextView == R.id.easyEqn1){
                        eqn1.setX(-80.0f + margin);
                        eqn1.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);

                    }
                    else if(draggedTextView == R.id.easyEqn2){
                        eqn2.setX(-80.0f + margin);
                        eqn2.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);

                    }
                    else if(draggedTextView == R.id.easyEqn3){
                        eqn3.setX(-80.0f + margin);
                        eqn3.setY(screenHeight + 80.0f);
                        isCorrect = false;
                        --livesCount;
                        lives.setText("Lives: " + livesCount);

                    }
                    else if(draggedTextView == R.id.easyEqn4){
                        eqn4.setX(-80.0f + margin);
                        eqn4.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    else if(draggedTextView == R.id.easyEqn5){
                        eqn5.setX(-80.0f + margin);
                        eqn5.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    else if(draggedTextView == R.id.easyEqn6){
                        eqn6.setX(-80.0f + margin);
                        eqn6.setY(screenHeight + 80.0f);
                        isCorrect = true;

                    }
                    if (isCorrect){
                        ++score;
                        scoresDisplay.setText("Points: " + score);
                        count = 0;
                    }
                    break;

            }
            return true;
        }
    };
}

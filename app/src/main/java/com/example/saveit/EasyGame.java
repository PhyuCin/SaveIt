package com.example.saveit;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class EasyGame extends AppCompatActivity{

    // Screen Size
    private int screenWidth;
    private int screenHeight;
    private int margin;

    // TextViews
    private TextView eqn1, eqn2, eqn3, eqn4, eqn5, eqn6;
    private TextView[] eqnTextViewsList;

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
    private float dogSpeed = 0.1f;

    // for shaking
    private SensorManager sensorManager;
    private float accelerationVal; // Current acceleration values and gravity
    private float accelerationLast; // Last acceleration values and gravity
    private float shake; // Acc value diff from gravity

    private TextView shakingDisplay;

    // for pause play
    private Button pausePlay;
    private Boolean paused = false;
    private LinearLayout overlayScreen;

    // for gameover
    private boolean lose;
    private TextView overlayTitle;
    private TextView finalScoreDisplay;
    private Button restartGame;

    // save score for adding to High Scores
    private SharedPreferences preferences;
    private ArrayList<Integer> highScores = new ArrayList<Integer>();
    private boolean isDuplicate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game);

        preferences = getSharedPreferences("value", MODE_PRIVATE);
        highScores = highScores = FileHelper.readData(this);


        eqn1 = (TextView) findViewById(R.id.easyEqn1);
        eqn2 = (TextView) findViewById(R.id.easyEqn2);
        eqn3 = (TextView) findViewById(R.id.easyEqn3);
        eqn4 = (TextView) findViewById(R.id.easyEqn4);
        eqn5 = (TextView) findViewById(R.id.easyEqn5);
        eqn6 = (TextView) findViewById(R.id.easyEqn6);

        eqnTextViewsList = new TextView[]{eqn1,eqn2,eqn3,eqn4,eqn5,eqn6};


        shakingDisplay = (TextView) findViewById(R.id.shakeDisplay);
        equationsList = equationsManager.getAllEquations();

        lives = (TextView) findViewById(R.id.lives);
        scoresDisplay = (TextView) findViewById(R.id.scoresDisplay);
        pausePlay = (Button) findViewById(R.id.pausePlayButton);
        overlayScreen = (LinearLayout) findViewById(R.id.overlayScreen);

        overlayScreen.setVisibility(View.INVISIBLE);

        lives.setText("Lives: " + livesCount);
        scoresDisplay.setText("Points: " + score);

        answer1 =(TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);

        overlayTitle = (TextView) findViewById(R.id.overlayTitle);
        finalScoreDisplay = (TextView) findViewById(R.id.loseScoreDisplay);
        restartGame = (Button) findViewById(R.id.restartGame);

        // choose random answers for the two answer areas
        answer1Num = rand.nextInt(9);
        while(answer1Num == answer2Num)
            answer2Num = rand.nextInt(9);

        // set the two equations list
        equationsSetOne = equationsList[answer1Num];
        equationsSetTwo = equationsList[answer2Num];

        answer1.setText(String.format("%d", (answer1Num + 1)));
        answer2.setText(String.format("%d", (answer2Num + 1)));


        // motion sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        accelerationVal = SensorManager.GRAVITY_EARTH;
        accelerationLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        // Get screen size
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = (size.x/3)*2;
        screenHeight = size.y;
        margin = size.x/6;

        // listens for touch on textviews
        // Move out of screen (top)
        for (TextView tv : eqnTextViewsList){
            tv.setOnTouchListener(touchListener);
            tv.setX(-80.0f + margin);
            tv.setY(screenHeight + 80.0f);
        }

        answer1.setOnDragListener(dragListener1);
        answer2.setOnDragListener(dragListener2);

        // Start timer
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePosition();
                        if (livesCount == 0 || eqn1y > screenHeight - 150 || eqn2y > screenHeight - 150 || eqn3y > screenHeight - 150
                                || eqn4y > screenHeight - 150
                                || eqn5y > screenHeight - 150
                                || eqn6y > screenHeight - 150) {
                            gameOver();
                        }
                    }
                });
            }
        }, 0, 20);
    }


    // for sensing the shake
    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accelerationLast = accelerationVal;
            accelerationVal = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = accelerationVal - accelerationLast;
            shake = shake * 0.9f + delta;

            // for when shaking is true every 5 pts
            if (shakingDisplay.getText().toString().equals("Shake the phone!")){
                if (shake > 5){
                    shakingDisplay.setText("");
                    // Move out of screen;
                    eqn1.setX(-80.0f + margin);
                    eqn1.setY(screenHeight + 80.0f);

                    eqn2.setX(-80.0f + margin);
                    eqn2.setY(screenHeight + 80.0f);

                    eqn3.setX(-80.0f + margin);
                    eqn3.setY(screenHeight + 80.0f);


                    eqn4.setX(-80.0f + margin);
                    eqn4.setY(screenHeight + 80.0f);

                    eqn5.setX(-80.0f + margin);
                    eqn5.setY(screenHeight + 80.0f);

                    eqn6.setX(-80.0f + margin);
                    eqn6.setY(screenHeight + 80.0f);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //
        }
    };


    // deals with position changing of textviews during the timer
    public void changePosition(){

        if (score > 4){
            answer1.setText("");
            answer2.setText("");
        }
        if (score % 5 == 0 && score != 0 && count == 0){
            dogSpeed += 0.3f;
            shakingDisplay.setText("Shake the phone!");
            ++count;
        }

        eqn1y += dogSpeed ;
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



        eqn2y += dogSpeed+0.1f;
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



        eqn3y += dogSpeed +0.2f;
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



        eqn4y += dogSpeed+0.05f;
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



        eqn5y += dogSpeed+0.15f;
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


        eqn6y += dogSpeed+0.25f;
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

    public void pauseGame(View view){
        if(paused){
            pausePlay.setBackgroundResource(R.drawable.pause);
            paused = false;
            overlayScreen.setVisibility(View.INVISIBLE);

            for (TextView tv : eqnTextViewsList){
                tv.setVisibility(View.VISIBLE);
            }

            // Start timer
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePosition();
                            if (livesCount == 0 || eqn1y > screenHeight - 150 || eqn2y > screenHeight - 150 || eqn3y > screenHeight - 150
                                    || eqn4y > screenHeight - 150
                                    || eqn5y > screenHeight - 150
                                    || eqn6y > screenHeight - 150) {
                                gameOver();
                            }
                        }
                    });
                }
            }, 0, 20);
        }

        else{
            pausePlay.setBackgroundResource(R.drawable.play);
            paused = true;

            timer.cancel();
            timer = null;

            for (TextView tv : eqnTextViewsList){
                tv.setVisibility(View.INVISIBLE);
            }

            overlayScreen.setVisibility(View.VISIBLE);

        }
    }

    // handle game over
    public void gameOver(){
        // for when the player runs out of lives or any of the text views touches the ground
//        timer.cancel();
//        timer = null;

        for (TextView tv : eqnTextViewsList){
            tv.setVisibility(View.INVISIBLE);
        }

        int highestScore = highScores.get(0);

        overlayScreen.setVisibility(View.VISIBLE);


        if (highestScore < score){
            overlayTitle.setText("New Highscore!");
        }

        else overlayTitle.setText("Game Over!");

        restartGame.setText("Play Again");
        finalScoreDisplay.setText("Score: " + score);

        saveScore();
    }


    //save score
    public void saveScore(){
        // check duplicates

        for (int s : highScores) {
            if (s == score) {
                isDuplicate = true;
            }
        }

        if (!isDuplicate) {
            highScores.add(score);
            System.out.println(highScores);
            //using Collections.sort() to sort ArrayList descending
            Collections.sort(highScores, Collections.reverseOrder());

            if(highScores.size() > 5){
                //remove the extra value
                highScores.remove(highScores.size() - 1);
            }
            //save to file
            FileHelper.writeData(highScores, this);
            System.out.println(highScores);
        }
    }

    //on click of back button
    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class );
        startActivity(intent);
    }

    //on click of the settings button
    public void goToSettings(View view){
        Intent intent = new Intent(this, Settings.class );
        startActivity(intent);
    }

    public void toRestartGame(View view){
        Intent intent = new Intent(this, this.getClass());
        startActivity(intent);
    }
}
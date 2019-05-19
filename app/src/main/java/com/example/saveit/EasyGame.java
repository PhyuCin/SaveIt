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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class EasyGame extends AppCompatActivity {
    // Screen Size
    private int screenWidth;
    private int screenHeight;
    private int margin;

    // TextViews
    private TextView eqn1, eqn2, eqn3, eqn4, eqn5;

    // Positions
    private float eqn1x, eqn1y, eqn2x, eqn2y, eqn3x, eqn3y, eqn4x, eqn4y, eqn5x, eqn5y;


    // Initialise Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();


    private String[] equations = new String[] {"12 - 4", "3 + 5", "8/2"};
    private int count = 0;

    //    private Button change;
    private TextView answerStatus;
    private TextView scoresDisplay;
    private TextView answer1;
    private TextView answer2;

    // drag id
    private int draggedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easy_game);

        eqn1 = (TextView) findViewById(R.id.easyEqn1);
        eqn2 = (TextView) findViewById(R.id.easyEqn2);
        eqn3 = (TextView) findViewById(R.id.easyEqn3);
        eqn4 = (TextView) findViewById(R.id.easyEqn4);
        eqn5 = (TextView) findViewById(R.id.easyEqn5);

        answerStatus = (TextView) findViewById(R.id.answerStatus);
        scoresDisplay = (TextView) findViewById(R.id.scoresDisplay);

        answer1 =(TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);

        eqn1.setOnTouchListener(touchListener);
        eqn2.setOnTouchListener(touchListener);
        eqn3.setOnTouchListener(touchListener);
        eqn4.setOnTouchListener(touchListener);
        eqn5.setOnTouchListener(touchListener);

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
        eqn2.setY(screenHeight + 80.0f);

        eqn3.setX(-80.0f + margin);
        eqn3.setY(screenHeight + 80.0f);

        eqn4.setX(-80.0f + margin);
        eqn4.setY(screenHeight + 80.0f);

        eqn5.setX(-80.0f + margin);
        eqn5.setY(screenHeight + 80.0f);

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

    // deals with position changing of views during the timer
    public void changePosition(){
        // down

        eqn1y += 1;
        //for going out of the screen
        if(eqn1.getY() > screenHeight){
//            eqn1.setVisibility(View.VISIBLE);
            if (count >  2){
                count = 0;
            }
            eqn1.setText(equations[count]);

            eqn1x = (float) Math.floor(Math.random() *(screenWidth - eqn1.getWidth()));
            eqn1y = -100.0f;
            ++count;
        }

        eqn1.setX(eqn1x + margin);
        eqn1.setY(eqn1y);



        eqn2y += 2;
        //for going out of the screen
        if(eqn2.getY() > screenHeight){
//            eqn2.setVisibility(View.VISIBLE);
            if (count >  2){
                count = 0;
            }
            eqn2.setText(equations[count]);

            eqn2x = (float) Math.floor(Math.random() *(screenWidth - eqn2.getWidth()));
            eqn2y = -100.0f;
            ++count;
        }

        eqn2.setX(eqn2x + margin);
        eqn2.setY(eqn2y);



        eqn3y += 3;
        //for going out of the screen
        if(eqn3.getY() > screenHeight){
//            eqn3.setVisibility(View.VISIBLE);
            if (count >  2){
                count = 0;
            }
            eqn3.setText(equations[count]);

            eqn3x = (float) Math.floor(Math.random() *(screenWidth - eqn3.getWidth()));
            eqn3y = -100.0f;
            ++count;
        }

        eqn3.setX(eqn3x + margin);
        eqn3.setY(eqn3y);



        eqn4y += 4;
        //for going out of the screen
        if(eqn4.getY() > screenHeight){
//            eqn3.setVisibility(View.VISIBLE);
            if (count >  2){
                count = 0;
            }
            eqn4.setText(equations[count]);

            eqn4x = (float) Math.floor(Math.random() *(screenWidth - eqn4.getWidth()));
            eqn4y = -100.0f;
            ++count;
        }

        eqn4.setX(eqn4x + margin);
        eqn4.setY(eqn4y);



        eqn5y += 5;
        //for going out of the screen
        if(eqn5.getY() > screenHeight){
//            eqn5.setVisibility(View.VISIBLE);
            if (count >  2){
                count = 0;
            }
            eqn5.setText(equations[count]);

            eqn5x = (float) Math.floor(Math.random() *(screenWidth - eqn5.getWidth()));
            eqn5y = -100.0f;
            ++count;
        }

        eqn5.setX(eqn5x + margin);
        eqn5.setY(eqn5y);

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
                        answerStatus.setText("Correct!");
                        eqn1.setX(-80.0f + margin);
                        eqn1.setY(screenHeight + 80.0f);

//                        eqn1.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn2){
                        answerStatus.setText("Wrong!");
                        eqn2.setX(-80.0f + margin);
                        eqn2.setY(screenHeight + 80.0f);

//                        eqn2.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn3){
                        answerStatus.setText("Wrong!");
                        eqn3.setX(-80.0f + margin);
                        eqn3.setY(screenHeight + 80.0f);

//                        eqn3.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn4){
                        answerStatus.setText("Wrong!");
                        eqn4.setX(-80.0f + margin);
                        eqn4.setY(screenHeight + 80.0f);

//                        eqn4.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn5){
                        answerStatus.setText("Wrong!");
                        eqn5.setX(-80.0f + margin);
                        eqn5.setY(screenHeight + 80.0f);

//                        eqn5.setVisibility(View.INVISIBLE);
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
                        answerStatus.setText("Wrong!");
                        eqn1.setX(-80.0f + margin);
                        eqn1.setY(screenHeight + 80.0f);

//                        eqn1.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn2){
                        answerStatus.setText("Correct!");
                        eqn2.setX(-80.0f + margin);
                        eqn2.setY(screenHeight + 80.0f);

//                        eqn2.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn3){
                        answerStatus.setText("Correct!");
                        eqn3.setX(-80.0f + margin);
                        eqn3.setY(screenHeight + 80.0f);

//                        eqn3.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn4){
                        answerStatus.setText("Correct!");
                        eqn4.setX(-80.0f + margin);
                        eqn4.setY(screenHeight + 80.0f);

//                        eqn4.setVisibility(View.INVISIBLE);
                    }
                    else if(draggedTextView == R.id.easyEqn5){
                        answerStatus.setText("Correct!");
                        eqn5.setX(-80.0f + margin);
                        eqn5.setY(screenHeight + 80.0f);

//                        eqn5.setVisibility(View.INVISIBLE);
                    }
                    break;

            }
            return true;
        }
    };
}

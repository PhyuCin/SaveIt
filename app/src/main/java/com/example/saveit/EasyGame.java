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

import org.w3c.dom.Text;

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
    private String[] equations;

    private Random rand;
    private int answer1Num = 0;
    private int answer2Num = 0;
    private int count = 0;

    //    private Button change;
    private TextView answerStatus;
    private TextView scoresDisplay;
    private TextView answer1;
    private TextView answer2;

    // drag id
    private int draggedTextView;

    //

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

        answerStatus = (TextView) findViewById(R.id.answerStatus);
        scoresDisplay = (TextView) findViewById(R.id.scoresDisplay);

        answer1 =(TextView) findViewById(R.id.answer1);
        answer2 = (TextView) findViewById(R.id.answer2);

        // choose random answers for the two answer areas
        rand = new Random();
        answer1Num = rand.nextInt(9);
        while(answer1Num == answer2Num)
            answer2Num = rand.nextInt(9);


        answer1.setText(Integer.toString(answer1Num));
        answer2.setText(Integer.toString(answer2Num));

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

    // deals with position changing of views during the timer
    public void changePosition(){
        // down

        eqn1y += 0.5f;
        //for going out of the screen
        if(eqn1.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn1.setText(oneEquations[count]);

            eqn1x = (float) Math.floor(Math.random() *(screenWidth - eqn1.getWidth()));
            eqn1y = -100.0f;
            ++count;
        }

        eqn1.setX(eqn1x + margin);
        eqn1.setY(eqn1y);



        eqn2y += 0.1f;
        //for going out of the screen
        if(eqn2.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn2.setText(oneEquations[count]);

            eqn2x = (float) Math.floor(Math.random() *(screenWidth - eqn2.getWidth()));
            eqn2y = -100.0f;
            ++count;
        }

        eqn2.setX(eqn2x + margin);
        eqn2.setY(eqn2y);



        eqn3y += 0.15f;
        //for going out of the screen
        if(eqn3.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn3.setText(oneEquations[count]);

            eqn3x = (float) Math.floor(Math.random() *(screenWidth - eqn3.getWidth()));
            eqn3y = -100.0f;
            ++count;
        }

        eqn3.setX(eqn3x + margin);
        eqn3.setY(eqn3y);



        eqn4y += 0.2f;
        //for going out of the screen
        if(eqn4.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn4.setText(twoEquations[count]);

            eqn4x = (float) Math.floor(Math.random() *(screenWidth - eqn4.getWidth()));
            eqn4y = -100.0f;
            ++count;
        }

        eqn4.setX(eqn4x + margin);
        eqn4.setY(eqn4y);



        eqn5y += 0.25f;
        //for going out of the screen
        if(eqn5.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn5.setText(twoEquations[count]);

            eqn5x = (float) Math.floor(Math.random() *(screenWidth - eqn5.getWidth()));
            eqn5y = -100.0f;
            ++count;
        }

        eqn5.setX(eqn5x + margin);
        eqn5.setY(eqn5y);


        eqn6y += 0.3f;
        //for going out of the screen
        if(eqn6.getY() > screenHeight){
            if (count >  29){
                count = 0;
            }
//            eqn6.setText(twoEquations[count]);

            eqn6x = (float) Math.floor(Math.random() *(screenWidth - eqn6.getWidth()));
            eqn6y = -100.0f;
            ++count;
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
                        answerStatus.setText("Correct!");
                        eqn1.setX(-80.0f + margin);
                        eqn1.setY(screenHeight + 80.0f);

                    }
                    else if(draggedTextView == R.id.easyEqn2){
                        answerStatus.setText("Correct!");
                        eqn2.setX(-80.0f + margin);
                        eqn2.setY(screenHeight + 80.0f);

                    }
                    else if(draggedTextView == R.id.easyEqn3){
                        answerStatus.setText("Correct!");
                        eqn3.setX(-80.0f + margin);
                        eqn3.setY(screenHeight + 80.0f);

                    }
                    else{
                        answerStatus.setText("Wrong!");
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

                    if(draggedTextView == R.id.easyEqn4){
                        answerStatus.setText("Correct!");
                        eqn4.setX(-80.0f + margin);
                        eqn4.setY(screenHeight + 80.0f);

                    }
                    else if(draggedTextView == R.id.easyEqn5){
                        answerStatus.setText("Correct!");
                        eqn5.setX(-80.0f + margin);
                        eqn5.setY(screenHeight + 80.0f);

                    }
                    else if(draggedTextView == R.id.easyEqn6){
                        answerStatus.setText("Correct!");
                        eqn6.setX(-80.0f + margin);
                        eqn6.setY(screenHeight + 80.0f);

                    }
                    else{
                        answerStatus.setText("Wrong!");
                    }
                    break;

            }
            return true;
        }
    };
}

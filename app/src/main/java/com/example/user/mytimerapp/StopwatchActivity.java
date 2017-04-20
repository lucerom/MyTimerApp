package com.example.user.mytimerapp;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class StopwatchActivity extends AppCompatActivity {


    private int mins = 0;
    private int secs = 0;
    private int milliseconds = 0;
    private long startTime = 0L;
    private long timeInMilliseconds = 0L;
    private long timeSwapBuff = 0L;
    private long updatedTime = 0L;
    private Handler myHandler = new Handler();
    private TextView digitsTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        digitsTextView = (TextView) findViewById(R.id.digits_PT_ID);
    }




    // The Start button was pressed
    public void startPressed(View view){
        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimer, 0);
    }



    // The Stop button was pressed
    public void stopPressed(View view){
        timeSwapBuff += timeInMilliseconds;
        myHandler.removeCallbacks(updateTimer);
    }



    // The Reset button was pressed
    public void resetPressed(View view){
        // Clear everything out
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updatedTime = 0L;

        mins = 0;
        secs = 0;
        milliseconds = 0;
        myHandler.removeCallbacks(updateTimer);
        digitsTextView.setText("00:00:00");
    }




    // Show the time
    private void showTime() {
        // Format the time
        String time = String.format("%02d:%02d:%02d", mins, secs, milliseconds);
        // Display the time
        digitsTextView.setText(time);
    }



    // The Timer logic
    public Runnable updateTimer = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            secs = (int) (updatedTime/1000);
            mins =  secs / 60;
            secs = secs % 60;
            milliseconds = (int) (updatedTime / 10) % 100;
            showTime();

            myHandler.postDelayed(this, 0);

        }
    };



}

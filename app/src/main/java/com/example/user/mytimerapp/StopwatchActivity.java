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




    public void startPressed(View view){
        startTime = SystemClock.uptimeMillis();
        myHandler.postDelayed(updateTimer, 0);
    }



    public void stopPressed(View view){
        timeSwapBuff += timeInMilliseconds;
        myHandler.removeCallbacks(updateTimer);
    }

    public void resetPressed(View view){
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





//    public void msToDigits(long millis) {
//
//        long m = TimeUnit.MILLISECONDS.toMinutes(millis) -
//                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))
//
//
//
//        String time = String.format("%02d:%02d:%02d",
//                TimeUnit.MILLISECONDS.toHours(millis),
//                TimeUnit.MILLISECONDS.toMinutes(millis) -
//                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
//                TimeUnit.MILLISECONDS.toSeconds(millis) -
//                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
//
//        digit6 = time.substring(0, 1);
//        digit5 = time.substring(1, 2);
//        digit4 = time.substring(3, 4);
//        digit3 = time.substring(4, 5);
//        digit2 = time.substring(6, 7);
//        digit1 = time.substring(7);
//
//    }

    private void showTime() {
        String time = String.format("%02d:%02d:%02d", mins, secs, milliseconds);
        digitsTextView.setText(time);
    }

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

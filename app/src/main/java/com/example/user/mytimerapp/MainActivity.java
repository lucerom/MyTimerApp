package com.example.user.mytimerapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public String TAG = "user.MyTimerApp_Logs";// Debugging


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "The App Just Started");

    }



    // Goes to RegularTimer screen
    public void goToRegularTimer(View view){
        Intent i = new Intent(this, RegularTimerActivity2.class);
        startActivity(i);
    }

    // Goes to PhoneTimer screen
    public void goToPhoneTimer(View view){
        Intent i = new Intent(this, PhoneTimerActivity.class);
        startActivity(i);
    }

    // Goes to CameraTimer screen
    public void goToCameraTimer(View view){
        Intent i = new Intent(this, CameraTimerActivity.class);
        startActivity(i);
    }

    // Goes to StopWatch screen
    public void goToStopwatch(View view){
        Intent i = new Intent(this, StopwatchActivity.class);
        startActivity(i);
    }



}



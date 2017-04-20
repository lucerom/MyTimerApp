package com.example.user.mytimerapp;

import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class SetPhoneNumActivity extends AppCompatActivity {



    private TextView digitsTextView;

    private String phoneNumStr = "";
    private String digit1 = " ";
    private String digit2 = " ";
    private String digit3 = " ";
    private String digit4 = " ";
    private String digit5 = " ";
    private String digit6 = " ";
    private String digit7 = " ";
    private String digit8 = " ";
    private String digit9 = " ";
    private String digit10 = " ";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone_num);
        digitsTextView = (TextView) findViewById(R.id.digits_PT_ID);
    }




    //-----------------------------
    //    The Numeric Digits
    //-----------------------------

    public void number0(View view){
        numberButtonPress("0");
    }

    public void number1(View view){
        numberButtonPress("1");
    }

    public void number2(View view){
        numberButtonPress("2");
    }

    public void number3(View view){
        numberButtonPress("3");
    }

    public void number4(View view){
        numberButtonPress("4");
    }

    public void number5(View view){
        numberButtonPress("5");
    }

    public void number6(View view){
        numberButtonPress("6");
    }

    public void number7(View view){
        numberButtonPress("7");
    }

    public void number8(View view){
        numberButtonPress("8");
    }

    public void number9(View view){
        numberButtonPress("9");
    }




    // A number button was pressed
    public void numberButtonPress(String num) {
        // Rotate the digits down
        digit10 = digit9;
        digit9 = digit8;
        digit8 = digit7;
        digit7 = digit6;
        digit6 = digit5;
        digit5 = digit4;
        digit4 = digit3;
        digit3 = digit2;
        digit2 = digit1;
        digit1 = num;

        // Show the digits
        showPhoneNumber();
    }




    // The delete button was pressed
    public void deletePressed(View view) {
        // Rotate the digits back up
        digit1 = digit2;
        digit2 = digit3;
        digit3 = digit4;
        digit4 = digit5;
        digit5 = digit6;
        digit6 = digit7;
        digit7 = digit8;
        digit8 = digit9;
        digit9 = digit10;
        digit10 = " ";

        // Show the digits
        showPhoneNumber();
    }




    // The Clear button was pressed
    public void clearPressed(View view){
            clearDigits();
            showPhoneNumber();
    }





    // The Enter button was pressed
    public void enterPressed(View view){
        // Pass the number to PhoneTimerActivity
        Intent i = new Intent(this, PhoneTimerActivity.class);
        String phoneNumDigits = digit10 + digit9 + digit8 + digit7 + digit6 + digit5 + digit4 + digit3 + digit2 + digit1;

//        final EditText appleInput = (EditText) findViewById(R.id.appleTextInput_ID);
//        String userMsg = appleInput.getText().toString();
        i.putExtra("phoneNumber", phoneNumDigits);

        // Go back to the PhoneTimerActivity
        startActivity(i);
    }




    // Display the PhoneNumber
    public void showPhoneNumber(){
        // Put all internal digits into a string
        phoneNumStr = "(" + digit10 + digit9 + digit8 + ")" + digit7 + digit6 + digit5 + "-" + digit4 + digit3 + digit2 + digit1;
        // Display the string
        digitsTextView.setText(phoneNumStr);

    }




    // Reset all the digits to spaces
    public void clearDigits(){
        digit1 = " ";
        digit2 = " ";
        digit3 = " ";
        digit4 = " ";
        digit5 = " ";
        digit6 = " ";
        digit7 = " ";
        digit8 = " ";
        digit9 = " ";
        digit10 = " ";
    }



}

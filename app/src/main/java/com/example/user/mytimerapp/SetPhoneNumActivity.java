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



    private CountDownTimer cdt;
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

    private String origDigit1 = "0";
    private String origDigit2 = "0";
    private String origDigit3 = "0";
    private String origDigit4 = "0";
    private String origDigit5 = "0";
    private String origDigit6 = "0";

    private boolean resume = false;
    private boolean inProgress = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone_num);
        digitsTextView = (TextView) findViewById(R.id.digits_PT_ID);
    }







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




    public void numberButtonPress(String num) {
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

        showTime();
    }




    public void deletePressed(View view) {
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

        showTime();
    }




    public void clearPressed(View view){
            clearDigits();
            showTime();
    }



//    public void startPressed(View view){
//        if(!inProgress) {
//            inProgress = true;
//
//            // Make sure digits are in range & set origs
//            if (!resume) {
//                int mins = Integer.parseInt(digit4);
//                if (mins >= 6) {
//                    digit4 = "5";
//                    digit3 = "9";
//                }
//
//                int secs = Integer.parseInt(digit2);
//                if (secs >= 6) {
//                    digit2 = "5";
//                    digit1 = "9";
//                }
//                copyDigits();
//            }
//
//            // Use digits to start Timer
//            long totalTime = digitsToMs();
//            setTimer(totalTime);
//            cdt.start();
//        }
//
//    }
//
//
//    public void stopPressed(View view){
//        if(inProgress) {
//            cdt.cancel();
//            inProgress = false;
//            resume = true;
//        }
//    }


//    public void resetPressed(View view){
//        if(inProgress) {
//            cdt.cancel();
//        }
//        digit1 = origDigit1;
//        digit2 = origDigit2;
//        digit3 = origDigit3;
//        digit4 = origDigit4;
//        digit5 = origDigit5;
//        digit6 = origDigit6;
//
//        showTime();
//        inProgress = false;
//        resume = false;
//    }




    public void enterPressed(View view){
        // Pass the number to PhoneTimerActivity
        Intent i = new Intent(this, PhoneTimerActivity.class);
        String phoneNumDigits = digit10 + digit9 + digit8 + digit7 + digit6 + digit5 + digit4 + digit3 + digit2 + digit1;

//        final EditText appleInput = (EditText) findViewById(R.id.appleTextInput_ID);
//        String userMsg = appleInput.getText().toString();
        i.putExtra("phoneNumber", phoneNumDigits);

        startActivity(i);


        // Go back to the PhoneTimerActivity

    }




    public void showTime(){
        phoneNumStr = "(" + digit10 + digit9 + digit8 + ")" + digit7 + digit6 + digit5 + "-" + digit4 + digit3 + digit2 + digit1;
        digitsTextView.setText(phoneNumStr);

    }


    public long digitsToMs(){
        long total = (Integer.parseInt(digit1) * 1000); // 1's sec
        total += (Integer.parseInt(digit2) * 10 * 1000); // 10's sec

        total += (Integer.parseInt(digit3) * 60 * 1000); // 1's mins
        total += (Integer.parseInt(digit4) * 10 * 60 * 1000); // 10's mins

        total += (Integer.parseInt(digit5) * 60 * 60 * 1000); // 1's hrs
        total += (Integer.parseInt(digit6) * 10 * 60 * 60 * 1000); // 10's hrs

        return total;
    }



    public void msToDigits(long millis) {

        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        digit6 = time.substring(0, 1);
        digit5 = time.substring(1, 2);
        digit4 = time.substring(3, 4);
        digit3 = time.substring(4, 5);
        digit2 = time.substring(6, 7);
        digit1 = time.substring(7);

    }


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

    public void copyDigits(){
        origDigit1 = digit1;
        origDigit2 = digit2;
        origDigit3 = digit3;
        origDigit4 = digit4;
        origDigit5 = digit5;
        origDigit6 = digit6;

    }

    public void setTimer(long totalTime){
        cdt = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                msToDigits(millisUntilFinished);
                showTime();
            }

            @Override
            public void onFinish() {
                clearDigits();
                showTime();

                // Play alarm sound
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                r.play();
            }
        };

    }





}

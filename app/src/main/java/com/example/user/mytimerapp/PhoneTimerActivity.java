package com.example.user.mytimerapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class PhoneTimerActivity extends AppCompatActivity {

    public String TAG = "user.MyTimerApp_Logs";
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 0;
    private TextView loading_tv2;


    private CountDownTimer cdt;
    private TextView digitsTextView;

    private String fullPhoneNumber = "tel:5208704920";
    private String phoneNum = "";
    private String timerStr = "";
    private String digit1 = "0";
    private String digit2 = "0";
    private String digit3 = "0";
    private String digit4 = "0";
    private String digit5 = "0";
    private String digit6 = "0";

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
        setContentView(R.layout.activity_phone_timer);
        digitsTextView = (TextView) findViewById(R.id.digits_PT_ID);

        // Request CALL_PHONE permission
        getCallPhonePermission();

        // Get Phone Number from other Activity
        Bundle phoneNumData = getIntent().getExtras();
        if (phoneNumData != null) {
            phoneNum = phoneNumData.getString("phoneNumber");
//            digitsTextView.setText(phoneNum);
            fullPhoneNumber = "tel:" + phoneNum;
//            Log.i(TAG, "The fullPhoneNumber = " + fullPhoneNumber);
//            final TextView baconText = (TextView) findViewById(R.id.baconText_ID);
//            baconText.setText(appleMsg);
        }

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
        if(!inProgress) {
            // Rotate the digits down
            digit6 = digit5;
            digit5 = digit4;
            digit4 = digit3;
            digit3 = digit2;
            digit2 = digit1;
            digit1 = num;

            // Show the digits
            showTime();
            resume = false;
        }
    }



    // The delete button was pressed
    public void deletePressed(View view){
        if(!inProgress) {
            // Rotate the digits back up
            digit1 = digit2;
            digit2 = digit3;
            digit3 = digit4;
            digit4 = digit5;
            digit5 = digit6;
            digit6 = "0";

            // Show the digits
            showTime();
            resume = false;
        }
    }



    // The Clear button was pressed
    public void clearPressed(View view){
        // Don't do anything if running
        if(!inProgress) {
            clearDigits();
            showTime();
            resume = false;
        }
    }



    // The Start button was pressed
    public void startPressed(View view){
        // Don't do anything if running
        if(!inProgress) {
            inProgress = true;

            // Make sure digits are in range & set origs
            if (!resume) {
                int mins = Integer.parseInt(digit4);
                if (mins >= 6) {
                    digit4 = "5";
                    digit3 = "9";
                }

                int secs = Integer.parseInt(digit2);
                if (secs >= 6) {
                    digit2 = "5";
                    digit1 = "9";
                }
                copyDigits();
            }

            // Use digits to start Timer
            long totalTime = digitsToMs();
            setTimer(totalTime);
            cdt.start();
        }

    }


    // The Stop button was pressed
    public void stopPressed(View view){
        // Don't do anything if running
        if(inProgress) {
            cdt.cancel();
            inProgress = false;
            resume = true;
        }
    }



    // The Reset button was pressed
    public void resetPressed(View view){
        // Check if running
        if(inProgress) {
            cdt.cancel();
        }
        digit1 = origDigit1;
        digit2 = origDigit2;
        digit3 = origDigit3;
        digit4 = origDigit4;
        digit5 = origDigit5;
        digit6 = origDigit6;

        showTime();
        inProgress = false;
        resume = false;
    }


    // Display the time
    public void showTime(){
        // Put all internal digits into a string
        timerStr = digit6 + digit5 + ":" + digit4 + digit3 + ":" + digit2 + digit1;
        // Display the string
        digitsTextView.setText(timerStr);
    }


    // Convert the digits to milliseconds
    public long digitsToMs(){
        long total = (Integer.parseInt(digit1) * 1000); // 1's sec
        total += (Integer.parseInt(digit2) * 10 * 1000); // 10's sec

        total += (Integer.parseInt(digit3) * 60 * 1000); // 1's mins
        total += (Integer.parseInt(digit4) * 10 * 60 * 1000); // 10's mins

        total += (Integer.parseInt(digit5) * 60 * 60 * 1000); // 1's hrs
        total += (Integer.parseInt(digit6) * 10 * 60 * 60 * 1000); // 10's hrs

        return total;
    }



    // Convert the time to string digits
    public void msToDigits(long millis) {

        // Format the time to a string
        String time = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        // Parse that time string to digits
        digit6 = time.substring(0, 1);
        digit5 = time.substring(1, 2);
        digit4 = time.substring(3, 4);
        digit3 = time.substring(4, 5);
        digit2 = time.substring(6, 7);
        digit1 = time.substring(7);

    }




    // Reset all the digits to 0
    public void clearDigits(){
        digit1 = "0";
        digit2 = "0";
        digit3 = "0";
        digit4 = "0";
        digit5 = "0";
        digit6 = "0";
    }



    // Make a copy of current digits
    public void copyDigits(){
        origDigit1 = digit1;
        origDigit2 = digit2;
        origDigit3 = digit3;
        origDigit4 = digit4;
        origDigit5 = digit5;
        origDigit6 = digit6;

    }

    // Set the Timer
    public void setTimer(long totalTime){
        cdt = new CountDownTimer(totalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                msToDigits(millisUntilFinished);
                showTime();
            }

//            @SuppressWarnings("MissingPermission")
            @Override
            public void onFinish() {
                clearDigits();
                showTime();

                // Make the Call
                Intent makePhoneCall = new Intent(Intent.ACTION_CALL);
                makePhoneCall.setData(Uri.parse(fullPhoneNumber));
                try {
//                    Log.i(TAG, "Trying to make the Phone Call");
//                    Log.i(TAG, "In Try: The fullPhoneNumber = " + fullPhoneNumber);
                    startActivity(makePhoneCall);
                } catch(Exception e){
//                    Log.i(TAG, "This is a F-ing Exception!");
                    e.printStackTrace();
                }


            }
        };

    }





//    May want this later
//    public void loadIMEI(){
//        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
//            request
//        }
//    }




    //----------------------------
    //  OverFlow Menu Methods
    //----------------------------

    // Create the Overflow menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phone_timer, menu);
        return true;
    }


    // Deal with a selection
    public boolean onOptionsItemSelected(MenuItem item) {
        RelativeLayout main_view = (RelativeLayout) findViewById(R.id.relativeLayout_ID);

        switch (item.getItemId()) {
            // Set to Black
            case R.id.menu_backgroundBlack_ID:
                if (item.isCheckable()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                main_view.setBackgroundColor(Color.BLACK);
                return true;
            // Set to White
            case R.id.menu_backgroundWhite_ID:
                if (item.isCheckable()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                main_view.setBackgroundColor(Color.WHITE);
                return true;
            // Set to Grey
            case R.id.menu_backgroundGrey_ID:
                if (item.isCheckable()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                main_view.setBackgroundColor(ContextCompat.getColor(this, R.color.myLightGrey));
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    // Go to the phone number set activity
    public void phoneMenu_GoToPhoneNumSet(MenuItem item){
        Intent i = new Intent(this, SetPhoneNumActivity.class);
        startActivity(i);
    }




    //----------------------------------
    //    Permission Requests
    //----------------------------------

    /**
     * Called when this Activity is entered.
     */
    public void getCallPhonePermission() {
        // Check if the CALL_PHONE permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            // CALL_PHONE permission has not been granted.
            requestCallPhonePermission();
        }
//        else{
//            // READ_PHONE_STATE permission is already been granted.
//            doPermissionGrantedStuffs();
//        }
    }



    /**
     * Requests the CALL_PHONE permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestCallPhonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(PhoneTimerActivity.this)
                    .setTitle("Permission Request")
                    .setMessage(getString(R.string.permission_read_phone_state_rationale))
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(PhoneTimerActivity.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        }
                    })
//                .setIcon(R.drawable.onlinlinew_warning_sign) // Was originally here
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
        } else {
            // CALL_PHONE permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            // Received permission result for READ_PHONE_STATE permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // READ_PHONE_STATE permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
//                doPermissionGrantedStuffs();
            } else {
                alertAlert(getString(R.string.permissions_not_granted_read_phone_state));
            }
        }
    }

    private void alertAlert(String msg) {
        new AlertDialog.Builder(PhoneTimerActivity.this)
                .setTitle("My Alert Msg")
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do somthing here
                    }
                })
//                .setIcon(R.drawable.onlinlinew_warning_sign) // Was originally here
                .setIcon(R.mipmap.ic_launcher)
                .show();
    }


//    public void doPermissionGrantedStuffs() {
//        Toast.makeText(this, "Permission is already granted you fucking whore!", Toast.LENGTH_LONG).show();


        //Have an  object of TelephonyManager
//        TelephonyManager tm =(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        //Get IMEI Number of Phone  //////////////// for this example i only need the IMEI
//        String IMEINumber=tm.getDeviceId();

        /************************************************
         * **********************************************
         * NOTES FOR FUTURE PROJECTS:
         * This is just an icing on the cake
         * the following are other children of TELEPHONY_SERVICE
         *
         //Get Subscriber ID
         String subscriberID=tm.getDeviceId();

         //Get SIM Serial Number
         String SIMSerialNumber=tm.getSimSerialNumber();

         //Get Network Country ISO Code
         String networkCountryISO=tm.getNetworkCountryIso();

         //Get SIM Country ISO Code
         String SIMCountryISO=tm.getSimCountryIso();

         //Get the device software version
         String softwareVersion=tm.getDeviceSoftwareVersion()

         //Get the Voice mail number
         String voiceMailNumber=tm.getVoiceMailNumber();


         //Get the Phone Type CDMA/GSM/NONE
         int phoneType=tm.getPhoneType();

         switch (phoneType)
         {
         case (TelephonyManager.PHONE_TYPE_CDMA):
         // your code
         break;
         case (TelephonyManager.PHONE_TYPE_GSM)
         // your code
         break;
         case (TelephonyManager.PHONE_TYPE_NONE):
         // your code
         break;
         }

         //Find whether the Phone is in Roaming, returns true if in roaming
         boolean isRoaming=tm.isNetworkRoaming();
         if(isRoaming)
         phoneDetails+="\nIs In Roaming : "+"YES";
         else
         phoneDetails+="\nIs In Roaming : "+"NO";


         //Get the SIM state
         int SIMState=tm.getSimState();
         switch(SIMState)
         {
         case TelephonyManager.SIM_STATE_ABSENT :
         // your code
         break;
         case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_PIN_REQUIRED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_PUK_REQUIRED :
         // your code
         break;
         case TelephonyManager.SIM_STATE_READY :
         // your code
         break;
         case TelephonyManager.SIM_STATE_UNKNOWN :
         // your code
         break;

         }
         */
        // Now read the desired content to a textview.
//        loading_tv2 = (TextView) findViewById(R.id.loading_tv2);
//        loading_tv2.setText(IMEINumber);
//    }













}
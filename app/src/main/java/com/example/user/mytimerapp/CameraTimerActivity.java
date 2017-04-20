package com.example.user.mytimerapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.TimeUnit;

public class CameraTimerActivity extends AppCompatActivity {

    // This is a way to identify intents since there are several
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;



    private CountDownTimer cdt;
    private TextView digitsTextView;

    Bitmap photo;
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



    // Create this screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_timer);

        digitsTextView = (TextView) findViewById(R.id.digits_PT_ID);

        getCameraPermission();

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




    // Run the actual timer
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

                launchCamera();
//                goToPictureViewer();
            }
        };

    }








    // Launching the camera
    public void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Take a picture and pass teh results along to onActivityResult
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }


    // If you want to return image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            // Get the photo
            Bundle extras = data.getExtras();
            photo = (Bitmap) extras.get("data");

            // Saving the bitmap in a file in the internal storage of this app
            String filename = "myImage";
            try{
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                FileOutputStream fo = openFileOutput(filename, Context.MODE_PRIVATE);
                fo.write(bytes.toByteArray());
                fo.close();
            } catch (Exception e){
                e.printStackTrace();
                filename = null;
            }


            // Go to Picture Viewer Activity
            Intent i = new Intent(this, PictureViewerActivity.class);
            startActivity(i);



//            // This is how to pass a image thru extras, but degrades the quality
//            // Compressing image
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            byte[] bytes = stream.toByteArray();
//            // Passing byte array as an extra
//            Intent i = new Intent(this, PictureViewerActivity.class);
//            i.putExtra("bitmapBytes", bytes);


        }
    }



    // Go to Picture Viewer Activity to see Pic
    public void goToPictureViewer(MenuItem item){
        Intent i = new Intent(this, PictureViewerActivity.class);
        startActivity(i);
    }








    //----------------------------
    //  OverFlow Menu Methods
    //----------------------------

    // Create the Overflow menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_camera_timer, menu);
        return true;
    }

    // Deal with a selection
    public boolean onOptionsItemSelected(MenuItem item) {
                return true;
    }








    /**
     * Called when this Activity is entered.
     */
    public void getCameraPermission() {
        // Check if the CAMERA permission is already available.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // The permission has not been granted.
            requestCameraPermission();
        }
    }



    /**
     * Requests the CAMERA permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.
            new AlertDialog.Builder(CameraTimerActivity.this)
                    .setTitle("Permission Request")
                    .setMessage("This is AlertDialog.Builder .setMessage in requestCameraPermission()")
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //re-request
                            ActivityCompat.requestPermissions(CameraTimerActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
        } else {
            // The permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        }
    }




    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            // Received permission result for permission.est.");
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission has been granted, proceed with displaying IMEI Number
                //alertAlert(getString(R.string.permision_available_read_phone_state));
//                doPermissionGrantedStuffs();
            } else {
                alertAlert("This is in onRequestPermissionResult() but being passed to alertAlert");
            }
        }
    }


    // Create Alert (pop up)
    private void alertAlert(String msg) {
        new AlertDialog.Builder(CameraTimerActivity.this)
                .setTitle("My alertAlert .setTitle")
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


}

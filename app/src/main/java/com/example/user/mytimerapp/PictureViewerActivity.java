package com.example.user.mytimerapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PictureViewerActivity extends AppCompatActivity {

    // This is a way to identify intents if i have several
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView myImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_viewer);

        myImageView = (ImageView) findViewById(R.id.imageView_ID);

        // Show the image just taken
        Bitmap photo = null;
        try {
            photo = BitmapFactory.decodeStream(this.openFileInput("myImage"));
            myImageView.setImageBitmap(photo);
        } catch (Exception e){
            e.printStackTrace();
        }



        //---------------------------------------------
        //  Various attempts i may want for futrue
        //---------------------------------------------

        // This is getting the bitmap from the extras
//        byte[] bytes = getIntent().getByteArrayExtra("bitmapBytes");
//        Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        myImageView.setImageBitmap(photo);



        // Should have had this code here for un compressed try
//        Bundle extras = data.getExtras();
//        Bitmap photo = (Bitmap) extras.get("data");
//        myImageView.setImageBitmap(photo);

    }



    // Exits this activity
    public void goBackToCameraTimer(View view){
        Intent i = new Intent(this, CameraTimerActivity.class);
        startActivity(i);
    }



    // Check if the user has a camera
    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }





    // If you want to return image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            // Get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            myImageView.setImageBitmap(photo);
        }
    }




}

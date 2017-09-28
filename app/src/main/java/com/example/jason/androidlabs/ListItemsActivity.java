package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageButton cameraButton;

    // create an intent to capture an image with the camera
    private void dispatchTakeImageIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // checks to see if an existing Activity exists
        if (takePictureIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        // create reference to camera button
        cameraButton = findViewById(R.id.camera_button);

        //create an on click listener
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              dispatchTakeImageIntent();
            }
        });

    }

    // This method is called when the thumbnail is saved
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            cameraButton.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}

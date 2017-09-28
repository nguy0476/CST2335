package com.example.jason.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    protected static final String ACTIVITY_NAME = "LoginActivity";
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private ImageButton cameraButton;
    private Switch mySwitch;
    private CheckBox myCheckBox;

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

        // create reference to switch
        mySwitch = findViewById(R.id.on_off_switch);

        // create reference to the checkbox
        myCheckBox = findViewById(R.id.finish_checkbox);

        // create a set on checked listener
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
                CharSequence text = "";
                int duration = Toast.LENGTH_SHORT;
                Toast toast;
                if (isChecked){
                    text = "Switch is On";
                    //duration = Toast.LENGTH_SHORT;
                }else{
                    text = "Switch is Off";
                    duration = Toast.LENGTH_LONG;
                }
                toast = Toast.makeText(ListItemsActivity.this, text, duration);
                toast.show();
            }
        });

        // create a set on click listener for the CheckBox
        myCheckBox.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                onCheckChanged();
            }
        });

        //create an on click listener
        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              dispatchTakeImageIntent();
            }
        });

    }

    // create dialog when Checkbox is clicked
    public void onCheckChanged(){
       // create the dialog builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(ListItemsActivity.this);
        // Chain together setter methods to set the dialog characteristics
        builder.setMessage(R.string.CB_dialog_message)
                .setTitle(R.string.CB_dialog_title)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // user clicked ok
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("ok_response","ListItemsActivity Passed: My information to share");
                        setResult(Activity.RESULT_OK, resultIntent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        // user clicked cancel
                    }
                })
                .show();

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

package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.util.Log.i;

public class StartActivity extends AppCompatActivity {

    private Button helloButton;

    protected static final String ACTIVITY_NAME = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // Create reference to button
       helloButton = (Button) findViewById(R.id.hello_button);

        // Create button listener
        helloButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create the intent and call the startActivity
                // Intent(context, activity that should be started)
                Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 10);
            }
        });
    }

    // checks if request code == 10
    // checks if resultcode == RESULT_OK
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==10)
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
            // if result code matches the onchecked result code from the ListItemsActivity
            // Get the message from extra data and output that message
        if (resultCode == Activity.RESULT_OK){
            String messagePassed = data.getStringExtra("ok_response");
            Toast toast = Toast.makeText(this, messagePassed, Toast.LENGTH_LONG );
            toast.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        i(ACTIVITY_NAME, "In onDestroy()");
    }
}

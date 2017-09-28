package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

    private Button loginButton;
    // Create SharedPreferences
    private SharedPreferences pref;
    // Create reference for view objects
    EditText LoginText;
    EditText PassText;

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String MY_PREF = "SharedPrefsFile";

    public void showSavedPref(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // create the text fields
        LoginText = findViewById(R.id.Email_Text);
        PassText = findViewById(R.id.Password_Text);
        loginButton = findViewById(R.id.Button_Login);

        // create the preference, giving it a name and setting the mode
        pref = getSharedPreferences(MY_PREF, MODE_PRIVATE);

        // Set the defaults for the login text and pass text
        // if a value for the key is not present, return the 2nd parameter - null
        LoginText.setText(pref.getString("DefaultEmail", "Please enter the email"));
        //PassText.setText(pref.getString("DefaultPassword", ""));

        // Create button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SharedPreferences.Editor editor = pref.edit();
                // Store the data using the put method
                editor.putString("DefaultEmail", LoginText.getText().toString());
                editor.putString("DefaultPassword", PassText.getText().toString());
                //apply asynchronous changes on a separate thread
                editor.apply();
                // Create the intent and call the startActivity
                // Intent(context, activity that should be started)
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);

            }
        });

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

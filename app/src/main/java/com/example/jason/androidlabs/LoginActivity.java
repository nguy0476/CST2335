package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private Button loginButton;

    protected static final String ACTIVITY_NAME = "LoginActivity";
    public static final String MY_PREF = "SharedPrefsFile";

    EditText LoginText;
    EditText PassText;

    public void showSavedPref(){

        // Create SharedPreferences
        SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        LoginText = findViewById(R.id.Email_Text);
        PassText = findViewById(R.id.Password_Text);

        // Set default text that appears every time the application loads
        String defaultUsername = pref.getString("DefaultEmail", "email@domain.com");
        String defaultPassword = pref.getString("DefaultPassword", "***");

        LoginText.setText(defaultUsername);
        PassText.setText(defaultPassword);

        // Store the Data into Shared Preferences
        editor.putString("key1", LoginText.getText().toString());
        editor.putString("key2", PassText.getText().toString());

        // Apply the changes
        editor.apply();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Store reference to the button
        loginButton = findViewById(R.id.Button_Login);

        // Create button listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Create the intent and call the startActivity
                // Intent(context, activity that should be started)
                Intent intent = new Intent(LoginActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });

        showSavedPref();

    }

    public void sendLoginMessage(View v){
        Toast.makeText(LoginActivity.this, "Message", Toast.LENGTH_LONG).show();
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

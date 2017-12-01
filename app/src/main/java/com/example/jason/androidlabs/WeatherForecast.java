package com.example.jason.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WeatherForecast extends Activity {

    private TextView currentText;
    private TextView minText;
    private TextView maxText;
    private ImageView weatherImageView;
    private Bitmap weatherPic;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        currentText = findViewById(R.id.current_text);
        minText = findViewById(R.id.min_text);
        maxText = findViewById(R.id.max_text);
        weatherImageView = findViewById(R.id.imageView_weather);

        // set the progress bar visibility to visible
        progressBar.setVisibility(View.VISIBLE);
    }

    /*class ForecastQuery extends AsyncTask<String, Integer, String>{

        @Override
        public String doInBackground(){

            return "";
        }

    }*/

}

package com.example.jason.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    private RelativeLayout rootLayout;

    private TextView currentText;
    private TextView minText;
    private TextView maxText;

    public String current, minimum, maximum;

    private ImageView weatherImageView;
    private Bitmap weatherPic;
    private ProgressBar progressBar;

    // Weather URL
    private final static String URL =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        rootLayout = findViewById(R.id.rootLayout);

        progressBar = findViewById(R.id.progressBar);
        currentText = findViewById(R.id.current_text);
        minText = findViewById(R.id.min_text);
        maxText = findViewById(R.id.max_text);
        weatherImageView = findViewById(R.id.imageView_weather);

        // set the progress bar visibility to visible
        progressBar.setVisibility(View.VISIBLE);

        ForecastQuery fq = new ForecastQuery();
        fq.execute();



    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        @Override
        public String doInBackground(String...args){
            try {

                URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(conn.getInputStream(), null);

                int eventType = -1;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    // Check if
                    if (eventType == XmlPullParser.START_TAG) {
                        // Assign the locationValue to the starting tag
                        String tempValues = parser.getName();

                        // if the name of the start tag is location, execute the code
                        if (tempValues.equals("temperature")) {

                            // get the attributes within the location tag
                            String value = parser.getAttributeValue(null, "value");
                            current = value;
                            publishProgress(25);
                            String min = parser.getAttributeValue(null, "min");
                            minimum = min;
                            publishProgress(50);
                            String max = parser.getAttributeValue(null, "max");
                            maximum = max;
                            publishProgress(75);
                            Log.i("PullParser", "Fetched value, min and max");

                        }
                    }
                    // Check the next eventType to end the loop
                    eventType = parser.next();
                }

            }catch (IOException e){
                e.printStackTrace();
            }catch (XmlPullParserException e){
                e.printStackTrace();
            }
            return "doInBackground";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            currentText.setText(current);
            minText.setText(minimum);
            maxText.setText(maximum);
        }
    }

    // Given a string representation of a URL, sets up a connection and gets
    // an input stream.
    public InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }

    public void processURLData(InputStream inputStream) throws IOException, XmlPullParserException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, null);

        int eventType = -1;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            // Check if
            if (eventType == XmlPullParser.START_TAG) {
                // Assign the locationValue to the starting tag
                String tempValues = parser.getName();

                // if the name of the start tag is location, execute the code
                if (tempValues.equals("temperature")) {

                    // get the attributes within the location tag
                    String value = parser.getAttributeValue(null, "value");

                    String min = parser.getAttributeValue(null, "min");
                    String max = parser.getAttributeValue(null, "max");

                    // print the values
                   // printValues(value, min, max);
                    currentText.setText(value);
                    minText.setText(min);
                    maxText.setText(max);

                }
            }
            // Check the next eventType to end the loop
            eventType = parser.next();
        }
    }

    private void printValues(String value, String min, String max) {
        // create nested linear layout
        LinearLayout DataRows = new LinearLayout(this);
        // Set the parameters of the three TextViews being created dynamically
        // Set the width to 0 dp, set the height to wrap content
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);

        // since the width is set to 0, we can specify weight instead
        params.weight = 1;

        DataRows.setOrientation(LinearLayout.HORIZONTAL);

        // Create local TextView Variables to be generated
        TextView cityColumn = new TextView(this);
        TextView tempColumn = new TextView(this);
        TextView weatherColumn = new TextView(this);

        // apply the parameters set earlier to each TextView
        cityColumn.setLayoutParams(params);
        tempColumn.setLayoutParams(params);
        weatherColumn.setLayoutParams(params);

        // Set the TextViews with method parameters
        cityColumn.setText(value);
        tempColumn.setText(min);
        weatherColumn.setText(max);

        // Add the TextViews to the Created DataRow Layout
        DataRows.addView(cityColumn);
        DataRows.addView(tempColumn);
        DataRows.addView(weatherColumn);

        // Add the DataRow Layout to the main layout
        rootLayout.addView(DataRows);
    }
}

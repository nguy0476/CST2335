package com.example.jason.androidlabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    private TextView currentText;
    private TextView minText;
    private TextView maxText;
    private ImageView weatherImageView;
    private ProgressBar progressBar;

    // Log tag
    private final static String LOGTAG = "WeatherForecast";
    // Weather URL
    private final static String URL =
            "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        currentText = findViewById(R.id.current_text);
        minText = findViewById(R.id.min_text);
        maxText = findViewById(R.id.max_text);
        weatherImageView = findViewById(R.id.imageView_weather);

        ForecastQuery fq = new ForecastQuery();
        fq.execute();
    }

    class ForecastQuery extends AsyncTask<String, Integer, String> {

        private String current, minimum, maximum, iconName;
        private Bitmap icon;

        @Override
        public String doInBackground(String...args){
            try {

                URL url = new URL(URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.connect();

                // Get the InputStream
                conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();

                // get the InputStream and and place it in the XmlPullParser Object
                parser.setInput(conn.getInputStream(), null);

                // set the eventType to -1 to initiate the while loop
                int eventType = -1;

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    // Check if eventType is at the Start tag
                    if (eventType == XmlPullParser.START_TAG) {
                        // Assign the locationValue to the starting tag
                        String tempValues = parser.getName();

                        // if the name of the start tag is location, execute the code
                        if (tempValues.equals("temperature")) {

                            // get the attributes within the location tag: value, min, max
                            String value = parser.getAttributeValue(null, "value");
                            current = value;
                            publishProgress(25);
                            String min = parser.getAttributeValue(null, "min");
                            minimum = min;
                            publishProgress(50);
                            String max = parser.getAttributeValue(null, "max");
                            maximum = max;
                            publishProgress(75);
                            Log.i(LOGTAG, "Fetched value, min and max");

                        }

                        // if the name of the start tag is weather, execute the code
                        if (parser.getName().equals("weather")) {
                            // get the attributes within the weather tag: icon
                            iconName = parser.getAttributeValue(null, "icon");
                            String iconFile = iconName+".png";
                            // if the file exists locally, retrieve the icon
                            if (fileExistance(iconFile)) {
                                FileInputStream inputStream = null;
                                try {
                                    inputStream = new FileInputStream(getBaseContext().getFileStreamPath(iconFile));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                icon = BitmapFactory.decodeStream(inputStream);
                                Log.i(LOGTAG, "Image already exists");
                            } else {
                                URL iconUrl = new URL("http://openweathermap.org/img/w/" + iconName + ".png");
                                icon = getImage(iconUrl);
                                FileOutputStream outputStream = openFileOutput(iconName + ".png", Context.MODE_PRIVATE);
                                icon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                outputStream.flush();
                                outputStream.close();
                                Log.i(LOGTAG, "Adding new image");
                            }
                            Log.i(LOGTAG, "file name="+iconFile);
                            publishProgress(100);
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
        protected void onProgressUpdate(Integer... value) {
            Log.i(LOGTAG, "In onProgressUpdate");
            // set the progress bar visibility to visible
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // set the degreeSymbol variable to the symbol
            String degreeSymbol = Character.toString((char) 0x00B0);

            // Set the textViews with parsed data
            currentText.setText("Current:\t\t" + current + degreeSymbol + "C");
            minText.setText("Minimum:\t\t\t" + minimum + degreeSymbol + "C");
            maxText.setText("Maximum:\t\t\t" + maximum + degreeSymbol + "C");
            weatherImageView.setImageBitmap(icon);
            progressBar.setVisibility(View.INVISIBLE);
        }

    } // end of ForecastQuery Class

    // returns Bitmap that is either downloaded or taken locally
    protected static Bitmap getImage(URL url) {
        Log.i(LOGTAG, "In getImage");
        HttpURLConnection iconConn = null;
        try {
            iconConn = (HttpURLConnection) url.openConnection();
            iconConn.connect();
            int response = iconConn.getResponseCode();
            // if response code is 200, execute the code
            if (response == 200) {
                return BitmapFactory.decodeStream(iconConn.getInputStream());
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (iconConn != null) {
                iconConn.disconnect();
            }
        }
    }

    // Check if file exists and returns a boolean
    public boolean fileExistance(String fileName) {
        Log.i(LOGTAG, "In fileExistance");
        Log.i(LOGTAG, getBaseContext().getFileStreamPath(fileName).toString());
        File file = getBaseContext().getFileStreamPath(fileName);
        return file.exists();
    }



}

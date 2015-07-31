package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hackdfw.epiphanytripapp.Attraction.AttractionDatabase;
import com.example.hackdfw.epiphanytripapp.R;


public class MainActivity extends Activity {
    AttractionDatabase attdb = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new Re().execute();
//        WeatherQuery wq = new WeatherQuery();

        /*try {
            //attdb = new AttractionDatabase(str1, date, distance);
            attdb = new AttractionDatabase("Austin, TX", new Date(), 10);
//            Weather w = wq.getWeather("Dallas, TX", new Date());
//            Log.v("MainActivity", w.getSummary() + " " +  w.getPicURL());
        }catch(XPathException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }*/
        setContentView(R.layout.activity_main);
    }
/*
    private class Re extends AsyncTask<Void, Void, Void> {

        private Exception exception;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                AttractionDatabase attdb = new AttractionDatabase("Austin, TX", new Date(), 10);
            }catch(XPathException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

    }
*/

    /** Called when the user clicks the Next button */
    public void enterNextPage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, QueryPage.class);
        //EditText editText = (EditText) findViewById(R.id.edit_message);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}

package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hackdfw.epiphanytripapp.Attraction.AttractionDatabase;
import com.example.hackdfw.epiphanytripapp.R;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Timer nextPageTask = new Timer();
        nextPageTask.schedule(task, 2000);
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Intent intent = new Intent(getApplicationContext(), QueryPage.class);
            startActivity(intent);
        }
    };

    public void enterNextPage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, QueryPage.class);
        startActivity(intent);
    }
}

package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.os.Bundle;

import com.example.hackdfw.epiphanytripapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;


/**
 * Created by Gerry on 2015/8/26.
 */
public class TestActivity extends Activity {
    private GoogleMap googleMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        if(googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }

}

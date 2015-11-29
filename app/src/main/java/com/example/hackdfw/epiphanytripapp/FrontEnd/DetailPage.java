package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackdfw.epiphanytripapp.Attraction.Attraction;
import com.example.hackdfw.epiphanytripapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;


public class DetailPage extends Activity {

    private final String TAG = "DetailActivity";
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        Bundle bundle = getIntent().getExtras();
        if(bundle == null)
            Log.v(TAG, "bundle null");
        Attraction att = bundle.getParcelable("Chose");

        TextView tv_name = (TextView) findViewById(R.id.name_detail);
        tv_name.setText(att.getName());

        TextView tv_city = (TextView) findViewById(R.id.city);
        tv_city.setText(att.getCity());

//        TextView tv_url = (TextView) findViewById(R.id.url);
//        tv_url.setText("URL: " + att.getPicURL());

        if(att.getWeather() != null) {
            TextView tv_weather = (TextView) findViewById(R.id.weather);
            tv_weather.setText(att.getWeather().getSummary());
        }

        TextView tv_rating = (TextView) findViewById(R.id.rating);
        tv_rating.setText("Rating: " + att.getRating() + "/5.0");

        TextView tv_distance = (TextView) findViewById(R.id.Distance);
        double distance = att.getDistanceFromStart() * 0.000621371192;
        distance = Math.round(distance * 100) / 100;
        tv_distance.setText("Distance from origin: "+ distance +" (miles)");

        new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                .execute(att.getPicURL());

        LatLng thisBusiness = new LatLng(att.getLatitude(), att.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(thisBusiness, 10f));
        googleMap.addMarker(new MarkerOptions().position(thisBusiness).title(att.getName()));
    }
}

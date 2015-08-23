package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hackdfw.epiphanytripapp.Attraction.Attraction;
import com.example.hackdfw.epiphanytripapp.Attraction.AttractionDatabase;
import com.example.hackdfw.epiphanytripapp.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPathException;

/*
 *		name = n;
 *		cityName = cn; //city, STATE
 *		rating = r;
 *		distanceFromStart = dfs;
 *		picURL = pu;  *
 *		weather = null;
 */

public class ChoiceListPage extends Activity {
    private final String TAG = "ChoiceListPage";
    private AttractionDatabase attdb = null;
    private ListView listview;
    private LatLng locationLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listview =  (ListView) findViewById(R.id.list);

        Bundle b = this.getIntent().getExtras();
        Date date = (Date) b.getSerializable("Date");
        locationLatLng = b.getParcelable("LocationLatLng");

        if(date == null)
            date = Calendar.getInstance().getTime();

        try{
            String start_location = Double.toString(locationLatLng.latitude) + ", " + Double.toString(locationLatLng.longitude);
            attdb = new AttractionDatabase(start_location, date, b.getInt("Distance")); }
        catch (XPathException | IOException e){ e.printStackTrace(); }
        CreateList(attdb.getAllAttractions());
    }

    public void CreateList(ArrayList<Attraction> list){
        final ArrayList<String> entries = new ArrayList<String>();
        final ArrayList<String> URLentries = new ArrayList<String>();

        for (int i = 0; i < list.size(); i++){  // Log.v("CreateList", list.get(i).getName() + "\n" + list.get(i).getCity() + "\n" + list.get(i).getWeather().getSummary());
            String details = list.get(i).getName() + "\n\t\t" + list.get(i).getCity();
            if(list.get(i).getWeather() != null)
                details +=  "\n\t\t" + list.get(i).getWeather().getSummary();
            entries.add(details);
            String URLs = list.get(i).getPicURL();
            URLentries.add(URLs);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, entries, URLentries);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        Intent intent = new Intent(getBaseContext(), DetailPage.class);
                        Bundle bundle = new Bundle();
                        Log.v("Id : ", "" + position);
                        bundle.putParcelable("Chose", attdb.getAllAttractions().get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
            }
        });
    }
}

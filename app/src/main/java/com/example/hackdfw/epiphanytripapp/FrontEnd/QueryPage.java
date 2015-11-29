package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackdfw.epiphanytripapp.R;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class QueryPage extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    private Date date = null;
    private TextView Query_edit_text_2;
    private GoogleMap googleMap;
    private SeekBar seekBar;
    private LatLng locationLatLng;
    private Circle mapCircle;
    private double currentRadius;
    private int distance;

    private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";
    private static final boolean PRINT_AS_STRING = false;
    public static final String TAG = "QueryPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_page);
        Query_edit_text_2 = (TextView) findViewById(R.id.Query_edit_text_2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        setupSeekbarListeners();
        currentRadius = 0;
        distance = 0;

        if(googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        }

        GoogleMap.OnCameraChangeListener moveCricleWithCamera = new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Log.v(TAG, "onCameraChange");
                drawCircle(currentRadius);
            }
        };
        googleMap.setOnCameraChangeListener(moveCricleWithCamera);
    }

    private void setupSeekbarListeners(){
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean arg2){
                // Draw circle and set distance
                drawCircle((double) (progress + 1)  * 1609.3);
                currentRadius = (progress + 1)  * 1609.3;
                distance = progress + 1;

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        };
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void drawCircle(double radius) {
        if(mapCircle != null)
            mapCircle.remove();
        CircleOptions circle = new CircleOptions();
        locationLatLng = googleMap.getCameraPosition().target;
        Log.v(TAG, "Draw circle on " + locationLatLng.toString() + " with radius " + radius);
        circle.center(locationLatLng);
//        circle.strokeColor(0xFFFFA420);
        circle.strokeWidth(2f);
//        circle.fillColor(0x11FFA420);
        circle.radius(radius);
        mapCircle = googleMap.addCircle(circle);

    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        // Do something with the date chosen by the user
        Calendar c = new GregorianCalendar(year, month,day);
        date = c.getTime();
        Query_edit_text_2.setText(date.toString());
    }

    public void showDatePickerDialog(View v) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                QueryPage.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    public void startSearch(View view)  {
        locationLatLng = googleMap.getCameraPosition().target;
        Log.v(TAG, locationLatLng.toString());

        // Do something in response to button
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(1000);

        // TODO: Check query formats
        if(date != null && (date.before(new Date()) && date.getDate() != (new Date()).getDate()  )   ){
            Toast.makeText(this, "Start date format error.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, ChoiceListPage.class);
            Bundle bundle=new Bundle();

            // TODO: new a query class(Parcelable) to make pass in easier
            Log.v(TAG, "Distance = " + distance + "(miles)");
            bundle.putInt("Distance", distance);
            bundle.putSerializable("Date", date);
            bundle.putParcelable("LocationLatLng", locationLatLng);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

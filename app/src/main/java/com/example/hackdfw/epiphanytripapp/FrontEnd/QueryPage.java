package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackdfw.epiphanytripapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class QueryPage extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    private Date date = null;
    private TextView Query_edit_text_2;
    private EditText Query_edit_text_city, Query_edit_text_state, Query_edit_text_distance;
    private GoogleMap googleMap;
    public static final String TAG = "QueryPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_page);
        Query_edit_text_2 = (TextView) findViewById(R.id.Query_edit_text_2);
        Query_edit_text_city = (EditText) findViewById(R.id.Query_edit_text_location_city);
        Query_edit_text_state = (EditText) findViewById(R.id.Query_edit_text_location_state);
        Query_edit_text_distance = (EditText) findViewById(R.id.Query_edit_text_distance);
        if(googleMap == null) {
            try{
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
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
        LatLng locationLatLng = googleMap.getCameraPosition().target;
        Log.v(TAG, locationLatLng.toString());

        // Do something in response to button
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(1000);

        String str1 = Query_edit_text_city.getText().toString().trim();
        String str2 = Query_edit_text_state.getText().toString().trim();

        String location = str1 + ", " + str2;

        String str3 = Query_edit_text_distance.getText().toString().trim();

        int distance = 0;
        if(!"".equals(str3)) {
            distance = Integer.parseInt(str3);
        }

        // TODO: Check query formats
        if(!location.isEmpty() && !location.matches("\\w+, [A-Z][A-Z]")) {
            Toast.makeText(this, "Please use the format [city, ST].", Toast.LENGTH_SHORT).show();
        }
        else if(date != null && (date.before(new Date()) && date.getDate() != (new Date()).getDate()  )   ){
            Toast.makeText(this, "Start date format error.", Toast.LENGTH_SHORT).show();
        }
        else if(!str3.isEmpty() && distance <= 0){
            Toast.makeText(this, "Distance format error.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(this, ChoiceListPage.class);
            Bundle bundle=new Bundle();

            // TODO: new a query class(Parcelable) to make pass in easier
            bundle.putString("Location", location);
            bundle.putInt("Distance", distance);
            bundle.putSerializable("Date", date);
            bundle.putParcelable("LocationLatLng", locationLatLng);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}

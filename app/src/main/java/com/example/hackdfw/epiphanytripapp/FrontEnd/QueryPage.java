package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hackdfw.epiphanytripapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class QueryPage extends ActionBarActivity {

    private static Date date = null;
    private static int year_g = 0;
    private static int month_g = 0;
    private static int day_g = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_page);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_query_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            year_g = year;
            month_g = month;
            day_g = day;

            Calendar c = new GregorianCalendar();
            c.set(year, month,day);
            date = c.getTime();
            TextView Query_edit_text_2 = (TextView) getActivity().findViewById(R.id.Query_edit_text_2);
            Query_edit_text_2.setText(date.toString());
        }
    }

    private DialogFragment newFragment  = null;
    public void showDatePickerDialog(View v) {
        newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void startSearch(View view)  {
        // Do something in response to button
        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(1000);

        EditText Query_edit_text_city = (EditText) findViewById(R.id.Query_edit_text_location_city);
        String str1 = Query_edit_text_city.getText().toString().trim();

        EditText Query_edit_text_state = (EditText) findViewById(R.id.Query_edit_text_location_state);
        String str2 = Query_edit_text_state.getText().toString().trim();

        String location = str1 + ", " + str2;

        EditText Query_edit_text_distance = (EditText) findViewById(R.id.Query_edit_text_distance);
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
            bundle.putInt("Year", year_g);
            bundle.putInt("Month", month_g);
            bundle.putInt("Day", day_g);
            intent.putExtras(bundle);
            startActivity(intent);
        }

    }
}

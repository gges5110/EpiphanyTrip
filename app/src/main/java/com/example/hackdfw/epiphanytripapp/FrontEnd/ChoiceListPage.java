package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class ChoiceListPage extends Activity implements View.OnClickListener {
    private AttractionDatabase attdb = null;
    private ListView listview;
    @Override

    public void onClick(View v){
        Intent intent = new Intent(this, DetailPage.class);
        Bundle bundle=new Bundle();
        Log.v("Id : ", "" + v.getId());
        bundle.putInt("Chosen", v.getId());

        bundle.putString("name",attdb.getAllAttractions().get(v.getId()).getName());
        bundle.putString("city",attdb.getAllAttractions().get(v.getId()).getCity());
        bundle.putString("url",attdb.getAllAttractions().get(v.getId()).getPicURL());
        if(attdb.getAllAttractions().get(v.getId()).getWeather() == null)
            bundle.putString("weather","Not Available");
        else
            bundle.putString("weather",attdb.getAllAttractions().get(v.getId()).getWeather().getSummary());
        bundle.putDouble("rating",attdb.getAllAttractions().get(v.getId()).getRating());
        bundle.putDouble("distance",attdb.getAllAttractions().get(v.getId()).getDistanceFromStart());

        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        listview =  (ListView) findViewById(R.id.list);

        Bundle b=this.getIntent().getExtras();
        int year = b.getInt("Year");
        int month = b.getInt("Month");
        int day = b.getInt("Day");
        int distance = b.getInt("Distance");
        String location = b.getString("Location");

        Calendar c = new GregorianCalendar();
        c.set(year, month,day);
        Date date = c.getTime();
        try{
            attdb = new AttractionDatabase(location, date, distance);
        }catch(XPathException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

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
        }

        for (int i = 0; i < list.size(); i++){
            String details = list.get(i).getPicURL();
            URLentries.add(details);
        }
//        LinearLayout ll = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.activity_choice_list_page, null);
//        for(int k = 0; k < entries.size() ; k++){ //change size
//            TextView tv = new TextView(this);
//            tv.setTextColor(0xFF000000);
//            tv.setId(k);
//            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);
//            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            String str = entries.get(k);
//            str = (k + 1) +". "+ str;
//            tv.setText(str);
//            ll.addView(tv);
//            tv.setOnClickListener(this);
//        }
//        setContentView(ll);

//        final ArrayList<String> list = new ArrayList<String>();
//        for (int i = 0; i < values.length; ++i) {
//            list.add(values[i]);
//        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, entries, URLentries);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                        Intent intent = new Intent(getBaseContext(), DetailPage.class);
                        Bundle bundle=new Bundle();
                        Log.v("Id : ", "" + position);
                        bundle.putInt("Chosen", position);

                        bundle.putString("name",attdb.getAllAttractions().get(position).getName());
                        bundle.putString("city", attdb.getAllAttractions().get(position).getCity());
                        bundle.putString("url",attdb.getAllAttractions().get(position).getPicURL());
                        if(attdb.getAllAttractions().get(position).getWeather() == null)
                            bundle.putString("weather","Not Available");
                        else
                            bundle.putString("weather",attdb.getAllAttractions().get(position).getWeather().getSummary());
                        bundle.putDouble("rating",attdb.getAllAttractions().get(position).getRating());
                        bundle.putDouble("distance",attdb.getAllAttractions().get(position).getDistanceFromStart());

                        intent.putExtras(bundle);
                        startActivity(intent);
//                final String item = (String) parent.getItemAtPosition(position);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                entries.remove(item);
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
            }

        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        ArrayList<String> details;
        ArrayList<String> imageURL;
        HashMap<String, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects, ArrayList<String> URLstr) {
            super(context, textViewResourceId, objects);
            this.details = (ArrayList<String>) objects;
            imageURL = URLstr;
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(details.get(position));

            new DownloadImageTask(imageView).execute(imageURL.get(position));
            return rowView;
        }

        private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
            ImageView bmImage;

            public DownloadImageTask(ImageView bmImage) {
                this.bmImage = bmImage;
            }

            protected Bitmap doInBackground(String... urls) {
                String urldisplay = urls[0];
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                return mIcon11;
            }

            protected void onPostExecute(Bitmap result) {
                bmImage.setImageBitmap(result);
            }
        }




        @Override
        public long getItemId(int position) {
            return mIdMap.get(getItem(position));
        }

        @Override
        public boolean hasStableIds() { return true; }
    }

}

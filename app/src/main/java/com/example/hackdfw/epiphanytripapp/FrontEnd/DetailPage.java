package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.app.Activity;
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

import java.io.InputStream;


public class DetailPage extends Activity {
    private final String TAG = "DetailActivity";
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        Bundle bundle = getIntent().getBundleExtra("bundleA");
        if(bundle == null)
            Log.v(TAG, "bundle null");
        Attraction att = bundle.getParcelable("Chose");

        Attraction att1 = bundle.getParcelable("Chose");

        if(att == null) {
            Log.v(TAG, "att == null");
        }
        if(att1 == null) {
            Log.v(TAG, "att1 == null");
        }

        String name = bundle.getString("name");
        Log.v("Details", name);
        TextView tv_name = (TextView) findViewById(R.id.name_detail);
        if (tv_name == null)
            Log.v("Detail", "ERROROROROROR");
        //name = "Attraction: " + name;
        tv_name.setText(att1.getName());
        tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP,28);

        String city = bundle.getString("city");
        TextView tv_city = (TextView) findViewById(R.id.city);
        city = "City: "+city;
        tv_city.setText(att.getCity());
        tv_city.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        String URL = bundle.getString("url");
        TextView tv_url = (TextView) findViewById(R.id.url);
        tv_url.setText("URL: " +URL);
        tv_url.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        String weather = bundle.getString("weather");
        TextView tv_weather = (TextView) findViewById(R.id.weather);
        weather = "Weather: " + weather;
        tv_weather.setText(weather);
        tv_weather.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        double rating = bundle.getDouble("rating");
        TextView tv_rating = (TextView) findViewById(R.id.rating);
        tv_rating.setText("Rating: " + rating + "/5.0");
        tv_rating.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        double distance = bundle.getDouble("distance");
        TextView tv_distance = (TextView) findViewById(R.id.Distance);
        tv_distance.setText("Distance from origin: "+((int) distance)*0.000621371192 +" (miles)");
        tv_distance.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView2))
                .execute(URL);

//        ImageView mImgView = (ImageView) findViewById(R.id.imageView2);
//        //String url = "https://www.morroccomethod.com/components/com_virtuemart/shop_image/category/resized/Trial_Sizes_4e4ac3b0d3491_175x175.jpg";
//        BitmapFactory.Options bmOptions;
//        bmOptions = new BitmapFactory.Options();
//        bmOptions.inSampleSize = 1;
//        Bitmap bm = BitmapFactory.loadBitmap(URL, bmOptions);
//        mImgView.setImageBitmap(bm);
    }


}

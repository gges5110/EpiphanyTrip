package com.example.hackdfw.epiphanytripapp.FrontEnd;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hackdfw.epiphanytripapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yuchiawu on 8/10/15.
 */
public class StableArrayAdapter extends ArrayAdapter<String> {
    ArrayList<String> details;
    ArrayList<String> imageURL;
    HashMap<String, Integer> mIdMap = new HashMap<>();
    Context context = null;

    public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects, ArrayList<String> URLstr) {
        super(context, textViewResourceId, objects);
        this.context = context;
        this.details = (ArrayList<String>) objects;
        imageURL = URLstr;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(details.get(position));

        new DownloadImageTask(imageView).execute(imageURL.get(position));
        return rowView;
    }

    @Override
    public long getItemId(int position) {
        return mIdMap.get(getItem(position));
    }

    @Override
    public boolean hasStableIds() { return true; }
}

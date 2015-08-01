package com.example.hackdfw.epiphanytripapp.Weather;

import android.os.Parcel;
import android.os.Parcelable;

public class Weather implements Parcelable{

	private String summary;
	private String picURL;

    // Parcelable need the following methods
    public Weather(Parcel in) {
        summary = in.readString();
        picURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(summary);
        dest.writeString(picURL);
    }

    public static final Parcelable.Creator<Weather> CREATOR = new Parcelable.Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };
	//Parcelable ends
	public Weather(String w, String wURL){
		summary = w;
		picURL= wURL;
	}

	
	public String getSummary(){
		return summary;
	}
	
	public String getPicURL(){
		return picURL;
	}
}

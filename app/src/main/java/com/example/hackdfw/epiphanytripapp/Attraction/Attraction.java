package com.example.hackdfw.epiphanytripapp.Attraction;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hackdfw.epiphanytripapp.Weather.Weather;

public class Attraction implements Parcelable{
	private String name;
	private String cityName;
	private double rating;
	private double distanceFromStart;
	private String picURL;
	Weather weather;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(cityName);
        dest.writeDouble(rating);
        dest.writeDouble(distanceFromStart);
        dest.writeString(picURL);
        dest.writeParcelable(weather, flags);
    }

    private Attraction(Parcel in){
        this.name = in.readString();
        this.cityName = in.readString();
        this.rating = in.readDouble();
        this.distanceFromStart = in.readDouble();
        this.picURL = in.readString();
        this.weather = in.readParcelable(Weather.class.getClassLoader());
    }

    public static final Parcelable.Creator<Attraction> CREATOR = new Parcelable.Creator<Attraction>() {
        @Override
        public Attraction createFromParcel(Parcel source) {
            return new Attraction(source);
        }

        @Override
        public Attraction[] newArray(int size) {
            return new Attraction[size];
        }
    };

	public Attraction(String n, String cn, double r, double dfs, String pu){
		name = n;
		cityName = cn; //city, STATE
		rating = r;
		distanceFromStart = dfs;
		picURL = pu;
		weather = null;
	}
		
	public String getName(){
		return name;
	}
	
	public String getCity(){
		return cityName;
	}
	
	public double getRating(){
		return rating;
	}
	
	public double getDistanceFromStart(){
		return distanceFromStart;
	}
	
	public void setWeather(Weather w){
		weather = w;
	}
	
	public Weather getWeather(){
		return weather;
	}
	
	public String getPicURL(){
		return picURL;
	}
}

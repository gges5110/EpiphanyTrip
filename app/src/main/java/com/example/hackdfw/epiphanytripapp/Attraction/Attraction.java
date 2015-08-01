package com.example.hackdfw.epiphanytripapp.Attraction;

import com.example.hackdfw.epiphanytripapp.Weather.Weather;

public class Attraction {
	private String name;
	private String cityName;
	private double rating;
	private double distanceFromStart;
	private String picURL;
	Weather weather;
	
	//possibly adding addresses, directions, descriptions, review list...
	public Attraction(){

    }
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

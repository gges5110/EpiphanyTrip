package com.example.hackdfw.epiphanytripapp.Attraction;
import android.util.Log;

import com.example.hackdfw.epiphanytripapp.Weather.Weather;
import com.example.hackdfw.epiphanytripapp.Weather.WeatherQuery;
import com.example.hackdfw.epiphanytripapp.Yelp.YelpQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import javax.xml.xpath.XPathExpressionException;


public class AttractionDatabase {
	private ArrayList<Attraction> attractions;

	public AttractionDatabase(String start_location, Date date, int distance) throws XPathExpressionException, IOException{
        Log.v("AttractionDatabase", start_location);
		YelpQuery yq = new YelpQuery();
		attractions = yq.getAttractions(start_location , distance);
		WeatherQuery wq = new WeatherQuery();
        HashMap<String, Weather> UniCity = new HashMap();
		for(int i = 0; i<attractions.size(); i++){
            if(!UniCity.containsKey(attractions.get(i).getCity())){
                UniCity.put(attractions.get(i).getCity(), wq.getWeather(attractions.get(i).getCity(), date));
                Log.v("AttractionDatabase", attractions.get(i).getCity());
            }
			attractions.get(i).setWeather(UniCity.get(attractions.get(i).getCity()));

		}
	}

    public ArrayList<Attraction> getAllAttractions(){
        return attractions;
    }
    
    public ArrayList<Attraction> filterByDistance(int d , ArrayList<Attraction> attr){
        ArrayList<Attraction> temp_attr = new ArrayList<Attraction>();
    	if(attr.size()==0)
    		return null;
    	else{
    		for(int i = 0; i<attr.size(); i++){
	        	if(attr.get(i).getDistanceFromStart()<=d){
	        		temp_attr.add(attr.get(i));
	        	}
    		}
    		return temp_attr;
        }     
        
    }

    public ArrayList<Attraction> filterByDistance(int d){
       return this.filterByDistance(d,attractions);
    }

	public ArrayList<Attraction> filterByWeather(String w , ArrayList<Attraction> attr){
		
		ArrayList<Attraction> temp_attr = new ArrayList<Attraction>();
		if(attr.size() == 0)
			return null;
		else{	
			for(int i = 0; i < attr.size(); i++){
			    if(attr.get(i).getWeather().getSummary().equals(w)){
			        temp_attr.add(attr.get(i));
			    }
			}
			return temp_attr;
		}
	}

    public ArrayList<Attraction> filterByWeather(String w){
        return this.filterByWeather(w,attractions);
    }
	
	public ArrayList<Attraction> filterByRating(double r , ArrayList<Attraction> attr) {
		ArrayList<Attraction> temp_attr = new ArrayList<Attraction>();
    	if(attr.size()==0)
    		return null;
    	else{
    		for(int i = 0; i<attr.size(); i++){
	        	if(attr.get(i).getDistanceFromStart()>=r){
	        		temp_attr.add(attr.get(i));
	        	}
    		}
    		return temp_attr;
        }  
	}

    public ArrayList<Attraction> filterByRating(double r) {
        return this.filterByRating(r,attractions);
    }

	public ArrayList<Attraction> filterByCityName(String cn , ArrayList<Attraction> attr){
		ArrayList<Attraction> temp_attr = new ArrayList<Attraction>();
		if(attr.size() == 0)
			return null;
		else{	
			for(int i = 0; i < attr.size(); i++){
			    if(attr.get(i).getWeather().equals(cn)){
			        temp_attr.add(attr.get(i));
			    }
			}
			return temp_attr;
		}
	}

    public ArrayList<Attraction> filterByCityName(String n){
        return this.filterByCityName(n,attractions);
    }

}

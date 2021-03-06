package com.example.hackdfw.epiphanytripapp.Weather;

import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;


public class WeatherQuery {

	private HttpURLConnection connection;
	private String httpRequest = "http://api.wunderground.com/api/7b5d56ecee70528f/";
    private XPath xpath;
    private InputSource inputXml = null;
    private static final String TAG = "WeatherQuery";
		
	public WeatherQuery() {
		connection = null;
		httpRequest = "http://api.wunderground.com/api/7b5d56ecee70528f/";
		xpath = XPathFactory.newInstance().newXPath();
		inputXml = null;
	}
	
	
	// adding private to query API
	public Weather getWeather(String cn, Date d) throws XPathExpressionException, IOException{
		String[] parts = cn.split(", ");
        Log.v("Weather", cn);
        FetchAPI fapi = new FetchAPI("forecast10day/q/" + parts[1] + "/" + parts[0] + ".xml", d);
        fapi.start();
        try {
            fapi.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
		return fapi.getResult();
	}

    public class FetchAPI extends Thread {
        private String request;
        private Date date;
        private Weather weather;
        public FetchAPI(String r, Date d){
            request = r;
            date = d;
        }

        public void run() {
            try {
                weather = getAPIResponse(request, date);
            }catch(XPathExpressionException | IOException e){
                e.printStackTrace();
            }
        }

        public Weather getResult(){
            return weather;
        }
    }

	private Weather getAPIResponse(String request, Date d) throws IOException, XPathExpressionException{
		
		URL url = new URL(httpRequest + request);
		connection = (HttpURLConnection) url.openConnection();
		
		inputXml = new InputSource(connection.getInputStream());
		NodeList iconNodes = (NodeList) xpath.compile("/response/forecast/txt_forecast/forecastdays/forecastday/icon").evaluate(inputXml, XPathConstants.NODESET);
		
		connection = (HttpURLConnection) url.openConnection();
		inputXml = new InputSource(connection.getInputStream());
		NodeList urlNodes = (NodeList) xpath.compile("/response/forecast/txt_forecast/forecastdays/forecastday/icon_url").evaluate(inputXml, XPathConstants.NODESET);

		int diff = d.compareTo(new Date());
        diff = (diff < 1)? 1:diff;
        
        if(diff > 10){
        	return new Weather("","");
        }

		Log.v(TAG, urlNodes.item(diff).getFirstChild().getNodeValue());


		return new Weather(iconNodes.item(diff).getFirstChild().getNodeValue(), urlNodes.item(diff).getFirstChild().getNodeValue());
	}
}

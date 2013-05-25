package scas.net.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class ElevationService {
	
	public String getLocation(String address) throws IOException{
		address = address.replaceAll(" ","+");
		String request = "http://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=true";
		JSONObject jsonObject = new JSONObject(connect(request));
		JSONArray array = jsonObject.getJSONArray("results");
		//System.out.println(array.getJSONObject(0).get("geometry"));
		JSONObject geometry = array.getJSONObject(0).getJSONObject("geometry");
		JSONObject location = geometry.getJSONObject("location");
		String lng = location.get("lng").toString();
		String lat = location.get("lat").toString();
		//System.out.println(lng+ " " + lat);
		return lat+","+lng;
	}
	
	public String getElevationOfLocation(String location) throws IOException{
		String request = "http://maps.googleapis.com/maps/api/elevation/json?locations="+location+"&sensor=true";
		JSONObject jsonObject = new JSONObject(connect(request));
		JSONArray array = jsonObject.getJSONArray("results");
		//System.out.println(array.getJSONObject(0).get("elevation"));
		return array.getJSONObject(0).get("elevation").toString();
	}
	
	public String connect(String _url) throws IOException{
		URL url = new URL(_url);
		URLConnection urlConnection = url.openConnection();
		try{
			urlConnection.setConnectTimeout(3000);
			urlConnection.setReadTimeout(5000);
			urlConnection.connect();
		}catch(SocketTimeoutException ex){
			System.out.println("Connection timeout");
		}
				StringBuffer bufferString = new StringBuffer();
		try{
			BufferedReader bufferedReader;
			bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String line;
			while((line = bufferedReader.readLine()) !=null){
				bufferString.append(line);
			}
			bufferedReader.close();
		} catch (IOException e) {
			
		}
		return bufferString.toString();
	}
	public float getElevationDifference(String address1, String address2) throws IOException{
		String elevation1 = getElevationOfLocation(getLocation(address1));
		String elevation2 = getElevationOfLocation(getLocation(address2));
		//System.out.println(elevation1 + " " + elevation2);
		float result = Float.parseFloat(elevation1) - Float.parseFloat(elevation2);
		if(result < 0){
			System.out.println(address1+" is " + Math.abs(result) + " meters lower than " + address2 );
		}
		else
			System.out.println(address1+" is " + result + " meters higher than " + address2 );
		return result;
	}
	
	
	public static void main(String [] args) throws IOException{
		String address1 = "31694 Ponderosa Way, Evergreen CO USA";
		String address2 = "Agricultori 86, Bucharest, Romania";
		System.out.println(new ElevationService().getElevationDifference(address1, address2));
	}
}

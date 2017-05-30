package com.weberfly.service.threads;

import java.io.InputStream;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.weberfly.entities.Country;
import com.weberfly.entities.Location;
import com.weberfly.service.CountryService;
import com.weberfly.service.LocationService;


public class PostLocationDetection extends Thread {
	public static final String USER_AGENT = "Mozilla/5.0";
	@Autowired
	LocationService locationService;
	@Autowired
	CountryService countryService;

	private volatile boolean running = true;
	private String ipAdresse;

	public PostLocationDetection(String ipAdresse) {
		this.ipAdresse = ipAdresse;
	}

	public void arreter() {
		this.running = false;
	}

	@Override
	public void run() {

		// traitement du thread
		try {
			getLocationByIpAdresse(this.ipAdresse);
			System.out.println("thread finish");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Location getLocationByIpAdresse(String ip) throws Exception {
		Location location = null;
		System.out.println(ip);
		 location=locationService.find(ip);

		if (location != null) {// the location already exist with the same or					
			return location;  // other user session
		} else {// create the location , probably this is the first time
				// connection by the current user
			String url = "http://ip-api.com/json/" + ip;
			try {
				location = new Location();
				location.setIp(ip);
				InputStream stream = new URL(url).openStream();
				String genreJson = IOUtils.toString(stream);
				JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(genreJson);

				double latitude = (double) genreJsonObject.get("lat");
				double longitude = (double) genreJsonObject.get("lon");
				System.out.println((String) genreJsonObject.get("countryCode"));
				Country country = countryService.find((String) genreJsonObject.get("countryCode"));

				location.setCountry(country);
				location.setLatitude(latitude);
				location.setLongitude(longitude);
				System.out.print(location);
				locationService.save(location);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println(location.getLongitude());
			return location;
		}

	}
}

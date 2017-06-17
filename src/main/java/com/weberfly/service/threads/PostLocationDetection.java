package com.weberfly.service.threads;

import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.weberfly.entities.Country;
import com.weberfly.entities.Location;
import com.weberfly.entities.Session;
import com.weberfly.entities.User;
import com.weberfly.service.CountryService;
import com.weberfly.service.LocationService;

import com.weberfly.service.UserService;

public class PostLocationDetection extends Thread {
	public static final String USER_AGENT = "Mozilla/5.0";
	@Autowired
	LocationService locationService;
	@Autowired
	CountryService countryService;
	@Autowired
	UserService userService;
	@Autowired
	HttpSession session;
	User connectd;

	public PostLocationDetection(User u) {
		this.connectd= u;
	}

	@Override
	public void run() {
		// traitement du thread
		try {
			getLocationByIpAdresse();
			System.out.println("thread finish");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public Location getLocationByIpAdresse() throws Exception {
		Location location = null;
		Country country =null;

		String url = "http://ip-api.com/json/";
		try {

			InputStream stream = new URL(url).openStream();
			String genreJson = IOUtils.toString(stream);
			JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(genreJson);

			String ipAdress = (String) genreJsonObject.get("query");
			Session session=new Session();
			session.setIpUser(ipAdress);
		
            this.connectd.setSession(session);
            userService.update(this.connectd);
            
			location = locationService.find(ipAdress);
			if (location != null) {// the location already exist with the same
									// or
				return location;
			}
			location = new Location();
			location.setIp(ipAdress);
			double latitude = (double) genreJsonObject.get("lat");
			double longitude = (double) genreJsonObject.get("lon");
			System.out.println((String) genreJsonObject.get("countryCode"));
			 country = countryService.find((String) genreJsonObject.get("countryCode"));

			location.setCountry(country);
			location.setLatitude(latitude);
			location.setLongitude(longitude);
			System.out.println(locationService.findLast());
			locationService.save(location);
			
			
		} catch (Exception e) {
			// if the ip is not valide we set default location to the 
			//created post by searchyin from th user session with ip 127.0.0.1;
			System.out.println("error while loeading location");
			Session session=new Session();
			this.connectd.setSession(session);
			userService.update(this.connectd);
			
		}

		return location;
	}

}

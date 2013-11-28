package controllers;

import play.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Core {
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	}
	
	public static String findCityAndCountryByIATACode(String IATACode) {
		String jsonStr = null;
		String cityName = null, countryName = null;
		try {
			jsonStr = Files.toString(new File(Play.application().resource("public/json/city_airport_codes.json").toURI()), Charsets.UTF_8);
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(jsonStr);
			for (JsonNode jsonNode : actualObj) {
				if (jsonNode.get("iata_code").textValue().equals(IATACode)) {
					cityName = jsonNode.get("city").textValue();
					countryName = jsonNode.get("country").textValue();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cityName.concat(", " + countryName);
	}
}

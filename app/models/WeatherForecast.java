package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.Core;

public class WeatherForecast {

	public List<WeatherData> wd;
	
	public WeatherForecast() {
		wd = new ArrayList<WeatherData>();
	}

	public void getTemperatureByLatLongOnDate(double Lat, double Long, String date) {
		String jsonStr;
		try {
			jsonStr = Core.readUrl("http://api.openweathermap.org/data/2.5/forecast?lat=" + Lat + "&lon=" + Long + "&units=metric&cnt=14");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode actualObj = mapper.readTree(jsonStr);
			JsonNode results = actualObj.get("list");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (JsonNode element: results) {
				if(formatter.parse(date).before(formatter.parse(element.get("dt_txt").toString().replace('"', ' ').trim()))) {
					wd.add(new WeatherData(formatter.parse(element.get("dt_txt").toString().replace('"', ' ').trim()), element.get("main").get("temp").toString().substring(0,2)));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
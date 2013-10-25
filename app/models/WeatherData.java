package models;

import java.util.Date;

public class WeatherData {

	public Date date;
	public String temperature;
	
	public WeatherData(Date date, String temperature) {
		this.date = date;
		this.temperature = temperature;
	}
}

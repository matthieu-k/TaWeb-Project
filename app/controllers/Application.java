package controllers;

import models.AirportService;
import models.WeatherForecast;

import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.flights;
import views.html.index;
import views.html.weather;
import views.html.airportInformationByCountry;

public class Application extends Controller {
	
    public static Result index() {
        return ok(
        			index.render()
    			);
    }
    
    public static Result GetAirportInformationByCountry(String country) {
    	return ok(
    				airportInformationByCountry.render(
    							AirportService.GetAirportInformationByCountry(country)
    						)
				);
    }
    
    public static Result flights() {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
        String iataCode = dynamicForm.get("iataCode");
        String arrivalDateTime = dynamicForm.get("arrivalDateTime");
        
    	return ok(
    				flights.render(iataCode, arrivalDateTime)
    			);
    }
    
    public static Result weather() {
    	WeatherForecast wf = new WeatherForecast();
    	wf.getWeatherByLatLongOnDate(43.61, 3.87, "2013-10-31 12:00:00");
    	return ok(
    				weather.render(wf.wd)
    			);
    }
}
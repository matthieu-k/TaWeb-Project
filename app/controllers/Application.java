package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import models.AirportService;
import models.Core;
import models.WeatherData;
import models.WeatherForecast;
import models.city.City;
import models.city.CityParser;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.results;
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
    
    public static Result results() throws ParseException {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
        String iataCode = dynamicForm.get("iataCode");
        String arrivalDateTimeStr = dynamicForm.get("arrivalDateTime");
        Date ArrivalDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(arrivalDateTimeStr);

        // GETTING CITY DATA
        System.out.println(iataCode);
        City city = CityParser.getCityInfoByIataCode(iataCode);
        
        // GET WEATHER INFORMATION
        List<WeatherData> weatherData = WeatherForecast.getWeatherByLatLongOnDate(43.61, 3.87, ArrivalDate);

    	return ok(results.render(city, ArrivalDate, weatherData));
    }
}
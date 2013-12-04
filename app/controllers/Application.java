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
import views.html.cityInformationByQuery;

public class Application extends Controller {
	
    public static Result index() {
        return ok(
        			index.render()
    			);
    }

    public static Result cityInformationByQuery(String query) {
    	return ok(cityInformationByQuery.render(Core.getCityByQuery(query)));
    }
    
    public static Result results() throws ParseException {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
        String destination = dynamicForm.get("destinationValue");
        String arrivalDateTimeStr = dynamicForm.get("arrivalDateTime");
        Date ArrivalDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(arrivalDateTimeStr);

        // GETTING CITY DATA
        System.out.println(destination);
        City city = CityParser.getCityInfoByIataCode(destination);
        
        // GET WEATHER INFORMATION
        List<WeatherData> weatherData = WeatherForecast.getWeatherByLatLongOnDate(43.61, 3.87, ArrivalDate);
        
        // GET CURRENCY INFORMATION

    	return ok(results.render(city, ArrivalDate, weatherData));
    }
}
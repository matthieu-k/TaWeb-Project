package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

public class Application extends Controller 
{
    public static Result index() 
    {
        return ok( index.render() );
    }

    public static Result cityInformationByQuery(String query) 
    {
    	return ok(cityInformationByQuery.render(Core.getCityByQuery(query)));
    }
    
    public static Result results() throws ParseException 
    {
    	// GET FORM DATA
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
        
    	String destination = dynamicForm.get("destinationValue");
        String arrivalDateTimeStr = dynamicForm.get("arrivalDateTime");
        Date ArrivalDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(arrivalDateTimeStr);

        // GET CITY DATA
        System.out.println(destination);
        City city = CityParser.parse("Montpellier");
        
        // GET WEATHER INFORMATION
        List<WeatherData> weatherData = WeatherForecast.getWeatherByLatLongOnDate(city.getLatitude(),city.getLongitude(), ArrivalDate);
        
    	return ok(results.render(city, ArrivalDate, weatherData));
    }
}
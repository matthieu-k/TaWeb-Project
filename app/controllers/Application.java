package controllers;

import models.WeatherForecast;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result weather() {
    	WeatherForecast wf = new WeatherForecast();
    	wf.getWeatherByLatLongOnDate(43.61, 3.87, "2013-10-31 12:00:00");
    	return ok(weather.render(wf.wd));
    }
}
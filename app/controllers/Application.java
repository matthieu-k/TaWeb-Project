package controllers;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

import models.Core;
import models.CurrencyService;
import models.WeatherData;
import models.WeatherForecast;
import models.city.City;
import models.city.CityParser;
import models.endpoint.SparqlEndpoint;
import play.Play;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.Json;
import play.libs.XML;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import views.html.results;
import views.html.cityInformationByQuery;
import views.html.sparql;
import views.html.sparqlresults;

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
        String arrivalDateStr = dynamicForm.get("arrivalDate");
        String arrivalTimeStr = dynamicForm.get("arrivalTime");
        Date ArrivalDate;
        if(arrivalTimeStr.trim().isEmpty()) {
        	ArrivalDate = new SimpleDateFormat("dd/MM/yyyy").parse(arrivalDateStr);
        }
        else {
        	ArrivalDate = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(arrivalDateStr + " " + arrivalTimeStr);
        }

        // GET CITY DATA
        City city = CityParser.parse(destination);
        
        // GET WEATHER INFORMATION
        List<WeatherData> weatherData = WeatherForecast.getWeatherByLatLongOnDate(city.getLatitude(),city.getLongitude(), ArrivalDate);
        
        // GET CURRENCY INFORMATION
        String currency = CurrencyService.getCurrency(city.getCurrencyCode());
        
        // GET GOOGLE MAP API KEY
        String GMAPIKEY = Play.application().configuration().getString("GMAPIKEY");
        
    	return ok(results.render(city, ArrivalDate, weatherData, currency, GMAPIKEY));
    }
    
    public static Result sparql() 
    {
    	return ok(sparql.render());
    }
    
    public static Result sparqlresults()
    {
    	DynamicForm dynamicForm = Form.form().bindFromRequest();
    	String query = dynamicForm.get("query");
    	String format = null;
    	format = dynamicForm.get("format");
    	
    	int formatInt = 0;
    	if(format == null) {
    		if(request().accepts("text/html")) {
        		formatInt = 0;
        	}
        	else if(request().accepts("application/json")) {
        		formatInt = 1;
        	}
        	else if (request().accepts("text/xml")) {
    	    	formatInt = 2;
    	    }
        	else if (request().accepts("application/rdf+xml")) {
    	    	formatInt = 3;
    	    }
    	}
    	else {
    		formatInt = Core.parseInt(format);
    	}
    	
    	ResultSet results = SparqlEndpoint.queryData(query);
    	if(results == null) {
    		return ok(sparqlresults.render("Error on SPARQL query"));
    	}

		switch(formatInt) {
			case 0:
				String resultsHtml = SparqlEndpoint.outputHtml(results);
				return ok(sparqlresults.render(resultsHtml));
			case 1:
				ByteArrayOutputStream baosJson = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsJSON(baosJson, results);
				return ok(Json.parse(baosJson.toString()));
			case 2:
				ByteArrayOutputStream baosXml = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsXML(baosXml, results);
				return ok(baosXml.toString());
			case 3:
				ByteArrayOutputStream baosRDF = new ByteArrayOutputStream();
				ResultSetFormatter.outputAsRDF(baosRDF, "RDF/XML-ABBREV", results);
				return ok(baosRDF.toString());
		}
		return null;
    }
}
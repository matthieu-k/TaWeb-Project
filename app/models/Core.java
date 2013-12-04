package models;

import play.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;

import models.city.City;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

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
	
	public static String getCityByQuery(String query)
	{
		String prefixes = "" +
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
				"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +	
				"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
				"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> " +
				"PREFIX dbpprop: <http://dbpedia.org/property/> ";
		
		String service_ep = "http://dbpedia.org/sparql";
		String queryString = 	prefixes +
								"select distinct ?nomCity ?nomCountry ?nomDep where "
								+ "{ "
								+ "{ "
								+ "?r a <http://dbpedia.org/ontology/Settlement> . "
								+ "?r rdfs:label ?nomCity . "
								+ "?r dbpedia-owl:country ?countryResource . "
								+ "?countryResource rdfs:label ?nomCountry . "
								+ "FILTER (lang(?nomCity) = \"en\" && lang(?nomCountry) = \"en\" && regex(str(?nomCity), \""+ query +"\", \"i\")) "
								+ "OPTIONAL { ?r dbpedia-owl:department ?depResource . ?depResource rdfs:label ?nomDep . FILTER (lang(?nomDep) = \"en\") } "
								+ "} "
								+ "UNION "
								+ "{ "
								+ "?r a <http://dbpedia.org/ontology/City> . "
								+ "?r rdfs:label ?nomCity . "
								+ "?r dbpedia-owl:country ?countryResource . "
								+ "?countryResource rdfs:label ?nomCountry . "
								+ "FILTER (lang(?nomCity) = \"en\" && lang(?nomCountry) = \"en\" && regex(str(?nomCity), \""+ query +"\", \"i\")) "
								+ "OPTIONAL { ?r dbpedia-owl:department ?depResource . ?depResource rdfs:label ?nomDep . FILTER (lang(?nomDep) = \"en\") } "
								+ "} "
								+ "} "
								+ "LIMIT 10";
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service_ep, queryString);
		ResultSet results = qexec.execSelect() ;
		
		String resultsStr = "{\"cities\":[";
		String departmentNameStr = "";
		
		for ( ; results.hasNext() ; )
		{
			departmentNameStr = "";
			
			QuerySolution qsolution = results.nextSolution() ;

			Literal resultCityName = qsolution.getLiteral("nomCity");
		    String cityName = resultCityName.getString();

		    String departmentName = "";
		    if(qsolution.contains("nomDep")) {
		    	Literal resultDepartmentName = qsolution.getLiteral("nomDep");
		    	departmentName = resultDepartmentName.getString();
		    }
	    	
		    Literal resultCountryName = qsolution.getLiteral("nomCountry");
		    String countryName = resultCountryName.getString();
		    
		    if(departmentName != "") departmentNameStr = departmentName + ", ";
		    
		    resultsStr += "{\"value\" : \"" + cityName + "\",";
		    resultsStr += "\"name\" : \"" + cityName + ", " + departmentNameStr + countryName + "\"}";
		    if (results.hasNext()) {
		    	resultsStr += ", ";
		    }
		}
		resultsStr += "]}";

		return resultsStr;
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

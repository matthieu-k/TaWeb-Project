package models;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import controllers.Core;

public class AirportService {
	
	private static final String AIRPORT_INFORMATION_SERVICE_URL_CALL =
			"http://www.webservicex.net/airport.asmx/GetAirportInformationByCountry?country=";

	public static String GetAirportInformationByCountry(String country) {
		String rawXML = "";
		String JSONResponse = "";
		
		try {
			// web service call
			rawXML = Core.readUrl(AIRPORT_INFORMATION_SERVICE_URL_CALL + country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Parsing the malformed XML
		rawXML = rawXML.replace("&lt;", "<");
		rawXML = rawXML.replace("&gt;", ">");
		Document doc = Jsoup.parse(rawXML, "", Parser.xmlParser());
		Elements tables = doc.select("Table");
		
		// and making it into a JSON response
		int arrayIndex = 0;
		
		JSONResponse += "[";
		for (Element table : tables) {
			// In the response, every elements are in double
			if (arrayIndex % 2 != 0) {
				++arrayIndex;
				continue;
			}
			
			if (arrayIndex != 0) {
				JSONResponse += ",";
			}
			
			JSONResponse += "{\"iata_code\" : \"" + table.select("AirportCode").text() + "\",";
			JSONResponse += "\"airport_name\" : \"" + table.select("CityOrAirportName").text() + "\"} ";
			
			++arrayIndex;
		}
		JSONResponse += "]";
		
		return JSONResponse;
	}
}

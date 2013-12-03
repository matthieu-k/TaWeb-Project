package models.city;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;

public class CityParser
{
	private static String PREFIX = "" +
			"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
			"PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +	
			"PREFIX foaf: <http://xmlns.com/foaf/0.1/> " +
			"PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> " +
			"PREFIX dbpprop: <http://dbpedia.org/property/> ";
	
	//======================================================================================================================	
	public static City getCityInfoByIataCode(String iataCode)
	{
		City city = new City();
		city.setOverview("Sorry, we have not overview for this city.");
		
		if( doIataCodeChecker(iataCode))
			return getCityByIataCode(iataCode);
		return city;
	}
	
	//======================================================================================================================
	private static boolean doIataCodeChecker(String iataCode)
	{
		String service_ep = "http://dbpedia.org/sparql";
		String queryString= String.format(PREFIX + " ASK { ?r <http://dbpedia.org/ontology/iataLocationIdentifier> \"%s\"@en.}", iataCode);
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service_ep, queryString);
		
		return qexec.execAsk();
	}
	
	//======================================================================================================================	
	private static City getCityByIataCode(String iataCode) 
	{   
		String service_ep = "http://dbpedia.org/sparql";
		String queryString= PREFIX +
				"SELECT DISTINCT ?cityName ?cityAbstract "+
				"WHERE "+
				"{ "+
				"  { "+
				"   ?r <http://dbpedia.org/ontology/iataLocationIdentifier> '"+iataCode+"'@en . "+
				"   ?r dbpedia-owl:city ?cityResource. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:Settlement }. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:City }. "+
				"   ?cityResource rdfs:label ?cityName. "+
				"   ?cityResource dbpedia-owl:abstract ?cityAbstract. "+
				"   FILTER ( lang(?cityName)= 'en' && lang(?cityAbstract)= 'en') "+
				"  } "+
				"  UNION "+
				"  { "+
				"   ?r <http://dbpedia.org/ontology/iataLocationIdentifier> '"+iataCode+"'@en . "+
				"   ?r dbpedia-owl:location ?cityResource. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:Settlement }. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:City }. "+
				"   ?cityResource rdfs:label ?cityName. "+
				"   ?cityResource dbpedia-owl:abstract ?cityAbstract. "+
				"   FILTER ( lang(?cityName)= 'en' && lang(?cityAbstract)= 'en') "+
				"  } "+
				"  UNION "+
				"  { "+
				"   ?r <http://dbpedia.org/ontology/iataLocationIdentifier> '"+iataCode+"'@en . "+
				"   ?r dbpprop:location ?cityResource. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:Settlement }. "+
				"   OPTIONAL { ?cityResource rdf:type dbpedia-owl:City }. "+
				"   ?cityResource rdfs:label ?cityName. "+
				"   ?cityResource dbpedia-owl:abstract ?cityAbstract. "+
				"   FILTER ( lang(?cityName)= 'en' && lang(?cityAbstract)= 'en') "+
				"  }  "+ 
				"} "+
				"LIMIT 1";
		
		QueryExecution qexec = QueryExecutionFactory.sparqlService(service_ep, queryString);
		ResultSet results = qexec.execSelect() ;

		City city = new City();
		
		for ( ; results.hasNext() ; )
		{
			QuerySolution qsolution = results.nextSolution() ;
		    
			Literal result = qsolution.getLiteral("cityName") ;
		    String cityName = result.getString();
		    
		    result = qsolution.getLiteral("cityAbstract") ;
		    String cityAbstract = result.getString();	
		    
		    city.setName(cityName);
		    city.setOverview(cityAbstract);
		}
		
		return  city;
	}			
	//======================================================================================================================	
}
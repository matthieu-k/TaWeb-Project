package models.city;

public class City 
{
	private String country; // dbpedia-owl:country
	private String name;
	private String overview;
	private double latitude;
	private double logitude; //geo:lat
	private int populationTotal; // dbpedia-owl:populationTotal
	//dbpprop:currencyCode
	
	public City() 
	{
		this.country = new String();
		this.name = new String();
		this.overview = new String("We have not information available for the city.");
		this.latitude = 0.0;
		this.logitude = 0.0;
		this.populationTotal = 0;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = Double.parseDouble(latitude);
	}

	public double getLongitude() {
		return logitude;
	}

	public void setLogitude(String logitude) {
		this.logitude = Double.parseDouble(logitude);
	}

	public int getPopulationTotal() {
		return populationTotal;
	}

	public void setPopulationTotal(String populationTotal) {
		this.populationTotal = Integer.parseInt(populationTotal);
	}
}

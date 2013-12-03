package models.city;

public class City 
{
	private String name;
	private String overview;

	public City() 
	{
		this.name = new String();
		this.overview = new String("We have not information available for the city.");
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
}

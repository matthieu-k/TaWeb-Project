$(function() {
	$.getJSON("/assets/json/city_airport_codes.json", function(data) {	
		$.each(data, function(i, value) {
			$("#iataCode").append(
					'<option value="' 
					+ data[i].iata_code 
					+ '">'
					+ data[i].complete_location
					+ '</option>');	
		});
	});
});
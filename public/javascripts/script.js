$(document).ready(function () {
	if ($("#index").length) {
		$('#arrivalDate').datepicker({
			dateFormat: "dd/mm/yy"
		});
		
		$('#arrivalTime').timepicker({
			dateFormat: "hh:mm"
		});
		
		$("#destination" ).autocomplete({
			 source: function( request, response ) {
			 $.ajax({
				 beforeSend: function() {
					 $("#destination" ).after("<span id=\"loader\"></span>");
				 },
				 url: "/cityInformationByQuery/" + request.term,
				 dataType: "json",
				 success: function( data ) {
					 response( $.map( data.cities, function( item ) {
						 return {
							 label: item.name,
							 value: item.name,
							 hiddenValue: item.value
						 }
					 },
					 $("#loader").remove()
					 ));
				 	}
			 	});
			 },
			 minLength: 2,
			 select: function( event, ui ) {
				 $("#destination").val(ui.item.name);
				 $("#destinationValue").val(ui.item.hiddenValue);
			 }
		 });
	}
});
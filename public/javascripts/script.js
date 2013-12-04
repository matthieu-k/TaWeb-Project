$(document).ready(function () {
	if ($("#index").length) {
		$('#arrivalDateTime').datetimepicker({
			dateFormat: "dd/mm/yy"
		});
		
		$("#destination" ).autocomplete({
			 source: function( request, response ) {
			 $.ajax({
				 beforeSend: function() {
					 $("#loader").css('display','inline-block');
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
					 $("#loader").css('display','none')
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
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
					 if (!$("#loader").length) {
						 $("#destination" ).after("<span id=\"loader\"></span>");
					 }
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
		
		$("#destination-date-form").submit(function(event)
		{
			if($("#destination").val() == "" || $("#arrivalDate").val() == "") {
				$("#error-message").remove();
				$("#destination-date-form").append("<div id=\"error-message\">Please fill in required fields</div>");
				return false;
			}
			return true;
			event.preventDefault();
		});
		
//		 $( "#destination" ).autocomplete({
//		 source: function( request, response ) {
//			 $.ajax({
//				 beforeSend: function() {
//					 $("#destination" ).after("<span id=\"loader\"></span>");
//				 },
//				 url: "http://lookup.dbpedia.org/api/search/PrefixSearch",
//				 dataType: "xml",
//				 data: {
//					 QueryClass: "Settlement",
//					 MaxHits: 12,
//					 QueryString: request.term
//				 },
//				 success: function( data ) {
//					 var xml = $.parseXML(data),
//					 $xml = $( xml ),
//					 $result = $xml.find('Result');
//					 console.log(xml);
//					 console.log($result);
//					 response( $.map( data.ArrayOfResult, function( item ) {
//						 return {
//							 label: item.name + (item.adminName1 ? ", " + item.adminName1 : "") + ", " + item.countryName,
//							 value: item.name,
//							 hiddenValue: item.name
//						 }
//					 },
//					 $("#loader").remove()
//					 ));
//				 },
//			 });
//		 },
//		 minLength: 2,
//		 select: function( event, ui ) {
//			 console.log(ui.item.label);
//			 console.log(ui.item.value);
//			 console.log(ui.item.hiddenValue);
//			 $("#destination").val(ui.item.label);
//			 $("#destinationValue").val(ui.item.hiddenValue);
//		 }
//	 });
	}
});
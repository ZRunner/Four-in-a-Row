function logoff(){
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: '/api/logout',
		dataType:"json",
		success: function(data){
			window.location.replace("/signin");
		},
		error: function(error){
			console.log("An error occurred during logging out");
		}
	});
}
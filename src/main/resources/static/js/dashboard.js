function setInfos(){
	var username = document.getElementById("username_info");
	var date_creation = document.getElementById("date_info");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/me/profile',
        success: function(data){
        	let creationDate = new Date(data.createdAt);
        	let dd = creationDate.getDate();
            let mm = creationDate.getMonth() + 1;
            let yyyy = creationDate.getFullYear();
            if (dd < 10) {
                dd = '0' + dd;
            }
            if (mm < 10) {
                mm = '0' + mm;
            }
        	username.innerHTML = data.username;
        	date_creation.innerHTML = dd + '/' + mm + '/' + yyyy;
        	console.log(data);
        },
        error: function(error){
        	$(".info-message").text(error.responseText);
        }
     });
}

setInfos();
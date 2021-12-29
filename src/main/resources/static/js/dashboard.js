function setInfos(){
	var username = document.getElementById("username_info");
	var date_creation = document.getElementById("date_info");
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: '/api/me/profile',
        dataType:"json",
        success: function(data){
        	let creationDate = new Date(data.createdAt);
        	username.innerHTML = data.username;
        	date_creation.innerHTML = creationDate.toLocaleDateString();
        },
        error: function(error){
        	$(".info-message").text(JSON.parse(error.responseText).error);
        }
     });
}

function changeUsername(){
	let msg_element = document.getElementsByClassName("info-message")[0];
	let username = document.forms["username_form"].elements["username"].value.trim();
	let username_verif = document.forms["username_form"].elements["username_verif"].value.trim();
	if (username != username_verif){
		msg_element.innerHTML = "Usernames are not the same";
		msg_element.style.color = "red";
    	msg_element.style.display = "block";
		return;
	}else if(username == ""){
		msg_element.innerHTML = "Usernames are empty";
		msg_element.style.color = "red";
    	msg_element.style.display = "block";
		return;
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType:"json",
        url: '/api/me/username',
        data: JSON.stringify({username:username}),
        success: function(data){
    		msg_element.style.color = "lime";
        	msg_element.innerHTML = "Username changed";
        	setInfos();
        },
        error: function(error){
        	msg_element.innerHTML = JSON.parse(error.responseText).error;
        	msg_element.style.color = "red";
        },
        complete: function(){
        	msg_element.style.display = "block";
        }
     });
}

function changePassword(){
	let msg_element = document.getElementsByClassName("info-message")[1];
	let password = document.forms["password_form"].elements["password"].value.trim();
	let password_verif = document.forms["password_form"].elements["password_verif"].value.trim();
	if (password != password_verif){
		msg_element.innerHTML = "Passwords are not the same";
		msg_element.style.color = "red";
    	msg_element.style.display = "block";
		return;
	}else if(password == ""){
		msg_element.innerHTML = "Passwords are empty";
		msg_element.style.color = "red";
    	msg_element.style.display = "block";
		return;
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        dataType:"json",
        url: '/api/me/password',
        data: JSON.stringify({password:password}),
        success: function(data){
    		msg_element.style.color = "lime";
        	msg_element.innerHTML = "Password changed";
        	setInfos();
        },
        error: function(error){
        	msg_element.innerHTML = JSON.parse(error.responseText).error;
        	msg_element.style.color = "red";
        },
        complete: function(){
        	msg_element.style.display = "block";
        }
     });
}

function disconnect(){
	$.ajax({
        type: "POST",
        contentType: "application/json",
        dataType:"text",
        url: '/api/logout',
        success: function(data){
        	window.location.replace("/signin");
        },
        error: function(error){
        	console.log(error);
        }
     });
}

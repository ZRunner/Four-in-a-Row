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
        },
        error: function(error){
        	$(".info-message").text(error.responseText);
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
        dataType:"text",
        url: '/api/me/username',
        data: JSON.stringify({username:username}),
        success: function(data){
    		msg_element.style.color = "lime";
        	msg_element.innerHTML = "Username changed";
        	setInfos();
        },
        error: function(error){
        	msg_element.innerHTML = error.responseText;
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
        dataType:"text",
        url: '/api/me/password',
        data: JSON.stringify({password:password}),
        success: function(data){
    		msg_element.style.color = "lime";
        	msg_element.innerHTML = "Password changed";
        	setInfos();
        },
        error: function(error){
        	msg_element.innerHTML = error.responseText;
        	msg_element.style.color = "red";
        },
        complete: function(){
        	msg_element.style.display = "block";
        }
     });
}

setInfos();





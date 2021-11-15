function signin(){
	var form = document.forms["login_form"];
	let username = form.elements["username"].value.trim();
	let password = form.elements["password"].value.trim();
	if(password =="" || username==""){
    	$("#errorMsg").text("Username or password is empty");  
		return;
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/api/login',
        data: JSON.stringify({username:username,password:password}),
        success: function(data){
        	window.location.href = "/home";
        },
        error: function(error){
        	$("#errorMsg").text(error.responseText);
        }
     });
}

function signup(){
	var form = document.forms["login_form"];
	let username = form.elements["username"].value.trim();
	let password = form.elements["password"].value.trim();
	let password_verif = form.elements["password_verif"].value.trim();
	if(password =="" || username==""){
    	$("#errorMsg").text("Username or password is empty");  
		return;
	}else if(password != password_verif){
		$("#errorMsg").text("Passwords are not the same");
		return;
	}
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: '/api/signup',
        data: JSON.stringify({username:username,password:password}),
        success: function(data){
        	window.location.href = "/home";  	
        },
        error: function(error){
        	$("#errorMsg").text(error.responseText);
        }        	
     });
}
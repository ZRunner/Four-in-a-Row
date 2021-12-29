function deleteUser(id){
	console.log("User "+id+" deleted.");
	loadUsers();
}

function resetUser(id){
	console.log("User "+id+" reinitialized.");
}

function alertCreator(type, title, message){
	var alert = document.createElement("div");
	alert.setAttribute("role","alert");
	alert.classList.add("alert", "alert-dismissible", "fade", "show");
	alert.classList.add("alert-"+type);

	let titleElement = document.createElement("strong");
	titleElement.innerText = title;
	alert.appendChild(titleElement);

	let messageElement = document.createElement("div");
	messageElement.innerText = message;
	alert.appendChild(messageElement);

	let button = document.createElement("button");
	button.classList.add("btn-close");
	button.setAttribute("type","button");
	button.setAttribute("data-bs-dismiss","alert");
	button.setAttribute("aria-label","Close");
	alert.appendChild(button);

	document.getElementsByClassName("content")[0].prepend(alert);
}

function actionsButtons(id){
	return ('<button type="button" class="btn btn-warning" onclick="resetUser('+id+')">Reset</button> '
	+'<button type="button" class="btn btn-danger" onclick="deleteUser('+id+')">Delete</button>');
}

function loadUsers(){
	var username = document.getElementById("username_info");
	var date_creation = document.getElementById("date_info");
	$.ajax({
		type: "GET",
		contentType: "application/json",
		url: '/api/admin/users?password=123abc',
		dataType:"json",
		success: function(data){
			var usersTable = document.getElementById("users");
			usersTable.innerHTML = "<tr><th>#</th><th>Username</th><th>Action</th></tr>";
			users = data;
			users.forEach(user => {
				let tdId = document.createElement("td");
				let tdUsername = document.createElement("td");
				let tdActions = document.createElement("td");

				tdActions.innerHTML = actionsButtons(user.id);
				tdId.innerText = user.id;
				tdUsername.innerText = user.username;

				let tr = document.createElement("tr");
				tr.appendChild(tdId);
				tr.appendChild(tdUsername);
				tr.appendChild(tdActions);
				usersTable.appendChild(tr);
			});
		},
		error: function(error){
			alertCreator("danger","An error occurred",JSON.parse(error.responseText).error);
		}
	});
}

loadUsers();
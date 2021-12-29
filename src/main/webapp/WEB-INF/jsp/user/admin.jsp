<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="../include/header.jsp" %>
<body>
	<%@include file="../include/navbar.jsp" %>
	<div class="content">
		<h1>Account management</h1>
		<table id="users" class="table table-hover">
			<tr>
				<th>#</th>
				<th>Username</th>
				<th>Action</th>
			</tr>
			<tr>
				<td>1</td>
				<td>Theo</td>
				<td>
					<button type="button" class="btn btn-danger" onclick="deleteUser(1)">Delete</button>
					<button type="button" class="btn btn-warning" onclick="resetUser(1)">Reset</button>
				</td>
			</tr>
		</table>
		
	</div>
<%@include file="../include/footer.jsp" %>
</body>
</html>
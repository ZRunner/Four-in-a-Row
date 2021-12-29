<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="../include/header.jsp" %>
<body>
	<input type="checkbox" id="check" class="hide">
	<div class="container">
		<div class="sidebar">
			<label for="check">
				<i class="bi bi-caret-left" id="arrow"></i>
			</label>
			<div class="imgBox">
				<img src="/img/statistics.jpg" alt="statistics">
			</div>
			<ol class="navbar">
				<li class="searchbox"></li>
				<li><a href="/settings/profile"><i class="bi bi-person-circle"></i><p>Profile</p></a></li>
				<li><a href="/settings/statistics"><i class="bi bi-activity"></i><p>Statistics</p></a></li>
				<li><a href="/settings/history"><i class="bi bi-clock-history"></i><p>History</p></a></li>
				<li><a onclick="disconnect()"><i class="bi bi-box-arrow-in-left"></i><p>Logout</p></a></li>
			</ol>
		</div>
		<div class="content">
			<h1><c:out value = "${title}"/></h1>
			<c:choose>
		        <c:when test="${title == 'Statistics'}">
					<h1>This page is under building, it coming soon !</h1>
				</c:when>
		        <c:when test="${title == 'Game history'}">
					<h1>This page is under building, it coming soon !</h1>
		        </c:when>
		        <c:otherwise>
					<h3>Current account informations</h3>
					<div class="account">
						<div><i class="bi bi-person"></i><p id="username_info">...</p></div>
						<div><i class="bi bi-calendar"></i><p id="date_info">...</p></div>
					</div>
					<h3>Change username</h3>
					<div class="info-message">Error message</div>
					<div class="account">
						<form id="username_form">
							<div class="labelBox">
								<label for="username">Enter the new username</label>
								<div class="inputBox">
									<i class="bi bi-people"></i>
									<input type="text" id="username" name="username">
								</div>
							</div>
							<div class="labelBox">
								<label for="username_verif">Confirm the new username</label>
								<div class="inputBox">
									<i class="bi bi-people"></i>
									<input type="text" id="username_verif" name="username_verif">
								</div>
							</div>
							<button type="button" onclick="changeUsername()">Change</button>
						</form>
					</div>
					<h3>Change password</h3>
					<div class="info-message">Error message</div>
					<div class="account">
						<form id="password_form">
							<div class="labelBox">
								<label for="password">Enter the new password</label>
								<div class="inputBox">
									<i class="bi bi-key-fill"></i>
									<input type="password" id="password" name="password">
								</div>
							</div>
							<div class="labelBox">
								<label for="password_verif">Confirm the new password</label>
								<div class="inputBox">
									<i class="bi bi-key-fill"></i>
									<input type="password" id="password_verif" name="password_verif">
								</div>
							</div>
							<button type="button" onclick="changePassword()">Change</button>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
<%@include file="../include/footer.jsp" %>
<c:if test = "${title != 'Game history' && title != 'Statistics'}"><script>setInfos();</script></c:if>
</body>
</html>
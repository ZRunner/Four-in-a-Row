<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>
		<c:if test = "${title != null}"><c:out value = "${title}"/></c:if>
		<c:if test = "${title == null}"><c:out value = "Projet JEE"/></c:if>
	</title>
	<c:forEach items="${css}" var ="css_style">
		<link rel="stylesheet" href="${css_style}">
	</c:forEach>
	<!-- Check token cookie -->
</head>
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
						<div><i class="bi bi-person"></i><p>Théo</p></div>
						<div><i class="bi bi-calendar"></i><p>10 mars tkt</p></div>
					</div>
					<h3>Change username</h3>
					<div class="info-message">Error message</div>
					<div class="account">
						<form id="username_form">
							<div class="labelBox">
								<label for="username">Enter the new username</label>
								<div class="inputBox">
									<i class="bi bi-people"></i>
									<input type="text" id="username">
								</div>
							</div>
							<div class="labelBox">
								<label for="username_verif">Confirm the new username</label>
								<div class="inputBox">
									<i class="bi bi-people"></i>
									<input type="text" id="username_verif">
								</div>
							</div>
							<button onclick="changeUsername()">Change</button>
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
									<input type="password" id="password">
								</div>
							</div>
							<div class="labelBox">
								<label for="password_verif">Confirm the new password</label>
								<div class="inputBox">
									<i class="bi bi-key-fill"></i>
									<input type="password" id="password_verif">
								</div>
							</div>
							<button onclick="changePassword()">Change</button>
						</form>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
<footer>
	<c:forEach items="${js}" var ="javascript">
		 <script src="${javascript}"></script>
	</c:forEach>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
</footer>
</body>
</html>
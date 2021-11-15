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
				<img src="/img/statistics.jpg">
			</div>
			<ol class="navbar">
				<li class="searchbox"></li>
				<li><a href="/settings/profile"><i class="bi bi-person-circle"></i><p>Profile</p></a></li>
				<li><a href="/settings/statistics"><i class="bi bi-activity"></i><p>Statistics</p></a></li>
				<li><a href="/settings/history"><i class="bi bi-clock-history"></i><p>History</p></a></li>
				<!-- Maybe add home page and logout -->
			</ol>
		</div>
		<div class="content">
			<h1><c:out value = "${title}"/></h1>
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
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
	<c:forEach  items="${css}" var ="css">
		<link rel="stylesheet" href="${css}">
	</c:forEach>
</head>
<body>
	<section>
		<div class="imgBx">
			<img src="img/login_image.jpg" alt="tablet">
		</div>
		<div class="contentBx">
			<div class="formBx">
				<h2>${title}</h2>
				<form id="login_form">
					<p class="errorMsg"></p>
					<div class="inputBx">
						<span>Username</span>
						<input type="text" name="username">
					</div>
					<div class="inputBx">
						<span>Password</span>
						<input type="password" name="password">
					</div>
					<c:if test = "${title == 'Sign up'}">
						<div class="inputBx">
							<span>Confirm password</span>
							<input type="password" name="password_verif">
						</div>
					</c:if>
					<div class="inputBx">
						<button class="input_submit" type="button">${title}</button>
					</div>
					<div class="inputBx">
					<c:if test = "${title == 'Sign in'}">
						<p>Don't have an account ? <a href="/signup">Sign up</a></p>
					</c:if>
					<c:if test = "${title == 'Sign up'}">
						<p>Already have an account ? <a href="/signin">Sign in</a></p>
					</c:if>
					</div>
				</form>
			</div>
		</div>
	</section>
</body>
</html>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=ISO-8859-1">
		<meta name="Content-Language" content="fr" />
		<meta name="Description" content="" />
		<meta name="Keywords" content="Jeu de morpion" />
		<meta name="Subject" content="" />
		<meta name="Content-Type" content="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Projet JEE</title>
	
	    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
		
		<spring:url value="/css/home.css" var="homeCss" />
		<link href="${homeCss}" rel="stylesheet" />
		
		<spring:url value="/css/footer.css" var="footerCss" />
		<link href="${footerCss}" rel="stylesheet" />
		
		<spring:url value="/css/tictactoe.css" var="tictactoeCss" />
		<link href="${tictactoeCss}" rel="stylesheet" />
		
		<spring:url value="/js/tictactoe.js" var="tictactoeJs" />
		<script src="${tictactoeJs}"></script>
		
	</head>

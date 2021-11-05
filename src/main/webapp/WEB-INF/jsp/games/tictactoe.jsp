<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



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
	
	<spring:url value="/css/tictactoe.css" var="tictactoeCss" />
	
	<link href="${tictactoeCss}" rel="stylesheet" />
</head>

	<%@include file="../include/header.jsp" %>
<body>

<h1>Page de tictactoe</h1>

<table>
	<tbody>
		<tr class="plateau">
			<td><button>Case1</button></td>
			<td><button>Case2</button></td>
			<td><button>Case3</button></td>
		</tr>
		<tr class="plateau">
			<td><button>Case4</button></td>
			<td><button>Case5</button></td>
			<td><button>Case6</button></td>
		</tr>
		<tr class="plateau">
			<td><button>Case7</button></td>
			<td><button>Case8</button></td>
			<td><button>Case1</button></td>
		</tr>
	</tbody>
</table>


	<%@include file="../include/footer.jsp" %>
</body>
</html>
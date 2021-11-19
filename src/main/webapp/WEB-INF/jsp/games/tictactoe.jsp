<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
	<%@include file="../include/header.jsp" %>
	<body class="bg-info text-dark bg-opacity-10">
		<%@include file="../include/navbar.jsp" %>
		<h1 class="title-page">Tic-Tac-Toe</h1>
		<div id="game">
			<table>
				<tbody>
					<tr class="plateau plateau1">
						<td><button onclick="onclick1(0)" id="0"></button></td>
						<td><button onclick="onclick1(1)" id="1"></button></td>
						<td><button onclick="onclick1(2)" id="2"></button></td>
					</tr>
					<tr class="plateau">
						<td><button onclick="onclick1(3)" id="3"></button></td>
						<td><button onclick="onclick1(4)" id="4"></button></td>
						<td><button onclick="onclick1(5)" id="5"></button></td>
					</tr>
					<tr class="plateau">
						<td><button onclick="onclick1(6)" id="6"></button></td>
						<td><button onclick="onclick1(7)" id="7"></button></td>
						<td><button onclick="onclick1(8)" id="8"></button></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="alert alert-warning d-flex align-items-center" role="alert">
			<svg class="bi flex-shrink-0 me-2" width="24" height="24" role="img" aria-label="Warning:"><use xlink:href="#exclamation-triangle-fill"/></svg>
			<div>
		    	An example warning alert with an icon
		    </div>
		</div>
		<div id="result">
			<h1></h1>
		</div>
		<footer>	
			<spring:url value="/js/tictactoe.js" var="tictactoeJs" />
			<script src="${tictactoeJs}"></script>
		</footer>
	</body>
</html>
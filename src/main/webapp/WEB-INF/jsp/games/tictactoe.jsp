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
						<td><button class="game" onclick="onclick1(0)"></button></td>
						<td><button class="game" onclick="onclick1(1)"></button></td>
						<td><button class="game" onclick="onclick1(2)"></button></td>
					</tr>
					<tr class="plateau">
						<td><button class="game" onclick="onclick1(3)"></button></td>
						<td><button class="game" onclick="onclick1(4)"></button></td>
						<td><button class="game" onclick="onclick1(5)"></button></td>
					</tr>
					<tr class="plateau">
						<td><button class="game" onclick="onclick1(6)"></button></td>
						<td><button class="game" onclick="onclick1(7)"></button></td>
						<td><button class="game" onclick="onclick1(8)"></button></td>
					</tr>
				</tbody>
			</table>
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
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
						<td class="simple"><button class="game" onclick="onclick1(0)" id="0"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(1)" id="1"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(2)" id="2"></button></td>
					</tr>
					<tr class="plateau">
						<td class="simple"><button class="game" onclick="onclick1(3)" id="3"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(4)" id="4"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(5)" id="5"></button></td>
					</tr>
					<tr class="plateau">
						<td class="simple"><button class="game" onclick="onclick1(6)" id="6"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(7)" id="7"></button></td>
						<td class="simple"><button class="game" onclick="onclick1(8)" id="8"></button></td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="modal fade" id="modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><h5 class="modal-title" id="resultat">R&#233;sultat</h5></div>
					<div class="modal-body" id="result"></div>
					<div class="modal-footer"><button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="reinit()">Close</button></div>
				</div>
	   		</div>
		</div>

		<%@include file="../include/footer.jsp" %>
	</body>
</html>
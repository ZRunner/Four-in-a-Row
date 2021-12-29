<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
	<%@include file="../include/header.jsp" %>
	<body class="bg-info text-dark bg-opacity-10">
		<%@include file="../include/navbar.jsp" %>
		<h1 class="title-page">N in a row</h1>
		<div id="buttonSelection">
			<button type="button" class="btn btn-primary btnSelection position-relative top-50 start-50 translate-middle" data-bs-toggle="modal" data-bs-target="#exampleModalCenter">Select your game board's size</button>
		</div>
		<div id="game">
		</div>
		<div id="result">
			<h1></h1>
		</div>
		<div class="modal fade" id="exampleModalCenter" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="staticBackdropLabel">Select N</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<input type="number" id="select" min="3" max="7" length="10" value="3">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="save()" >Save changes</button>
				</div>
				</div>
			</div>
		</div>


		<div class="modal fade" id="testModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header"><h5 class="modal-title" id="exampleModalLabel">R&#233;sultat</h5></div>
					<div class="modal-body" id="test"></div>
					<div class="modal-footer"><button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="reinit()">Close</button></div>
				</div>
	   		</div>
		</div>
	<%@include file="../include/footer.jsp" %>
</body>
</html>
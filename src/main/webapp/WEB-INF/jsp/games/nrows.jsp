<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="../include/header.jsp" %>
<body class="bg-info text-dark bg-opacity-10">
	<%@include file="../include/navbar.jsp" %>
	<h1 class="title-page">N in a rows</h1>
	<div id="buttonSelection">
		<button type="button" class="btn btn-primary btnSelection" data-toggle="modal" data-target="#exampleModalCenter">Select your game board's size</button>
	</div>
	<div id="game">
	</div>
	<div id="result">
		<h1></h1>
	</div>
<!-- Modal -->
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLongTitle">Select N :</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<input type="number" id="select" min="3" max="7" length="10" value="3">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" onclick=save() data-dismiss="modal">Save changes</button>
				</div>
	   		</div>
		</div>
	</div>	
	<%@include file="../include/footer.jsp" %>
</body>
</html>
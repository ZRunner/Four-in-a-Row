<!DOCTYPE html>
<html>
	<%@include file="./include/header.jsp" %>
	<body class="bg-info text-dark bg-opacity-10">
		<%@include file="./include/navbar.jsp" %>
		<h1>Our JEE project</h1>
		<section>
			<div class="imgBox">
				<img src="img/jee.png" alt="JEE">
			</div>
			<div class="description">
				<p>This is the project realized by our group composed of Arthur Blaise, Théo Julien & Aurélien Guimont. We wanted to combine several school subject in order to make a project as complete as possible. We decided to create different kind of games (like a TicTacToe's game or a Four in a row's game) with a implemented AI.</p>
			</div>
		</section>		
		<h1>Our games</h1>
		<section>
			<div class="imgBox">
				<img src="img/tictactoe.png" class="img-fluid rounded-start" alt="TicTacToeImg"  style="max-width: 200px;">
			</div>
			<div class="description">
				<h3>Tic-Tac-Toe</h3><br>
					<p >This is the Tic-Tac-Toe's game developed by ourselves with its own AI implemented.</p>
					<a href="tictactoe"><div class="button">Play</div></a>
			</div>
		</section>
		<section>
			<div class="imgBox">
				<img src="img/tictactoe.png" class="img-fluid rounded-start" alt="TicTacToeImg"  style="max-width: 200px;">
			</div>
			<div class="description">
				<h3>N in a row</h3><br>
					<p >This a game of "N in a row", developed by ourselves with an other implemented AI.</p>
					<a href="nrows"><div class="button">Play</div></a>
			</div>
		</section>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

	</body>
</html>
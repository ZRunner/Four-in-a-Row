<nav class="navbar navbar-expand-sm navbar-dark">
	<div class="container-fluid">
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarText">
			<ul class="navbar-nav me-auto mb-2 mb-md-0">
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="/">Home</a>
				</li>
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="tictactoe">Tic-Tac-Toe</a>
				</li>
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="nrows">N in a row</a>
				</li>
			</ul>
			<ul class="navbar-nav mb-2 mb-lg-0">
				<li class="nav-item">
					<c:if test = "${logged == false}">
						<a class="nav-link active" href="signin">Sign in</a>
					</c:if>
					<c:if test = "${logged == true}">
							<a class="nav-link active" onclick="logoff()" style="cursor:pointer">Log out</a>
					</c:if>
				</li>
			</ul>
		</div>
	</div>
</nav>
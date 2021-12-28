<%@include file="../include/header_tag.jsp" %>
<!DOCTYPE html>
<html>
<%@include file="../include/header.jsp" %>
<body>
	<section>
		<div class="imgBx">
			<img src="img/login_image.jpg" alt="tablet">
		</div>
		<div class="contentBx">
			<div class="formBx">
				<h2>${title}</h2>
				<form id="login_form" >
					<p id="errorMsg"></p>
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
						<div class="inputBx">
							<button class="input_submit" type="button" onclick="signup()">${title}</button>
						</div>
						<div class="inputBx">
							<p>Already have an account ? <a href="/signin">Sign in</a></p>
						</div>
					</c:if>
					<c:if test = "${title == 'Sign in'}">
						<div class="inputBx">
							<button class="input_submit" type="button" onclick="signin()">${title}</button>
						</div>
						<div class="inputBx">
							<p>Don't have an account ? <a href="/signup">Sign up</a></p>
						</div>
					</c:if>
				</form>
			</div>
		</div>
	</section>
<%@include file="../include/footer.jsp" %>
</body>
</html>
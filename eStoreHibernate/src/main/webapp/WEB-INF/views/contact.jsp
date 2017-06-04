<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div class="container-wrapper">
	<div class="container">
		<h3>Contact Us</h3>

		<form action="<c:url value="/contact"/>" method="get">
			<div class="form-group">
				<input type="text" class="form-control" name="username"
					style="width: 50%" placeholder="Username">
			</div>
			<div class="form-group">
				<input type="email" class="form-control" name="email"
					style="width: 50%" placeholder="E-mail">
			</div>
			<div class="form-group">
				<input type="text" class="form-control" name="message"
					style="width: 50%" placeholder="Message">
			</div>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" /> <input type="submit" value="Contact"
				class="btn btn-default">
		</form>
	</div>
</div>
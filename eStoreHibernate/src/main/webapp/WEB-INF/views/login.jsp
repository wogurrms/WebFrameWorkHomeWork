<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<div class="container">
	<h4>Login with Username and Password</h4>
	
	<c:if test="${not empty logout}">
	<div style="color:#0000ff"> <h4> ${logout}</h4> </div>
	</c:if>
	
	<c:if test="${not empty error}">
	<div style="color:#ff0000"> <h4> ${error}</h4> </div>
	</c:if>
	
		<form action="<c:url value="/login"/>" method="post">
			<div class="form-group">
				<input type="text"	class="form-control" name="username" style="width:50%" placeholder="Username">
			</div>
			<div class="form-group">
				<input type="password" class="form-control" name="password" style="width:50%" placeholder="Password">
			</div>
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="submit" value="Let me in" class="btn btn-default">
		</form>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<div class="container">
	
		<h1>Register User</h1>
		<p class="lead">회원 가입을 위한 정보를 입력해 주세요:</p>
		
		<sf:form action="${pageContext.request.contextPath }/register"
			method="post" modelAttribute="user"> <!-- modelAttribute값과 controller의 addAttribute값과 같아야함 -->
			
			<h3>기본 정보</h3>
			<div class="form-group">
				<label for="username">ID:</label>
				<span style="color:#ff0000"> ${usernameMsg}</span>
				<sf:input path="username" id="username" class="form-control"/> <!-- user 객체가 바인딩 -->
				<sf:errors path="username" cssStyle="color:#ff0000"/><!-- error 객체가 바인딩 -->
			</div>
			
			<div class="form-group">
				<label for="password">Password:</label>
				<sf:password path="password" id="password" class="form-control"/>
				<sf:errors path="password" cssStyle="color:#ff0000"/>
			</div>
			
			<div class="form-group">
				<label for="email">E-mail:</label>
				<sf:input path="email" id="email" class="form-control"/>
				<sf:errors path="email" cssStyle="color:#ff0000"/>
			</div>
			
			<br>
			<h3>배송 주소 정보</h3>
			<div class="form-group">
				<label for="shippingStreet">Address:</label>
				<sf:input path="shippingAddress.address" id="shippingStreet" class="form-control"/>
			</div>
			
			<div class="form-group">
				<label for="shippingCountry">Country:</label>
				<sf:input path="shippingAddress.country" id="shippingCountry" class="form-control"/>
			</div>
			
			<div class="form-group">
				<label for="shippingZip">Zip-Code:</label>
				<sf:input path="shippingAddress.zipCode" id="shippingZip" class="form-control"/>
			</div>
			
			<!-- Post 방식의 addProduct Method 이용 -->
			<input type="submit" value="submit" class="btn btn-default">
			<!-- cancel시 홈페이지로 이동 -->
			<a href="<c:url value="/" />" class="btn btn-default">Cancel</a>
			
		</sf:form>
		<br>
	</div>
</div>

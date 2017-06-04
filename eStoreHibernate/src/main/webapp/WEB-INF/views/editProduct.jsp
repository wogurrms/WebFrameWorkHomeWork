<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-wrapper">
	<div class="container">
		<h1>Edit Product</h1>
		<p class="lead">Edit the below information to edit a product:</p>
		
		<sf:form action="${pageContext.request.contextPath }/admin/productInventory/editProduct?${_csrf.parameterName}=${_csrf.token}"
			method="post" modelAttribute="product" enctype="multipart/form-data">
			
			<!-- hidden 으로 Product 모델을 받을때 id값을 받지만 화면에 보이지는 않음 -->
			<sf:hidden path="id"/>
			
			<div class="form-group">
				<label for="name">Name:</label>
				<sf:input path="name" id="name" class="form-control"/>
				<sf:errors path="name" cssStyle="color:#ff0000"/>
			</div>
			<div class="form-group">
				<label for="category">Category:</label>
				<sf:radiobutton path="category" id="category" value="컴퓨터"/>컴퓨터
				<sf:radiobutton path="category" id="category" value="가전"/>가전
				<sf:radiobutton path="category" id="category" value="신발"/>신발
			</div>
			<div class="form-group">
				<label for="description">Description:</label>
				<sf:input path="description" id="description" class="form-control"/>
			</div>
			<div class="form-group">
				<label for="price">Price:</label>
				<sf:input path="price" id="price" class="form-control"/>
				<sf:errors path="price" cssStyle="color:#ff0000"/>
			</div>
			<div class="form-group">
				<label for="unitInStock">Unit In Stock:</label>
				<sf:input path="unitInStock" id="unitInStock" class="form-control"/>
				<sf:errors path="unitInStock" cssStyle="color:#ff0000"/>
			</div>
			<div class="form-group">
				<label for="manufacturer">Manufacturer:</label>
				<sf:input path="manufacturer" id="manufacturer" class="form-control"/>
				<sf:errors path="manufacturer" cssStyle="color:#ff0000"/>
			</div>
			<div class="form-group">
				<label for="productImage">Upload Picture:</label>
				<sf:input path="productImage" id="productImage" type="file" class="form-control"/>
			</div>
			<br>
			
			<!-- Post 방식의 addProduct Method 이용 -->
			<input type="submit" value="submit" class="btn btn-default">
			<a href="<c:url value="/admin/productInventory" />" class="btn btn-default">Cancel</a>
			
		</sf:form>
		<br>
	</div>
</div>
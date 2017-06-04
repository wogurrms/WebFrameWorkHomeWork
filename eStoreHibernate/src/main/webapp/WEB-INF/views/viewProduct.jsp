<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="<c:url value="/resources/js/controller.js"/>"></script>

<div class="container-wrapper">
	<div class="container" ng-app="cartApp">
		<div class="row" ng-controller="cartCtrl">

			<div class="col-md-6">

				<p>
					<strong>Category</strong> : Home > ${product.category }
				</p>

				<h3>Product Details</h3>
				<p>Here is the detail information for product</p>

				<img
					src="<c:url value="/resources/images/${product.imageFileName }"/>"
					alt="image" style="width: 80%" />
			</div>

			<div class="col-md-6">

				<h3>${product.name }</h3>
				<p>${product.description }</p>
				<p>
					<strong>Price</strong> : ${product.price } 원
				</p>
				<p>
					<strong>Manufacturer</strong> : ${product.manufacturer }
				</p>
				<p>
					<strong>Unit In Stock</strong> : ${product.unitInStock }
				</p>
				<br>
				<c:if test="${pageContext.request.userPrincipal.name != null }">
					<c:set var="role"  value="${param.role }" />
					<c:set var="url"  value="/products" />
					<c:if test="${role='admin'}">
						<c:set var="url"  value="/admin/productInventory" />
					</c:if>

					<p>
						<a href="<c:url value="${url}"/>" class="btn btn-default">back</a>
						<button class="btn btn-warning btn-large"
							ng-click="addToCart('${product.id}')">
							<span class="glyphicon glyphicons-shopping-cart"></span>Order Now
						</button>
						<a href="<c:url value="/cart"/>" class="btn btn-default"> <span
							class="glyphicon glyphicons-hand-right"></span>View Cart
						</a>
					</p>
				</c:if>

			</div>

		</div>
	</div>
</div>

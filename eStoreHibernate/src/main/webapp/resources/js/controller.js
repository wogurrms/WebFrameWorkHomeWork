var cartApp = angular.module('cartApp',[]);

cartApp.controller("cartCtrl", function($scope, $http){
	
	$scope.initCartId = function(cartId){
		$scope.cartId = cartId;
		$scope.refreshCart();
	};
	
	$scope.refreshCart = function(){
		$http.get('/eStoreHibernate/rest/cart/'+$scope.cartId).then(
				function successCallback(response){
					$scope.cart = response.data;
				});
	};
	
	$scope.addToCart = function(productId){
		$scope.setCsrfToken();
		
		$http.put('/eStoreHibernate/rest/cart/add/'+productId).then(
				function sucessCallback(response){
					alert("Product successfully added to cart!");
				},function errorCallback(){
					alert("Adding to the cart failed!");
				});
	};
	
	$scope.removeFromCart = function(productId){
		
		$scope.setCsrfToken();
		
		$http({
			method: 'DELETE',
			url: '/eStoreHibernate/rest/cart/cartitem/'+productId
		}).then(function successCallback(){
			$scope.refreshCart();
		}, function errorCallback(response){
			console.log(response.data);			
		});
	};
	
	$scope.clearCart = function(){
		$scope.setCsrfToken();
		$http({
			method: 'DELETE',
			url: '/eStoreHibernate/rest/cart/'+$scope.cartId
		}).then(function successCallback(){
			$scope.refreshCart();
		}, function errorCallback(response){
			console.log(response.data);			
		});
	};
	
	$scope.calGrandTotal = function(){
		var grandTotal = 0;
		for(var i = 0; i < $scope.cart.cartItems.length; i++){
			grandTotal += $scope.cart.cartItems[i].totalPrice;
		}
		
		return grandTotal;
	};
	
	$scope.setCsrfToken = function(){
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		
		$http.defaults.headers.common[csrfHeader] = csrfToken;
	};
	
	
	$scope.addQuantityCartItem = function(productId) {

	   $scope.setCsrfToken();
	   $http.post('/eStoreHibernate/rest/cart/cartitem/' + productId + "/addQuantity").then(
	      function successCallback(response) {
	         $scope.refreshCart();
	         console.log(response);
	      },
	      function errorCallback() {
	         alert("stocks are sold out!");
	      });
	   
	};

	$scope.subQuantityCartItem = function(productId) {

	   $scope.setCsrfToken();
	   $http.post('/eStoreHibernate/rest/cart/cartitem/' + productId + "/subQuantity").then(
	      function successCallback(response) {
	         $scope.refreshCart();
	         console.log(response);
	      },
	      function errorCallback() {
	         alert("you can't sub under 0");
	      });
	      
	};
});
function displayFilteredProducts(products) {
	var productsLength = products.length;
	var totalProductsLength = products.length;

	var productCountElement = document.getElementById('productCount');
	if (productCountElement) {
		productCountElement.textContent = 'Showing ' + productsLength + ' of ' + totalProductsLength + ' results';
	}

	$('#productsContainer').empty();

	if (Array.isArray(products) && products.length > 0) {
		products.forEach(function(p, index) {
			var availabilityBlock = p.availability === 'yes' ?
				'<div class="action-link-left">' +
				'<form id="addToCartForm' + index + '">' +
				'<input type="hidden" name="pid" value="' + p.id + '">' +
				'<input type="hidden" name="qty" value="1">' +
				'<button id="addToCartBtn' + index + '" style="color: aliceblue;">Add to Cart</button>' +
				'</form>' +
				'</div>' :
				'<button style="color: aliceblue; cursor:no-drop;">Add to Cart</button>';

			var productHtml = '<div class="col-xl-4 col-sm-6 col-12">' +
				'<div class="product-default-single-item product-color--golden" data-aos="fade-up" data-aos-delay="0">' +
				'<div class="image-box">' +
				'<a href="/product-details?id=' + p.id + '" class="image-link">' +
				'<img src="../images/product/' + p.img1 + '" alt="">' +
				'<img src="../images/product/' + p.img2 + '" alt="">' +
				'</a>' +
				'<div class="action-link">' +
				availabilityBlock +
				'<div class="action-link-right">' +
				'<form action="/addToWishlist" method="post">' +
				'<input type="hidden" name="pid" value="' + p.id + '">' +
				'<button style="color: white;"><i class="icon-heart"></i></button>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'<div class="content">' +
				'<div class="content-left">' +
				'<h6 class="title"><a href="/product-details?id=' + p.id + '">' + p.pname + '</a></h6>' +
				'<ul class="review-star">' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="empty"><i class="ion-android-star"></i></li>' +
				'</ul>' +
				'</div>' +
				'<div class="content-right">' +
				'<span class="price">' + p.price + '</span>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';

			$('#productsContainer').append(productHtml);
		});
	} else {
		// If no products found, display a message or perform any appropriate action
		$('#productsContainer').html('<img  class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="no product found" src="assets/images/no-product-found.png">');
	}
}

$(document).ready(function() {
	$('#productsContainer').on('click', '[id^="addToCartBtn"]', function(e) {
		e.preventDefault(); // Prevent the default form submission

		var btnId = $(this).attr('id').replace('addToCartBtn', '');
		var formData = $('#addToCartForm' + btnId).serialize(); // Serialize form data

		$.ajax({
			type: 'POST',
			url: 'user/addToCart', // URL for the addToCart endpoint
			data: formData,
			success: function(response) {
				fetchheaderCartData();
				fetchheadertotal();
				fetchcartcount();

				if (response === "Product added successfully.") {
					$('#insert').addClass('show');
					setTimeout(function() {
						$('#insert').removeClass('show');
					}, 3000);
				}
			},
			error: function(xhr) {
				if (xhr.status === 401) {
					$('#info').addClass('show');
					setTimeout(function() {
						$('#info').removeClass('show');
					}, 3000);
				} else {
					console.error('Error:', xhr);
					// Handle other errors if needed
				}
			}
		});
	});
});



function displayListProducts(products) {
	var productsContainer = $('#productsListContainer');
	productsContainer.empty(); // Clear existing products before displaying new ones

	if (products && Array.isArray(products) && products.length > 0) {
		products.forEach(function(product,index) {
			var availabilityBlock = '';
			if (product.availability === 'yes') {
				availabilityBlock = '<form id="addToCartForm' + index + '">' +
					'<input type="hidden" name="pid" value="' + product.id + '">' +
					'<input type="hidden" name="qty" value="1">' +
					'<button id="addToCartBtn' + index + '" class="btn btn-lg btn-black-default-hover">Add to cart</button>' +
					'</form>' + '&nbsp;';
			} else {
				availabilityBlock = '<button style="cursor:no-drop;" class="btn btn-lg btn-black-default-hover">Add to cart</button>';
			}

			// Construct HTML for each product
			var productHtml = '<div class="product-list-single product-color--golden">' +
				'<a href="/product-details?id=' + product.id + '" class="product-list-img-link">' +
				'<img src="../images/product/' + product.img1 + '" height="300px;" alt="">' +
				'<img src="../images/product/' + product.img2 + '" height="300px;" alt="">' +
				'</a>' +
				'<div class="product-list-content">' +
				'<h5 class="product-list-link">' +
				'<a href="/product-details?id=' + product.id + '">' + product.pname + '</a></h5>' +
				'<ul class="review-star">' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="empty"><i class="ion-android-star"></i></li>' +
				'</ul>' +
				'<span class="product-list-price">' + product.price + '</span>' +
				'<p>' + product.description + '</p>' +
				'<div class="product-action-icon-link-list d-md-flex justify-content-md-start">' +
				availabilityBlock +
				'<form action="/addToWishlist" method="post">' +
				'<input type="hidden" name="pid" value="' + product.id + '">' +
				'<button class="btn btn-lg btn-black-default-hover"><i class="icon-heart"></i></button>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>';

			productsContainer.append(productHtml);
		});
	} else {
		// Display a message when no products are found
		productsContainer.html('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
	}
}

$(document).ready(function() {
	$('#productsListContainer').on('click', '[id^="addToCartBtn"]', function(e) {
		e.preventDefault(); // Prevent the default form submission

		var btnId = $(this).attr('id').replace('addToCartBtn', '');
		var formData = $('#addToCartForm' + btnId).serialize(); // Serialize form data

		$.ajax({
			type: 'POST',
			url: 'user/addToCart', // URL for the addToCart endpoint
			data: formData,
			success: function(response) {
				fetchheaderCartData();
				fetchheadertotal();
				fetchcartcount();

				if (response === "Product added successfully.") {
					$('#insert').addClass('show');
					setTimeout(function() {
						$('#insert').removeClass('show');
					}, 3000);
				}
			},
			error: function(xhr) {
				if (xhr.status === 401) {
					$('#info').addClass('show');
					setTimeout(function() {
						$('#info').removeClass('show');
					}, 3000);
				} else {
					console.error('Error:', xhr);
					// Handle other errors if needed
				}
			}
		});
	});
});

//filter product show 
function displayFilteredProduct(products) {
	var productsLength = products.length; // Get the length of the current products array
	var totalProductsLength = products.length; // Assuming 'totalProducts' is the array containing all products

	var productCountElement = document.getElementById('productCounts');
	if (productCountElement) {
		productCountElement.textContent = 'Showing ' + productsLength + ' of ' + totalProductsLength + ' results';
	}

	$('#productsContainers').empty(); // Clear existing products before displaying filtered ones

	if (Array.isArray(products) && products.length > 0) {
		products.forEach(function(p) {
			var availabilityBlock = p.availability === 'yes' ?
				'<div class="action-link-left">' +
				'<form action="/addToCart" method="post">' +
				'<input type="hidden" name="pid" value="' + p.id + '">' +
				'<input type="hidden" name="qty" value="1">' +
				'<button style="color: aliceblue;">Add to Cart</button>' +
				'</form>' +
				'</div>' :
				'<button style="color: aliceblue; cursor:no-drop;">Add to Cart</button>';

			var productHtml = '<div class="col-xl-4 col-sm-6 col-12">' +
				'<div class="product-default-single-item product-color--golden" data-aos="fade-up" data-aos-delay="0">' +
				'<div class="image-box">' +
				'<a href="/product-details?id=' + p.id + '" class="image-link">' +
				'<img src="../images/product/' + p.img1 + '" alt="">' +
				'<img src="../images/product/' + p.img2 + '" alt="">' +
				'</a>' +
				'<div class="action-link">' +
				availabilityBlock +
				'<div class="action-link-right">' +
				'<form action="/addToWishlist" method="post">' +
				'<input type="hidden" name="pid" value="' + p.id + '">' +
				'<button style="color: white;"><i class="icon-heart"></i></button>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'<div class="content">' +
				'<div class="content-left">' +
				'<h6 class="title"><a href="/product-details?id=' + p.id + '">' + p.pname + '</a></h6>' +
				'<ul class="review-star">' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="empty"><i class="ion-android-star"></i></li>' +
				'</ul>' +
				'</div>' +
				'<div class="content-right">' +
				'<span class="price">' + p.price + '</span>' +
				'</div>' +
				'</div>' +
				'</div>' +
				'</div>';
			$('#productsContainers').append(productHtml);
		});
	} else {
		// If no products found, display a message or perform any appropriate action
		$('#productsContainers').html('<img  class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="no product found" src="assets/images/no-product-found.png">');
	}
}


function displayListProduct(products) {
	var productsContainer = $('#productsListContainers');
	productsContainer.empty(); // Clear existing products before displaying new ones

	if (products && Array.isArray(products) && products.length > 0) {
		products.forEach(function(product) {
			var availabilityBlock = '';
			if (product.availability === 'yes') {
				availabilityBlock = '<form action="/addToCart" method="post">' +
					'<input type="hidden" name="pid" value="' + product.id + '">' +
					'<input type="hidden" name="qty" value="1">' +
					'<button class="btn btn-lg btn-black-default-hover">Add to cart</button>' +
					'</form>' + '&nbsp;';
			} else {
				availabilityBlock = '<button style="cursor:no-drop;" class="btn btn-lg btn-black-default-hover">Add to cart</button>';
			}

			// Construct HTML for each product
			var productHtml = '<div class="product-list-single product-color--golden">' +
				'<a href="/product-details?id=' + product.id + '" class="product-list-img-link">' +
				'<img src="../images/product/' + product.img1 + '" height="300px;" alt="">' +
				'<img src="../images/product/' + product.img2 + '" height="300px;" alt="">' +
				'</a>' +
				'<div class="product-list-content">' +
				'<h5 class="product-list-link">' +
				'<a href="/product-details?id=' + product.id + '">' + product.pname + '</a></h5>' +
				'<ul class="review-star">' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="fill"><i class="ion-android-star"></i></li>' +
				'<li class="empty"><i class="ion-android-star"></i></li>' +
				'</ul>' +
				'<span class="product-list-price">' + product.price + '</span>' +
				'<p>' + product.description + '</p>' +
				'<div class="product-action-icon-link-list d-md-flex justify-content-md-start">' +
				availabilityBlock +
				'<form action="/addToWishlist" method="post">' +
				'<input type="hidden" name="pid" value="' + product.id + '">' +
				'<button class="btn btn-lg btn-black-default-hover"><i class="icon-heart"></i></button>' +
				'</form>' +
				'</div>' +
				'</div>' +
				'</div>';

			productsContainer.append(productHtml);
		});
	} else {
		// Display a message when no products are found
		productsContainer.html('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
	}
}
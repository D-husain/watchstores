$(document).ready(function() {
	fetchCartData();
	fetchtotal();
	fetchsubtotal();
	fetchheaderCartData();
	fetchheadertotal();
	fetchcartcount();
});

// Add to cart user
$(document).ready(function() {
	for (let i = 1; i <= 10000; i++) {
		$('#addToCartBtn' + i).click(function(e) {
			e.preventDefault(); // Prevent the default form submission

			var formData = $('#addToCartForm' + i).serialize(); // Serialize form data

			$.ajax({
				type: 'POST',
				url: 'user/addToCart',
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
	}
});

// Function to update cart via AJAX
function updateCart(element) {
	var id = $(element).closest('tr').find('.idInput').val();
	var qty = $(element).val();

	$.ajax({
		type: 'POST',
		url: '/user/updateCart',
		data: { id: id, qty: qty },
		success: function(response) {
			fetchCartData();
			fetchtotal();
			fetchsubtotal();
			fetchheaderCartData();
			fetchheadertotal();
			$('#update').addClass('show');
			setTimeout(function() {
				$('#update').removeClass('show');
			}, 3000);
		},
		error: function(error) {
			console.error('Error updating cart:', error);
		}
	});
}

function fetchCartData() {
	$.ajax({
		type: 'GET',
		url: '/cart/data',
		success: function(cartData) {
			$('#cartTable tbody').empty();

			if (cartData && cartData.length > 0) {

				cartData.forEach(function(cartItem) {
					var tableRow = '<tr>' +
						'<td class="product_thumb"><a href="/product-details?id=' + cartItem.productid + '">' +
						'<img src="../images/product/' + cartItem.img + '" alt=""></a></td>' +
						'<td class="product_name"><a href="/product-details?id=' + cartItem.productid + '">' + cartItem.pname + '</a></td>' +
						'<td class="product-price">₹ ' + cartItem.price + '</td>' +
						'<td class="product_quantity"><label>Quantity</label>' +
						'<form class="updateCartForm">' +
						'<input type="hidden" class="idInput" name="id" value="' + cartItem.id + '" />' +
						'<input min="1" max="10" class="qtyInput" name="qty" value="' + cartItem.qty + '" type="number" onchange="updateCart(this)" />' +
						'</form></td>' +
						'<td class="product_total">₹ ' + cartItem.total + '</td>' +
						'<td class="product_remove"><a data-id=' + cartItem.id + ' class="delete"><i class="fa fa-trash-o"></i></a></td>' +
						'</tr>';

					$('#cartTable tbody').append(tableRow);
				});
			} else {
				var emptyCartHTML = '<div class="empty-cart-section section-fluid">' +
					'<div class="emptycart-wrapper">' +
					'<div class="container">' +
					'<div class="row">' +
					'<div class="col-12 col-md-10 offset-md-1 col-xl-6 offset-xl-3">' +
					'<div class="emptycart-content text-center">' +
					'<h4 class="title">Your Cart is Empty !</h4>' +
					'<h6 class="sub-title">Sorry Mate... No items found inside your cart!</h6>' +
					'<a href="/shop" class="btn btn-lg btn-golden">Continue Shopping</a>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>';

				$('#empty-cart').append(emptyCartHTML);
				$('#cartTable').empty();
				$('#hide-checkout').empty();
			}
		},
		error: function(xhr) {
			if (xhr && xhr.status < 401) {
				var emptyCartHTML = '<div class="empty-cart-section section-fluid">' +
					'<div class="emptycart-wrapper">' +
					'<div class="container">' +
					'<div class="row">' +
					'<div class="col-12 col-md-10 offset-md-1 col-xl-6 offset-xl-3">' +
					'<div class="emptycart-content text-center">' +
					'<h4 class="title">You Are Logged Out!</h4>' +
					'<h6 class="sub-title">Sorry Mate... Please log in first</h6>' +
					'<a href="/login" class="btn btn-lg btn-golden">Login</a>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>';

				$('#logout-cart').append(emptyCartHTML); // Show the user login prompt
				$('#cartTable').empty(); // Clear the cart table
			} else {
				console.error('Error:', xhr);
			}
		}
	});
}

// Event handling for delete buttons
$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "/cart/delete/" + id,
			cache: false,
			success: function() {
				$('#deleteModal').modal('hide');
				fetchtotal();
				fetchsubtotal();
				fetchheaderCartData();
				fetchheadertotal();
				fetchcartcount();
				// Set a flag in localStorage to indicate deletion
				localStorage.setItem('deletionOccurred', 'true');
				window.location.reload();
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#deleteModal').modal('hide');
			}
		});
	});
});

// Check for the deletion flag on page load
$(document).ready(function() {
	var deletionFlag = localStorage.getItem('deletionOccurred');
	if (deletionFlag === 'true') {
		// Display the message or perform any action you need
		$('#delete').addClass('show');
		setTimeout(function() {
			$('#delete').removeClass('show');
		}, 3000);
		// Clear the flag after displaying the message
		localStorage.removeItem('deletionOccurred');
	}
});

function fetchtotal() {
	$.ajax({
		type: 'GET',
		url: '/user/cart/total',
		success: function(total) {
			if (total === 0) {
				$('#total').text('₹ 0.00'); // Update total to 0 if the cart is empty
			} else {
				$('#total').text('₹ ' + total.toFixed(2)); // Update the fetched total
			}
		},
		error: function(error) {
			console.error('Error fetching subtotal:', error);
			// Handle error scenario here
		}
	});
}

function fetchsubtotal() {
	$.ajax({
		type: 'GET',
		url: '/user/cart/subtotal',
		success: function(subtotal) {
			if (subtotal === 0) {
				$('#subtotal').text('₹ 0.00'); // Update subtotal to 0 if the cart is empty
			} else {
				$('#subtotal').text('₹ ' + subtotal.toFixed(2)); // Update the fetched subtotal
			}
		},
		error: function(error) {
			console.error('Error fetching subtotal:', error);
			// Handle error scenario here
		}
	});
}

//------------------------------------------------------------------------------------------------------------------------------

function fetchheaderCartData() {
	$.ajax({
		type: 'GET',
		url: '/cart/data', // Use a relative URL assuming the endpoint is on the same domain
		success: function(cartData) {
			$('.offcanvas-cart').empty(); // Clear existing cart items

			if (cartData && cartData.length > 0) {

				cartData.forEach(function(cartItem) {
					var tableRow = '<li class="offcanvas-cart-item-single">' +
						'<div class="offcanvas-cart-item-block">' +
						'<a href="/product-details?id=' + cartItem.productid + '" class="offcanvas-cart-item-image-link">' +
						'<img src="../images/product/' + cartItem.img + '" alt="" class="offcanvas-cart-image"></a>' +
						'<div class="offcanvas-cart-item-content">' +
						'<a href="/product-details?id=' + cartItem.productid + '" class="offcanvas-cart-item-link">' + cartItem.pname + '</a>' +
						'<div class="offcanvas-cart-item-details">' +
						'<span class="offcanvas-cart-item-details-quantity">' + cartItem.qty + ' x </span>' +
						'<span class="offcanvas-cart-item-details-price">₹ ' + cartItem.price + '</span>' +
						'</div></div></div>' +
						'<div class="offcanvas-cart-item-delete text-right">' +
						'<a data-id=' + cartItem.id + ' class="offcanvas-cart-item-delete delete"><i class="fa fa-trash-o"></i></a>' +
						'</div></li>';

					$('.offcanvas-cart').append(tableRow);
				});

				$('.offcanvas-cart-button').empty().append('<li><a href="checkout" class="btn btn-block btn-pink mt-5">Checkout</a></li>');
			} else {
				$('.offcanvas-cart').append('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
				$('.offcanvas-cart-button').empty().append('<li style="cursor:no-drop;"><button class="btn btn-block btn-pink mt-5" disabled>Checkout</button></li>');
			}
		},
		error: function(xhr) {
			if (xhr && xhr.status < 401) {
				$('.offcanvas-cart').append('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
				$('.offcanvas-cart-button').empty().append('<li style="cursor:no-drop;"><button class="btn btn-block btn-pink mt-5" disabled>Checkout</button></li>');
			} else {
				console.error('Error:', xhr);
			}
		}
	});
}


function fetchheadertotal() {
	$.ajax({
		type: 'GET',
		url: '/user/cart/total',
		success: function(total) {
			if (total === 0) {
				$('#totals').text('₹ 0.00'); // Update subtotal to 0 if the cart is empty
			} else {
				$('#totals').text('₹ ' + total.toFixed(2)); // Update the fetched subtotal
			}
		},
		error: function(error) {
			console.error('Error fetching subtotal:', error);
			// Handle error scenario here
		}
	});
}

function fetchcartcount() {
	$.ajax({
		type: 'GET',
		url: 'user/cart/count',
		success: function(size) {
			var cartCountElement = $('#cartcount');

			if (typeof size === 'number') {
				// Assuming 'size' is the count of items in the cart
				if (size === 0) {
					cartCountElement.text('0'); // Update subtotal to 0 if the cart is empty
				} else {
					cartCountElement.text(size); // Update the fetched count
				}
			} else {
				console.error('Invalid size value received:', size);
				// Handle invalid response
			}
		},
		error: function(error) {
			console.error('Error fetching cartcount:', error);
			// Handle error scenario here
		}
	});
}



$(document).ready(function() {
	fetchCartData();
	fetchtotal();
	fetchsubtotal();
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

			if (cartData && cartData.length === 0) {
				var emptyCartHTML = '<tr><td colspan="6">' +
					'<div class="empty-cart-section section-fluid">' +
					'<div class="emptycart-wrapper">' +
					'<div class="container">' +
					'<div class="row">' +
					'<div class="col-12 col-md-10 offset-md-1 col-xl-6 offset-xl-3">' +
					'<div class="emptycart-content text-center">' +
					'<h4 class="title">Your Cart is Empty</h4>' +
					'<h6 class="sub-title">Sorry Mate... No items found inside your cart!</h6>' +
					'<a href="shop" class="btn btn-lg btn-golden">Continue Shopping</a>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div>' +
					'</div></td></tr>';

				$('#cartTable tbody').append(emptyCartHTML);
			} else {
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
			}
		},
		error: function(xhr) {
			if (xhr.status === 401) {
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

				$('#cartTableempty').html(emptyCartHTML); // Show the user login prompt
				$('#cartTable').empty(); // Clear the cart table
			} else {
				console.error('Error:', xhr);
			}
		}
	});
}


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
// Call fetchCartData function to fetch cart data when the page loads
$(document).ready(function() {
	fetchheaderCartData();
	fetchheadertotal();
	fetchcartcount();
});

function fetchheaderCartData() {
	$.ajax({
		type: 'GET',
		url: '/cart/data', // Use a relative URL assuming the endpoint is on the same domain
		success: function(cartData) {
			$('.offcanvas-cart').empty(); // Clear existing cart items

			if (cartData.length === 0) {
				$('.offcanvas-cart').append('<li>No products in the cart</li>');
			} else {
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
			}
		},
		error: function(error) {
			console.error('Error fetching cart data:', error);
			// Handle error scenario here
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

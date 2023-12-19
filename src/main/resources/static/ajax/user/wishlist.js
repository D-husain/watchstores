$(document).ready(function() {
	fetchWishlistData();
	fetchwishlistcount();
	fetchheaderwishData();
});

$(document).ready(function() {
	for (let i = 1; i <= 10000; i++) {
		$('#addToWishlistBtn' + i).click(function(e) {
			e.preventDefault(); // Prevent the default form submission

			var formData = $('#addToWishlistForm' + i).serialize(); // Serialize form data

			$.ajax({
				type: 'POST',
				url: 'user/addToWishlist',
				data: formData,
				success: function(response) {
					fetchheaderwishData();
					fetchwishlistcount();

					if (response === "Product added successfully.") {
						$('#insertwishlist').addClass('show');
						setTimeout(function() {
							$('#insertwishlist').removeClass('show');
						}, 3000);
					}
					else if(response === "Product is already in your wishlist."){
						$('#sem').addClass('show');
						setTimeout(function() {
							$('#sem').removeClass('show');
						}, 3000);
					}
				},
				error: function(xhr) {
					if (xhr.status === 401) {
						$('#infowishlist').addClass('show');
						setTimeout(function() {
							$('#infowishlist').removeClass('show');
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

function fetchWishlistData() {
	$.ajax({
		type: 'GET',
		url: '/wishlist/data',
		success: function(wishlistData) {
			const wishlistTableBody = $('#wishlistTable tbody');
			const emptyWishlistSection = $('#empty-wishlist');
			const logoutWishlistSection = $('#logout-wishlist');

			wishlistTableBody.empty();
			emptyWishlistSection.empty();
			logoutWishlistSection.empty();

			if (wishlistData && wishlistData.length > 0) {
				wishlistData.forEach(function(wishlistItem,index) {
					const tableRow = `<tr>
                              <td class="product_thumb">
                                <a href="/product-details?id=${wishlistItem.pid}">
                                  <img src="../images/product/${wishlistItem.img}" alt="">
                                </a>
                              </td>
                              <td class="product_name">
                                <a href="/product-details?id=${wishlistItem.pid}">${wishlistItem.pname}</a>
                              </td>
                              <td class="product-price">₹ ${wishlistItem.price}</td>
                              <td class="product_stock">
                                <span class="${wishlistItem.stock === 'yes' ? 'in-stock' : 'out-of-stock'}">
                                  ${wishlistItem.stock === 'yes' ? 'In stock' : 'Out of stock'}
                                </span>
                              </td>
                              <td class="product_addcart">
                                ${wishlistItem.stock === 'yes' ?
							`<form id="addToCartForm${index}">
                                      <input type="hidden" name="pid" value="${wishlistItem.pid}">
                                      <input type="hidden" name="qty" value="1">
                                      <button id="addToCartBtn${index}" class="btn btn-md btn-golden" data-bs-toggle="modal">Add To Cart</button>
                                    </form>` :
							`<button style="cursor: no-drop;" class="btn btn-md btn-golden" data-bs-toggle="modal">Add To Cart</button>`
						}
                              </td>
                              <td class="product_remove">
                                <a class="delete" data-id="${wishlistItem.id}">
                                  <i class="fa fa-trash-o"></i>
                                </a>
                              </td>
                            </tr>`;

					wishlistTableBody.append(tableRow);
				});
			} else {
				const emptyCartHTML = `<div class="empty-cart-section section-fluid">
                                <div class="emptycart-wrapper">
                                  <div class="container">
                                    <div class="row">
                                      <div class="col-12 col-md-10 offset-md-1 col-xl-6 offset-xl-3">
                                        <div class="emptycart-content text-center">
                                          <h4 class="title">Unfortunately, Your Wishlist Cart Is Empty</h4>
                                          <h6 class="sub-title">Please Add Something In your wishlist Cart</h6>
                                          <a href="/shop" class="btn btn-lg btn-golden">Continue Shopping</a>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>`;

				emptyWishlistSection.append(emptyCartHTML);
				$('#wishlistTable').empty();
			}
		},
		error: function(xhr) {
			if (xhr && xhr.status < 401) {
				const emptyWishlistHTML = `<div class="empty-cart-section section-fluid">
                                <div class="emptycart-wrapper">
                                  <div class="container">
                                    <div class="row">
                                      <div class="col-12 col-md-10 offset-md-1 col-xl-6 offset-xl-3">
                                        <div class="emptycart-content text-center">
                                          <h4 class="title">You Are Logged Out!</h4>
                                          <h6 class="sub-title">Sorry Mate... Please log in first</h6>
                                          <a href="/logins" class="btn btn-lg btn-golden">Login</a>
                                        </div>
                                      </div>
                                    </div>
                                  </div>
                                </div>
                              </div>`;

				$('#logout-wishlist').append(emptyWishlistHTML);
				$('#wishlistTable').empty();
			} else {
				console.error('Error:', xhr);
			}
		}
	});
}


$(document).ready(function() {
	$('#wishlistTable tbody').on('click', '[id^="addToCartBtn"]', function(e) {
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
				fetchWishlistData();
				fetchheaderwishData();
				fetchwishlistcount();

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


$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "/wishlist/delete/" + id,
			cache: false,
			success: function() {
				$('#deleteModal').modal('hide');
				fetchWishlistData();
				fetchwishlistcount();
				fetchheaderwishData();
				// Set a flag in localStorage to indicate deletion
				$('#delete').addClass('show');
				setTimeout(function() {
					$('#delete').removeClass('show');
				}, 3000);
				// After deleting, check if the cart is empty
				if ($('#wishlistTable tbody tr').length === 0) {
					$('#wishlistTable').empty();
				}
				$('#empty-wishlist').empty();
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#deleteModal').modal('hide');
			}
		});
	});
});

//---------------------------------------------------- Herader cart --------------------------------------------------------------

function fetchheaderwishData() {
	$.ajax({
		type: 'GET',
		url: '/wishlist/data', // Use a relative URL assuming the endpoint is on the same domain
		success: function(cartData) {
			$('.offcanvas-wishlist').empty(); // Clear existing cart items

			if (cartData && cartData.length > 0) {

				cartData.forEach(function(wishlistItem) {
					var tableRow = '<li class="offcanvas-wishlist-item-single">' +
						'<div class="offcanvas-wishlist-item-block">' +
						'<a href="/product-details?id=' + wishlistItem.pid + '" class="offcanvas-wishlist-item-image-link">' +
						'<img src="../images/product/' + wishlistItem.img + '" alt="" class="offcanvas-wishlist-image"></a>' +
						'<div class="offcanvas-wishlist-item-content">' +
						'<a href="/product-details?id=' + wishlistItem.pid + '" class="offcanvas-wishlist-item-link">' + wishlistItem.pname + '</a>' +
						'<div class="offcanvas-wishlist-item-details">' +
						'<span class="offcanvas-wishlist-item-details-price">₹ ' + wishlistItem.price + '</span>' +
						'</div></div></div>' +
						'<div class="offcanvas-wishlist-item-delete text-right">' +
						'<a data-id=' + wishlistItem.id + ' class="offcanvas-wishlist-item-delete delete"><i class="fa fa-trash-o"></i></a>' +
						'</div></li>';

					$('.offcanvas-wishlist').append(tableRow);
				});
				$('#total-hide').show();
				$('.offcanvas-wishlist-button-shop').empty().append('<li><a href="/wishlist" class="btn btn-block btn-pink">View Wishlist</a></li>');
			} else {
				$('.offcanvas-wishlist').append('<div class=" text-center">' +
					'<h4 class="title">Unfortunately, Your Cart Is Empty</h4>' +
					'<h6 class="sub-title">Please Add Something In your Cart</h6>' +
					'</div>');
				$('#total-hide').empty();
				$('.offcanvas-wishlist-button').empty();
				$('.offcanvas-wishlist-button-shop').empty().append('<li><a href="/shop" class="btn btn-block btn-pink">Countinue Shopping</a></li>');

			}
		},
		error: function(xhr) {
			if (xhr && xhr.status < 401) {
				$('.offcanvas-wishlist').append('<div class=" text-center">' +
					'<h4 class="title">Unfortunately, Your are Logged Out</h4>' +
					'<h6 class="sub-title">Please Login  In your first</h6>' +
					'</div>');
				$('.offcanvas-wishlist-button').empty().append('<li><a href="logins" class="btn btn-block btn-pink mt-5">Login</a></li>');
			} else {
				console.error('Error:', xhr);
			}
		}
	});
}

function fetchwishlistcount() {
	$.ajax({
		type: 'GET',
		url: 'user/wishlist/count',
		success: function(size) {
			var wishlistCountElement = $('#wishlistcount');

			if (typeof size === 'number') {
				if (size === 0) {
					wishlistCountElement.text('0');
				} else {
					wishlistCountElement.text(size);
				}
			} else {
				console.error('Invalid size value received:', size);
			}
		},
		error: function(error) {
			console.error('Error fetching cartcount:', error);
		}
	});
}

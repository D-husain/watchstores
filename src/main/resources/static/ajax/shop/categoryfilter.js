/*$(document).ready(function() {
	// Fetch categories and populate the category filter
	$.ajax({
		url: '/category/data',
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			if (response && response.length > 0) {
				var categoryFilter = $('#categoryFilter');
				response.forEach(function(category) {
					var categoryListItem = '<li class="sidebar-menu-collapse-list">' +
						'<div class="accordion">' +
						'<button onclick="filterByCategory(\'' + category.cname + '\')">' + category.cname + '</button>' +
						'</div>' +
						'</li>';
					categoryFilter.append(categoryListItem);
				});
			}
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
});*/

$(document).ready(function() {
	
	function fetchCategoryData() {
		$.ajax({
			url: '/category/data',
			method: 'GET',
			dataType: 'json',
			success: function(response) {
				if (response && response.length > 0) {
					var productsLength = response.length;
					var categoryFilter = $('#categoryFilter');
					var previousCheckbox = null; // To keep track of the previously checked checkbox
					response.forEach(function(category, index) {
						var categoryListItem = $('<li></li>');
						var checkboxId = 'categoryCheckbox_' + index;
						var label = $('<label class="checkbox-default" for="' + checkboxId + '"></label>');
						var input = $('<input type="checkbox" id="' + checkboxId + '">');
						var span = $('<span>' + category.cname + ' (' + productsLength + ')</span>');

						label.append(input);
						label.append(span);
						categoryListItem.append(label);
						categoryFilter.append(categoryListItem);

						// Event listener for checkbox change
						input.on('change', function() {
							if (this.checked) {
								
								if (previousCheckbox && previousCheckbox !== this) {
									$(previousCheckbox).prop('checked', false);
								}
								filterByCategory(category.cname);
								previousCheckbox = this;
							} else {
								// Clear filtered products if unchecked
								getAllProducts();
								//clearFilteredProducts();
							}
						});
					});
				} else {
					console.error('Empty or invalid response received from the server.');
				}
			},
			error: function(xhr, status, error) {
				console.error('AJAX request failed:', error);
			}
		});
	}

	fetchCategoryData();
});

// Function to filter products by category
function filterByCategory(category) {
	$.ajax({
		url: '/api/shop?category=' + category,
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			// On successful response, clear existing products and display filtered products
			displayFilteredProducts(response);
			displayListProducts(response);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

function getAllProducts() {
	$.ajax({
		url: '/api/shop',
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			// On successful response, clear existing products and display filtered products
			displayFilteredProducts(response);
			displayListProducts(response);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}
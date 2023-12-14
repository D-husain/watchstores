$(document).ready(function() {
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
$(document).ready(function() {
	$.ajax({
		url: '/brand/data',
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			if (response) {
				var brandFilter = $('#brandFilter');
				response.forEach(function(brand) {
					var brandListItem = '<li class="sidebar-menu-collapse-list">' +
						'<div class="accordion">' +
						'<button onclick="filterByBrand(\'' + brand.brand + '\')">' + brand.brand + '</button>' +
						'</div>' +
						'</li>';
					brandFilter.append(brandListItem);
				});
			}
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
});

function filterByBrand(brand) {
	$.ajax({
		url: 'http://localhost:8081/api/shop?brand=' + brand,
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
function filterByProductSortby(sort) {
	$.ajax({
		url: '/api/sort?sort=' + sort,
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			// On successful response, display filtered products
			displayFilteredProducts(response);
			displayListProducts(response);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}
$(document).ready(function() {
	getAllProducts();
});

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

//---------------------------------------Products Sorting -----------------------------------------------------------

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

//---------------------------------------Category Filter -----------------------------------------------------------

$(document).ready(function() {
	
	function fetchCategoryData() {
		$.ajax({
			url: '/category/data',
			method: 'GET',
			dataType: 'json',
			success: function(response) {
				if (response && response.length > 0) {
					var categoryFilter = $('#categoryFilter');
					var previousCheckbox = null; // To keep track of the previously checked checkbox
					response.forEach(function(category, index) {
						var categoryListItem = $('<li></li>');
						var checkboxId = 'categoryCheckbox_' + index;
						var label = $('<label class="checkbox-default" for="' + checkboxId + '"></label>');
						var input = $('<input type="checkbox" id="' + checkboxId + '">');
						var span = $('<span>' + category.cname + ' </span>');

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

//---------------------------------------Brand Filter -----------------------------------------------------------

$(document).ready(function() {
	
	function fetchBrandData() {
		$.ajax({
			url: '/brand/data',
			method: 'GET',
			dataType: 'json',
			success: function(response) {
				if (response && response.length > 0) {
					var brandFilter = $('#brandFilter');
					var previousCheckbox = null; // To keep track of the previously checked checkbox
					response.forEach(function(brand, index) {
						var brandListItem = $('<li></li>');
						var checkboxId = 'brandCheckbox_' + index;
						var label = $('<label class="checkbox-default" for="' + checkboxId + '"></label>');
						var input = $('<input type="checkbox" id="' + checkboxId + '">');
						var span = $('<span>' + brand.brand + '</span>');

						label.append(input);
						label.append(span);
						brandListItem.append(label);
						brandFilter.append(brandListItem);

						// Event listener for checkbox change
						input.on('change', function() {
							if (this.checked) {
								
								if (previousCheckbox && previousCheckbox !== this) {
									$(previousCheckbox).prop('checked', false);
								}
								filterByBrand(brand.brand);
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

	fetchBrandData();
});


// Function to filter products by brand
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

//---------------------------------------Color Filter -----------------------------------------------------------

$(document).ready(function() {
	
	function fetchColorData() {
		$.ajax({
			url: '/product/color',
			method: 'GET',
			dataType: 'json',
			success: function(response) {
				if (response && response.length > 0) {
					var brandFilter = $('#colorFilter');
					var previousCheckbox = null; // To keep track of the previously checked checkbox
					response.forEach(function(color, index) {
						var colorListItem = $('<li></li>');
						var checkboxId = 'colorCheckbox_' + index;
						var label = $('<label class="checkbox-default" for="' + checkboxId + '"></label>');
						var input = $('<input type="checkbox" id="' + checkboxId + '">');
						var span = $('<span>' +color + '</span>');

						label.append(input);
						label.append(span);
						colorListItem.append(label);
						brandFilter.append(colorListItem);

						// Event listener for checkbox change
						input.on('change', function() {
							if (this.checked) {
								
								if (previousCheckbox && previousCheckbox !== this) {
									$(previousCheckbox).prop('checked', false);
								}
								filterBycolore(color);
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

	fetchColorData();
});

function filterBycolore(color) {
	$.ajax({
		url: 'http://localhost:8081/api/shop?color=' + color,
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

//---------------------------------------Brand Filter -----------------------------------------------------------
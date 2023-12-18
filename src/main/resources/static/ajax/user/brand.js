document.addEventListener("DOMContentLoaded", function() {
	const brandLinks = document.querySelectorAll('.brandLink');

	brandLinks.forEach(function(link) {
		link.addEventListener('click', function(event) {
			event.preventDefault();
			const brand = this.getAttribute('data-brand');
			window.location.href = `barnd-product?brand=${brand}`;
		});
	});
});

































/*document.addEventListener("DOMContentLoaded", function() {
	const brandLinks = document.querySelectorAll('.brandLink');

	brandLinks.forEach(function(link) {
		link.addEventListener('click', function(event) {
			event.preventDefault();

			const brand = this.getAttribute('data-brand');

			fetch(`/api/shop?brand=${brand}`)
				.then(response => response.json())
				.then(data => {
					// Display the fetched product data
					displayProducts(data);
				})
				.catch(error => {
					console.error('Error fetching data:', error);
				});
		});
	});

	function displayProducts(products) {
		const productListDiv = document.querySelector('.product-list');
		productListDiv.innerHTML = ''; // Clear previous content

		products.forEach(function(product) {
			const productDiv = document.createElement('div');
			productDiv.classList.add('product');
			productDiv.textContent = `Product Name: ${product.pname}, Price: ${product.price}`;

			productListDiv.appendChild(productDiv);
		});
	}
});*/



























/*function fetchBrandProducts(brand) {
	$.ajax({
		url: '/api/shop?brand=' + brand,
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			sessionStorage.setItem('filteredBrandProducts', JSON.stringify(response));
			window.location.href = "/shop?brand=" + encodeURIComponent(brand);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

$(document).ready(function() {
	$('body').on('click', 'a.brandLink', function(event) {
		event.preventDefault();
		var brand = $(this).find('.brandButton').attr('id');
		fetchBrandProducts(brand);
	});
});*/
document.addEventListener("DOMContentLoaded", function() {
	const urlParams = new URLSearchParams(window.location.search);
	const category = urlParams.get('category');

	if (category) {
		fetch(`/api/shop?category=${category}`)
			.then(response => response.json())
			.then(data => {
				
			displayFilteredProducts(data);
			displayListProducts(data);
			})
			.catch(error => {
				console.error('Error fetching data:', error);
			});
	} else {
		console.error('Category parameter not found');
	}

});
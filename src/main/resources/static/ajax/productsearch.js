// Function to handle the form submission
document.getElementById('searchForm').addEventListener('keypress', function(event) {
	if (event.key === 'Enter') {
		event.preventDefault(); // Prevent the default form submission
		let keyword = document.getElementById('searchInput').value; // Get the keyword from input
		searchByKeyword(keyword); // Call the search function
		closeSearchModal(); // Function to close the modal
	}
});

// Function to perform search by keyword
function searchByKeyword(keyword) {
	$.ajax({
		url: 'api/productsearch?keyword=' + encodeURIComponent(keyword), // Encoded URL
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			searchproducts(response);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

function closeSearchModal() {
	var searchModal = document.getElementById('search');
	if (searchModal.classList.contains('show')) {
		searchModal.classList.remove('show');
		// Reset the search input field if needed
		document.getElementById('searchInput').value = '';
	}
}

function searchproducts(products) {
	var productsContainer = $('#searchproduct');
	productsContainer.empty(); // Clear existing products before displaying new ones

	if (products && Array.isArray(products) && products.length > 0) {
		products.forEach(function(product) {
			var productHtml = `
					            <br><li class="offcanvas-wishlist-item-single">
					                <div class="offcanvas-wishlist-item-block">
					                    <a href="/product-details?id=${product.id}" class="offcanvas-wishlist-item-image-link">
					                        <img src="../images/product/${product.img1}" alt="" class="offcanvas-wishlist-image">
					                    </a>
					                    <div class="offcanvas-wishlist-item-content">
					                        <a href="/product-details?id=${product.id}" class="offcanvas-wishlist-item-link">${product.pname}</a>
					                        <div class="offcanvas-wishlist-item-details">
					                            <span class="offcanvas-wishlist-item-details-price">₹ ${product.price}</span>
					                        </div>
					                    </div>
					                </div>
					            </li>
					        `;
			productsContainer.append(productHtml);
		});
	} else if (products && products.empty) {
		productsContainer.html('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
	} else {
		productsContainer.html('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
	}

}
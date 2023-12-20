// Function to fetch products based on the entered keyword
function fetchProducts() {
    var keyword = $('#keywordInput').val();
    if (keyword.trim() !== '') {
        // Redirect to a page with the keyword in the URL for fetching more products
        window.location.href = 'productsearch?keyword=' + encodeURIComponent(keyword);
    }
}



/*// Function to handle the form submission
document.getElementById('searchForm').addEventListener('keypress', function(event) {
	if (event.key === 'Enter') {
		event.preventDefault(); 
		let keyword = document.getElementById('searchInput').value; 
		searchByKeyword(keyword); 
		closeSearchModal();
	}
});

// Function to perform search by keyword
function searchByKeyword(keyword) {
	$.ajax({
		url: 'api/productsearch?keyword=' + encodeURIComponent(keyword),
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			searchProducts(response);
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
		document.getElementById('searchInput').value = '';
	}
}

function searchProducts(products) {
    var productsContainer = $('#searchproduct');
    productsContainer.empty(); // Clear existing products before displaying new ones

    if (!products || (Array.isArray(products) && products.length === 0)) {
        productsContainer.html('<img src="assets/images/no-product-found.png" class="rounded mx-auto d-block" style="width:400px;height:200px;" alt="No product found">');
    } else {
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

        // Input field for keyword and fetch button
        var fetchProductsHtml = `
            <div class="input-group mb-3">
                <input type="text" id="keywordInput" class="form-control" placeholder="Enter keyword">
                <div class="input-group-append">
                    <button id="fetchProductsBtn" class="btn btn-primary" onclick="fetchProducts()">Fetch Products</button>
                </div>
            </div>
        `;
        productsContainer.append(fetchProductsHtml);
    }
}

*/
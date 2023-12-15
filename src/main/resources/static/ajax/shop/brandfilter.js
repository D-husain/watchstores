$(document).ready(function() {
    // Function to fetch brand data and populate checkboxes
    function fetchBrandData() {
        $.ajax({
            url: '/brand/data',
            method: 'GET',
            dataType: 'json',
            success: function(response) {
                if (response && response.length > 0) {
					var productsLength = response.length;
                    var brandFilter = $('#brandFilter');
                    var previousCheckbox = null; // To keep track of the previously checked checkbox
                    response.forEach(function(brand, index) {
                        var brandListItem = $('<li></li>');
                        var checkboxId = 'brandCheckbox_' + index;
                        var label = $('<label class="checkbox-default" for="' + checkboxId + '"></label>');
                        var input = $('<input type="checkbox" id="' + checkboxId + '">');
                        var span = $('<span>' + brand.brand + ' (' + productsLength + ')</span>');

                        label.append(input);
                        label.append(span);
                        brandListItem.append(label);
                        brandFilter.append(brandListItem);

                        // Event listener for checkbox change
                        input.on('change', function() {
                            if (this.checked) {
                                // If another checkbox was previously checked, uncheck it
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
                // Handle error scenario here, e.g., show an error message to the user
            }
        });
    }

    // Rest of your code (filterByBrand function, displayFilteredProducts, displayListProducts, clearFilteredProducts)

    // Call function to fetch brand data and populate checkboxes
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
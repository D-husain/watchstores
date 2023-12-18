$(document).ready(function() {
	fetchCategories();
});

function fetchCategories() {
	$.ajax({
		url: '/category/data',
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			if (response && response.length > 0) {
				var categoryFilter = $('#category');
				response.forEach(function(category) {
					var categoryElement = createCategoryElement(category);
					categoryFilter.append(categoryElement);
				});

				addClickEventListeners(); // Attach click event listeners after appending categories
			}
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

function createCategoryElement(category) {
	var categoryListItem = $('<a>', {
		'href': '#',
		'class': 'banner-single-item banner-style-7 banner-animation banner-color--green float-left categoryLink', // Change ID to class
		'data-category': category.cname,
		'data-aos': 'fade-up',
		'data-aos-delay': '0',
		'html': $('<div>', {
			'class': 'image',
			'html': $('<img>', {
				'class': 'img-fluid',
				'src': '../images/category/' + category.cimg,
				'alt': category.cname
			})
		})
	});
	return categoryListItem;
}

function addClickEventListeners() {
	$('.categoryLink').on('click', function(event) {
		event.preventDefault();
		const category = $(this).data('category');
		window.location.href = `/category-product?category=${category}`;
	});
}




/*function fetchcatProducts(category) {
	$.ajax({
		url: '/api/shop?category=' + category,
		method: 'GET',
		dataType: 'json',
		success: function(response) {
			sessionStorage.setItem('filteredProducts', JSON.stringify(response));
			window.location.href = "/shop?category=" + encodeURIComponent(category);
		},
		error: function(xhr, status, error) {
			console.error(error);
		}
	});
}

$(document).ready(function() {
	$('body').on('click', '.categoryButton', function() {
		var category = $(this).attr('id'); // Get the ID (category name) of the clicked button
		fetchcatProducts(category);
	});
});*/
document.addEventListener("DOMContentLoaded", function() {
	const searchLinks = document.querySelectorAll('.searchLink');

	searchLinks.forEach(function(link) {
		link.addEventListener('click', function(event) {
			event.preventDefault();
			const keyword = this.getAttribute('data-keyword');
			window.location.href = `productsearch?keyword=${keyword}`;
		});
	});
});

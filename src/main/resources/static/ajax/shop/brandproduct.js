function fetchProductsByBrand(brand) {
  fetch(`/api/shop?brand=${brand}`)
    .then(response => response.json())
    .then(data => {
      displayFilteredProducts(data);
      displayListProducts(data);
    })
    .catch(error => {
      console.error('Error fetching data:', error);
    });
}

document.addEventListener("DOMContentLoaded", function() {
  const urlParams = new URLSearchParams(window.location.search);
  const brand = urlParams.get('brand');

  if (brand) {
    fetchProductsByBrand(brand);
  } else {
    console.error('Brand parameter not found');
  }
});
// Event handling for delete buttons
$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "/cart/delete/" + id,
			cache: false,
			success: function() {
				$('#deleteModal').modal('hide');
				fetchtotal();
				fetchsubtotal();
				fetchheaderCartData();
				fetchheadertotal();
				fetchcartcount();
				$('#delete').addClass('show');
				setTimeout(function() {
					$('#delete').removeClass('show');
				}, 3000);
				window.setTimeout(function() { location.reload() })
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#deleteModal').modal('hide');
			}
		});
	});
});
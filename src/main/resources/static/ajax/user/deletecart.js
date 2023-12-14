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
				// Set a flag in localStorage to indicate deletion
				localStorage.setItem('deletionOccurred', 'true');
				window.location.reload();
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#deleteModal').modal('hide');
			}
		});
	});
});

// Check for the deletion flag on page load
$(document).ready(function() {
	var deletionFlag = localStorage.getItem('deletionOccurred');
	if (deletionFlag === 'true') {
		// Display the message or perform any action you need
		$('#delete').addClass('show');
		setTimeout(function() {
			$('#delete').removeClass('show');
		}, 3000);
		// Clear the flag after displaying the message
		localStorage.removeItem('deletionOccurred');
	}
});
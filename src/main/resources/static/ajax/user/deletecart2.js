$('li').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModals').modal('show');
	$('#offcanvas-add-cart').model('hide');

	// Confirm delete action
	$('#confirmDeletes').off().on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "/cart/delete/" + id,
			cache: false,
			success: function() {
				$('#deleteModals').modal('hide');
				fetchtotal();
				fetchsubtotal();
				fetchheadertotal();
				fetchcartcount();
				fetchheaderCartData();
				fetchCartData();
				$('a[data-id="' + id + '"]').closest('li').fadeOut('slow', function() {
					$(this).remove();
				});
				$('#delete').addClass('show');
				setTimeout(function() {
					$('#delete').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				console.error('Error deleting category:', error);
				$('#deleteModals').modal('hide');
			}
		});
	});
});

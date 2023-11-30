/*<button data-toggle="modal" data-target="#deletemodal"
	class="btn btn-sm btn-danger btn-icon-text text-decoration-none">
	Remove <i class="typcn typcn-delete btn-icon-append"></i>
</button>*/
$(document).ready(function() {
	getAllSubscribe();
});

function getAllSubscribe() {
	$.ajax({
		url: 'http://localhost:8081/user/subscribe',
		method: 'GET',
		dataType: 'json',
		success: function(json) {
			var tableBody = $('table tbody');
			tableBody.empty();
			if (json.length > 0) {
				$(json).each(function(index, subscribe) {
					var tr = '<tr>' +
						'<td>' + (index + 1) + '</td>' +
						'<td>' + subscribe.fname + ' ' + subscribe.lname + '</td>' +
						'<td>' + subscribe.email + '</td>' +
						'<td>' +
						'<button class=\'btn btn-sm btn-danger btn-icon-text text-decoration-none delete\' data-id=' + subscribe.id + '>Remove <i class="typcn typcn-delete btn-icon-append"></i></button>' +
						'</td>' +
						'</tr>';
					tableBody.append(tr);
				});
				getPagination('#myTable');
			} else {
				var message = '<tr>' +
					'<th colspan="4">No subscribers found</th>' +
					'</tr>';
				tableBody.append(message);
			}
		},
		error: function(error) {
			alert('Error fetching subscribe: ' + error);
		}
	});
}



$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deletemodal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8081/subscribe/delete/" + id,
			cache: false,
			success: function() {
				$('#deletemodal').modal('hide');
				$('button[data-id="' + id + '"]').closest('tr').fadeOut('slow', function() {
					$(this).remove();
				});
				$('#delete').addClass('show');
				setTimeout(function() {
					$('#delete').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				console.error('Error deleting subscribe:', error);
				$('#deletemodal').modal('hide');
			}
		});
	});
});
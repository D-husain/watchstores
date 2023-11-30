$(document).ready(function() {
	getAllCountry();
});

function checkConame() {
	let cname = document.getElementById('countryname').value;
	let cnameerr = document.getElementById('conameerr');
	if (cname === "") {
		cnameerr.innerHTML = "Please enter the country name";
		return false;
	} else {
		cnameerr.innerHTML = "";
		return true;
	}
}

function validate() {
	let isCnameValid = checkConame();

	return isCnameValid; // Return true only if both validations pass
}

$(document).delegate('#submitCountryOrigin', 'click', function(event) {
	event.preventDefault();

	var cname = $('#countryname').val();

	if (validate()) {
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "/product/origin/save",
			data: JSON.stringify({ 'countryname': cname }),
			cache: false,
			success: function() {
				$('#addmodal').modal('hide');
				$('#countryname').val('');
				$('#insert').addClass('show');
				getAllCountry();
				setTimeout(function() {
					$('#insert').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				alert(error);
			}
		});
	}
});

function getAllCountry() {
	$.ajax({
		url: '/product/countryorigin',
		method: 'GET',
		dataType: 'json',
		success: function(json) {
			var tableBody = $('table tbody');
			tableBody.empty();
			$(json).each(function(index, origin) {
				var tr = '<tr>' +
					'<td>' + (index + 1) + '</td>' +
					'<td>' + origin.countryname + '</td>' +
					'<td><button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + origin.id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>&nbsp;&nbsp;' +
					'<button class=\'btn btn-danger delete\' data-id=' + origin.id + '>Delete</button></td>' +
					'</tr>';
				tableBody.append(tr);
			});
		},
		error: function(error) {
			alert('Error fetching categories: ' + error);
		}
	});
}

$('table').on('click', '.edit', function() {
	var id = $(this).data('id');
	$.getJSON('product/countryorigin/' + id, function(origin) {
		$('#countryId').val(origin.id);
		$('#Countryname').val(origin.countryname);

		$('#editModal').modal('show');
	});
});

function checkConames() {
	let cname = document.getElementById('Countryname').value;
	let cnameerr = document.getElementById('conameerrs');
	if (cname === "") {
		cnameerr.innerHTML = "Please enter the country name";
		return false;
	} else {
		cnameerr.innerHTML = "";
		return true;
	}
}

function validates() {
	let isCnameValid = checkConames();

	return isCnameValid; 
}

$(document).on('click', '#updateCountry', function(event) {
	event.preventDefault();

	var id = $('#countryId').val();
	var countryname = $('#Countryname').val();

	var dataToSend = {
		countryname: countryname,
	};

	if (validates()) {
		$.ajax({
			type: 'PUT',
			url: 'http://localhost:8081/product/origin/update/' + id,
			contentType: 'application/json',
			data: JSON.stringify(dataToSend),
			success: function() {
				$('#editModal').modal('hide');
				$('#update').addClass('show');
				getAllCountry();
				setTimeout(function() {
					$('#update').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				console.error('Error updating category:', error);
			}
		});
	}
});

$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deletemodal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "/product/origin/delete/" + id,
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
				console.error('Error deleting category:', error);
				$('#deletemodal').modal('hide');
			}
		});
	});
});




















$(document).delegate('.deletecountry', 'click', function() {
	var id = $(this).attr('id');
	var parent = $(this).parent().parent();
	$.ajax({
		type: "DELETE",
		url: "http://localhost:8081/country/delete/" + id,
		cache: false,
		success: function() {
			window.setTimeout(function() { location.reload() }, 500)
		},
		error: function() {
			$('#err').html('<span style=\'color:red; font-weight: bold; font-size: 30px;\'>Error deleting record').fadeIn().fadeOut(4000, function() {
				$(this).remove();
			});
		}
	});

});

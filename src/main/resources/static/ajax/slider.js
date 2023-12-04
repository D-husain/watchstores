$(document).ready(function() {
	getAllSlider();
});
function checkslidername() {
	let name = document.getElementById('name').value;
	let slidernameerr = document.getElementById('slidernameerr');
	if (name === "") {
		slidernameerr.innerHTML = "Please enter the slider name";
		return false;
	} else {
		slidernameerr.innerHTML = "";
		return true;
	}
}

function checkslidercat() {
	let scat = document.getElementById('slidercat').value;
	let scategoryerr = document.getElementById('scategoryerr');
	if (scat === "") {
		scategoryerr.innerHTML = "Please select slider category";
		return false;
	} else {
		scategoryerr.innerHTML = "";
		return true;
	}
}

function checkslidertital() {
	let tital = document.getElementById('tital').value;
	let slidertitalterr = document.getElementById('slidertitalterr');
	if (tital === "") {
		slidertitalterr.innerHTML = "Please enter slider tital";
		return false;
	} else {
		slidertitalterr.innerHTML = "";
		return true;
	}
}

function checksliderimg() {
	let fileInput = document.getElementById('image');
	let sliderimgerr = document.getElementById('sliderimgerr');
	let allowedExtensions = /(\.webp|\.jpg)$/i;

	if (fileInput.files.length === 0) {
		sliderimgerr.innerHTML = "Please select an image";
		return false;
	}
	else if (!allowedExtensions.exec(fileInput.value)) {
		sliderimgerr.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		sliderimgerr.innerHTML = "";
		return true;
	}
}


function validate() {
	let isnameValid = checkslidername();
	let iscatValid = checkslidercat();
	let istitalValid = checkslidertital();
	let issimgValid = checksliderimg();

	return isnameValid && iscatValid && istitalValid && issimgValid; // Return true only if both validations pass
}

$(document).ready(function() {
	$("#submitSlider").click(function(e) {
		e.preventDefault();
		var formData = new FormData($('#sliderForm')[0]);

		if (validate()) {
			$.ajax({
				type: 'POST',
				url: 'http://localhost:8081/admin/slider', // Update URL endpoint
				data: formData,
				processData: false,
				contentType: false,
				success: function() {
					$('#addmodal').modal('hide');
					$('#insert').addClass('show');
					getAllSlider();
					$('#sliderForm')[0].reset();
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
});

function getAllSlider() {
	$.ajax({
		url: 'http://localhost:8081/slider/data',
		method: 'GET',
		dataType: 'json',
		success: function(json) {
			var tableBody = $('table tbody');
			tableBody.empty();

			$(json).each(function(index, slider) {
				var tr = '<tr>' +
					'<td>' + (index + 1) + '</td>' +
					'<td>' + slider.tital + '</td>' +
					'<td>' + slider.category + '</td>' +
					'<td><img src="../images/slider/' + slider.image + '" alt="Image"></td>' +
					'<td>' +
					'<button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + slider.id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>' +
					'<button class=\'btn btn-danger delete\' data-id=' + slider.id + '>Delete</button>' +
					'</td>' +
					'</tr>';
				tableBody.append(tr);
			});
			getPagination('#myTable');
		},
		error: function(error) {
			alert('Error fetching slider data: ' + error);
		}
	});
}


// Event handling for edit buttons
$('table').on('click', '.edit', function() {
	var id = $(this).data('id');
	$.getJSON('http://localhost:8081/slider/' + id, function(slider) {
		$('#sliderId').val(slider.id);
		$('#slidername').val(slider.name);
		$('#slidercategory').val(slider.category);
		$('#slidertital').val(slider.tital);
		$('#sliderimg').attr('src', '../images/slider/' + slider.image);

		$('#editModal').modal('show');
	});
});

function checkslidernames() {
	let name = document.getElementById('slidername').value;
	let slidernameerr = document.getElementById('sslidernameerr');
	if (name === "") {
		slidernameerr.innerHTML = "Please enter the slider name";
		return false;
	} else {
		slidernameerr.innerHTML = "";
		return true;
	}
}

function checkslidercats() {
	let slidercat = document.getElementById('slidercategory').value;
	let scategoryerr = document.getElementById('sscategoryerr');
	if (slidercat === "") {
		scategoryerr.innerHTML = "Please select slider category";
		return false;
	} else {
		scategoryerr.innerHTML = "";
		return true;
	}
}

function checkslidertitals() {
	let tital = document.getElementById('slidertital').value;
	let slidertitalterr = document.getElementById('sslidertitalterr');
	if (tital === "") {
		slidertitalterr.innerHTML = "Please enter slider tital";
		return false;
	} else {
		slidertitalterr.innerHTML = "";
		return true;
	}
}

function checksliderimgs() {
	let fileInput = document.getElementById('sliderimg');
	let sliderimgerr = document.getElementById('ssliderimgerr');
	let allowedExtensions = /(\.webp|\.jpg)$/i;

	if (fileInput.files.length === 0) {
		sliderimgerr.innerHTML = "Please select an image";
		return false;
	}
	else if (!allowedExtensions.exec(fileInput.value)) {
		sliderimgerr.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		sliderimgerr.innerHTML = "";
		return true;
	}
}


function validates() {
	let isnameValids = checkslidernames();
	let iscatValids = checkslidercats();
	let istitalValids = checkslidertitals();
	let issimgValids = checksliderimgs();

	return isnameValids && iscatValids && istitalValids && issimgValids; // Return true only if both validations pass
}


$(document).on('click', '#updateSlider', function(event) {
	event.preventDefault();

	var id = $('#sliderId').val();
	var name = $('#slidername').val();
	var category = $('#slidercategory').val();
	var tital = $('#slidertital').val();
	var image = $('#sliderimg').val();

	// FormData to handle file uploads
	var formData = new FormData();
	formData.append('id', id);
	formData.append('slidername', name);
	formData.append('category', category);
	formData.append('tital', tital);
	formData.append('image', $('#sliderimg')[0].files[0]); // Assuming your input ID is 'img'

	if (validates()) {
		$.ajax({
			type: 'PUT',
			url: 'http://localhost:8081/slider/update/' + id,
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function() {
				$('#editModal').modal('hide');
				$('#update').addClass('show');
				getAllSlider();
				setTimeout(function() {
					$('#update').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				alert(error);
			}
		});
	}
});

// Event handling for delete buttons
$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deletemodal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8081/slider/delete/" + id,
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
















// Fetch category data and populate the table
/*$.getJSON('http://localhost:8081/slider/data', function(json) {
	var tr = [];
	for (var i = 0; i < json.length; i++) {
		tr.push('<tr>');
		tr.push('<td>' + (i + 1) + '</td>');
		tr.push('<td>' + json[i].tital + '</td>');
		tr.push('<td>' + json[i].category + '</td>');
		tr.push('<td><img src="../images/' + json[i].image + '" alt="Image"></td>');
		tr.push('<td><button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + json[i].id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>&nbsp;&nbsp;' +
			'<button class=\'btn btn-danger delete\' data-id=' + json[i].id + '>Delete</button></td>');
		tr.push('</tr>');
	}
	$('table').append($(tr.join('')));
});*/


/*$(document).delegate('.delete', 'click', function() {
	var id = $(this).attr('id');
	var parent = $(this).parent().parent();

	// Using SweetAlert for confirmation
	Swal.fire({
		title: 'Do you really want to delete the record?',
		icon: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#d33',
		cancelButtonColor: '#3085d6',
		confirmButtonText: 'Yes, delete it!'
	}).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				type: 'DELETE',
				url: 'http://localhost:8081/category/delete/' + id,
				cache: false,
				success: function() {
					parent.fadeOut('slow', function() {
						$(this).remove();
					});
					Swal.fire('Deleted!', 'The record has been deleted.', 'success');
				},
				error: function() {
					$('#err').html('<span style=\'color:red; font-weight: bold; font-size: 30px;\'>Error deleting record').fadeIn().fadeOut(4000, function() {
						$(this).remove();
					});
					Swal.fire('Error!', 'There was an error deleting the record.', 'error');
				}
			});
		}
	});
});
*/
$(document).ready(function() {
	getAllCategories();
});
function checkCname() {
	let cname = document.getElementById('cname').value;
	let cnameerr = document.getElementById('cnameerr');
	if (cname === "") {
		cnameerr.innerHTML = "Please enter the category name";
		return false;
	} else {
		cnameerr.innerHTML = "";
		return true;
	}
}

function checkCimg() {
	let fileInput = document.getElementById('cimg');
	let cimgerr = document.getElementById('cimgerr');
	let allowedExtensions = /(\.webp|\.jpg)$/i;

	if (fileInput.files.length === 0) {
		cimgerr.innerHTML = "Please select an image";
		return false;
	}
	else if (!allowedExtensions.exec(fileInput.value)) {
		cimgerr.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		cimgerr.innerHTML = "";
		return true;
	}
}


function validate() {
	let isCnameValid = checkCname();
	let isCimgValid = checkCimg();

	return isCnameValid && isCimgValid; // Return true only if both validations pass
}

$(document).ready(function() {
	$("#submitCategory").click(function(e) {
		e.preventDefault();
		var formData = new FormData($('#categoryForm')[0]);

		if (validate()) {
			$.ajax({
				type: 'POST',
				url: 'http://localhost:8081/admin/categoryadd', // Update URL endpoint
				data: formData,
				processData: false,
				contentType: false,
				success: function() {
					$('#addmodal').modal('hide');
					$('#insert').addClass('show');
					getAllCategories();
					$('#categoryForm')[0].reset();
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


// Fetch category data and populate the table
function getAllCategories() {
	$.ajax({
		url: 'http://localhost:8081/category/data',
		method: 'GET',
		dataType: 'json',
		success: function(json) {
			var tableBody = $('table tbody');
			tableBody.empty();
			$(json).each(function(index, category) {
				var tr = '<tr>' +
					'<td>' + (index + 1) + '</td>' +
					'<td>' + category.cname + '</td>' +
					'<td><img src="../images/' + category.cimg + '" alt="Image"></td>' +
					'<td><button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + category.id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>&nbsp;&nbsp;' +
					'<button class=\'btn btn-danger delete\' data-id=' + category.id + '>Delete</button></td>' +
					'</tr>';
				tableBody.append(tr);
			});
			getPagination('#myTable');
		},
		error: function(error) {
			alert('Error fetching categories: ' + error);
		}
	});
}

// Event handling for edit buttons
$('table').on('click', '.edit', function() {
	var id = $(this).data('id');
	$.getJSON('http://localhost:8081/category/' + id, function(category) {
		$('#categoryId').val(category.id);
		$('#categoryname').val(category.cname);
		$('#categoryimg').attr('src', '../images/' + category.cimg);

		$('#editModal').modal('show');
	});
});

function checkCnames() {
	let cname = document.getElementById('categoryname').value;
	let cnameerr = document.getElementById('cnameserr');
	if (cname === "") {
		cnameerr.innerHTML = "Please enter the category name";
		return false;
	} else {
		cnameerr.innerHTML = "";
		return true;
	}
}

function checkCimgs() {
	let fileInput = document.getElementById('categoryimg');
	let cimgerr = document.getElementById('cimgserr');
	let allowedExtensions = /(\.webp|\.jpg)$/i;

	if (fileInput.files.length === 0) {
		cimgerr.innerHTML = "Please select an image";
		return false;
	} else if (!allowedExtensions.exec(fileInput.value)) {
		cimgerr.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		cimgerr.innerHTML = "";
		return true;
	}
}


function validates() {
	let issCnameValid = checkCnames();
	let issCimgValid = checkCimgs();

	return issCnameValid && issCimgValid; // Return true only if both validations pass
}


$(document).on('click', '#updateCategory', function(event) {
	event.preventDefault();

	var id = $('#categoryId').val();
	var cname = $('#categoryname').val();
	var cimg = $('#categoryimg').val();

	// FormData to handle file uploads
	var formData = new FormData();
	formData.append('id', id);
	formData.append('cname', cname);
	formData.append('cimg', $('#categoryimg')[0].files[0]); // Assuming your input ID is 'cimg'

	if (validates()) {
		$.ajax({
			type: 'PUT',
			url: 'http://localhost:8081/category/update/' + id,
			data: formData,
			contentType: false,
			processData: false,
			enctype: 'multipart/form-data',
			success: function() {
				$('#editModal').modal('hide');
				$('#update').addClass('show');
				getAllCategories();
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


// Event handling for delete buttons
$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8081/category/delete/" + id,
			cache: false,
			success: function() {
				$('#deleteModal').modal('hide');
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
				$('#deleteModal').modal('hide');
			}
		});
	});
});



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
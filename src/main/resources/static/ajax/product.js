$(document).ready(function() {
	getAllProduct();
});
function checkproname() {
	let pname = document.getElementById('pname').value;
	let pronameerr = document.getElementById('pronameerr');
	if (pname === "") {
		pronameerr.innerHTML = "Please enter the product name";
		return false;
	} else {
		pronameerr.innerHTML = "";
		return true;
	}
}

function checkprocat() {
	let category = document.getElementById('category').value;
	let procaterr = document.getElementById('procaterr');
	if (category === "") {
		procaterr.innerHTML = "Please select category";
		return false;
	} else {
		procaterr.innerHTML = "";
		return true;
	}
}

function checkprobrand() {
	let category = document.getElementById('brand').value;
	let probranderr = document.getElementById('probranderr');
	if (category === "") {
		probranderr.innerHTML = "Please select barnd";
		return false;
	} else {
		probranderr.innerHTML = "";
		return true;
	}
}

function checkproimg1() {
	let img1 = document.getElementById('img1');
	let proimg1err = document.getElementById('proimg1err');
	let allowedimg1 = /(\.webp|\.jpg)$/i;

	if (img1.files.length === 0) {
		proimg1err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg1.exec(img1.value)) {
		proimg1err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg1err.innerHTML = "";
		return true;
	}
}

function checkproimg2() {
	let img2 = document.getElementById('img2');
	let proimg2err = document.getElementById('proimg2err');
	let allowedimg2 = /(\.webp|\.jpg)$/i;

	if (img2.files.length === 0) {
		proimg2err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg2.exec(img2.value)) {
		proimg2err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg2err.innerHTML = "";
		return true;
	}
}

function checkproimg3() {
	let img3 = document.getElementById('img3');
	let proimg3err = document.getElementById('proimg3err');
	let allowedimg3 = /(\.webp|\.jpg)$/i;

	if (img3.files.length === 0) {
		proimg3err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg3.exec(img3.value)) {
		proimg3err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg3err.innerHTML = "";
		return true;
	}
}

function checkproimg4() {
	let img4 = document.getElementById('img4');
	let proimg4err = document.getElementById('proimg4err');
	let allowedimg4 = /(\.webp|\.jpg)$/i;

	if (img4.files.length === 0) {
		proimg4err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg4.exec(img4.value)) {
		proimg4err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg4err.innerHTML = "";
		return true;
	}
}

function checkprodesc() {
	let description = document.getElementById('description').value;
	let prodeserr = document.getElementById('prodeserr');
	if (description === "") {
		prodeserr.innerHTML = "Please enter product description";
		return false;
	} else {
		prodeserr.innerHTML = "";
		return true;
	}
}

function checkprospec() {
	let specification = document.getElementById('specification').value;
	let prospecerr = document.getElementById('prospecerr');
	if (specification === "") {
		prospecerr.innerHTML = "Please enter product specification";
		return false;
	} else {
		prospecerr.innerHTML = "";
		return true;
	}
}

function checkprogname() {
	let gname = document.getElementById('gname').value;
	let prognameerr = document.getElementById('prognameerr');
	if (gname === "") {
		prognameerr.innerHTML = "Please enter product Generic Name";
		return false;
	} else {
		prognameerr.innerHTML = "";
		return true;
	}
}

function checkprocountry() {
	let country = document.getElementById('country').value;
	let procountryerr = document.getElementById('procountryerr');
	if (country === "") {
		procountryerr.innerHTML = "Please select country of origin";
		return false;
	} else {
		procountryerr.innerHTML = "";
		return true;
	}
}

function checkprocolor() {
	let colore = document.getElementById('colore').value;
	let procolorerr = document.getElementById('procolorerr');
	if (colore === "") {
		procolorerr.innerHTML = "Please select product color";
		return false;
	} else {
		procolorerr.innerHTML = "";
		return true;
	}
}

function checkproqty() {
	let qty = document.getElementById('qty').value;
	let progqtyerr = document.getElementById('progqtyerr');
	if (qty === "") {
		progqtyerr.innerHTML = "Please enter product qty";
		return false;
	} else {
		progqtyerr.innerHTML = "";
		return true;
	}
}

function checkproprice() {
	let price = document.getElementById('price').value;
	let propriceerr = document.getElementById('propriceerr');
	if (price === "") {
		propriceerr.innerHTML = "Please enter product price";
		return false;
	} else {
		propriceerr.innerHTML = "";
		return true;
	}
}

function checkprostock() {
	let stock = document.getElementById('stock').value;
	let prostockeerr = document.getElementById('prostockeerr');
	if (stock === "") {
		prostockeerr.innerHTML = "Please enter product stock";
		return false;
	} else {
		prostockeerr.innerHTML = "";
		return true;
	}
}


function validate() {
	let ispnameValid = checkproname();
	let isprocatValid = checkprocat();
	let isprobrandValid = checkprobrand();
	let isproimg1Valid = checkproimg1();
	let isproimg2Valid = checkproimg2();
	let isproimg3Valid = checkproimg3();
	let isproimg4Valid = checkproimg4();
	let isprodesValid =checkprodesc();
	let isprospecValid =checkprospec();	
	let isprognameValid =checkprogname();
	let isprocountryValid =checkprocountry();
	let isprocolorValid =checkprocolor();
	let isproqtyValid =checkproqty();
	let ispropriceValid =checkproprice();	
	let isProstockValid =checkprostock();		

	return ispnameValid && isprocatValid&&isprobrandValid&&isproimg1Valid&&isproimg2Valid
	&&isproimg3Valid&&isproimg4Valid&&isprodesValid&&isprospecValid&&isprognameValid&&isprocountryValid
	&&isprocolorValid&&isproqtyValid&&ispropriceValid&&isProstockValid;
}

$(document).ready(function() {
	$("#submitProduct").click(function(e) {
		e.preventDefault();
		var formData = new FormData($('#productForm')[0]);
		if (validate()) {
			$.ajax({
				type: 'POST',
				enctype: 'multipart/form-data',
				url: 'http://localhost:8081/admin/productadd', // Update URL endpoint
				data: formData,
				processData: false,
				contentType: false,
				success: function() {
					$('#addmodal').modal('hide');
					$('#insert').addClass('show');
					getAllProduct();
					$('#productForm')[0].reset();
					setTimeout(function() {
						$('#insert').removeClass('show');
					}, 3000);
				},
				error: function(error) {
					alert(error)
				}
			});
		}
	});
});

function getAllProduct() {
    $.ajax({
        url: 'http://localhost:8081/product/data',
        method: 'GET',
        dataType: 'json',
        success: function(json) {
            var tableBody = $('table tbody');
            tableBody.empty();

            $(json).each(function(index, product) {
                var truncatedName = (product.pname.length > 25) ? product.pname.substring(0, 25) + '...' : product.pname;
                var tr = '<tr>' +
                    '<td>' + (index + 1) + '</td>' +
                    '<td style="cursor:pointer" class=\'detail\' data-id=' + product.id + ' title="' + product.pname + '">' + truncatedName + '</td>' +
                    '<td>' + product.category + '</td>' +
                    '<td>' + product.availability + '</td>' +
                    '<td>' +
                    '<button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + product.id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>' +
                    '<button class=\'btn btn-danger delete\' data-id=' + product.id + '>Delete</button>' +
                    '</td>' +
                    '</tr>';
                tableBody.append(tr);
            });
            getPagination('#myTable');
        },
        error: function(error) {
            alert('Error fetching products: ' + error);
        }
    });
}



// Event handling for edit buttons
$('table').on('click', '.edit', function() {
	var id = $(this).data('id');
	$.getJSON('http://localhost:8081/product/' + id, function(product) {
		$('#productId').val(product.id);
		$('#productname').val(product.pname);
		$('#productdesc').val(product.description);
		$('#productspec').val(product.specification);
		$('#productcat').val(product.category);
		$('#productbrand').val(product.brand);
		$('#productcountry').val(product.country);
		$('#productgname').val(product.genericname);
		$('#productqty').val(product.qty);
		$('#productprice').val(product.price);
		$('#productcolore').val(product.colore);
		if (product.availability === 'yes') {
			$('#inStockRadio').prop('checked', true);
		} else if (product.availability === 'no') {
			$('#outOfStockRadio').prop('checked', true);
		}
		$('#productstock').val(product.stock);
		$('#productimg1').attr('src', '../images/product/' + product.img1);
		$('#productimg2').attr('src', '../images/product/' + product.img2);
		$('#productimg3').attr('src', '../images/product/' + product.img3);
		$('#productimg4').attr('src', '../images/product/' + product.img4);

		$('#editmodel').modal('show');
	});
});

function checkpronames() {
	let pname = document.getElementById('productname').value;
	let pronameerr = document.getElementById('pronameerrs');
	if (pname === "") {
		pronameerr.innerHTML = "Please enter the product name";
		return false;
	} else {
		pronameerr.innerHTML = "";
		return true;
	}
}

function checkprocats() {
	let category = document.getElementById('productcat').value;
	let procaterr = document.getElementById('procaterrs');
	if (category === "") {
		procaterr.innerHTML = "Please select category";
		return false;
	} else {
		procaterr.innerHTML = "";
		return true;
	}
}

function checkprobrands() {
	let category = document.getElementById('productbrand').value;
	let probranderr = document.getElementById('probranderrs');
	if (category === "") {
		probranderr.innerHTML = "Please select barnd";
		return false;
	} else {
		probranderr.innerHTML = "";
		return true;
	}
}

function checkproimgs1() {
	let img1 = document.getElementById('productimg1');
	let proimg1err = document.getElementById('proimg1errs');
	let allowedimg1 = /(\.webp|\.jpg)$/i;

	if (img1.files.length === 0) {
		proimg1err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg1.exec(img1.value)) {
		proimg1err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg1err.innerHTML = "";
		return true;
	}
}

function checkproimgs2() {
	let img2 = document.getElementById('productimg2');
	let proimg2err = document.getElementById('proimg2errs');
	let allowedimg2 = /(\.webp|\.jpg)$/i;

	if (img2.files.length === 0) {
		proimg2err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg2.exec(img2.value)) {
		proimg2err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg2err.innerHTML = "";
		return true;
	}
}

function checkproimgs3() {
	let img3 = document.getElementById('productimg3');
	let proimg3err = document.getElementById('proimg3errs');
	let allowedimg3 = /(\.webp|\.jpg)$/i;

	if (img3.files.length === 0) {
		proimg3err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg3.exec(img3.value)) {
		proimg3err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg3err.innerHTML = "";
		return true;
	}
}

function checkproimgs4() {
	let img4 = document.getElementById('productimg4');
	let proimg4err = document.getElementById('proimg4errs');
	let allowedimg4 = /(\.webp|\.jpg)$/i;

	if (img4.files.length === 0) {
		proimg4err.innerHTML = "Please select image";
		return false;
	}
	else if (!allowedimg4.exec(img4.value)) {
		proimg4err.innerHTML = "Please select a .webp or .jpg file";
		return false;
	} else {
		proimg4err.innerHTML = "";
		return true;
	}
}

function checkprodescs() {
	let description = document.getElementById('productdesc').value;
	let prodeserr = document.getElementById('prodeserrs');
	if (description === "") {
		prodeserr.innerHTML = "Please enter product description";
		return false;
	} else {
		prodeserr.innerHTML = "";
		return true;
	}
}

function checkprospecs() {
	let specification = document.getElementById('productspec').value;
	let prospecerr = document.getElementById('prospecerrs');
	if (specification === "") {
		prospecerr.innerHTML = "Please enter product specification";
		return false;
	} else {
		prospecerr.innerHTML = "";
		return true;
	}
}

function checkprognames() {
	let gname = document.getElementById('productgname').value;
	let prognameerr = document.getElementById('prognameerrs');
	if (gname === "") {
		prognameerr.innerHTML = "Please enter product Generic Name";
		return false;
	} else {
		prognameerr.innerHTML = "";
		return true;
	}
}

function checkprocountrys() {
	let country = document.getElementById('productcountry').value;
	let procountryerr = document.getElementById('procountryerrs');
	if (country === "") {
		procountryerr.innerHTML = "Please select country of origin";
		return false;
	} else {
		procountryerr.innerHTML = "";
		return true;
	}
}

function checkprocolors() {
	let colore = document.getElementById('productcolore').value;
	let procolorerr = document.getElementById('procolorerrs');
	if (colore === "") {
		procolorerr.innerHTML = "Please select product color";
		return false;
	} else {
		procolorerr.innerHTML = "";
		return true;
	}
}

function checkproqtys() {
	let qty = document.getElementById('productqty').value;
	let progqtyerr = document.getElementById('progqtyerrs');
	let q = /^[0-9]+$/
	if (qty === "") {
		progqtyerr.innerHTML = "Please enter product qty";
		return false;
	} 
	else if (!qty.match(q)) {
		progqtyerr.innerHTML = "Please only enter numeric characters only for your Qty!";
		return false;
	}else {
		progqtyerr.innerHTML = "";
		return true;
	}
}

function checkproprices() {
	let price = document.getElementById('productprice').value;
	let propriceerr = document.getElementById('propriceerrs');
	let p = /^[0-9]+$/
	if (price === "") {
		propriceerr.innerHTML = "Please select product color";
		return false;
	} else if (!price.match(p)) {
		propriceerr.innerHTML = "Please only enter numeric characters only for your Price!";
		return false;
	}
	else {
		propriceerr.innerHTML = "";
		return true;
	}
}

function checkprostocks() {
	let stock = document.getElementById('productstock').value;
	let prostockerr = document.getElementById('prostockerrs');
	let s = /^[0-9]+$/
	if (stock === "") {
		prostockerr.innerHTML = "Please enter product stock";
		return false;
	} else if (!stock.match(s)) {
		prostockerr.innerHTML = "Please only enter numeric characters only for your Stock!";
		return false;
	}
	else {
		prostockerr.innerHTML = "";
		return true;
	}
}


function validates() {
	let ispnameValid = checkpronames();
	let isprocatValid = checkprocats();
	let isprobrandValid = checkprobrands();
	let isproimg1Valid = checkproimgs1();
	let isproimg2Valid = checkproimgs2();
	let isproimg3Valid = checkproimgs3();
	let isproimg4Valid = checkproimgs4();
	let isprodesValid =checkprodescs();
	let isprospecValid =checkprospecs();	
	let isprognameValid =checkprognames();
	let isprocountryValid =checkprocountrys();
	let isprocolorValid =checkprocolors();
	let isproqtyValid =checkproqtys();
	let ispropriceValid =checkproprices();	
	let isprostockValid =checkprostocks();		

	return ispnameValid && isprocatValid&&isprobrandValid&&isproimg1Valid&&isproimg2Valid
	&&isproimg3Valid&&isproimg4Valid&&isprodesValid&&isprospecValid&&isprognameValid&&isprocountryValid
	&&isprocolorValid&&isproqtyValid&&ispropriceValid&&isprostockValid;
}

$(document).on('click', '#updateProduct', function(event) {
	event.preventDefault();

	var id = $('#productId').val();
	var pname = $('#productname').val();
	var description = $('#productdesc').val();
	var specification = $('#productspec').val();
	var category = $('#productcat').val();
	var brand = $('#productbrand').val();
	var country = $('#productcountry').val();
	var genericname = $('#productgname').val();
	var qty = $('#productqty').val();
	var price = $('#productprice').val();
	var colore = $('#productcolore').val();
	var stock = $('#productstock').val();

	var availabilityRadio = $('input[name="availability"]:checked');
	var availability = (availabilityRadio.length > 0) ? availabilityRadio.val() : null;

	var img1 = $('#productimg1').val();
	var img2 = $('#productimg2').val();
	var img3 = $('#productimg3').val();
	var img4 = $('#productimg4').val();

	// Creating FormData object to handle file uploads
	var formData = new FormData();
	formData.append('id', id);
	formData.append('pname', pname);
	formData.append('description', description);
	formData.append('specification', specification);
	formData.append('category', category);
	formData.append('brand', brand);
	formData.append('country', country);
	formData.append('gname', genericname);
	formData.append('qty', qty);
	formData.append('price', price);
	formData.append('colore', colore);
	if (availability !== null) {
		formData.append('availability', availability);
	}
	formData.append('stock', stock);
	formData.append('img1', $('#productimg1')[0].files[0]); // Assuming img1 is an input for image file
	formData.append('img2', $('#productimg2')[0].files[0]);
	formData.append('img3', $('#productimg3')[0].files[0]);
	formData.append('img4', $('#productimg4')[0].files[0]);

	if (validates()) {
		$.ajax({
			type: 'PUT',
			url: 'http://localhost:8081/product/update/' + id,
			data: formData,
			contentType: false,
			processData: false,
			success: function() {
				if (availability === 'yes') {
					$('#inStockRadio').prop('checked', true);
				} else if (availability === 'no') {
					$('#outOfStockRadio').prop('checked', true);
				}
				$('#editmodel').modal('hide');
				$('#productimg1').val('');
				$('#productimg2').val('');
				$('#productimg3').val('');
				$('#productimg4').val('');
				$('#update').addClass('show');
				getAllProduct();
				setTimeout(function() {
					$('#update').removeClass('show');
				}, 3000);
			},
			error: function(error) {
				alert(error)
			}
		});
	}
});


// Event handling for detail buttons
$('table').on('click', '.detail', function() {
	var id = $(this).data('id');
	$.getJSON('http://localhost:8081/product/' + id, function(product) {
		//$('#proId').attr('data-id', product.id);
		$('#proname').text(product.pname);
		$('#prodesc').text(product.description);
		$('#prospec').text(product.specification);
		$('#procat').text(product.category);
		$('#probrand').text(product.brand);
		$('#procountry').text(product.country);
		$('#prpgname').text(product.genericname);
		$('#proqty').text(product.qty);
		$('#proprice').text(product.price);
		$('#procolore').text(product.colore);
		$('#proavailability').text(product.availability);
		$('#proimg1').attr('src', '../images/product/' + product.img1);
		$('#proimg2').attr('src', '../images/product/' + product.img2);
		$('#promg3').attr('src', '../images/product/' + product.img3);
		$('#proimg4').attr('src', '../images//product' + product.img4);

		$('#detailmodal').modal('show');
	});
});


$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8081/product/delete/" + id,
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



/*$(document).ready(function() {
	$.getJSON('/product/data', function(json) {
		if (json.length === 0) {
			$('#myTable').html('<tbody><tr><th colspan="5"><center>No Products Yet</center></th></tr></tbody>');
		} else {
			var tr = [];
			for (var i = 0; i < json.length; i++) {
				tr.push('<tr>');
				tr.push('<td>' + (i + 1) + '</td>');
				tr.push('<td style="cursor:pointer" class=\'detail\' data-id=' + json[i].id + '>' + json[i].pname + '</td>');
				tr.push('<td>' + json[i].category + '</td>');
				tr.push('<td>' + json[i].availability + '</td>');
				tr.push('<td><button class=\'btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none edit\' data-id="' + json[i].id + '">Edit<i class="typcn typcn-pencil btn-icon-append"></i></button>&nbsp;&nbsp;' +
					'<button class=\'btn btn-danger delete\' data-id=' + json[i].id + '>Delete</button></td>');
				tr.push('</tr>');
			}
			$('#myTable').append($(tr.join('')));
		}
	});
});*/


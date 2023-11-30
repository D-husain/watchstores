$(document).ready(function() {
	getAllCoupon();
});
function checkcouponcode() {
	let couponcode = document.getElementById('couponcode').value;
	let couponcodeerr = document.getElementById('couponcodeerr');

	if (couponcode === "") {
		couponcodeerr.innerHTML = "Please enter the coupon code";
		return false;
	}
	else {
		couponcodeerr.innerHTML = "";
		return true;
	}
}

function checkcoupontype() {
	let coupontype = document.getElementById('coupontype').value;
	let coupontypeerr = document.getElementById('coupontypeerr');

	if (coupontype === "") {
		coupontypeerr.innerHTML = "Please select the coupon type";
		return false;
	}
	else {
		coupontypeerr.innerHTML = "";
		return true;
	}
}
function checkcouponval() {
	let m = /^(\d*([.,](?=\d{1}))?\d+)+((?!\2)[.,]\d\d)?$/
	let couponval = document.getElementById('couponval').value;
	let couponvalerr = document.getElementById('couponvalerr');

	if (couponval === "") {
		couponvalerr.innerHTML = "Please enter the coupon value";
		return false;
	}
	else if (!couponval.match(m)) {
		couponvalerr.innerHTML = "Coupon value can only have numbers";
		return false;
	}
	else {
		couponvalerr.innerHTML = "";
		return true;
	}
}
function checkmincartvalue() {
	let m = /^(\d*([.,](?=\d{1}))?\d+)+((?!\2)[.,]\d\d)?$/
	let mincartvalue = document.getElementById('mincartvalue').value;
	let mincartvalueerr = document.getElementById('mincartvalueerr');

	if (mincartvalue === "") {
		mincartvalueerr.innerHTML = "Please enter the minimum cart value";
		return false;
	}
	else if (!mincartvalue.match(m)) {
		mincartvalueerr.innerHTML = "Minimum cart value can only have numbers";
		return false;
	}
	else {
		mincartvalueerr.innerHTML = "";
		return true;
	}
}

function validate() {
	let iscodeValid = checkcouponcode();
	let istypeValid = checkcoupontype();
	let isvalValid = checkcouponval();
	let isvalueValid = checkmincartvalue();

	return iscodeValid && istypeValid && isvalValid && isvalueValid; // Return true only if both validations pass
}

$(document).delegate('#addcoupon', 'click', function(event) {
	event.preventDefault();

	var code = $('#couponcode').val();
	var type = $('#coupontype').val();
	var value = $('#couponval').val();
	var mvalue = $('#mincartvalue').val();

	if (validate()) {
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "http://localhost:8081/coupon/upadte/save",
			data: JSON.stringify({ 'code': code, 'type': type, 'value': value, 'mvalue': mvalue, }),
			cache: false,
			success: function() {
				$('#addmodal').modal('hide');
				$('#couponcode').val('');
				$('#coupontype').val('');
				$('#couponval').val('');
				$('#mincartvalue').val('');
				$('#insert').addClass('show');
				getAllCoupon();
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

function getAllCoupon() {
	$.ajax({
		url: 'http://localhost:8081/coupon/data',
		method: 'GET',
		dataType: 'json',
		success: function(json) {
			var tableBody = $('table tbody');
			tableBody.empty();

			$(json).each(function(index, coupon) {
				var tr = '<tr>' +
					'<td>' + (index + 1) + '</td>' +
					'<td>' + coupon.code + '</td>' +
					'<td>' + coupon.type + '</td>' +
					'<td>' + coupon.value + '</td>' +
					'<td>' + coupon.mvalue + '</td>' +
					'<td><button class=\'btn btn-danger delete\' data-id=' + coupon.id + '>Delete</button></td>' +
					'</tr>';
				tableBody.append(tr);
			});
			getPagination('#myTable');
		},
		error: function(error) {
			alert('Error fetching coupons: ' + error);
		}
	});
}


$('table').on('click', '.delete', function() {
	var id = $(this).data('id');

	$('#deleteModal').modal('show');

	// Confirm delete action
	$('#confirmDelete').on('click', function() {
		$.ajax({
			type: "DELETE",
			url: "http://localhost:8081/coupon/delete/" + id,
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



/*$.getJSON('http://localhost:8081/coupon/data', function(json) {
	var tr = [];
	var count = 1;
	for (var i = 0; i < json.length; i++) {
		tr.push('<tr>');
		tr.push('<td>' + count + '</td>');
		tr.push('<td>' + json[i].code + '</td>');
		tr.push('<td>' + json[i].type + '</td>');
		tr.push('<td>' + json[i].value + '</td>');
		tr.push('<td>' + json[i].mvalue + '</td>');
		tr.push('<td><button class=\'btn btn-danger delete\' data-id=' + json[i].id + '>Delete</button></td>');
		tr.push('</tr>');
		count++;
	}
	$('table').append($(tr.join('')));
});*/
$.getJSON('http://localhost:8081/user/data', function(json) {
		var tr = [];
		var count=1;
		for (var i = 0; i < json.length; i++) {
			tr.push('<tr>');
			tr.push('<td>' + count + '</td>');
			tr.push('<td>' + json[i].fname +' '+ json[i].lname + '</td>');
			tr.push('<td>' + json[i].email + '</td>');
			tr.push('<td>' + json[i].contact + '</td>');
			tr.push('<td><button data-toggle="modal" data-target="#detailmodal' + json[i].id + '" class="btn btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none">Details <i class="typcn typcn-eye btn-icon-append"></i></button></td>');
			tr.push('</tr>');
			count++;
		}
		$('table').append($(tr.join('')));
	});
		
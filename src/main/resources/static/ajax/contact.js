$(document).ready(function() {
	getAllinquirie();
});
function getAllinquirie(){
$.ajax({
        url: 'http://localhost:8081/contact/data',
        method: 'GET',
        dataType: 'json',
        success: function(json) {
            var tableBody = $('table tbody');
            tableBody.empty();
            $(json).each(function(index, contact) {
                var tr = '<tr>' +
                    '<td>' + (index + 1) + '</td>' +
                    '<td>' + contact.name + '</td>' +
                    '<td>' + contact.email + '</td>' +
                    '<td><button class=\'btn btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none reply\' data-id=' + contact.id + '>Reply<i class="typcn typcn-mail btn-icon-append"></i></button></td>'
                    '</tr>';
                tableBody.append(tr);
            });
            getPagination('#myTable');
        },
        error: function(error) {
            alert('Error fetching inquiries: ' + error);
        }
    });
}
$('table').on('click', '.reply', function() {
    var id = $(this).data('id');
    $.getJSON('http://localhost:8081/reply/' + id, function(reply) {
        if (reply !== null && reply.replay !== null) {
            $('#inquirerMessage').text(reply.message);
            $('#yourReply').text(reply.replay);
            $('#replyDetailsSection').show();
            $('#replyFormSection').hide();
        } else {
            $('#inquirerId').val(reply.id);
            $('#inquirerMessages').val(reply.message);
            $('#inquirerEmail').val(reply.email);
            $('#replyDetailsSection').hide();
            $('#replyFormSection').show();
        }
        $('#replyModal').modal('show');
    });
});

$(document).ready(function() {
    $('#replyForm').submit(function(e) {
        e.preventDefault(); 
                
        var formData = {
            contactId: $('#inquirerId').val(),
            reply: $('#inquirerReplay').val(),
            email: $('#inquirerEmail').val(),
            message: $('#inquirerMessages').val()
        };
        
        $.ajax({
            type: 'POST',
            url: '/user/replyMessage',
            data: formData,
			success: function() {
				$('#replyModal').modal('hide');
				$('#replay').addClass('show');
				getAllinquirie();
				setTimeout(function() {
					$('#replay').removeClass('show');
				}, 3000);
			},
            error: function(xhr, status, error){
                console.error(xhr, status, error);
                $('#replyModal').modal('hide');
                $('#replay').addClass('show');
                setTimeout(function() {
					$('#replay').removeClass('show');
				}, 3000);
            }
        });
    });
});



/*function getAllCategories() {
$.getJSON('http://localhost:8081/contact/data', function(json) {
	var tr = [];
	var count = 1;
	for (var i = 0; i < json.length; i++) {
		tr.push('<tr>');
		tr.push('<td>' + count + '</td>');
		tr.push('<td>' + json[i].name + '</td>');
		tr.push('<td>' + json[i].email + '</td>');
		tr.push('<td><button class=\'btn btn-sm btn-secondary btn-icon-text mr-2 text-decoration-none reply\' data-id=' + json[i].id + '>Reply<i class="typcn typcn-mail btn-icon-append"></i></button></td>');
		tr.push('</tr>');
		count++;
	}
	$('table').append($(tr.join('')));
});
}*/

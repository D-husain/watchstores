function checkfname() {
	let fname = document.getElementById('fname').value;
	let fnameerr = document.getElementById('fnameerr');
	let f = /^[a-zA-Z]+$/
	if (fname === "") {
		fnameerr.innerHTML = "Please enter the first name";
		return false;
	}else if(!fname.match(f)){
		fnameerr.innerHTML = "First name must be in alphabets only";
		return false;
	}else {
		fnameerr.innerHTML = "";
		return true;
	}
}

function checklname() {
	let lname = document.getElementById('lname').value;
	let lnameerr = document.getElementById('lnameerr');
	let l = /^[a-zA-Z]+$/
	if (lname === "") {
		lnameerr.innerHTML = "Please enter the last name";
		return false;
	}else if(!lname.match(l)){
		lnameerr.innerHTML = "Last name must be in alphabets only";
		return false;
	}else {
		lnameerr.innerHTML = "";
		return true;
	}
}

function checkemail() {
	let email = document.getElementById('email').value;
	let emailerr = document.getElementById('emailerr');
	let ep = /[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
	
	if (email === "") {
		emailerr.innerHTML = "Please enter the email address";
		return false;
	}else if(!email.match(ep)){
		emailerr.innerHTML = "Invalid email address";
		return false;
	}
	 else {
		emailerr.innerHTML = "";
		return true;
	}
}

function checkphone() {
	let contact = document.getElementById('contact').value;
	let phoneerr = document.getElementById('phoneerr');
	let p=/^(0|91)?[6-9][0-9]{9}$/;
	if (contact === "") {
		phoneerr.innerHTML = "Please enter the contact";
		return false;
	}else if(!contact.match(p)){
		phoneerr.innerHTML = "Invalid contact number";
		return false;
	}
	else {
		phoneerr.innerHTML = "";
		return true;
	}
}

function checkadd() {
	let address = document.getElementById('address').value;
	let adderr = document.getElementById('adderr');
	let a = /[a-zA-Z0-9@,]+$/
	if (address === "") {
		adderr.innerHTML = "Please enter the address";
		return false;
	} else {
		adderr.innerHTML = "";
		return true;
	}
}

function checkcountry() {
	let country = document.getElementById('country').value;
	let countryerr = document.getElementById('countryerr');
	if (country === "") {
		countryerr.innerHTML = "Please select country";
		return false;
	} else {
		countryerr.innerHTML = "";
		return true;
	}
}

function checkstate() {
	let state = document.getElementById('state').value;
	let stateerr = document.getElementById('stateerr');
	if (state === "") {
		stateerr.innerHTML = "Please select state";
		return false;
	} else {
		stateerr.innerHTML = "";
		return true;
	}
}

function checkcity() {
	let city = document.getElementById('city').value;
	let cityerr = document.getElementById('cityerr');
	if (city === "") {
		cityerr.innerHTML = "Please select city";
		return false;
	} else {
		cityerr.innerHTML = "";
		return true;
	}
}

function checkzip() {
	let zip = document.getElementById('zip').value;
	let ziperr = document.getElementById('ziperr');
	let lenc = zip.length;
	if (zip === "") {
		ziperr.innerHTML = "Please enter the zip";
		return false;
	}else if (isNaN(zip)){
		ziperr.innerHTML = "Zip code must be in numeric only";
		return false;
	}else if (lenc < 6) {
		ziperr.innerHTML = "Invalid zip code";
		return false;
	}else if (lenc > 6){
		ziperr.innerHTML = "Invalid zip code";
		return false;
	} else {
		ziperr.innerHTML = "";
		return true;
	}
}

function checkpassword() {
	let password = document.getElementById('password').value;
	let passeerr = document.getElementById('passeerr');
	let len = password.length;
	let pass = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+~`|}{\]\[:";'<>?,.])[A-Za-z\d!@#$%^&*()_+~`|}{\]\[:";'<>?,.]{8,}$/;

	if (password === "") {
		passeerr.innerHTML = "Please enter the password";
		return false;
	}else if (len < 8){
		passeerr.innerHTML = "Your password must be at least 8 characters";
		return false;
	}else if(!password.match(pass)){
		passeerr.innerHTML = "Your password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one number, and one special character.";
		return false;
	}else if(password.length < 8){
		passeerr.innerHTML = "Your password must be at least 8 characters";
		return false;
	}else {
		passeerr.innerHTML = "";
		return true;
	}
}


function validate() {
	let isfnameValid = checkfname();
	let islastValid = checklname();
	let isemailValid = checkemail();
	let iscontactValid = checkphone();
	let isaddressValid = checkadd();
	let iscountryValid = checkcountry();
	let isstateValid = checkstate();
	let iscityValid = checkcity();
	let iszipValid = checkzip();
	let ispassValid = checkpassword();

	return isfnameValid && islastValid && isemailValid && iscontactValid && isaddressValid
		&& iscountryValid && isstateValid && iscityValid && iszipValid && ispassValid;
}

/*$(document).delegate('#register_user', 'click', function(event) {
	event.preventDefault();

	var fname = $('#fname').val();
	var lname = $('#lname').val();
	var email = $('#email').val();
	var contact = $('#contact').val();
	var address = $('#address').val();
	var country = $('#country').val();
	var state = $('#state').val();
	var city = $('#city').val();
	var zip = $('#zip').val();
	var password = $('#password').val();

	if (validate()) {
		$.ajax({
			type: "POST",
			contentType: "application/json; charset=utf-8",
			url: "http://localhost:8081/user/register",
			data: JSON.stringify({
				'fname': fname, 'lname': lname, 'email': email, 'contact': contact,
				'address': address, 'country': country, 'state': state, 'city': city, 'zip': zip, 'password': password
			}),
			cache: false,
			success: function(result) {
				window.setTimeout(function() { window.location.href = "/logins"; }, 500)
				$('#userregister').addClass('show');
			},
			error: function(err) {
				$("#msg").html("<span style='color: red'>Name is required</span>");
			}
		});
	}
});*/


$(document).ready(function() {
    // Initialize nice-select on page load
    $('select').niceSelect();

    // Load countries on page load
    $.ajax({
        url: "/countries",
        type: "GET",
        success: function(data) {
            let countryOptions = '<option value="">Select Country</option>';
            data.forEach(function(country) {
                countryOptions += `<option value="${country.countryname}">${country.countryname}</option>`;
            });
            $('#country').html(countryOptions).niceSelect('update'); // Update nice-select after changing options
        },
        error: function(xhr, status, error) {
            $('#countryerr').text('Failed to load countries. Please try again.');
        }
    });

    // On country change, load states
    $('#country').on('change', function() {
        const countryName = $(this).val();
        if (countryName) {
            $.ajax({
                url: `/states/${countryName}`,
                type: "GET",
                success: function(data) {
                    let stateOptions = '<option value="">Select State</option>';
                    data.forEach(function(state) {
                        stateOptions += `<option value="${state.name}">${state.name}</option>`;
                    });
                    $('#state').html(stateOptions).niceSelect('update'); // Update nice-select
                    $('#city').html('<option value="">Select City</option>').niceSelect('update'); // Reset and update city dropdown
                    $('#stateerr').text(''); // Clear any previous error message
                },
                error: function(xhr, status, error) {
                    $('#stateerr').text('Failed to load states. Please try again.');
                }
            });
        } else {
            $('#state').html('<option value="">Select State</option>').niceSelect('update');
            $('#city').html('<option value="">Select City</option>').niceSelect('update');
        }
    });

    // On state change, load cities
    $('#state').on('change', function() {
        const stateName = $(this).val();
        if (stateName) {
            $.ajax({
                url: `/cities/${stateName}`,
                type: "GET",
                success: function(data) {
                    let cityOptions = '<option value="">Select City</option>';
                    data.forEach(function(city) {
                        cityOptions += `<option value="${city.city}">${city.city}</option>`;
                    });
                    $('#city').html(cityOptions).niceSelect('update'); // Update nice-select
                    $('#cityerr').text(''); // Clear any previous error message
                },
                error: function(xhr, status, error) {
                    $('#cityerr').text('Failed to load cities. Please try again.');
                }
            });
        } else {
            $('#city').html('<option value="">Select City</option>').niceSelect('update');
        }
    });
});
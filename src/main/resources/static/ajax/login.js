var name = document.getElementById('name').value;
var password = document.getElementById('password').value;

//checkname
function checkname() {
	var name = document.getElementById('name').value;
	var m = /^[a-zA-Z]+$/;
	if (name == '') {
		document.getElementById('nameerr').style.display = 'block';
		document.getElementById('nameerr').innerHTML = 'Please enter username';
	}
	else if (!name.match(m)) {
		document.getElementById('nameerr').style.display = 'block';
		document.getElementById('nameerr').innerHTML = 'Invalid username';
	}
	else {
		document.getElementById('nameerr').style.display = 'none';
		document.getElementById('nameerr').innerHTML = '';
	}
}
//checkpass
function checkpassword() {
	var password = document.getElementById('password').value;
	var pass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
	if (password == '') {
		document.getElementById('passeerr').style.display = 'block';
		document.getElementById('passeerr').innerHTML = 'Please enter password';
	}
	else if (!password.match(pass)) {
		document.getElementById('passeerr').style.display = 'block';
		document.getElementById('passeerr').innerHTML = 'Invalid password';
	}
	else {
		document.getElementById('passeerr').style.display = 'none';
		document.getElementById('passeerr').innerHTML = '';
	}
}
//checkvalidate
function checkvalidate() {
	var name = document.getElementById('name').value;
	var password = document.getElementById('password').value;
	var m = /^[a-zA-Z]+$/;
	var pass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;

	if (name == "" && password == "") {
		document.getElementById('nameerr').style.display = 'block';
		document.getElementById('nameerr').innerHTML = 'Please enter username';
		document.getElementById('passeerr').style.display = 'block';
		document.getElementById('passeerr').innerHTML = 'Please enter password';
		return false;
	}
	else if (name == "") {
		document.getElementById('nameerr').style.display = 'block';
		document.getElementById('nameerr').innerHTML = 'please enter username';
		document.getElementById('passeerr').style.display = 'none';
		document.getElementById('passeerr').innerHTML = '';
		return false;
	}
	else if (!name.match(m)) {
		document.getElementById('nameerr').style.display = 'block';
		document.getElementById('nameerr').innerHTML = 'Invalid username';
		document.getElementById('passeerr').style.display = 'none';
		document.getElementById('passeerr').innerHTML = '';
		return false;
	}
	else if (password == "") {
		document.getElementById('nameerr').style.display = 'none';
		document.getElementById('nameerr').innerHTML = '';
		document.getElementById('passeerr').style.display = 'block';
		document.getElementById('passeerr').innerHTML = 'please enter password';
		return false;
	}
	else if (!password.match(pass)) {
		document.getElementById('nameerr').style.display = 'none';
		document.getElementById('nameerr').innerHTML = '';
		document.getElementById('passeerr').style.display = 'block';
		document.getElementById('passeerr').innerHTML = 'Invalid password';
		return false;
	}
	else {
		return true;
	}
}
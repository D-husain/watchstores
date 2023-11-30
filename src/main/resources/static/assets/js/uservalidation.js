    var name = document.getElementById('name').value;
    var email = document.getElementById('email').value;
    var phone = document.getElementById('phone').value;
    var password = document.getElementById('password').value;
    function checkname(){
    	var name = document.getElementById('name').value;
    	var m = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/
    	if(name == ''){
    		document.getElementById('nameerr').style.display = 'block';
    		document.getElementById('nameerr').innerHTML = 'Please enter username';
    	}
    	else if(!name.match(m)){
    		document.getElementById('nameerr').style.display = 'block';
    		document.getElementById('nameerr').innerHTML = 'Invalid username';
    	}
    	else{
    		document.getElementById('nameerr').style.display = 'none';
    		document.getElementById('nameerr').innerHTML = '';
    	}
    }
    function checkemail(){
    	var email = document.getElementById('email').value;
    	var e= /r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'/
    	if(email == ''){
    		document.getElementById('emailerr').style.display = 'block';
    		document.getElementById('emailerr').innerHTML = 'Please enter email address';
    	}
    	else if (!email.match(e)) {
    		document.getElementById('emailerr').style.display = 'block';
    		document.getElementById('emailerr').innerHTML = 'Invalid email address';
		}
    	else{
    		document.getElementById('emailerr').style.display = 'none';
    		document.getElementById('emailerr').innerHTML = '';
    	}
    }
    function checkphone(){
    	var phone = document.getElementById('phone').value;
    	var p= /^\d+$/
    	if(phone == ''){
    		document.getElementById('phoneerr').style.display = 'block';
    		document.getElementById('phoneerr').innerHTML = 'Please enter contact';
    	}
    	else if(!phone.match(p)){
    		document.getElementById('phoneerr').style.display = 'block';
    		document.getElementById('phoneerr').innerHTML = 'Invalid contact number';
    	}
    	else{
    		document.getElementById('phoneerr').style.display = 'none';
    		document.getElementById('phoneerr').innerHTML = '';
    	}
    }
    function checkpassword(){
    	var passw = document.getElementById('password').value;
    	var pass=/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/
    	if(passw == ''){
    		document.getElementById('passeerr').style.display = 'block';
    		document.getElementById('passeerr').innerHTML = 'Please enter Password';
    	}
    	else if(!passw.match(pass)){
     		document.getElementById('passeerr').style.display = 'block';
     		document.getElementById('passeerr').innerHTML = 'Invalid Password';
     	}
    	else{
    		document.getElementById('passeerr').style.display = 'none';
    		document.getElementById('passeerr').innerHTML = '';
    	}
    }
    function checkvalidate(){
    	var name = document.getElementById('name').value;
    	var email = document.getElementById('email').value;
    	var phone = document.getElementById('phone').value;
    	var password = document.getElementById('password').value;
    	

	    var m = /^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$/;
	    var pass = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/;
	    var e = /r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'/;
	    var p = /^\d+$/;
        		 
        if(name == "" && email == "" && phone == "" && password == ""){
    		document.getElementById('nameerr').style.display = 'block';
    		document.getElementById('nameerr').innerHTML = 'Please enter username';
    		document.getElementById('emailerr').style.display = 'block';
    		document.getElementById('emailerr').innerHTML = 'Please enter email address';
    		document.getElementById('phoneerr').style.display = 'block';
    		document.getElementById('phoneerr').innerHTML = 'Please enter contact number';
    		document.getElementById('passeerr').style.display = 'block';
    		document.getElementById('passeerr').innerHTML = 'Please enter password';
    		return false;
    	}
    	else if(!name.match(m)){
    		document.getElementById('nameerr').style.display = 'block';
    		document.getElementById('nameerr').innerHTML = 'Invalid username';
    		document.getElementById('emailerr').style.display = 'none';
    		document.getElementById('emailerr').innerHTML = '';
    		document.getElementById('phoneerr').style.display = 'none';
    		document.getElementById('phoneerr').innerHTML = '';
    		document.getElementById('passeerr').style.display = 'none';
    		document.getElementById('passeerr').innerHTML = '';
    		return false;
    	}
    	else if(!passw.match(pass)){
    		document.getElementById('nameerr').style.display = 'none';
    		document.getElementById('nameerr').innerHTML = '';
    		document.getElementById('emailerr').style.display = 'none';
    		document.getElementById('emailerr').innerHTML = '';
    		document.getElementById('phoneerr').style.display = 'none';
    		document.getElementById('phoneerr').innerHTML = '';
    		document.getElementById('passeerr').style.display = 'block';
    		document.getElementById('passeerr').innerHTML = 'Invalid password';
     		return false;
     	}
    	else if(!email.match(e)){
    		document.getElementById('nameerr').style.display = 'none';
    		document.getElementById('nameerr').innerHTML = '';
    		document.getElementById('emailerr').style.display = 'block';
    		document.getElementById('emailerr').innerHTML = 'Invalid email address';
    		document.getElementById('phoneerr').style.display = 'none';
    		document.getElementById('phoneerr').innerHTML = '';
    		document.getElementById('passeerr').style.display = 'none';
    		document.getElementById('passeerr').innerHTML = '';
     		return false;
     	}
    	else if(!phone.match(p)){
    		document.getElementById('nameerr').style.display = 'none';
    		document.getElementById('nameerr').innerHTML = '';
    		document.getElementById('emailerr').style.display = 'none';
    		document.getElementById('emailerr').innerHTML = '';
    		document.getElementById('phoneerr').style.display = 'block';
    		document.getElementById('phoneerr').innerHTML = 'Invalid contact number';
    		document.getElementById('passeerr').style.display = 'none';
    		document.getElementById('passeerr').innerHTML = '';
     		return false;
     	}
    	else{
    		return true;	
    	}
    	
    }
    //(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}
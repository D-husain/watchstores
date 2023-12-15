document.addEventListener('DOMContentLoaded', function() {
	setTimeout(function() {
		var errorToast = document.getElementById('errorToast');
		var bsToast = new bootstrap.Toast(errorToast);
		bsToast.hide();
	}, 1500); // Adjust the time in milliseconds as needed
});

/*document.addEventListener('DOMContentLoaded', function () {
        var toastContainer = document.querySelector('.toast-container');
        var toast = document.querySelector('.toast');

        // Adjust the position when the window is scrolled
        window.addEventListener('scroll', function () {
            var scrollTop = window.scrollY;
            toastContainer.style.bottom = scrollTop + 'px';
        });
    });*/
let inactivityTimer;

function resetTimer() {
    clearTimeout(inactivityTimer);
    inactivityTimer = setTimeout(logoutUser, 60 * 1000); // 10 minutes in milliseconds //10 * 60 * 1000
}

// Reset the timer on user interaction (e.g., mouse move, key press)
document.addEventListener('mousemove', resetTimer);
document.addEventListener('keypress', resetTimer);

function logoutUser() {
	alert("You have been logged out due to inactivity.");
    window.location.href = "/user-logout"; // Redirect to logout endpoint
}

// Start the initial timer on login or page load
resetTimer();

package com.example.demo.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.User;
import com.example.demo.repository.PasswordResetTokenRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.PasswordResetService;

@RestController
@RequestMapping("/user")
public class PasswordResetController {
	
	@Autowired private PasswordResetService passwordResetService;
	@Autowired private UserRepository userRepository;
	@Autowired private PasswordResetTokenRepository tokenrepo;

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		User user = userRepository.findByEmail(email);

		if (user == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		}

		String token = UUID.randomUUID().toString();
		passwordResetService.createPasswordResetTokenForUser(user, token);
		passwordResetService.sendResetPasswordEmail(user, token);

		return ResponseEntity.ok("Password reset instructions sent to your email");
	}

	// Endpoint for resetting password using token
	@GetMapping("/reset-password")
	public ModelAndView showResetPasswordForm(@RequestParam String token) {
		ModelAndView modelAndView = new ModelAndView();

		PasswordResetToken passwordResetToken = tokenrepo.findByToken(token);
		if (passwordResetToken != null && !passwordResetToken.isExpired()) {
			modelAndView.addObject("token", token);
			modelAndView.setViewName("reset_password"); // HTML page for reset password form
		} else {
			modelAndView.setViewName("invalid-token"); // HTML page for invalid token message
		}

		return modelAndView;
	}

	@PostMapping("/reset-password")
	public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
		PasswordResetToken passwordResetToken = tokenrepo.findByToken(token);

		if (passwordResetToken != null && !passwordResetToken.isExpired()) {
			User user = passwordResetToken.getUser();
			user.setPassword(newPassword);
			userRepository.save(user);

			// Delete the token after successful password reset
			//tokenrepo.delete(passwordResetToken);

			return ResponseEntity.ok("Password reset successful");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
		}
	}
}

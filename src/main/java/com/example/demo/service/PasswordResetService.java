package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demo.entity.PasswordResetToken;
import com.example.demo.entity.User;
import com.example.demo.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetService {
	
    @Autowired private PasswordResetTokenRepository tokenRepository;

    @Autowired private JavaMailSender javaMailSender;

    // Generate and save reset token for user
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        tokenRepository.save(resetToken);
    }

    // Send email with reset link
    public void sendResetPasswordEmail(User user, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset Your Password");
        mailMessage.setText("To reset your password, click here: " +
                "http://localhost:8081/user-reset-password?token=" + token);

        try {
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            // Handle mail sending exceptions
            // Log the exception or take appropriate action
            e.printStackTrace();
        }
    }
}


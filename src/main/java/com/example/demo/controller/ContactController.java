package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.Contact;
import com.example.demo.service.EmailService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ContactController {

	@Autowired
	private UserDao udao;
	@Autowired
	private EmailService emailService;
	
	@GetMapping("inquiries")
	public String inquiries(Model model) {
		model.addAttribute("contact", udao.ShowContact());
		return "admin/inquiries";
	}

	@PostMapping("/addcontact")
	public String AddContact(@ModelAttribute Contact contact, HttpSession session,RedirectAttributes redirAttrs) {

		String self = "dhusain4.3.2000@gmail.com";
		String subject = "Contact Inquiry";
		String message = ""
				+"<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+"<tr>"
				+"<td bgcolor='#F9FAFC'>"
				+"<div align='center' style='padding: 45px 0;'>"
				+"<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+"<thead>"
				+"<tr>"
				+"<td style='text-align: center;'>"
				+"<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+"<h2 align='center'>Contact Inquiry</h2>"
				+"</td>"
				+"</tr>"
				+"</thead>"
				+"<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>Admin</strong></p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Name:</strong> "+contact.getName()+".</p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Message:</strong> "+contact.getMessage()+".</p>"
				+"</td>"
				+"</tr>"
				+"</tbody>"
				+"<tfoot>"
				+"<tr>"
				+"<td>"
				+"<p align='center'>"
				+"<small style='color:#7a899f;'>&copy;2023 Copyright <a href='#' target='_blank' style='text-decoration: none; color: #1f74ca;'>Watch Mobile</a>. All Rights Reserved.</small>"
				+"</p>"
				+"</td>"
				+"</tr>"
				+"</tfoot>"
				+"</table>"
				+"</div>"
				+"</td>"
				+"</tr>"
				+"</table>";
		String to=self;

		boolean flag = this.emailService.sendEmail(subject, message, to);

		if (flag) {
			udao.AddContact(contact);
			redirAttrs.addFlashAttribute("success", "Contact send successfully.");
		} else {
			redirAttrs.addFlashAttribute("error", "Contact failed.");
			return "redirect:/contact-us";
		}
		
		return "redirect:/contact-us";
	}
	
	@PostMapping("ReplayMessage")
	public String ReplayMessage(@RequestParam("contactId") int contactId, @RequestParam("replay") String replay,
			@RequestParam("email") String email,@RequestParam("message") String contactmessage) {

		String subject = "Replay";
		String message =""
				+"<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+"<tr>"
				+"<td bgcolor='#F9FAFC'>"
				+"<div align='center' style='padding: 45px 0;'>"
				+"<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+"<thead>"
				+"<tr>"
				+"<td style='text-align: center;'>"
				+"<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+"<h2 align='center'>Contact Inquiry Reply</h2>"
				+"</td>"
				+"</tr>"
				+"</thead>"
				+"<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>User</strong></p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Your Message:</strong> "+contactmessage+".</p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Reply From The Watch Store:</strong> "+replay+".</p>"
				+"</td>"
				+"</tr>"
				+"</tbody>"
				+"<tfoot>"
				+"<tr>"
				+"<td>"
				+"<p align='center'>"
				+"<small style='color:#7a899f;'>&copy;2023 Copyright <a href='http://localhost:8081/' target='_blank' style='text-decoration: none; color: #1f74ca;'> Watch Store</a>. All Rights Reserved.</small>"
				+"</p>"
				+"</td>"
				+"</tr>"
				+"</tfoot>"
				+"</table>"
				+"</div>"
				+"</td>"
				+"</tr>"
				+"</table>";
		String to=email;

		boolean flag = this.emailService.sendEmail(subject, message, to);

		if (flag) {
			Contact c = new Contact();
			c.setId(contactId);
			c.setEmail(email);
			c.setReplay(replay);
			udao.ReplayMessage(contactId, email, replay);

		} else {

			return "redirect:/inquiries";
		}
		return "redirect:/inquiries";
	}
	
	
	//---------------------------------------------------- API ---------------------------------------------------------
	
	@GetMapping("/contact/data")
	public ResponseEntity<List<Contact>> getcontactList() {
		return new ResponseEntity<List<Contact>>(udao.ShowContact(), HttpStatus.OK);
	}
	
	@GetMapping("/reply/{id}")
	public ResponseEntity<Contact> getcontactID(@PathVariable Integer id) {
		return new ResponseEntity<Contact>(udao.getContactByIds(id), HttpStatus.OK);
	}
	
	@PostMapping("user/replyMessage")
	public String replyMessage(@RequestParam("contactId") int contactId, @RequestParam("reply") String reply,
			@RequestParam("email") String email, @RequestParam("message") String contactMessage) {
		try {
			String subject = "Reply";
			String message = buildReplyEmail(contactMessage, reply);
			String to = email;

			boolean emailSent = sendEmail(subject, message, to);

			if (emailSent) {
				udao.ReplayMessage(contactId, email, reply);
				return "Reply sent successfully!";
			} else {
				return "Failed to send email.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "An error occurred while processing the request.";
		}
	}

	private String buildReplyEmail(String contactMessage, String reply) {
		String emailBody = ""
				+"<table border='0' cellpadding='0' cellspacing='0' width='100%'>"
				+"<tr>"
				+"<td bgcolor='#F9FAFC'>"
				+"<div align='center' style='padding: 45px 0;'>"
				+"<table border='0' cellpadding='0' cellspacing='0' style='font-family:Arial,Helvetica,sans-serif;font-size:16px;line-height:1.5em;max-width: 500px;'>"
				+"<thead>"
				+"<tr>"
				+"<td style='text-align: center;'>"
				+"<img src='https://i0.wp.com/www.writefromscratch.com/wp-content/uploads/2018/12/demo-logo.png?ssl=1' style='margin-bottom: 1rem; width: 110px;' alt=''>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td style='background-color: #1f74ca; color: white; padding: 0 20px; border-radius: 15px 15px 0 0;'>"
				+"<h2 align='center'>Contact Inquiry Reply</h2>"
				+"</td>"
				+"</tr>"
				+"</thead>"
				+"<tbody style='background-color: white;padding: 40px 20px;border-radius: 0 0 15px 15px;display: block;box-shadow: 0 10px 30px -30px black;'>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='margin: 0; color: #475467;'>Hi,<strong>User</strong></p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Your Message:</strong> "+contactMessage+".</p>"
				+"</td>"
				+"</tr>"
				+"<tr>"
				+"<td>"
				+"<p align='center' style='color: #7a899f;margin-bottom: 0;font-size: 14px;'><strong>Reply From The Watch Store:</strong> "+reply+".</p>"
				+"</td>"
				+"</tr>"
				+"</tbody>"
				+"<tfoot>"
				+"<tr>"
				+"<td>"
				+"<p align='center'>"
				+"<small style='color:#7a899f;'>&copy;2023 Copyright <a href='http://localhost:8081/' target='_blank' style='text-decoration: none; color: #1f74ca;'> Watch Store</a>. All Rights Reserved.</small>"
				+"</p>"
				+"</td>"
				+"</tr>"
				+"</tfoot>"
				+"</table>"
				+"</div>"
				+"</td>"
				+"</tr>"
				+"</table>";
		return emailBody;
	}

	private boolean sendEmail(String subject, String message, String to) {
		try {
			return emailService.sendEmail(subject, message, to);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
}

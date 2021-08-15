package com.demo.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mail.model.MailRequest;
import com.demo.mail.service.MailerService;

@RestController
@RequestMapping( path = "/mailer")
public class MailerController {

	@Autowired
	private MailerService mailerService;
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> sendMail(@RequestBody MailRequest request) {
		return mailerService.sendMail(request);
	}
	
}

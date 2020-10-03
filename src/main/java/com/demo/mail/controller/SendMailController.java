package com.demo.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.mail.model.SendMail;
import com.demo.mail.service.SendMailService;

@RestController
@RequestMapping( path = "/sendMail/")
public class SendMailController {

	@Autowired
	SendMailService service;
	
	@PostMapping
	public String correo(@RequestBody SendMail request) {
		return service.enviarMail(request);
	}
	
}

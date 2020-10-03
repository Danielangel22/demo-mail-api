package com.demo.mail.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.demo.mail.model.SendMail;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class SendMailService {

	@Value("${spring.mail.username}")
	private String Remitente;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	public String enviarMail(SendMail request) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(request.getTo());
		helper.setFrom(Remitente);
		//helper.setFrom(request.getFrom()); //en caso de parametrizar el remitente
		helper.setText(request.getText());
		helper.setSubject(request.getSubject());
		javaMailSender.send(msg);
		}catch (Exception e) {
			// TODO: handle exception
			return "correo fallido"+e.getMessage();
		}
		
		return "correo enviado";
	}
	
}

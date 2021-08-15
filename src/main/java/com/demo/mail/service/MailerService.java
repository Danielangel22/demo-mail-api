package com.demo.mail.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.mail.model.MailRequest;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class MailerService {

	Logger log = LoggerFactory.getLogger(getClass());

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;

	public ResponseEntity<String> sendMail(MailRequest request) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, false);
			helper.setTo(request.getTo());
			helper.setFrom(from);
			helper.setText(request.getText());
			helper.setSubject(request.getSubject());
			javaMailSender.send(msg);
		} catch (MailException | MessagingException e) {
			log.info("error in sending mail, detail: [{}]", e.getMessage());
			return new ResponseEntity<>("Error in sending mail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok("Mail send");
	}

	public ResponseEntity<String> sendMailWithHTML(MailRequest request) {
		MimeMessage msg = javaMailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(request.getTo());
			helper.setFrom(from);
			String template = getDefaultTemplate();
			template = template.replace("{text}", request.getText());
			FileSystemResource icon = new FileSystemResource(new File("src/main/resources/images/icon.png"));
			helper.addInline("icon", icon); // this is a representation in html <img src=cid:icon>
			helper.setText(request.getText(), template);
			helper.setSubject(request.getSubject());
			javaMailSender.send(msg);
		} catch (MailException | MessagingException e) {
			log.info("error in sending mail, detail: [{}]", e.getMessage());
			return new ResponseEntity<>("Error in sending mail", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok("Mail send");
	}

	public static String getDefaultTemplate() {
		try {
			return Files.readString(Path.of("src/main/resources/templates/email-template.html"),
					Charset.defaultCharset());
		} catch (IOException e) {
			return "";
		}
	}
}

package com.memorynotfound.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Sending Email with Freemarker HTML Template Example");

        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("phanducthinh102@outlook.com");
        mail.setSubject("Sending Email with Freemarker HTML Template Example");

        Map<String, Object> model = new HashMap<String, Object>();
        Email email = new Email();
        email.setName("Memorynotfound.com");
//        email.setLocation("Belgium");
        email.setSignature("http://memorynotfound.com");
        model.put("object", email);
        mail.setModel(model);

        emailService.sendSimpleMessage(mail);
    }
}

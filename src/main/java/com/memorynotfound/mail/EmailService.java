package com.memorynotfound.mail;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Configuration freemarkerConfig;

    public void sendSimpleMessage(Mail mail) throws MessagingException, IOException, TemplateException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());

        helper.addAttachment("logo.png", new ClassPathResource("memorynotfound-logo.png"));

//        Template t = freemarkerConfig.getTemplate("email-template.ftl");
        String template = "<html>\n" + 
        		"<head>\n" + 
        		"    <title>Sending Email with Freemarker HTML Template Example</title>\n" + 
        		"\n" + 
        		"    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" + 
        		"    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" + 
        		"\n" + 
        		"    <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>\n" + 
        		"\n" + 
        		"    <!-- use the font -->\n" + 
        		"    <style>\n" + 
        		"        body {\n" + 
        		"            font-family: 'Roboto', sans-serif;\n" + 
        		"            font-size: 48px;\n" + 
        		"        }\n" + 
        		"    </style>\n" + 
        		"</head>\n" + 
        		"<body style=\"margin: 0; padding: 0;\">\n" + 
        		"\n" + 
        		"    <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse;\">\n" + 
        		"        <tr>\n" + 
        		"            <td align=\"center\" bgcolor=\"#78ab46\" style=\"padding: 40px 0 30px 0;\">\n" + 
        		"                <img src=\"cid:logo.png\" alt=\"http://memorynotfound.com\" style=\"display: block;\" />\n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"        <tr>\n" + 
        		"            <td bgcolor=\"#eaeaea\" style=\"padding: 40px 30px 40px 30px;\">\n" + 
        		"                <p>Dear ${object.name},</p>\n" + 
        		"                <p>Sending Email using Spring Boot with <b>FreeMarker template !!!</b></p>\n" + 
        		"                <p>Thanks</p>\n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"        <tr>\n" + 
        		"            <td bgcolor=\"#777777\" style=\"padding: 30px 30px 30px 30px;\">\n" + 
        		"                <p>${object.signature}</p>\n" + 
        		"                <#if object.location??>\n" + 
        		"                	<p>${object.location}</p>\n" + 
        		"                </#if>\n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"    </table>\n" + 
        		"\n" + 
        		"</body>\n" + 
        		"</html>";
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("email-template", template);
        freemarkerConfig.setTemplateLoader(stringLoader);
        
        
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("email-template"), mail.getModel());
        System.out.println(html);
//        helper.setTo(mail.getTo());
//        helper.setText(html, true);
//        helper.setSubject(mail.getSubject());
//        helper.setFrom(mail.getFrom());
//
//        emailSender.send(message);
    }

}

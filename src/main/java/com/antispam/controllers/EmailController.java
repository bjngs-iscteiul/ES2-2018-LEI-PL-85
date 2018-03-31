package com.antispam.controllers;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;

/**
 *
 */
@Controller

public class EmailController {

    @Autowired
    private JavaMailSender sender;

    /**
     * @param model
     * @return
     */
    @GetMapping("/email")
    public String emailSender(Model model){
        String message = "";
        try{
            sendEmail();
            message =  "Email sent!";
        } catch (Exception e){
            message ="Error sending email: " + e  ;
        }
         model.addAttribute("message", message );
        return "email-confirmation";
    }

    /**
     * @throws Exception
     */
    private void sendEmail()throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo("jicpl@iscte-iul.pt");
        helper.setText("Testing");
        helper.setSubject("Test SE");



        ClassPathResource file = new ClassPathResource("/config.xml");
        if (file.exists()){
            helper.addAttachment("config.xml", file);
        }

        sender.send(message);
    }

}

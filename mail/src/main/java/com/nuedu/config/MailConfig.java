package com.nuedu.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nuedu.pojo.Mail;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Configuration
public class MailConfig {
    @Resource
    JavaMailSenderImpl mailSender;
    @Resource
    ObjectMapper objectMapper;
    //用于监听queues队列，如果能监听到 则进行下面的操作
    @RabbitListener(queues = "socity-mail-password")
    public void getMessage(Message message) {
        String body = new String(message.getBody());
        System.out.println(body);
        try {
            Mail mail = objectMapper.readValue(body, Mail.class);
            MimeMessage mimeMessage=mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom(mail.getFrom());
            mimeMessageHelper.setTo(mail.getTo());
            mimeMessageHelper.setSubject(mail.getTitle());
            mimeMessageHelper.setText(mail.getMessage(),true);
            mailSender.send(mimeMessage);
            System.out.println("发送完成");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

}

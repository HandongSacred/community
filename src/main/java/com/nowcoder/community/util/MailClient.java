package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;

    //从appliaction获取发送人
    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String content){

        try {
            MimeMessage message = mailSender.createMimeMessage();
            //利用helper构建MimeMessage
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);//发送人
            helper.setTo(to);//收件人
            helper.setSubject(subject);//设置标题
            helper.setText(content,true);//设置内容 设定为html格式
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败" + e.getMessage());
        }

    }
}

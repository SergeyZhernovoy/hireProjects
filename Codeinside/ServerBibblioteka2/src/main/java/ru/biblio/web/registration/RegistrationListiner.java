package ru.biblio.web.registration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.biblio.web.domain.User;
import ru.biblio.web.service.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.UUID;

@Component
public class RegistrationListiner implements ApplicationListener<OnRegistrationCompleteEvent> {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationListiner.class);

    @Value("${mail.host}")
    private String host;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String enablettls;
    @Value("${mail.from}")
    private String from;

    @Autowired
    private UserService userService;

    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {

        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl   = event.getAppUrl() + "/account/registration-confirm?token=" + token;
        String message = "You registered successfully. We will send you a confirmation message to your email account. ";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.stmp.user", username);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", enablettls);
        props.put("mail.smtp.password", password);
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                String username = "mail-for-test-java@yandex.ru";
                String password = "123456654321";
                return new PasswordAuthentication(username,password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(from));
            msg.setRecipient(MimeMessage.RecipientType.TO, new    InternetAddress(recipientAddress));
            msg.setSubject(subject);
            Multipart body = new MimeMultipart();
            MimeBodyPart htmlTextPart = new MimeBodyPart();
            htmlTextPart.setContent(
                    "<h2>"
                    + message +
                    "</h2>" +
                    "<br>" +
                    "<div><a href= \'http://localhost:8080"
                    + confirmationUrl +
                    "\'>Confirm your registration !</a></div>", "text/html;charset=UTF-8");
            body.addBodyPart(htmlTextPart);
            msg.setContent(body);
            Transport transport = session.getTransport("smtp");
            transport.send(msg);
            logger.info("E-mail sent !");
        }   catch(Exception exc) {
            logger.error(exc.getMessage());
        }
    }
}


package main.project.movie.controller.command.impl;

import main.project.movie.controller.command.Command;
import main.project.movie.controller.command.Router;
import main.project.movie.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.Level;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import static main.project.movie.controller.message.LogMessage.*;
import static main.project.movie.controller.attribute.AttributeName.*;
import static main.project.movie.controller.path.PagePath.*;

public class SendConfirmPasswordCommand implements Command {
    private static final int RANDOM_RANGE = 1054321;
    private static int confirmPassword = 0;
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String userEmail = (String) request.getAttribute(EMAIL);
        String firstname = (String) request.getAttribute(FIRSTNAME);
        String lastname = (String) request.getAttribute(LASTNAME);
        HttpSession mySession = request.getSession();
        Random rand = new Random();
        confirmPassword = rand.nextInt(RANDOM_RANGE);
        logger.log(Level.INFO, CONFRM_PSW_LOG + confirmPassword);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, GOOGLE_SECURITY_PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Hi, " + firstname+" "+lastname);
            message.setText( CONFRM_PSW_LOG + confirmPassword);
            Transport.send(message);
            logger.log(Level.INFO, SUCCESSFULLY_SENT_PSW_LOG);
            request.setAttribute(MESSAGE, CONFRM_PSW_SENT_TO_EMAIL+userEmail);
            mySession.setAttribute(SENT_PASSWORD, confirmPassword);
            mySession.setAttribute(EMAIL, userEmail);
            router = new Router(CONFRM_PSW_PAGE, Router.Type.FORWARD);
            logger.log(Level.INFO, ROUTER_LOG + router);
            return router;
        } catch (MessagingException e) {
            logger.error(ERROR_SENDING_CONFRM_PSW_LOG, e);
            throw new CommandException(ERROR_SENDING_CONFRM_PSW_LOG,e);
        }
    }
}

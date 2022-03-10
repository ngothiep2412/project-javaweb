/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendmail;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Thiep Ngo
 */
public class SendEmail {

    public String getRandom() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        return String.format("%06d", number);
    }

    public static boolean sendMail(UserEmail user) throws MessagingException {
        boolean check = false;

        Properties pr = new Properties();
        pr.put("mail.smtp.auth", "true");
        pr.put("mail.smtp.starttls.enable", "true");
        pr.put("mail.smtp.host", "smtp.gmail.com");
        pr.put("mail.smtp.port", "587");

        String myAccountEmail = "ngothiep2412@gmail.com";
        String password = "thieppro123";

        Session session = Session.getInstance(pr, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });
        Message message = prepareMessage(session, myAccountEmail, user);
        if (message != null) {
            Transport.send(message);
            check = true;
        }
        return check;
    }

    private static Message prepareMessage(Session session, String myAccountEmail, UserEmail user) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail, "Vegetable Shop"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));
            message.setSubject("Email Verification Code Regarding Member account Registration");
            message.setText("Registered successfully. Please verify your account using this code:" + user.getCode());
            return message;
        } catch (Exception ex) {
            Logger.getLogger(SendEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

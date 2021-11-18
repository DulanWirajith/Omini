package lk.dbay.service.impl;

import com.sun.mail.smtp.SMTPTransport;
import lk.dbay.entity.DbayUser;
import lk.dbay.service.SendEmailSMTP;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class SendEmailSMTPImpl implements SendEmailSMTP {

    private static final String SMTP_SERVER = "smtp.gmail.com";
    private static final String USERNAME = "yewoitest@gmail.com";
    private static final String PASSWORD = "ouxellbguxmmnqqb";

    private static final String EMAIL_FROM = "yewoitest@gmail.com";
//    private static final String EMAIL_TO = "email_1@yahoo.com, email_2@gmail.com";
//    private static final String EMAIL_TO_CC = "";

//    private static final String EMAIL_SUBJECT = "Test Send Email via SMTP";
//    private static final String EMAIL_TEXT = "Hello Java Mail \n ABC123";

    public void sendEmail(String emailTo, String emailSubject, String emailText) throws Exception {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", SMTP_SERVER); //optional, defined in SMTPTransport
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.port", "587"); // default port 25
        prop.put("mail.smtp.starttls.enable", "true"); // default port 25

        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            // from
            msg.setFrom(new InternetAddress(EMAIL_FROM));

            // to
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailTo, false));

            // cc
//            msg.setRecipients(Message.RecipientType.CC,
//                    InternetAddress.parse(EMAIL_TO_CC, false));

            // subject
            msg.setSubject(emailSubject);

            // content
            msg.setText(emailText);

            msg.setSentDate(new Date());

            // Get SMTPTransport
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

            // connect
            t.connect(SMTP_SERVER, USERNAME, PASSWORD);

            // send
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();

        } catch (MessagingException e) {
            e.printStackTrace();
            throw new Exception("Email failed");
        }
    }

    public void sendRegistrationEmail(DbayUser dbayUser) throws Exception {
        new Thread(() -> {
            try {
                if (dbayUser.getEmail() != null && !dbayUser.getEmail().equals("")) {
                    String emailTxt = "Hello " + " (" + dbayUser.getUsername() + "), " +
                            "\nYou are now new " + dbayUser.getRole() + " of the (E-Class) system." +
                            "\nYour " + dbayUser.getRole() + " ID is " + dbayUser.getUserId();
                    sendEmail(dbayUser.getEmail(), "YEWOI(E-Class) New " + dbayUser.getRole(), emailTxt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}

package q.rest.messaging.operation;

import q.rest.messaging.dao.DAO;
import q.rest.messaging.helper.AppConstants;
import q.rest.messaging.model.SMSHistory;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Properties;

@Stateless
public class AsyncService {

    @EJB
    private DAO dao;

    @Asynchronous
    public void sendHtmlEmail(String email, String subject, String body) {
        Properties properties = System.getProperties();
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AppConstants.EMAIL_ADDRESS, AppConstants.PASSWORD);
            }
        });
        properties.setProperty("mail.smtp.host", AppConstants.SMTP_SERVER);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.starttls.enable", "true");
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(AppConstants.EMAIL_ADDRESS));
            System.out.println("sending email to " + email);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException ex) {
            System.out.println("Error in sending email");
            ex.printStackTrace();
        }
    }


    @Asynchronous
    public void sendSms(String mobile, String text){
        try{
            String textEncoded = URLEncoder.encode(text, StandardCharsets.UTF_8);
            String url = AppConstants.getSMSMaxLink(mobile, textEncoded);
            Response r = getRequest(url);
            int status = r.getStatus();
            String responseBody = r.readEntity(String.class);
            saveHistory(textEncoded, mobile, responseBody, status);
        }catch (Exception ex){

        }
    }

    private void saveHistory(String text, String mobile, String responseBody, int status){
        SMSHistory sms = new SMSHistory();
        sms.setBody(text);
        sms.setCreated(new Date());
        sms.setResponseMsg(responseBody);
        sms.setHttpStatus(status);
        sms.setMobile(mobile);
        dao.persist(sms);
    }



    public Response getRequest(String link) {
        return ClientBuilder.newClient().target(link).request().get();
    }
}

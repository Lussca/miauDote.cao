package controller.handler.email;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
		  public static void sendValidationEmail(String email, String validationCode) {
		    String host = "smtp.gmail.com";
		  //String host = "smtp.office365.com";
		    String port = "587";
		    final String username = "miauDote.Cao@gmail.com";
		    final String password = "32267228Aa#";
		    String fromAddress = "miauDote.Cao@gmail.com";
		    String toAddress = email;

		    Properties properties = new Properties();
		    properties.put("mail.smtp.auth", "true");
		    properties.put("mail.smtp.starttls.enable", "true");
		    properties.put("mail.smtp.host", host);
		    properties.put("mail.smtp.port", port);
		    //properties.put("mail.smtp.starttls.enable", "true");
		    //properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		    Session session = Session.getInstance(properties, new Authenticator() {
		      @Override
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication(username, password);
		      }
		    });

		    try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(fromAddress));
		      message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
		      message.setSubject("Email validation code");
		      message.setText("Your validation code is: " + validationCode);
		      Transport.send(message);
		      System.out.println("Email sent successfully.");
		    } catch (MessagingException e) {
		      e.printStackTrace();
		    }
		  }
		}


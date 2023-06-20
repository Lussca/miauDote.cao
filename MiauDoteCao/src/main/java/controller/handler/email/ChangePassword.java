package controller.handler.email;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import controller.Validations;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class ChangePassword {

	private Properties getEmailAndPassword() {
		Properties properties = new Properties();
		File f = new File("C:\\Projetos\\miauDote.cao\\admin\\emailConfig.ini");
		if(!f.exists()){
			 f = new File("C:\\Users\\Joao Gabriel\\Desktop\\miauDote.cao\\admin\\emailConfig.ini");
		}
        try (FileInputStream fileInputStream = new FileInputStream(f)) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
	
	public boolean send(String emailTo) {
		  Properties p = getEmailAndPassword();
	      String from = p.getProperty("email");
	      final String username = from;
	      final String password = p.getProperty("password");
	      final String host = "smtp.gmail.com";
	      Properties props = new Properties();
	      props.put("mail.smtp.auth", "true");
	      props.put("mail.smtp.starttls.enable", "true");
	      props.put("mail.smtp.host", host);
	      props.put("mail.smtp.port", "587");
	      Session session = Session.getInstance(props,
	         new jakarta.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	               return new PasswordAuthentication(username, password);        
	    }
	         });
	      try {
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
	    message.setSubject("Código de verificação");
	    message.setText("Aqui está seu código de verificação: "+Validations.randomNumber(1111, 9999));
	    Transport.send(message);
	    return true;
	      } catch (Exception e) {
	    	  return false;	        
	      }
	      
	   }
	
}
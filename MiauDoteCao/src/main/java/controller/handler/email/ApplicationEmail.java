package controller.handler.email;

import java.io.IOException;
import java.util.Properties;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import model.Dao;
import model.entity.*;

public class ApplicationEmail {
	Dao dao = new Dao();
	public boolean sendToOng(String idAdopter, String idAnimal) throws AddressException, MessagingException {
		  UserOng ong;
		try {
			ong = dao.getOngByIdAnimal(idAnimal);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		  UserAdopter adopter;
		try {
			adopter = dao.getUserAdopter(idAdopter);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		  Animal animal;
		try {
			animal = dao.getAnimalById(idAnimal);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		  String messageToSend = "Olá, " + ong.getOngName() + ".\n\nO usuário(a) " + adopter.getUsername() + " acaba de se candidatar para o processo de adoção do(a) " + animal.getName() + " (ID " + animal.getId() + ").\n\nPor favor, entre em contato com o adotante pelo telefone: " + adopter.getPhoneNumber() + " ou pelo email: " + adopter.getLogin() + ".\n\nObrigado por usar o MiauDote.Cão.";

		  Properties p = EmailConfig.emailConfig();
	      String from = p.getProperty("email");
	      final String username = from;
	      final String password = p.getProperty("password");
	      final String host = "smtp.gmail.com";
	      Properties props =  EmailConfig.emailConfig();
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
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(ong.getLogin()));
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	    message.setSubject("Novo processo de adoção - MiauDote.Cão");
	    message.setText(messageToSend);
	    Transport.send(message);
	    return true;
	   }
	
	public boolean sendToAdopter(String idAdopter, String idAnimal) throws AddressException, MessagingException {
		  UserOng ong;
		try {
			ong = dao.getOngByIdAnimal(idAnimal);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		  UserAdopter adopter;
		try {
			adopter = dao.getUserAdopter(idAdopter);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		  Animal animal;
		try {
			animal = dao.getAnimalById(idAnimal);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return false;
		}
		String messageToSend = "Olá, " + adopter.getUsername() + ".\n\nJá entramos em contato com a ONG " + ong.getOngName() + ". Assim que possível, um representante da ONG entrará em contato para dar sequência com o processo de adoção do(a) " + animal.getName() + ". Caso o contato não aconteça, envie um e-mail para a ONG através do endereço "+ ong.getLogin()+ ".\n\nObrigado por utilizar o MiauDote.Cão.";

		  Properties p = EmailConfig.emailConfig();
	      String from = p.getProperty("email");
	      final String username = from;
	      final String password = p.getProperty("password");
	      final String host = "smtp.gmail.com";
	      Properties props =  EmailConfig.emailConfig();
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
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress(from));
	    try {
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(adopter.getLogin()));
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	    message.setSubject("Novo processo de adoção - MiauDote.Cão");
	    message.setText(messageToSend);
	    Transport.send(message);
	    return true;
	   }
}

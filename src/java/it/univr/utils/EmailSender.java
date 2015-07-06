package it.univr.utils;

import it.univr.exceptions.DatabaseException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Classe che invia email automatiche tramite Gmail
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class EmailSender implements Serializable, SendEmailInterface {
	
	/** Serial Version UID. */
	private static final long serialVersionUID = 2782891016573358416L;

	/** Path del file di properties */
	private static final String configFileEmail = 
			"/it/univr/properties/email.properties";
	
	/** Properties delle email */
	private final Properties email_properties;
	
	/** Proprietà necessarie all'invio della email */
	private static Properties mailServerProperties;
	private static Session getMailSession;
	private static MimeMessage generateMailMessage;
	
	/**
	 * Costruisco l'oggetto {@code ViewStateBean} recuperando le properties corrette
	 */
	public EmailSender() {
		email_properties = new Properties();

		try {
			email_properties.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(configFileEmail));
		} catch (IOException e) {
			throw new DatabaseException();
		}
	}
	
	
	public boolean sendEmail(String emailBody, String to) {
		String server = email_properties.getProperty("mail.server");
		String address = email_properties.getProperty("mail.address");
		String password = email_properties.getProperty("mail.password");
		String subject = email_properties.getProperty("mail.subject");
		String protocol = email_properties.getProperty("mail.protocol");
	    		
		// Step1
		System.out.println("\n 1st ===> setup Mail Server Properties..");
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", "587");
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		System.out.println("Mail Server Properties have been setup successfully..");

		// Step2
		System.out.println("\n\n 2nd ===> get Mail Session..");
		
		try {
			getMailSession = Session.getDefaultInstance(mailServerProperties, null);
			generateMailMessage = new MimeMessage(getMailSession);
		
			generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			generateMailMessage.setSubject(subject);
			generateMailMessage.setContent(emailBody, "text/html");
			System.out.println("Mail Session has been created successfully..");

			// Step3
			System.out.println("\n\n 3rd ===> Get Session and Send mail");
			Transport transport = getMailSession.getTransport(protocol);
			
			transport.connect(server, address, password);
			transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
			transport.close();
		} catch (MessagingException e1) {
			return false;
		}
			
		System.out.println("\n\n ===> Your Java Program has just sent an Email successfully. Check your email..");
		return true;
	}
}

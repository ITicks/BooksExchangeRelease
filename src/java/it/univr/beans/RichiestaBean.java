package it.univr.beans;

import it.univr.exceptions.DatabaseException;
import it.univr.messages.MessagesHandler;
import it.univr.models.LibroModel;
import it.univr.models.UtenteModel;
import it.univr.utils.EmailSender;

import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean che gestisce le richieste di prestito di un libro.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
@ManagedBean
@SessionScoped
public class RichiestaBean extends ViewStateBean implements Serializable{

	/** Serial Version UID.	 */
	private static final long serialVersionUID = 2867999915550660547L;
	
	/** Il proprietario del libro */
	private UtenteModel utentePossessore;
	
	/** Utente che richiede il libro */
	private UtenteModel utenteRichiedente;
	
	/** Il libro da chiedere in prestito */
	private LibroModel libro;
	
	/** Messaggio di richesta da inviare per email */
	private String emailMessage;
	
	/** Email Sender */
	private EmailSender emailSender;
	
	/** Path del file di properties */
	private static final String configFilePageState = 
			"/it/univr/properties/email.properties";
	
	/** Properties che mappano gli stati con i pezzi jsf da includere */
	private Properties email_properties;
	
	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		this.libro = new LibroModel();
		this.utenteRichiedente = new UtenteModel();
		this.utentePossessore = new UtenteModel();
		this.emailSender = new EmailSender();
		email_properties = new Properties();

		try {
			email_properties.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(configFilePageState));
		} catch (IOException e) {
			throw new DatabaseException();
		}
	}
	
	public UtenteModel getUtenteRichiedente() { return utenteRichiedente;	}

	public void setUtenteRichiedente(UtenteModel utente) { this.utenteRichiedente = utente; }
	
	public UtenteModel getUtentePossessore() { return utentePossessore;	}

	public void setUtentePossessore(UtenteModel utentePossessore) { this.utentePossessore = utentePossessore; }
	
	public LibroModel getLibro() { return libro; }

	public void setLibro(LibroModel libro) { this.libro = libro; }

	public String getEmailMessage() { return emailMessage; }

	public void setEmailMessage(String emailMessage) { this.emailMessage = emailMessage; }
	
	/** Scrivo il testo della email standard da inviare */
	public void writeStandardEmailMessage(){
		
		String[] emailblocks = email_properties.getProperty("message.standard").replace("|", "\n").split("-");
		
		String[] contents = {
				utentePossessore.getNome(),
				libro.getTitolo(),
				libro.getISBN(),
				utenteRichiedente.getEmail(),
				utenteRichiedente.getNome() + " "
						+ utenteRichiedente.getCognome() };
		
		emailMessage="";
		
		for (int i = 0; i < contents.length; i++)
			emailMessage += emailblocks[i] + contents[i];
	}
	
	/** Invio la email scritta dall'utente */
	public String sendEmail(){
		if(!emailSender.sendEmail(emailMessage, utentePossessore.getEmail()))
			MessagesHandler.getInstance().buildMessage("erroreRichiesta", 
					FacesMessage.SEVERITY_INFO);
		else
			MessagesHandler.getInstance().buildMessage("richiestaInviata", 
					FacesMessage.SEVERITY_INFO);
		
		return this.setState("ricerca_libri", "utente");
	}
	
}

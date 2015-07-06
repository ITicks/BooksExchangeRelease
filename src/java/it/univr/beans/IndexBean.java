package it.univr.beans;


import it.univr.messages.MessagesHandler;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Bean di tutti i tipi di Utente.
 * 
 * @author Matteo Olivato
 * @author  Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class IndexBean extends ViewStateBean implements Serializable
{

    /** Serial Version UID. */
	private static final long serialVersionUID = 90526546886875511L;
	
	/** Bean dell'utente */
	@ManagedProperty(value = "#{utenteBean}")
	private UtenteBean utenteBean;
	
	/** Bean del correttore di bozze */
	@ManagedProperty(value = "#{correttoreBean}")
	private CorrettoreBean correttoreBean;
	
	/** Bean dell' amministratore */
	@ManagedProperty(value = "#{amministratoreBean}")
	private AmministratoreBean amministratoreBean;
	
	/** Bean della registrazione */
	@ManagedProperty(value = "#{registrazioneBean}")
	private RegistrazioneBean registrazioneBean;
	
	/** Username inserito */
	private String username;
	
	/** Password inserita */
	private String password;
	
	/** Indico se un utente si è loggato */
	private boolean loggedIn;

	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		loggedIn = false;
	}
	
	/**
	 * Eseguo il login selezionando l'utente corretto
	 * @return la pagina del tipo di utente loggato.
	 */
	public String login(){
		
		if(utenteBean.verifyUser(username, password) != null) {
			
			loggedIn = true;
			return this.setState("profilo", "utente");
		} else if (correttoreBean.verifyUser(username, password) != null) {
			loggedIn = true;
			return this.setState("lista_inserzioni", "correttore");
		}else if(amministratoreBean.verifyUser(username, password) != null) {
			loggedIn = true;
			return this.setState("statistiche", "amministratore");
		}else {
			MessagesHandler.getInstance().buildMessage("loginFailed", 
					FacesMessage.SEVERITY_ERROR);
		}
		
		return this.setState("registrazione_utente", "index");
	}
	
	/**
	 * Eseguo il logout di un utente.
	 */
	public void logout() {
		loggedIn = false;

		if (utenteBean.isLoggedIn())
			utenteBean.logout();
		
		else if (correttoreBean.isLoggedIn())
			correttoreBean.logout();
		
		else
			amministratoreBean.logout();
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UtenteBean getUtenteBean() {
		return utenteBean;
	}

	public void setUtenteBean(UtenteBean utenteBean) {
		this.utenteBean = utenteBean;
	}

	public CorrettoreBean getCorrettoreBean() {
		return correttoreBean;
	}

	public void setCorrettoreBean(CorrettoreBean correttoreBean) {
		this.correttoreBean = correttoreBean;
	}

	public AmministratoreBean getAmministratoreBean() {
		return amministratoreBean;
	}

	public void setAmministratoreBean(AmministratoreBean amministratoreBean) {
		this.amministratoreBean = amministratoreBean;
	}

	public RegistrazioneBean getRegistrazioneBean() {
		return registrazioneBean;
	}

	public void setRegistrazioneBean(RegistrazioneBean registrazioneBean) {
		this.registrazioneBean = registrazioneBean;
	}
	
}

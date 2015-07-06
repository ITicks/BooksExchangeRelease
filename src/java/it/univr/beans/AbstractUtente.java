package it.univr.beans;

import javax.annotation.PreDestroy;

/**
 * Classe astratta che rappresenta un Utente.
 * 
 * @author Matteo Olivato
 * @author  Federico Bianchi
 */
public abstract class AbstractUtente extends ViewStateBean
{
	/** Campo login del form. */
	private String username;
	
	/** Campo password del form. */
	private String password;
	

	/**
	 * Controlla se l'utente è registrato nel database. In caso positivo, salva
	 * i dati dell'utente nel suo bean.
	 * 
	 * @return l'outcome {@code ""} per ricaricare la pagina attuale.
	 */
	public abstract Object verifyUser(String username, String password);
	

    /**
     * Esegue il logout di un Utente e reindirizza la navigazione alla
     * homepage.
     * 
     * @return {@code redirectToIndex} per reindirizzare la navigazione alla
     *         homepage.
     */
    public String logout(){
    	//Pulisco i campi del Bean
    	cleanUp();
    	
    	//Setto lo stato del Bean nella pagina di benvenuto
    	return setState("pagina_benvenuto", "index");
    }
	
	/**
	 * Verifica se l'utente è loggato.
	 * 
	 * @return {@code true} se e solo se l'utente è loggato.
	 */
	public boolean getLoggedIn(Object obj){ return obj != null; }

	
	/**
	 * Reset del bean.
	 */
	@PreDestroy
	public abstract void cleanUp();
	
	/**
	 * Pulisce i campi del form di login dell'utente.
	 */
	public void cleanFields(){
		setUsername("");
	    setPassword("");
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
}

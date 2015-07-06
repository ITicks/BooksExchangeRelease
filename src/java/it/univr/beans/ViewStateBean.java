package it.univr.beans;

import it.univr.exceptions.DatabaseException;

import java.io.IOException;
import java.util.Properties;

/**
 * Classe che permette la gestione degli stati rispettivi alle jsf dei bean.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class ViewStateBean extends AbstractDataSourceBean {
	
	/** Stato della jsf */
	private static String state="standard_utente";
	
	/** Path del file di properties */
	private static final String configFilePageState = 
			"/it/univr/properties/page_state.properties";
	
	/** Properties che mappano gli stati con i pezzi jsf da includere */
	private final Properties page_state_properties;
	
	/**
	 * Costruisco l'oggetto {@code ViewStateBean} recuperando le properties corrette
	 */
	public ViewStateBean() {
		page_state_properties = new Properties();

		try {
			page_state_properties.load(Thread.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(configFilePageState));
		} catch (IOException e) {
			throw new DatabaseException();
		}
	}
	
	/**
	 * Restituisco l'indirizzo della pagina jsf da includere
	 * @return il pezzo di pagina jsf relativo allo stato attuale da includere
	 */
	public String getPageState(){
		return page_state_properties.getProperty(state);
	}
	
	/**
	 * Setto lo stato che deve essere renderizzato nella jsf
	 * @param state stato della jsf da renderizzare
	 * @param redirectpage pagina a cui devo fare il redirect
	 * @return {@code redirectpage}
	 */
	public String setState(String state, String redirectpage ){
		ViewStateBean.state = state;
		return redirectpage;
	}

}

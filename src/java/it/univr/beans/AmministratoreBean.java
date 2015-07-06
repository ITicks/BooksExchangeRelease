package it.univr.beans;

import it.univr.db.DataSourceAmministratore;
import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceStatistiche;
import it.univr.messages.MessagesHandler;
import it.univr.models.AmministrazioneModel;
import it.univr.models.StatisticheModel;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la gestione dell'Amministratore.
 * 
 * @author Matteo Olivato
 * @author  Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class AmministratoreBean extends AbstractUtente implements Serializable{

	/** Serial Version UID. */
	private static final long serialVersionUID = -6742179045212754138L;
	
	/** L'amministratore loggato */
	private AmministrazioneModel amministratore;
	
	/** Il nuovo correttore da inserire */
	private AmministrazioneModel correttore;

	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		dsAmm = new DataSourceAmministratore();
		dsStats = new DataSourceStatistiche();
		dsCorr = new DataSourceCorrettoreBozza();
		correttore = new AmministrazioneModel();
	}
	
	/**
	 * Metodo che ritorna un Amministratore
	 * 
	 * @return amministratore
	 */
	public AmministrazioneModel getAmministratore() {
		return amministratore;
	}
	
	/**
	 * Metodo che ritorna le Statistiche
	 * 
	 * @return le statistiche {@code StatisticheModel}
	 */
	public StatisticheModel getStatistiche() {
		return dsStats.getStatistiche();
	}
	
	public Object verifyUser(String username, String password) {
		
		this.setUsername(username);
		this.setPassword(password);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(getUsername());
		list.add(getPassword());
			
		amministratore = dsAmm.getAmministratore(list);
		
		if (amministratore == null)
           return null;
		else{
			// Aggiorno il timestamp del last login
			list = new ArrayList<Object>();
			list.add(amministratore.getId());
			dsAmm.updateLastLogin(list);			
			
			//Ritorno l'oggetto amministratore per indicare
			//l'avvenuto login
			return amministratore;
		}
	}

	/** 
	 * Metodo che effettua il logout dell'Amministrazione 
	 */
	public String logout() {
		
		if(amministratore != null){
		// Aggiorno il timestamp del last logout
		List<Object> list = new ArrayList<Object>();
		list.add(amministratore.getId());
		dsAmm.updateLastLogout(list);			
		}
		return super.logout();
	}

	/**
	 * Metodo che controlla un Amministratore
	 * 
	 * @return {code true} se è autenticato, altrimenti {@code false}
	 */
	public boolean isLoggedIn() { return this.getLoggedIn(amministratore); }

	/**
	 * Pulisco il Bean dagli oggetti non necessari
	 * dopo un logout.
	 */
	public void cleanUp() {
		cleanFields();
		
		this.amministratore = null;
		this.correttore = null;
	}
	
	/**
	 * Metodo per settare i campi nella creazione di un nuovo
	 * Correttore di Bozze
	 */
	public void setNuovoCorrettore(){
		List<Object> list = new ArrayList<Object>();
		
		list.add(correttore.getNome());
		list.add(correttore.getCognome());
		list.add(correttore.getUsername());
		list.add(correttore.getPassword());
		list.add(correttore.getEmail());
		list.add(correttore.getN_cell());
		
		dsCorr.insertCorrettoreBozza(list);
		
		MessagesHandler.getInstance().buildMessage("registrazioneAvvenuta", 
				FacesMessage.SEVERITY_INFO);
		
	}
	
	/**
	 * Metodo per eliminare un Correttore di Bozze
	 * 
	 * @param id
	 */
	public void deleteCorrettore(int id){
		List<Object> list = new ArrayList<Object>();
		
		list.add(id);
		
		dsCorr.deleteCorrettoreBozza(list);
	}
	
	/**
	 * Metodo per ritornare un Correttore di Bozze
	 * 
	 * @return correttore
	 */
	public AmministrazioneModel getCorrettore()
	{
		return correttore;
	}

	/**
	 * Metodo per settare un Correttore di Bozze
	 * 
	 * @param correttore
	 */
	public void setCorrettore(AmministrazioneModel correttore)
	{
		this.correttore = correttore;
	}
	
	/**
	 * Metodo per ritornare la lista dei Correttori di Bozze
	 * 
	 * @return lista dei Correttori di Bozze
	 */
	public List<AmministrazioneModel> getCorrettori(){
		
		return dsCorr.getCorrettoriBozza();
		
		
	}
	
}

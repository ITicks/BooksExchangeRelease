package it.univr.db;

import it.univr.models.AmministrazioneModel;

import java.util.List;

/**
 * DataSource che gestisce le query che riguardano gli amministratori.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class DataSourceAmministratore extends AbstractDataSourceAmministrazione {

	/** Serial Version UID. */
	private static final long serialVersionUID = 5655625247107101937L;
	
	/**
	 * Recupera un amministratore a partire dalle credenziali di accesso username e password.
	 * 
	 * @param list lista di parametri per la query
	 * @return l'amministratore se presente
	 */
	public AmministrazioneModel getAmministratore(List<Object> list){
		// Aggiungo il tipo di utente amministrativo che sto recuperando
		list.add("amministratore");
		return this.getAmministrazione(list);
	}
}

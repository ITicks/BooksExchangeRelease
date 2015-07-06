package it.univr.db;

import java.util.ArrayList;
import java.util.List;

import it.univr.models.AmministrazioneModel;

/**
 * Datasource che gestisce le query relative al correttore di bozze.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class DataSourceCorrettoreBozza extends AbstractDataSourceAmministrazione {

	/** Serial Version UID. */
	private static final long serialVersionUID = -1561938253909445371L;
	
	/**
	 * Recupero un correttore di bozza date le credenziali di accesso
	 * @param list lista di paramteri per la query
	 * @return un correttore di bozza {@code AmministrazioneModel}}
	 */
	public AmministrazioneModel getCorrettoreBozza(List<Object> list){
		list.add("correttore");
		return this.getAmministrazione(list);
	}
	
	/**
	 * Recupero la lista di correttori di bozza nell'applicazione
	 * @return lista di {@code AmministrazioneModel} dei correttori di bozza
	 */
	public List<AmministrazioneModel> getCorrettoriBozza(){
		List<Object> list = new ArrayList<Object>();
		
		list.add("correttore");
		
		return this.getAmministrazioni(list);
		
	}
	
	/**
	 * Cancello un correttore di bozza
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se la cancellazione è andato a buon fine, altrimenti {@code false}
	 */
	public boolean deleteCorrettoreBozza(List<Object> list){
		list.add("correttore");
		return this.deleteAmministrazione(list);
	}
	
	/**
	 * Inserisco un nuovo correttore di bozze
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l' inserimento è andato a buon fine, altrimenti {@code false}
	 */
	public boolean insertCorrettoreBozza(List<Object> list){
		list.add("correttore");
		return this.insertAmministrazione(list);
	}
}

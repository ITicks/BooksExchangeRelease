package it.univr.beans;

import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceInserzione;
import it.univr.db.DataSourceLibro;
import it.univr.db.DataSourceUtente;
import it.univr.messages.MessagesHandler;
import it.univr.models.AmministrazioneModel;
import it.univr.models.InserzioneModel;
import it.univr.models.PairInserzioneLibroModel;
import it.univr.utils.EmailSender;
import it.univr.utils.SendEmailInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean per la gestione del correttore di bozze.
 *
 * @author Matteo Olivato
 * @author  Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class CorrettoreBean extends AbstractUtente implements Serializable{
	
	/** Correttore loggato */
	private AmministrazioneModel correttore;
	
	/** Lista dei libri da Validare */
	private List<PairInserzioneLibroModel> listaInsLibDV;
	
	/** Email Sender */
	private SendEmailInterface emailSender;
	
	/** Messaggio Email da Inviare */
	private String emailMessage;
	
	/** Serial Version UID. */
	private static final long serialVersionUID = -5662233765654870404L;
	
	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		dsCorr = new DataSourceCorrettoreBozza();
		dsIns = new DataSourceInserzione();
		dsLib = new DataSourceLibro();
		dsUt = new DataSourceUtente();
		emailSender = new EmailSender();
	}

	@Override
	public Object verifyUser(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(getUsername());
		list.add(getPassword());
			
		correttore = dsCorr.getCorrettoreBozza(list);
		
		if (correttore == null)
           return null;
		else{
			// Aggiorno il timestamp del last login
			list = new ArrayList<Object>();
			list.add(correttore.getId());
			dsCorr.updateLastLogin(list);			
			
			//Ritorno l'oggetto amministratore per indicare
			//l'avvenuto login
			return correttore;
		}
	}
	
	public String logout() {
		
		if(correttore != null) {
			// Aggiorno il timestamp del last logout
			List<Object> list = new ArrayList<Object>();
			list.add(correttore.getId());
			dsCorr.updateLastLogout(list);			
		}
		return super.logout();
	}

	public boolean isLoggedIn() { return this.getLoggedIn(correttore); }
	
	@Override
	public void cleanUp() {
		cleanFields();
		
		this.correttore = null;
	}
		
	/**
	 * Ritorno la lista di coppie inserzione libro da validare
	 * 
	 * @return lista di coppie libro inserzione
	 */
	public List<PairInserzioneLibroModel> getInserzioniDaValidare() {

		listaInsLibDV = new ArrayList<PairInserzioneLibroModel>();

		// Recupero la lista di inserzioni
		List<InserzioneModel> ins = new ArrayList<InserzioneModel>();

		List<Object> list = new ArrayList<Object>();

		ins = dsIns.getInserzioniDaValidare();
		
		for (InserzioneModel inserzione : ins) {
			list.clear();
			list.add(inserzione.getCodice_libro());

			listaInsLibDV.add(new PairInserzioneLibroModel(inserzione, dsLib
					.getLibro(list)));
		}
		
		return listaInsLibDV;
	}
	
	/**
	 * Rifiuto l'inserzione di un utente.
	 * @param id_utente utente a cui rifiuto l'inserzione
	 * @param codice_libro codice del libro da eliminare
	 * @return la pagina da ricaricare
	 */
	public String rifiutaInserzione(int id_utente, int codice_libro){
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(id_utente);
		list.add(codice_libro);
		
		dsIns.deleteInserzione(list);
		
		list.clear();
		list.add(id_utente);
		
		// Invia una mail con la motivazione del rifiuto all'utente possessore del libro
		emailSender.sendEmail(emailMessage, dsUt.getUtente(list).getEmail());
		
		MessagesHandler.getInstance().buildMessage("emailRifiutoInviata", 
				FacesMessage.SEVERITY_INFO);
		
		return this.setState("lista_inserzioni","correttore");
	}

	/**
	 * Valido l'inserzione inserita da un Utente.
	 * @param id_utente utente che ha inserito l'inserzione
	 * @param codice_libro libro che deve essere validato
	 * @param titolo Titolo del libro che sarà utilizzato nella email di conferma dell'inserzione.
	 * @return la pagina da ricaricare.
	 */
	public String validaInserzione(int id_utente, int codice_libro, String titolo) {
		List<Object> list = new ArrayList<Object>();

		list.add(correttore.getId());
		list.add(id_utente);
		list.add(codice_libro);

		dsIns.validateInserzione(list);

		list.clear();
		list.add(id_utente);
		// Invia una mail con la motivazione del rifiuto all'utente possessore
		// del libro
		emailSender.sendEmail("Buongiorno,\n Il libro '\""
				+ titolo
				+ "\" da lei inserito è stato validato.\n Cordiali saluti,\n\nBookExchange",
				dsUt.getUtente(list).getEmail());
		
		MessagesHandler.getInstance().buildMessage("emailAccettazioneInviata", 
				FacesMessage.SEVERITY_INFO);
		
		return this.setState("lista_inserzioni","correttore");
	}

	public AmministrazioneModel getCorrettore() { return correttore; }
	
	public String getEmailMessage() {
		return emailMessage;
	}

	public void setEmailMessage(String emailMessage) {
		if(!emailMessage.equals("")) this.emailMessage = emailMessage;
	}
}

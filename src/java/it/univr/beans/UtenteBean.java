package it.univr.beans;


import it.univr.db.DataSourceInserzione;
import it.univr.db.DataSourceLibro;
import it.univr.db.DataSourceUtente;
import it.univr.messages.MessagesHandler;
import it.univr.models.InserzioneModel;
import it.univr.models.LibroModel;
import it.univr.models.PairInserzioneLibroModel;
import it.univr.models.UtenteModel;
import it.univr.utils.FTPFileUploader;
import it.univr.utils.FileUploadInterface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Bean degli Utenti Standard. 
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class UtenteBean extends AbstractUtente implements Serializable {

    /** Serial Version UID. */
	private static final long serialVersionUID = -3497344115815220064L;
	
	/** Lista inserzioni libri che sono stati validati */
	private List<PairInserzioneLibroModel> listaInsLibV;
	
	/** Lista inserzioni libri che non sono ancora stati validati */
	private List<PairInserzioneLibroModel> listaInsLibNV;
	
	/** L'Utente. */
	private UtenteModel utente;
	
	/** Nuovo libro che vuole inserire l'utente */
	private LibroModel libro;
	
	/** Uploader di file tramite FTP */
	private FileUploadInterface fileUploader;
	
	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		dsUt = new DataSourceUtente();
		dsIns = new DataSourceInserzione();
		dsLib = new DataSourceLibro();
		this.libro = new LibroModel();
		this.fileUploader = new FTPFileUploader();
	}
	
	public UtenteModel getUtente() { return utente; }
	
	public LibroModel getLibro() { return libro; }

	public void setLibro(LibroModel libro) { this.libro = libro; }
	
	public FileUploadInterface getFileUploader() { return fileUploader; }

	public void setFileUploader(FTPFileUploader fTPFileUploader) { this.fileUploader = fTPFileUploader; }
	
	public Object verifyUser(String username, String password)
	{
		
		this.setPassword(password);
		this.setUsername(username);
		
		List<Object> list = new ArrayList<Object>();
		
		list.add(this.getUsername());
		list.add(this.getPassword());
		
		utente = dsUt.checkUtente(list);
		
		if (utente == null)
           return null;
		else{
			//Recupero il Bean della Ricerca
			ELContext elContext = FacesContext.getCurrentInstance().getELContext();
			RicercaBean ricercaBean = (RicercaBean) FacesContext.getCurrentInstance()
					.getApplication().getELResolver()
					.getValue(elContext, null, "ricercaBean");
			ricercaBean.setUtente(utente);
			return utente;
		
		}
	}
	
	/**
	 * Verifico se l'utente standard è loggato nel sistema
	 * @return {@code true} se l'utente standard è loggato
	 */
	public boolean isLoggedIn() { return this.getLoggedIn(utente); }
	
	/**
	 * Pulisco il Bean dagli oggetti non necessari
	 * dopo un logout.
	 */
	@Override
	public void cleanUp()
	{
		cleanFields();
		this.resetListaInserzioniLibri();
		this.utente = null;
		this.libro = null;
	}
	
	/**
	 * Carico una nuovoa foto profilo Utente
	 * @return la pagina de profilo da ricaricare
	 */
	public String uploadFotoProfilo(){
		fileUploader.upload();
		
		List<String> strnameparts = new ArrayList<String>();
		
		strnameparts.add(""+utente.getId());
		strnameparts.add(utente.getUsername());
		
		//Normalizzo il nome del file che andrò a salvare
		String nomefile = fileUploader.normalizeUploadFileName(strnameparts);
		fileUploader.save(nomefile);
		utente.setFoto(fileUploader.getFilename());
		
		List<Object> list = new ArrayList<Object>();
		list.add(utente.getFoto());
		list.add(utente.getUsername());
		
		//Modifico la foto Profilo dell'utente
		dsUt.modificaFoto(list);
		
		return this.setState("profilo", "utente");
	}
	
	/**
	 * Resetto la lista di inserzioni libri così da ricaricarli
	 * quando serve
	 */
	private void resetListaInserzioniLibri() {
		this.listaInsLibV=null;
		this.listaInsLibNV=null;
	}

	/**
	 * Ritorno la lista di coppie inserzione libro validati dal correttore di bozza
	 * @return lista di coppie libro inserzione
	 */
	public List<PairInserzioneLibroModel> getListaInsLibValidati() {

		listaInsLibV = new ArrayList<PairInserzioneLibroModel>();

		// Recupero la lista di inserzioni
		List<InserzioneModel> ins = new ArrayList<InserzioneModel>();

		List<Object> list = new ArrayList<Object>();

		list.add(utente.getId());

		ins = dsIns.getInserzioniValidate(list);

		for (InserzioneModel inserzione : ins) {
			list.clear();
			list.add(inserzione.getCodice_libro());

			listaInsLibV.add(new PairInserzioneLibroModel(inserzione, dsLib
					.getLibro(list)));
		}

		return listaInsLibV;
	}
	
	/**
	 * Ritorno la lista di coppie inserzione libro non ancora validati
	 * @return lista di coppie libro inserzione
	 */
	public List<PairInserzioneLibroModel> getListaInsLibDaValidare() {
		listaInsLibNV = new ArrayList<PairInserzioneLibroModel>();

		// Recupero la lista di inserzioni
		List<InserzioneModel> ins = new ArrayList<InserzioneModel>();

		List<Object> list = new ArrayList<Object>();

		list.add(utente.getId());

		ins = dsIns.getInserzioniNonValidate(list);

		for (InserzioneModel inserzione : ins) {
			list.clear();
			list.add(inserzione.getCodice_libro());

			listaInsLibNV.add(new PairInserzioneLibroModel(inserzione, dsLib
					.getLibro(list)));
		}

		return listaInsLibNV;
	}

	/**
	 * Calcello una inserzione e il relativo libro
	 * @param codice_libro il codice univoco del libro
	 * @return la pagina da ricaricare
	 */
	public String deleteInserzioneLibro(int codice_libro){
		List<Object> list = new ArrayList<Object>();
		
		list.add(utente.getId());
		list.add(codice_libro);
		dsIns.deleteInserzione(list);
		
		list.clear();
		list.add(codice_libro);
		dsLib.deleteLibro(list);
		
		//Resetto la lista delle inserzioni
		this.resetListaInserzioniLibri();
		
		MessagesHandler.getInstance().buildMessage("libroCancellato", FacesMessage.SEVERITY_INFO);
		
		//Ritorno lo stato della pagina da ricaricare
		return "utente";
	}
	
	/**
	 * Modifico la disponibilita di un libro 
	 * @param codice_libro il codice univoco del libro
	 * @param stato stato della disponibilita che si vuole settatare: 
	 * 		{@code true} = disponibile, {@code false} = non disponibile
	 * @return la pagina da ricaricare
	 */
	public String setDisponibilita(int codice_libro, boolean stato){
		List<Object> list = new ArrayList<Object>();
		
		list.add(stato);
		list.add(utente.getId());
		list.add(codice_libro);
		
		//Setto la disponibilita di quella inserzione
		dsIns.setDisponibilitaInserzione(list);
		
		this.resetListaInserzioniLibri();
		
		//Ritorno lo stato della pagina da ricaricare
		return "utente";
	}
	
	/**
	 * Inserisco una nuova inserzione ed un nuovo libro
	 * @return la pagina da ricaricare che mostra il nuovo libro inserito
	 */
	public String insertNuovoLibro(){
		
		//Carico la foto inserita
		uploadFotoLibro();
		
		List<Object> list = new ArrayList<Object>() ;
		
		//Inserisco un nuovo libro
		list.add(libro.getISBN());
		list.add(libro.getAutore());
		list.add(libro.getTitolo());
		list.add(libro.getGenere());
		list.add(libro.getDescrizione());
		list.add(libro.getAnno());
		list.add(libro.getCasa_editrice());
		list.add(libro.getLingua());
		list.add(libro.getPagine());
		list.add(libro.getFoto());
		
		dsLib.insertLibro(list);
		
		//Recupero il codice assegnato al nuovo libro
		list.clear();
		list.add(libro.getISBN());
		list.add(libro.getTitolo());		
		int codice = dsLib.getCodiceLibro(list);
		
		//Inserisco la nuova inserzione riferita al libro
		list.clear();
		list.add(utente.getId());
		list.add(codice);
		dsIns.insertInserzione(list);
		
		//Resetto la lista di inserzioni libri che sarà ricaricata
		this.resetListaInserzioniLibri();
		
		return this.setState("imiei_libri", "utente");
	}
	
	/**
	 * Eseguo l'upload remoto dell'immagine del libro inserito 
	 * e poi aggiornandone il campo foto.
	 */
	private void uploadFotoLibro(){
		List<String> list = new ArrayList<String>();
		
		list.add(libro.getISBN());
		list.add(String.valueOf(libro.getAnno().toString()));
		
		//Normalizzo il nome del file che andrò a salvare
		String nomefile = fileUploader.normalizeUploadFileName(list);
		//Aggiorno il campo del libro relativo alla foto appena caricata
		fileUploader.save(nomefile);
		libro.setFoto(fileUploader.getFilename());
	}
	
	/**
	 * Setto il libro da visualizzare nel {@code LibroBean}
	 * @param codice codice del libro da visualizzare
	 * @return la pagina di visualizzazione dei dati del libro
	 */
	public String getLibroBean(int codice){
		
		//Recupero il Bean dei libri
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		LibroBean libroBean = (LibroBean) FacesContext.getCurrentInstance()
				.getApplication().getELResolver()
				.getValue(elContext, null, "libroBean");
		
		libroBean.getLibro(codice);
		
		return this.setState("dati_libro", "utente");
	}
	
	/**
	 * Indico se le liste dei libri sono vuote
	 * @return {@code true} se la lista dei libri è vuota, altrimenti {@code false}
	 */
	public boolean areListsEmpty() {
		return listaInsLibNV.isEmpty() && listaInsLibV.isEmpty();
	}
}

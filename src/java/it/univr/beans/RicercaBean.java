package it.univr.beans;

import it.univr.db.DataSourceInserzione;
import it.univr.db.DataSourceLibro;
import it.univr.db.DataSourceUtente;
import it.univr.models.Area;
import it.univr.models.FilterModel;
import it.univr.models.InserzioneModel;
import it.univr.models.LibroModel;
import it.univr.models.PairUtenteLibroModel;
import it.univr.models.UtenteModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 * Bean per la ricerca di un Libro.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class RicercaBean extends ViewStateBean implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = 7723552566746113674L;
	
	/** Filtro per specificare i parametri di ricerca */
	private FilterModel filtro;
	
	/** L'utente che ha effettuato la ricerca */
	private UtenteModel utente;
	
	/** Lista inserzioni libri che sono stati validati */
	private List<PairUtenteLibroModel> listaLibriCercati;
	
	/** Inizializza il bean. */
	@PostConstruct
	public void initialize() {
		dsUt = new DataSourceUtente();
		dsIns = new DataSourceInserzione();
		dsLib = new DataSourceLibro();
		this.filtro = new FilterModel();
		this.listaLibriCercati = new ArrayList<PairUtenteLibroModel>();
	}
	
	/**
	 * Ricerco il libro o i libri corrispondenti ai parametri di ricerca dentro il filtro.
	 * @return la pagina da ricaricare
	 */
	public String ricercaLibro() {
		listaLibriCercati = new ArrayList<PairUtenteLibroModel>();

		List<Object> list = new ArrayList<Object>();

		// Recupero la lista di libri ricercati
		List<LibroModel> libri = new ArrayList<LibroModel>();

		list.add(filtro.getGenere().toString());
		list.add(filtro.getTitolo());
		list.add(filtro.getAutore());

		libri = dsLib.ricercaLibri(list);
		
		// Recupero gli utenti che hanno inserito i libri
		if (libri.size() > 0)
			for (LibroModel libro : libri) {
				if(libro != null){
					list.clear();
					list.add(libro.getCodice());
					List<Object> temp = new ArrayList<Object>();
	
					InserzioneModel instemp = dsIns.getInserzione(list);
					
					if(instemp != null){
						temp.add(instemp.getId_utente());
						listaLibriCercati.add(new PairUtenteLibroModel(dsUt
							.getUtente(temp), libro, dsIns.getInserzione(list).getDisponibilita()));
					}
				}
			}
		
		if (listaLibriCercati.size() > 0) {
			List<PairUtenteLibroModel> temp = listaLibriCercati;

			listaLibriCercati = new ArrayList<PairUtenteLibroModel>();
			// Filtro per area dell'utente
			for (PairUtenteLibroModel pulm : temp) {
				if (filtro.getArea().equals(Area.COMUNE)
						&& pulm.getUtente().getComune()
								.equals(utente.getComune()))
					listaLibriCercati.add(pulm);

				else if (filtro.getArea().equals(Area.PROVINCIA)
						&& pulm.getUtente().getProvincia()
								.equals(utente.getProvincia()))
					listaLibriCercati.add(pulm);

				else if (filtro.getArea().equals(Area.REGIONE)
						&& pulm.getUtente().getRegione()
								.equals(utente.getRegione()))
					listaLibriCercati.add(pulm);

			}
		}
		
		return this.setState("ricerca_libri", "utente");
	}
	
	/**
	 * Invio la richiesta di prestito di un libro tramite email al suo possessore.
	 * @param utentePossessore possessore del libro
	 * @param libro libro da richiedere in prestito
	 * @return la pagina di richiesta del libro
	 */
	public String richiediLibro(UtenteModel utentePossessore, LibroModel libro){
		// Recupero il Bean della Richiesta
		ELContext elContext = FacesContext.getCurrentInstance().getELContext();
		RichiestaBean richiestaBean = (RichiestaBean) FacesContext.getCurrentInstance()
				.getApplication().getELResolver()
				.getValue(elContext, null, "richiestaBean");
		
		richiestaBean.setUtentePossessore(utentePossessore);
		richiestaBean.setLibro(libro);
		richiestaBean.setUtenteRichiedente(utente);
		richiestaBean.writeStandardEmailMessage();
				
		return this.setState("richiesta_libro", "utente");
	}
	
	public FilterModel getFiltro() { return filtro;	}

	public void setFiltro(FilterModel filtro) { this.filtro = filtro; }

	public UtenteModel getUtente() { return utente;	}

	public void setUtente(UtenteModel utente) {	this.utente = utente; }
	
	/**
	 * Se la lista non è vuota ritorno la lista dei libri cercati.
	 * @return lista dei libri cercati.
	 */
	public List<PairUtenteLibroModel> getListaLibriCercati() {
		if(utente!=null && filtro != null && !filtro.isEmpty()) this.ricercaLibro();
		return listaLibriCercati;
	}

	public void setListaLibriCercati(List<PairUtenteLibroModel> listaLibriCercati) {
		this.listaLibriCercati = listaLibriCercati;
	}

}

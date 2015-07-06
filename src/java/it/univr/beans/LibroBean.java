package it.univr.beans;

import it.univr.db.DataSourceLibro;
import it.univr.models.LibroModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean del Libro. 
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

@ManagedBean
@SessionScoped
public class LibroBean extends ViewStateBean implements Serializable {

    /** Serial Version UID. */
	private static final long serialVersionUID = 6464215260521061434L;

	/** Il Libro da visualizzare. */
	private LibroModel libro;
	
	/** Inizializza il bean */
	@PostConstruct
	public void initialize() { dsLib = new DataSourceLibro(); }

	/**
	 * Recupero un libro a partire dal suo codice.
	 * @param codice codice del libro da recuperare
	 * @return il libro {@code LibroModel} recuperato
	 */
	public LibroModel getLibro(int codice){
		List<Object> list = new ArrayList<Object>();
		list.add(codice);
		dsLib.incrementNVis(list);
		this.libro = dsLib.getLibro(list);
		return libro;
	}
	
	public LibroModel getLibro() {
		List<Object> list = new ArrayList<Object>();
		list.add(libro.getCodice());
		dsLib.incrementNVis(list);
		
		return libro;
	}

	public void setLibro(LibroModel libro) { this.libro = libro; }

}

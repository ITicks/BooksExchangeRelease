package it.univr.models;

import java.io.Serializable;

/**
 * Model che accoppia un Utente al suo Libro
 */
public class PairUtenteLibroModel implements Serializable {
	
	/** Serial Version UID. */
	private static final long serialVersionUID = -246108706907580537L;
	
	private LibroModel libro;
	private UtenteModel utente;
	private String disponibilita;
	
	public PairUtenteLibroModel(UtenteModel utente, LibroModel libro, String disponibilita){
		this.utente = utente;
		this.libro = libro;
		this.disponibilita = disponibilita;
	}
	
	public LibroModel getLibro() { return libro; }
	
	public void setLibro(LibroModel libro) { this.libro = libro; }
	
	public UtenteModel getUtente() { return utente; }
	
	public void setUtente(UtenteModel utente) { this.utente = utente; }

	public String getDisponibilita() { return disponibilita; }

	public void setDisponibilita(String disponibilita) {
		this.disponibilita = disponibilita;
	}
}

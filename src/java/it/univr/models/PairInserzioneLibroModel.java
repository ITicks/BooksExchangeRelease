package it.univr.models;

import java.io.Serializable;

/**
 * Model che accoppia una inserzione e un libro
 */
public class PairInserzioneLibroModel implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = 7948782996297651379L;

	private LibroModel libro;
	private InserzioneModel inserzione;
	
	public PairInserzioneLibroModel(InserzioneModel inserzione,LibroModel libro){
		this.inserzione = inserzione;
		this.libro = libro;
	}
	
	public LibroModel getLibro() { return libro; }
	
	public void setLibro(LibroModel libro) { this.libro = libro; }
	
	public InserzioneModel getInserzione() { return inserzione; }
	
	public void setInserzione(InserzioneModel inserzione) { 
		this.inserzione = inserzione;
	}
}

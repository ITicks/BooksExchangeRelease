package it.univr.models;

import java.io.Serializable;

public class InserzioneModel implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = 4810801005864278922L;

	private int id_utente;
	private int id_amministrazione;
	private int codice_libro;
	private boolean disponibilita;

	public int getId_utente() { return id_utente; }

	public void setId_utente(int id_utente) { this.id_utente = id_utente; }

	public int getId_amministrazione() { return id_amministrazione; }

	public void setId_amministrazione(int id_amministrazione) {
		this.id_amministrazione = id_amministrazione;
	}

	public int getCodice_libro() { return codice_libro; }

	public void setCodice_libro(int codice_libro) { 
		this.codice_libro = codice_libro;
	}

	public String getDisponibilita() {
		return disponibilita ? "disponibile" : "non disponibile";
	}

	public void setDisponibilita(boolean disponibilita) {
		this.disponibilita = disponibilita;
	}

}

package it.univr.models;

import java.io.Serializable;

public class UtenteModel implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = -6086967456070466114L;

	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private String indirizzo;
	private String comune;
	private String provincia;
	private String regione;
	private String foto = "avatar_anonimo.jpg";
	private String n_cell;
	private boolean confermato;

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getNome() { return nome; }

	public void setNome(String nome) { this.nome = nome; }

	public String getCognome() { return cognome; }

	public void setCognome(String cognome) { this.cognome = cognome; }

	public String getUsername() { return username; }

	public void setUsername(String username) { this.username = username; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getEmail() { return email; }

	public void setEmail(String email) { this.email = email; }

	public String getIndirizzo() { return indirizzo; }

	public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

	public String getComune() { return comune; }

	public void setComune(String comune) { this.comune = comune; }

	public String getProvincia() { return provincia; }

	public void setProvincia(String provincia) { this.provincia = provincia; }

	public String getRegione() { return regione; }

	public void setRegione(String regione) { this.regione = regione; }

	public String getFoto() { return foto; }

	public void setFoto(String foto) { this.foto = foto; }

	public String getN_cell() { return n_cell; }

	public void setN_cell(String n_cell) { this.n_cell = n_cell; }

	public boolean isConfermato() { return confermato; }

	public void setConfermato(boolean confermato) {
		this.confermato = confermato;
	}

}

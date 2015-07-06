package it.univr.models;


import java.io.Serializable;
import java.sql.Timestamp;

public class AmministrazioneModel implements Serializable
{

	/** Serial Version UID. */
	private static final long serialVersionUID = -1426065474072522810L;

	private int id;
	private String nome;
	private String cognome;
	private String username;
	private String password;
	private String email;
	private String n_cell;
	private String tipo;
	private Timestamp last_login;
	private Timestamp last_logout;

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

	public String getN_cell() { return n_cell; }

	public void setN_cell(String n_cell) { this.n_cell = n_cell; }

	public String getTipo() { return tipo; }

	public void setTipo(String tipo) { this.tipo = tipo; }

	public Timestamp getLast_login() { return last_login; }

	public void setLast_login(Timestamp last_login) {
		this.last_login = last_login;
	}

	public Timestamp getLast_logout() { return last_logout; }

	public void setLast_logout(Timestamp last_logout) {
		this.last_logout = last_logout;
	}

}

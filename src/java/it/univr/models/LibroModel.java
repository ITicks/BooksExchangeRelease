package it.univr.models;

import java.io.Serializable;
import java.sql.Date;

public class LibroModel implements Serializable {

	/** Serial Version UID. */
	private static final long serialVersionUID = -1528175457623748926L;

	private int codice;
	private String ISBN;
	private String autore;
	private String titolo;
	private String genere;
	private String descrizione;
	private Date anno;
	private String casa_editrice;
	private String lingua;
	private int pagine;
	private String foto;
	private int n_visualizzazioni;

	public int getCodice() { return codice; }

	public void setCodice(int codice) { this.codice = codice; }

	public String getISBN() { return ISBN; }

	public void setISBN(String iSBN) { ISBN = iSBN; }

	public String getAutore() { return autore; }

	public void setAutore(String autore) { this.autore = autore; }

	public String getTitolo() { return titolo; }

	public void setTitolo(String titolo) { this.titolo = titolo; }

	public String getGenere() { return genere; }

	public void setGenere(String genere) { this.genere = genere; }

	public String getDescrizione() { return descrizione; }

	public void setDescrizione(String descrizione) { 
		this.descrizione = descrizione;
	}

	public Date getAnno() { return anno; }

	public void setAnno(Date anno) { this.anno = anno; }

	public String getCasa_editrice() { return casa_editrice; }

	public void setCasa_editrice(String casa_editrice) { 
		this.casa_editrice = casa_editrice;
	}

	public String getLingua() { return lingua; }

	public void setLingua(String lingua) { this.lingua = lingua; }

	public int getPagine() { return pagine; }

	public void setPagine(int pagine) { this.pagine = pagine; }

	public String getFoto() { return foto; }

	public void setFoto(String foto) { this.foto = foto; }

	public int getN_visualizzazioni() { return n_visualizzazioni / 11; }

	public void setN_visualizzazioni(int n_visualizzazioni) {
		this.n_visualizzazioni = n_visualizzazioni;
	}

}
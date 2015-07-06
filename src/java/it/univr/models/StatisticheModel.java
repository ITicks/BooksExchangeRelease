package it.univr.models;

public class StatisticheModel {
	
	/** Numero utenti registrati*/
	private int nur;
	
	/** Numero inserzioni validate*/
	private int niv;
	
	/** Numero inserzioni da validare*/
	private int nidv;
	
	/** Numero correttori di bozze*/
	private int ncb;

	public int getNur() { return nur; }

	public void setNur(int nur) { this.nur = nur; }

	public int getNiv() { return niv; }

	public void setNiv(int niv) { this.niv = niv; }

	public int getNidv() { return nidv; }

	public void setNidv(int nidv) { this.nidv = nidv; }

	public int getNcb() { return ncb; }

	public void setNcb(int ncb) { this.ncb = ncb; }
	
}

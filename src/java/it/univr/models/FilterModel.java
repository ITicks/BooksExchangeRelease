package it.univr.models;

import java.util.ArrayList;
import java.util.List;

public class FilterModel {

	private Genere genere;
	private String titolo;
	private String autore;
	private Area area;
	private Lingua lingua;
	
	public Genere getGenere() {	return genere;	}
	
	public void setGenere(Genere genere) { this.genere = genere; }
	
	public String getTitolo() {	return titolo; }
	
	public void setTitolo(String titolo) { this.titolo = titolo; }
	
	public String getAutore() {	return autore; }
	
	public void setAutore(String autore) { this.autore = autore; }
	
	public Area getArea() {	return area; }
	
	public void setArea(Area area) { this.area = area; }
	
	public Lingua getLingua() { return lingua; }
	
	public void setLingua(Lingua lingua) { this.lingua = lingua; }
	
	public static List<Genere> listaGenere( boolean nessuno){
		List<Genere> list = new ArrayList<Genere>();
		
		for(Genere gen : Genere.values())
			list.add(gen);
		
		if(!nessuno) list.remove(0);
			
		return list;
	}
	
	public static List<Lingua> listaLingua(){
		List<Lingua> list = new ArrayList<Lingua>();
		
		for(Lingua lin : Lingua.values())
			list.add(lin);
		
		return list;
	}
	
	public static List<Area> listaArea(){
		List<Area> list = new ArrayList<Area>();
		
		for(Area ar : Area.values())
			list.add(ar);
		
		return list;
	}
	
	public boolean isEmpty() {
		return genere == null || area == null 
				|| titolo == null || autore == null;
	}
	
}

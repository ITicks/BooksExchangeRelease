package it.univr.utils;

/**
 * Classe di utilities generiche per le stringhe. 
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

public class StringUtils {
	
    /**
     * Conte le occorrenze di {@code occurence} nella stringa {@code str}.
     *
     * @param str la stringa in cui cercare le occorrenze.
     * @param occurrence le occorrenze da cercare.
     * @return il numero di occorrenze nella stringa.
     */
	public static int countMatches(String str, String occurrence) {
		
		return str.length() - str.replace(occurrence, "").length();
	}

}

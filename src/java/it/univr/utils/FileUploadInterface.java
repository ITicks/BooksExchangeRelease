package it.univr.utils;

import java.util.List;

/**
 * Interfaccia per l'upload di file.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public interface FileUploadInterface {
	
	/** Eseguo l'upload in memoria del file */
	public void upload();
	
	/**
	 * Salvo il file su filesystem, nella directory locale o remota.
	 * @param nomefile nome del file da salvare
	 */
	public void save(String nomefile);
	
	/**
	 * Ritorno il nome del file da salvare normalizzato.
	 * Ovvero a sconda delle stringhe passate come parametro genero un nome file valido.
	 * @param strnameparts Le stringhe di cui dovrà essere composto il nome del file
	 * @return Il nome del file normalizzato
	 */
	public String normalizeUploadFileName(List<String> strnameparts);
	
	/**
	 * Recupero il nome del file normalizzato
	 * @return il nome del file da salvare
	 */
	public String getFilename();
}

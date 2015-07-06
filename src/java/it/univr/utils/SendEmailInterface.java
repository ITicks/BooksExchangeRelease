package it.univr.utils;

/**
 * Interfaccia per l'invio delle email
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public interface SendEmailInterface {
	
	/**
	 * Invio una email
	 * @param emailBody testo della mail da inviare
	 * @param to ricevitore della mail
	 * @return {@code true} se è riuscito ad inviare la mail, altrimenti {@code false}
	 */
	public boolean sendEmail(String emailBody, String to);
	
}

package it.univr.tests;

import it.univr.beans.UtenteBean;

import org.junit.Test;

public class UtenteBeanTest {

	/**
	 * Testo le funzionalità principali di Utente Bean
	 */
	@Test
	public void testInitialize() {
		UtenteBean ub = new UtenteBean();
		ub.initialize();

		assert (ub.getLibro() != null);
		assert (ub.getFileUploader() != null);
		assert (ub.getUtente() == null);
		assert (ub.areListsEmpty());
	}

	@Test
	public void testIsLoggedIn() {
		UtenteBean ub = new UtenteBean();

		ub.verifyUser("teo", "ol");

		assert (ub.getUtente() != null);
		assert (ub.getUsername().equals("teo"));
		assert (ub.getPassword().equals("ol"));
	}

	@Test
	public void testCleanUp() {
		UtenteBean ub = new UtenteBean();

		ub.cleanUp();
		assert (ub.areListsEmpty());
		assert (ub.getUtente() == null);
		assert (ub.getLibro() == null);
	}
	
	@Test
	public void testUploadFotoProfilo() {
		UtenteBean ub = new UtenteBean();

		String temp = ub.uploadFotoProfilo();
		
		assert (!ub.getUtente().getFoto().equals("avatar_anonimo.jpg"));
		assert (temp.equals("utente"));
		assert (ub.getPageState().equals("profilo"));
	}
	
	@Test
	public void testInserimentoNuovoLibro(){
		UtenteBean ub = new UtenteBean();

		String temp = ub.insertNuovoLibro();
		
		assert (temp.equals("utente"));
		assert (ub.getPageState().equals("imiei_libri"));
		assert (ub.areListsEmpty());
	}

}

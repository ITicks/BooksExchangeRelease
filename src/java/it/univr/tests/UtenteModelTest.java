package it.univr.tests;

import static org.junit.Assert.assertEquals;
import it.univr.models.UtenteModel;

import org.junit.Test;

public class UtenteModelTest {
	
	/**
	 * Testo il setup della foto automatica per il profilo dell'utente
	 */
	@Test
	public void testGetFoto() {
		UtenteModel ut = new UtenteModel();
		
		assertEquals("avatar_anonimo.jpg",ut.getFoto());
	}

}

package it.univr.tests;

import it.univr.utils.EmailSender;

import org.junit.Test;

public class EmailSenderTest {
	
	/**
	 * Testo l'effettivo invio di una email da parte dell'EMail Sender
	 */
	@Test
	public void testSendEmail() {
		EmailSender es = new EmailSender();

		assert(es.sendEmail("Test dell'Email Sender", "matteoolivato@yahoo.it"));
	}

}

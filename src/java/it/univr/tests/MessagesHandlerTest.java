package it.univr.tests;

import it.univr.messages.MessagesHandler;

import javax.faces.application.FacesMessage;

import org.junit.Test;

public class MessagesHandlerTest {

	/**
	 * Testo l'effettivo funzionamento del generatore di messaggi di Errore
	 */
	@Test
	public void testBuildMessage() {
		assert(MessagesHandler.getInstance().buildMessage("loginFailed", FacesMessage.SEVERITY_ERROR));
	}

}

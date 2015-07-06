package it.univr.messages;

import it.univr.exceptions.MessagesException;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

/**
 * Gestore dei messaggi (Singleton). Questa classe fornisce i metodi per
 * stampare messaggi sulla pagina JSF attuale all'invocazione del metodo.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

public class MessagesHandler implements Serializable {
    
    /** Serial Version UID. */
    private static final long serialVersionUID = -6360313160764062479L;

    /** Package contenente i file dei messaggi. */
    private static final String messagesPackage = "/it/univr/properties/";
    
    /** Istanza. */
    private static MessagesHandler instance;
    
    /** Contenitore della proprietà lette dal file di properties. */
    private final Properties properties;
    
    
    /** Costruttore privato. */
    private MessagesHandler() {
        
        properties = new Properties();
        // Ottiene la lingua corrente
        Locale locale =
                FacesContext.getCurrentInstance().getViewRoot().getLocale();

        // Carica il file di properties corretto
        try {
            
            String path =
                    messagesPackage + "messages_" + locale.getLanguage()
                            + ".properties";

            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(path));
        }
        catch (IOException e) {
            throw new MessagesException();
        }
    }
    
    /**
     * Ritorna l'istanza unica in tutto il programma di questa classe.
     * 
     * @return l'istanza di questa classe.
     */
    public static synchronized MessagesHandler getInstance() {
        
        if (instance == null)
            instance = new MessagesHandler();
        
        return instance;
    }
    
    /**
     * Stampa il messaggio di chiave {@code key} sulla pagina corrente.
     * 
     * @param key la chiave del messaggio da stampare.
     * @param s il livello di severity.
     */
    public boolean buildMessage(String key, Severity s) {
        
        FacesMessage msg = new FacesMessage(properties.getProperty(key));
        msg.setSeverity(s);
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
        return true;
    }

}

package it.univr.exceptions;

import java.util.Iterator;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 * Personalizzazione dell'exception handler per il redirect su una pagina di
 * errore a fronte di una runtime exception.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

public class CustomExceptionHandler extends ExceptionHandlerWrapper {
    
    private ExceptionHandler wrapped;

    
    public CustomExceptionHandler(ExceptionHandler exception) {
        this.wrapped = exception;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        
        Iterator<ExceptionQueuedEvent> i =
                getUnhandledExceptionQueuedEvents().iterator();
        
        while (i.hasNext()) {
            
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context =
                    (ExceptionQueuedEventContext) event.getSource();

            // Ottiene l'eccezione
            Throwable t = context.getException();

            FacesContext fc = FacesContext.getCurrentInstance();
            Map<String, Object> requestMap =
                    fc.getExternalContext().getRequestMap();
            NavigationHandler nav =
                    fc.getApplication().getNavigationHandler();

            // Gestione dell'eccezione
            try {
                
                // Redirezione alla pagina di errore
                requestMap.put("exceptionMessage", t.getMessage());
                nav.handleNavigation(fc, null, "redirectToError");
                fc.renderResponse();
            }
            finally {
                i.remove();
            }
        }

        getWrapped().handle();
    }
    
}
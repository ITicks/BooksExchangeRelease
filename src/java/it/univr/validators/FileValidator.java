package it.univr.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;

/**
 * Validatore per il tipo e la dimensione del file inserito dall' utente.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
@FacesValidator
public class FileValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Part part = (Part) value;
        if(part.getSize()>8388608){
           throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "file is too large", "file is too large"));
        }
    }

}
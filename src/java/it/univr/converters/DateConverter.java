package it.univr.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.DateTimeConverter;

/**
 * Converte una data inserita nel form nel formato adatto per il database.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

@FacesConverter("it.univr.converters.DateConverter")
public class DateConverter implements Converter {
	
	@Override
    public Object getAsObject(FacesContext context, UIComponent component,
            String value) {

	    DateTimeConverter dtc = new DateTimeConverter();
		
		dtc.setPattern("yyyy-M-d");
		
        java.util.Date temp =
                (java.util.Date) dtc.getAsObject(context, component, value);

		if (temp == null)
		    return null;
		
		java.sql.Date result = new java.sql.Date(temp.getTime());
		
		return result;
	}

    @Override
    public String getAsString(FacesContext context, UIComponent component,
            Object value) {
        return value.toString();
    }

	
}

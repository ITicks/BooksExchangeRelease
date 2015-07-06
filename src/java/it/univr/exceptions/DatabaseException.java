package it.univr.exceptions;

/**
 * Eccezione lanciata dal modulo che interagisce con il database.
 */

public class DatabaseException extends RuntimeException {

    private static final long serialVersionUID = 7990943681189159020L;
    
    
    public DatabaseException() {
        super();
    }
    
}

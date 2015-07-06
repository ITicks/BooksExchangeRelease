package it.univr.beans;

import it.univr.db.DataSourceAmministratore;
import it.univr.db.DataSourceCorrettoreBozza;
import it.univr.db.DataSourceInserzione;
import it.univr.db.DataSourceLibro;
import it.univr.db.DataSourceStatistiche;
import it.univr.db.DataSourceUtente;

/**
 * Classe astratta che contiene tutti i Datasource necessari ai Bean.

 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public abstract class AbstractDataSourceBean {

	/** DataSource dell'Amministratore */
	protected static DataSourceAmministratore dsAmm;
	
	/** DataSource del correttore di Bozze */
	protected static DataSourceCorrettoreBozza dsCorr;
	
	/** Datasource delle Inserzioni */
	protected static DataSourceInserzione dsIns;
	
	/** Il DataSource del Libro */
	protected static DataSourceLibro dsLib;
	
	/** DataSource delle Statistiche */
	protected static DataSourceStatistiche dsStats;
	
	/** Datasource Utente. */
	protected static DataSourceUtente dsUt;
	
}

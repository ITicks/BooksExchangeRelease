package it.univr.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.univr.exceptions.DatabaseException;
import it.univr.models.InserzioneModel;

/**
 * Datasource che gestisce le query relative alle inserzioni.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class DataSourceInserzione extends AbstractDataSource {
	
	/** Recupero le inserzioni da validare per Correttore di Bozze */
	private String queryInserzioniDaValidare = "SELECT id_utente, id_amministrazione, codice_libro, disponibilita "
			+ "FROM Inserzione WHERE id_amministrazione IS NULL";
	
	/** Recupero le inserzioni dell'Utente non validate*/
	private String queryInserzioniValidate = "SELECT id_utente, id_amministrazione, codice_libro, disponibilita "
			+ "FROM Inserzione WHERE id_amministrazione IS NOT NULL AND id_utente = ? ";
	
	/** Recupero le inserzioni dell'utente validate */
	private String queryInserzioniNonValidate = "SELECT id_utente, id_amministrazione, codice_libro, disponibilita "
			+ "FROM Inserzione WHERE id_amministrazione IS NULL AND id_utente = ?";
	
	/** Recupero le inserzioni dell'utente validate */
	private String queryInserzioneLibro = "SELECT id_utente, id_amministrazione, codice_libro, disponibilita "
			+ "FROM Inserzione WHERE id_amministrazione IS NOT NULL AND codice_libro = ?";
	
	/** Inserisco una nuova inserzione */
	private String insertInserzione = "INSERT INTO Inserzione VALUES( ?, NULL, ?, 'true')";
	
	/** Cancello una inserzione per un libro*/
	private String deleteInserzione = "DELETE FROM Inserzione WHERE id_utente = ? AND codice_libro = ?";
	
	/** Aggiorno la disponibilita relativa all'inserzione */
	private String updateDispIns = "UPDATE Inserzione SET disponibilita = ? WHERE id_utente = ? AND codice_libro = ? ";
	
	/** Valido l'inserzione di un utente */
	private String validateIns = "UPDATE Inserzione SET id_amministrazione = ? WHERE id_utente = ? AND codice_libro = ? ";
	
	/** Serial Version UID. */
	private static final long serialVersionUID = -5787617577827311880L;
	
	/**
	 * Crea un modello di una Inserzione partendo da una riga del
	 * {@code ResultSet}.
	 * 
	 * @param rs
	 *            il {@code ResultSet} con i dati dell'inserzione.
	 * @return un {@code AmministrazioneModel} con i dati dell'inserzione.
	 */
	private InserzioneModel makeInserzioneModel(ResultSet rs) {
		InserzioneModel inserzione = new InserzioneModel();

		try {
			inserzione.setId_utente(rs.getInt("id_utente"));
			inserzione.setId_amministrazione(rs.getInt("id_amministrazione"));
			inserzione.setCodice_libro(rs.getInt("codice_libro"));
			inserzione.setDisponibilita(rs.getBoolean("disponibilita"));
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return inserzione;
	}
	
	/**
	 * Recupero le inserzioni da validare per il Correttore di Bozze
	 * @return lista di {@code InserzioneModel} da validare
	 */
	public List<InserzioneModel> getInserzioniDaValidare(){
		List<InserzioneModel> inserzioni = new ArrayList<InserzioneModel>();
		ResultSet rs = db.executeQuery(queryInserzioniDaValidare);

		try {

			while (rs.next())
				inserzioni.add(makeInserzioneModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}

		return inserzioni;
	}
	
	/**
	 * Recupero le inserzioni dell'Utente non validate
	 * @return lista di {@code InserzioneModel} non validate
	 */
	public List<InserzioneModel> getInserzioniNonValidate(List <Object> list){
		List<InserzioneModel> inserzioni = new ArrayList<InserzioneModel>();
		ResultSet rs = db.executeQuery(queryInserzioniNonValidate, list);

		try {

			while (rs.next())
				inserzioni.add(makeInserzioneModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}

		return inserzioni;
	}
	
	/**
	 * Recupero le inserzioni dell'Utente validate
	 * @return lista di {@code InserzioneModel} validate
	 */
	public List<InserzioneModel> getInserzioniValidate(List <Object> list){
		List<InserzioneModel> inserzioni = new ArrayList<InserzioneModel>();
		ResultSet rs = db.executeQuery(queryInserzioniValidate, list);

		try {

			while (rs.next())
				inserzioni.add(makeInserzioneModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}

		return inserzioni;
	}
	
	/**
	 * Recupero una inserzione
	 * @param list lista di oggetti da passare alla query
	 * @return un oggetto di tipo {@code InserzioneModel}
	 */
	public InserzioneModel getInserzione(List <Object> list){
		ResultSet rs = db.executeQuery(queryInserzioneLibro, list);

		try {
			if (rs.next())
				return makeInserzioneModel(rs);
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}

		return null;
	}
	
	/**
	 * Inserisco una nuova inserzione
	 * @param list lista di parametri per la query
	 */
	public boolean insertInserzione(List<Object> list){
        return db.executeUpdate(insertInserzione, list) != 0;
	}
	
	/**
	 * Setto la disponibilita di una inserzione {@code true} 
	 * se è disponibile, altrimenti {@code false}
	 * @param list lista di parametri per la query
	 */
	public boolean setDisponibilitaInserzione(List<Object> list){
        return db.executeUpdate(updateDispIns, list) != 0;
	}
	
	/**
	 * Cancello un'inserzione
	 * @param list lista di parametri per la query
	 */
	public boolean deleteInserzione(List<Object> list){
        return db.executeUpdate(deleteInserzione, list) != 0;
	}
	
	/**
	 * Valido l'inserzione inserita da un utente 
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'aggiornamento è andato a buon fine
	 */
	public boolean validateInserzione(List<Object> list) {
        return db.executeUpdate(validateIns , list) != 0;
	}
	
}

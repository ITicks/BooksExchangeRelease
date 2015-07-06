package it.univr.db;

import it.univr.exceptions.DatabaseException;
import it.univr.models.AmministrazioneModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSource che gestisce le query che riguardano gli utenti amministrativi.
 *
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public abstract class AbstractDataSourceAmministrazione extends AbstractDataSource {
	
	/** Serial Version UID. */
	private static final long serialVersionUID = 6970958895966866535L;
	
	/** Seleziono un utente amministrativo. */
	private String queryVerifyAmm = "SELECT id, nome, cognome, username, password, "
	        + "email, n_cell, tipo, last_login, last_logout "
			+ "FROM Amministrazione "
			+ "WHERE username = ? AND password = ? AND tipo = ?";
	
	/** Seleziono un utente amministrativo. */
	private String listaAmm = "SELECT id, nome, cognome, username, password, "
	        + "email, n_cell, tipo, last_login, last_logout "
			+ "FROM Amministrazione "
			+ "WHERE tipo = ?";
	
	/** Query per l'inserimento di un nuovo Correttore di Bozze. */
	private String insertCorrettoreBozze = "INSERT INTO Amministrazione (nome, cognome, "
			+ "username, password, email, n_cell, tipo, "
			+ "last_login, last_logout) "
			+ "VALUES(?, ?, ?, ?, ?, ?, ?, NULL, NULL) ";
	
	/** Query per l'aggiornamento del timestamp del last login. */
	private String updateLastLogin = "UPDATE Amministrazione "
			+ "SET last_login = CURRENT_TIMESTAMP WHERE id = ?";
	
	/** Query per l'aggiornamento del timestamp del last logout. */
	private String updateLastLogout = "UPDATE Amministrazione "
			+ "SET last_logout = CURRENT_TIMESTAMP WHERE id = ?";
	
	/** Query per la cancellazione di un Correttore di Bozze. */
	private String deleteAmm = "DELETE FROM Amministrazione "
			+ "WHERE id = ? AND tipo = ?";

	/**
	 * Crea un modello di un Amministratore partendo da una riga del
	 * {@code ResultSet}.
	 * 
	 * @param rs
	 *            il {@code ResultSet} con i dati dell'amministratore.
	 * @return un {@code AmministrazioneModel} con i dati dell'amministratore.
	 */
	private AmministrazioneModel makeAmministrazioneModel(ResultSet rs) {
		AmministrazioneModel amministrazione = new AmministrazioneModel();

		try {

			amministrazione.setId(rs.getInt("id"));
			amministrazione.setNome(rs.getString("nome"));
			amministrazione.setCognome(rs.getString("cognome"));
			amministrazione.setUsername(rs.getString("username"));
			amministrazione.setPassword(rs.getString("password"));
			amministrazione.setEmail(rs.getString("email"));
			amministrazione.setN_cell(rs.getString("n_cell"));
			amministrazione.setTipo(rs.getString("tipo"));
			amministrazione.setLast_login(rs.getTimestamp("last_login"));
			amministrazione.setLast_logout(rs.getTimestamp("last_logout"));
			
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return amministrazione;
	}
	
	/**
	 * Ritorna un oggetto di tipo {@code AmministrazioneModel} che rappresenta
	 * un utente amministrativo
	 * @param list lista dei parametri da sostituire nella query.
	 * @return  il modello {@code AmministrazioneModel} dell'utente amministrativo se l'
     *         utente amministrativo è registrato, {@code null} altrimenti.
	 */
	protected AmministrazioneModel getAmministrazione(List<Object> list){
		AmministrazioneModel amministrazione = null;
		ResultSet rs = db.executeQuery(queryVerifyAmm, list);

		try {
			
			// Se rs.next() vale true significa che è stata estratta almeno
			// una riga.
			if (rs.next())
				amministrazione = makeAmministrazioneModel(rs);
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}
		
		return amministrazione;
		
	}
	
	/**
	 * Ritorna una lista di {@code AmministrazioneModel}
	 * @param list lista dei parametri da sostituire nella query.
	 * @return  la lista di di {@code AmministrazioneModel} di un tipo specificato
	 */
	protected List<AmministrazioneModel> getAmministrazioni(List<Object> list){
		List<AmministrazioneModel> amministrazioni = new ArrayList<AmministrazioneModel>();
		ResultSet rs = db.executeQuery(listaAmm, list);

		try {

			while (rs.next())
				amministrazioni.add(makeAmministrazioneModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}

		return amministrazioni;		
	}
	
	/**
	 * Cancello un utente amministrativo
	 * @param list lista di parametri per la query
	 * @return {@code true} se la cancellazione è andato a buon fine, altrimenti {@code false}
	 */
	protected boolean deleteAmministrazione(List<Object> list){
        return db.executeUpdate(deleteAmm, list) != 0;
	}
	
	/**
	 * 
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'inserimento è andato a buon fine, altrimenti {@code false}
	 */
	protected boolean insertAmministrazione(List<Object> list){
        return db.executeUpdate(insertCorrettoreBozze, list) != 0;
	}

	/**
	 * Aggiorno l'ultimo login di un utente amministrativo
	 * @param list lista di parametri per la query
	 * @return {@code true} se l'aggiornamento è andato a buon fine, altrimenti {@code false}
	 */
	public boolean updateLastLogin(List<Object> list){
        return db.executeUpdate(updateLastLogin, list) != 0;
	}
	
	/**
	 * Aggiorno l'ultimo logout di un utente amministrativo
	 * @param list lista di parametri per la query
	 * @return {@code true} se l'aggiornamento è andato a buon fine, altrimenti {@code false}
	 */
	public boolean updateLastLogout(List<Object> list){
        return db.executeUpdate(updateLastLogout, list) != 0;
	}


}

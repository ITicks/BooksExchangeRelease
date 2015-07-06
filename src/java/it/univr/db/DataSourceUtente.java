package it.univr.db;


import it.univr.exceptions.DatabaseException;
import it.univr.models.UtenteModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSource che gestisce le query degli Utenti Standard.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

public class DataSourceUtente extends AbstractDataSource
{
	/** Serial Version UID. */
	private static final long serialVersionUID = -6914318858510827761L;
	
	/** Query che seleziona un utente a partire dall'username e la password */
	private String utente = "SELECT id, nome, cognome, username, "
			+ "password, email, indirizzo, comune, provincia, regione, "
			+ "foto, n_cell, confermato FROM Utente "
			+ "WHERE username = ? AND password = ?";
	
	/** Query che seleziona un utente partendo dal suo id */
	private String utenteId = "SELECT id, nome, cognome, username, "
			+ "password, email, indirizzo, comune, provincia, regione, "
			+ "foto, n_cell, confermato FROM Utente "
			+ "WHERE id = ?";
	
	/** Query che recupera la lsita degli utenti */
	private String lista_utenti = "SELECT * " + "FROM Utente ";

	/** Query che inserisce un nuovo utente */
	private String inserimento_utente = "INSERT INTO Utente(nome, cognome, "
			+ "username, password, email, indirizzo, comune, "
			+ "provincia, regione, foto, n_cell, confermato) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'true')";
	
	/** Query che modifica un utente */
	private String modifica_utente = "UPDATE Utente "
			+ "SET nome = ? cognome = ? email = ? indirizzo = ? comune = ? "
			+ "provincia = ? regione = ? foto = ? n_cell = ? " + "WHERE id = ?";
	
	/** Query che cancella un utente */
	private String cancella_utente = "DELETE FROM Utente " + "WHERE id=?";
	
	/** Query che modifica una foto dell'utente */
	private String modifica_foto_utente = "UPDATE Utente SET foto = ? WHERE username = ?";
	
	/** Query che recupera il numero di utenti con uno specifico username */
	private String query_check_username = " SELECT COUNT(*) AS num FROM Utente WHERE username = ? ";

	private UtenteModel makeUserModel(ResultSet rs)
	{
		UtenteModel user = new UtenteModel();

		try
		{

			user.setId(rs.getInt("id"));
			user.setNome(rs.getString("nome"));
			user.setCognome(rs.getString("cognome"));
			user.setUsername(rs.getString("username"));
			user.setEmail(rs.getString("email"));
			user.setIndirizzo(rs.getString("indirizzo"));
			user.setComune(rs.getString("comune"));
			user.setProvincia(rs.getString("provincia"));
			user.setRegione(rs.getString("regione"));
			user.setFoto(rs.getString("foto"));
			user.setN_cell(rs.getString("n_cell"));
			user.setConfermato(rs.getBoolean("confermato"));

		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return user;

	}

	/**
	 * Verifico la presenza di un utente nel database attraverso username e password.
	 * @param list lista di parametri da passare alla query
	 * @return l'utente verificato se presente con i relativo {@code UtenteModel}, altrimenti NULL.
	 */
	public UtenteModel checkUtente(List<Object> list)
	{

		UtenteModel user = null;
		ResultSet rs = db.executeQuery(utente, list);
				
		try
		{
			// Se rs.next() vale true significa che è stata estratta almeno
			// una riga.
			if (rs.next())
				user = makeUserModel(rs);
		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return user;
	}

	/**
	 * Recupero la lista degli utenti registrati
	 * @return la lista degli utenti registrati.
	 */
	public List<UtenteModel> getListaUtenti() {

		ResultSet result = null;
		List<UtenteModel> lista_user = new ArrayList<UtenteModel>();

		try {
			result = db.executeQuery(lista_utenti);
			while (result.next()) {
				lista_user.add(makeUserModel(result));
			}

		} catch (SQLException e) {
			throw new DatabaseException();

		}

		return lista_user;
	}

	/**
	 * Modifico i dati di un Utente.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'utente è stato modificato, altrimenti {@code false}
	 * @throws SQLException
	 */
	public boolean modificaUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(modifica_utente, list) != 0;
	}
	
	/**
	 * Cancello un Utente.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'utente è stato cancellato, altrimenti {@code false}
	 * @throws SQLException
	 */
	public boolean cancellaUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(cancella_utente, list) != 0;
	}
	
	/**
	 * Inserisco un nuovo Utente.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'utente è stato inserito, altrimenti {@code false}
	 * @throws SQLException
	 */
	public boolean inserimentoUtente(List<Object> list) throws SQLException
	{
		return db.executeUpdate(inserimento_utente, list) != 0;
	}

	public UtenteModel getUtente(List<Object> list) {
		ResultSet rs = db.executeQuery(utenteId, list);

		try {
			if (rs.next())
				return makeUserModel(rs);
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return null;
	}
	
	/**
	 * Modifico il nome della foto profilo riferita ad un Utente.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se la foto profilo dell'Utente è stata modificata, altrimenti {@code false}
	 * @throws SQLException
	 */
	public boolean modificaFoto(List<Object> list) {
		return db.executeUpdate(modifica_foto_utente , list) != 0;
	}
	
	/**
	 * Controllo se l'username inserito non sia gia' presente nel Database.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se l'username non è presente nel Database, altrimenti {@code false}
	 * @throws SQLException
	 */
	public boolean isNewUsername(List<Object> list) {
		ResultSet rs = db.executeQuery(query_check_username, list);

		try {
			if (rs.next())
				return rs.getInt("num") == 0;
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return false;
	}
}

package it.univr.db;


import java.sql.ResultSet;
import java.sql.SQLException;


import it.univr.models.StatisticheModel;

/**
 * DataSource delle Statistiche.
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class DataSourceStatistiche extends AbstractDataSource
{

	/** Serial Version UID. */
	private static final long serialVersionUID = -5940306097576879134L;

	/** Conto il numero degli utenti registrati*/
	private String countUtenti = "SELECT COUNT(*) AS numUtenti FROM Utente";

	/** Conto il numero dei correttori di bozza */
	private String countCorrettori = "SELECT COUNT(*) AS numCorrettori FROM Amministrazione "
			+ "WHERE tipo = 'correttore' ";

	/** Conto il numero delle inserzioni */
	private String countInserzioni = "SELECT COUNT(*) AS numInserzioni FROM Inserzione";

	/** Conto il numero delle inserzioni da validare */
	private String countInserzioniDaValidare = "SELECT COUNT(*) AS numInsDaValid FROM Inserzione "
			+ "WHERE id_amministrazione IS NULL";

	/**
	 * Recupero le statistiche per la base di dati
	 * 
	 * @return le statistiche della base di dati {@code StatisticheModel}
	 */
	public StatisticheModel getStatistiche() {
		StatisticheModel statistiche = new StatisticheModel();
		ResultSet rs;

		rs = db.executeQuery(countUtenti);
		try {
			if (rs.next())
				statistiche.setNur(rs.getInt("numUtenti"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rs = db.executeQuery(countCorrettori);
		try {
			if (rs.next())
				statistiche.setNcb(rs.getInt("numCorrettori"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rs = db.executeQuery(countInserzioni);
		try {
			if (rs.next())
				statistiche.setNiv(rs.getInt("numInserzioni"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		rs = db.executeQuery(countInserzioniDaValidare);
		try {
			if (rs.next())
				statistiche.setNidv(rs.getInt("numInsDaValid"));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return statistiche;

	}
}

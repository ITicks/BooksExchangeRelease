package it.univr.db;


import it.univr.exceptions.DatabaseException;
import it.univr.models.LibroModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DataSource che gestisce le query dei Libri.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */
public class DataSourceLibro extends AbstractDataSource
{

	/** Serial Version UID. */
	private static final long serialVersionUID = 2687086721206469849L;

	/** Query che inserisce un nuovo libro */
	private String inserisce_libro = "INSERT INTO Libro(ISBN, autore, "
			+ "titolo, genere, descrizione, anno, casa_editrice, "
			+ "lingua, pagine, foto, n_visualizzazioni) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0)";

	/** Query che cancella un libro a partire dal suo codice */
	private String cancella_libro = "DELETE FROM Libro WHERE codice = ?";

	/** Query che seleziona un libro a partire dal suo codice */
	private String libro = "SELECT codice, ISBN, autore, titolo,"
			+ "genere, descrizione, anno, casa_editrice, lingua, pagine, "
			+ "foto, n_visualizzazioni FROM Libro WHERE codice = ? ";
	
	/** Query che recupera il codice del libro generato automaticamente a partire da un ISBN e da un Titolo */
	private String codice_libro = "SELECT codice FROM Libro WHERE ISBN = ? AND Titolo = ?";
	
	/** Query che incrementa il numero di visualizzazioni di un Libro */
	private String incrementa_nvis = "UPDATE Libro SET n_visualizzazioni = (n_visualizzazioni + 1) WHERE codice = ?";
	
	/** Query che ricerca un Libro a partire dai parametri di ricerca derivati da un filtro */
	private String ricercaLibro = "SELECT codice, ISBN, autore, titolo, "
			+ "genere, descrizione, anno, casa_editrice, lingua, pagine, "
			+ "foto, n_visualizzazioni FROM Libro WHERE ";
	
	/**
	 * Cancello un Libro.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se il libro è stato cancellato, altrimenti {@code false}
	 */
	public boolean deleteLibro(List<Object> list) {
		return db.executeUpdate(cancella_libro, list) != 0;
	}

	/**
	 * Inserisco un Libro.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se il libro è stato inserito, altrimenti {@code false}
	 */
	public boolean insertLibro(List<Object> list){
		return db.executeUpdate(inserisce_libro, list) != 0;
	}
	
	private LibroModel makeLibroModel(ResultSet rs)
	{
		LibroModel book = new LibroModel();

		try
		{

			book.setCodice(rs.getInt("codice"));
			book.setISBN(rs.getString("ISBN"));
			book.setAutore(rs.getString("autore"));
			book.setTitolo(rs.getString("titolo"));
			book.setGenere(rs.getString("genere"));
			book.setDescrizione(rs.getString("descrizione"));
			book.setAnno(rs.getDate("anno"));
			book.setCasa_editrice(rs.getString("casa_editrice"));
			book.setLingua(rs.getString("lingua"));
			book.setPagine(rs.getInt("pagine"));
			book.setFoto(rs.getString("foto"));
			book.setN_visualizzazioni(rs.getInt("n_visualizzazioni"));

		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return book;
	}
	
	/**
	 * Recupero un Libro e costrusico il relativo {@code LibroModel}.
	 * @param list lista di parametri da passare alla query
	 * @return il libro cercato.
	 */
	public LibroModel getLibro(List<Object> list)
	{

		LibroModel book = null;
		ResultSet rs = db.executeQuery(libro, list);

		try
		{
			// Se rs.next() vale true significa che è stata estratta almeno
			// una riga.
			if (rs.next())
				book = makeLibroModel(rs);
		} catch (SQLException e)
		{
			throw new DatabaseException();
		}

		return book;
	}
	
	/**
	 * Ritorno il codice di un libro.
	 * @param list lista di parametri da passare alla query
	 * @return il codice de libro cercato
	 */
	public int getCodiceLibro(List<Object> list){
		int res = -1;

		ResultSet rs = db.executeQuery(codice_libro, list);

		try {
			if (rs.next())
				res = rs.getInt("codice");
		} catch (SQLException e) {
			throw new DatabaseException();
		}

		return res;
	}
	
	/**
	 * Incremento il numero di visualizzazioni di un Libro.
	 * @param list lista di parametri da passare alla query
	 * @return {@code true} se ho incrementato il numero di visualizzazioni
	 *  del libro, altrimenti {@code false}
	 */
	public boolean incrementNVis(List<Object> list){
		return db.executeUpdate(incrementa_nvis, list) != 0;
	}
	
	/**
	 * Ricerco i libri a seconda dei valori inseriti nel filtro.
	 * @param temp parametri derivati dal filtro
	 * @return la lista dei libri cercati
	 */
	public List<LibroModel> ricercaLibri(List<Object> temp){
		String[] vett = {" genere = ? ", " titolo = ? ", " autore = ? "};
		List<LibroModel> result = new ArrayList<LibroModel>();
		
		List<Object> list = new ArrayList<Object>();
		
		String tempRicercaLibro = ricercaLibro;
		
		//Costruisco le condizioni della query in automatico
		for(Object obj : temp)
			if(obj!=null)
				if(!obj.equals("") && !obj.equals("NESSUNO")){
					if(list.size()>0) tempRicercaLibro += "AND";
					tempRicercaLibro += vett[temp.indexOf(obj)];
					list.add(obj);
				}
		
		ResultSet rs = db.executeQuery(tempRicercaLibro, list);
		
		try {

			while (rs.next())
				result.add(makeLibroModel(rs));
		}
		catch (SQLException e) {
			throw new DatabaseException();
		}
		
		return result;
	}
}

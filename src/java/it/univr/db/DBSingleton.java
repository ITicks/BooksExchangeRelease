package it.univr.db;

import it.univr.exceptions.DatabaseException;
import it.univr.utils.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * Questa classe fornisce un'interfaccia per l'interazione con il database. In
 * particolare, espone i metodi per la gestione delle connessioni e per
 * l'esecuzione delle query.
 * </br>
 * Segue il pattern creazionale {@code Singleton} e, pertanto, assicura che
 * tutti gli utilizzare dell'unica istanza della classe vedano la stessa
 * configurazione.
 * 
 * @author Matteo Olivato
 * @author Federico Bianchi
 */

public class DBSingleton implements Serializable
{

    /** Serial Version UID. */

    private static final long serialVersionUID = -1529537111854712045L;

    /** File di properties contenente i dati per l'accesso al database. */
    
    private static final String configFile =
            "/it/univr/properties/config.properties";

    /** Istanza della classe. */
    
    private static DBSingleton instance;

    /** Properties lette dal file {@link #configFile}. */
    
    private final Properties properties;
    

    /**
     * Costruttore privato. Inizializza l'oggetto {@link #properties}.
     * 
     * @throws DatabaseException se avviene un errore durante il caricamento
     *         delle proprietà dal file di configurazione {@link #configFile} (
     *         {@code IOException}.
     */

    private DBSingleton()
    {
        properties = new Properties();

        try
        {
            properties.load(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream(configFile));
        }
        catch (IOException e)
        {
            throw new DatabaseException();
        }
    }

    /**
     * Costruisce un'istanza di {@code DBSingleton} se non è già stata creata in
     * precedenza.
     * 
     * @return un'istanza di {@link DBSingleton}.
     */

    public static synchronized DBSingleton getInstance()
    {
        if (instance == null)
            instance = new DBSingleton();

        return instance;
    }

    /**
     * Restituisce una nuova connessione al database.
     * 
     * @return una connessione al database.
     * @throws DatabaseException se avviene una {@code SQLException} durante il
     *         recupero di una connessione oppure se avviene una
     *         {@code ClassNotFoundException} al caricamento della classe
     *         contenente i driver per il database.
     */

    public Connection getNewConnection()
    {
        String url = properties.getProperty("dbmanager.url");
        String user = properties.getProperty("user.login");
        String psw = properties.getProperty("user.password");

        Connection connection = null;
        try
        {
            Class.forName(properties.getProperty("dbmanager.driver"));
            connection = DriverManager.getConnection(url, user, psw);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            throw new DatabaseException();
        }

        return connection;
    }

    /**
     * Chiude la connessione al database.
     * 
     * @param connection la connessione da chiudere.
     */

    public void closeConnection(Connection connection)
    {
        try
        {
            connection.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException();
        }
    }

    /**
     * Esegue una query di update.
     * 
     * @param query la query da eseguire.
     * @return il numero di righe aggiornate.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public int executeUpdate(String query)
    {
        int toRet = -1;
        Connection connection = getNewConnection();
        
        try
        {
            toRet = connection.prepareStatement(query).executeUpdate();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        closeConnection(connection);
        
        return toRet;
    }
    
    /**
     * Esegue una query di update.
     * 
     * @param connection la connessione da utilizzare.
     * @param query la query da eseguire.
     * @return il numero di righe aggiornate.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public int executeUpdate(Connection connection, String query)
    {
        int toRet = -1;
        
        try
        {
            toRet = connection.prepareStatement(query).executeUpdate();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        return toRet;
    }

    /**
     * Esegue una query di interrogazione.
     * 
     * @param query la query da eseguire.
     * @return il risultato della query.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public ResultSet executeQuery(String query)
    {
        ResultSet rs = null;
        Connection connection = getNewConnection();
        
        try
        {
            rs = connection.prepareStatement(query).executeQuery();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        closeConnection(connection);

        return rs;
    }
    
    /**
     * Esegue una query di interrogazione.
     * 
     * @param connection la connessione da utilizzare.
     * @param query la query da eseguire.
     * @return il risultato della query.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public ResultSet executeQuery(Connection connection, String query)
    {
        ResultSet rs = null;
        
        try
        {
            rs = connection.prepareStatement(query).executeQuery();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        return rs;
    }

    /**
     * Esegue una query di update parametrizzata.
     * 
     * @param query la query da eseguire.
     * @param list la lista di parametri da sostituire nella stringa al posto
     *        dei caratteri {@code ?}.
     * @return il numero di righe aggiornate.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public int executeUpdate(String query, List<Object> list)
    {
        int toRet = -1;
        Connection connection = getNewConnection();
        
        if (StringUtils.countMatches(query, "?") != list.size())
            throw new DatabaseException();

        try
        {
        	PreparedStatement pst = fillPreparedStatement(connection.prepareStatement(query),list);
        	
            toRet = pst.executeUpdate();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        closeConnection(connection);

        return toRet;
    }
    
    /**
     * Esegue una query di update parametrizzata.
     * 
     * @param connection la connessione da utilizzare.
     * @param query la query da eseguire.
     * @param list la lista di parametri da sostituire nella stringa al posto
     *        dei caratteri {@code ?}.
     * @return il numero di righe aggiornate.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public int executeUpdate(
            Connection connection,
            String query,
            List<Object> list)
    {
        int toRet = -1;
        
        if (StringUtils.countMatches(query, "?") != list.size())
            throw new DatabaseException();

        try
        {
            toRet =
                    fillPreparedStatement(connection.prepareStatement(query),
                            list).executeUpdate();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        return toRet;
    }

    /**
     * Esegue una query di interrogazione parametrizzata.
     * 
     * @param query la query da eseguire.
     * @param list la lista di parametri da sostituire nella stringa al posto
     *        dei caratteri {@code ?}.
     * @return il risultato della query.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public ResultSet executeQuery(String query, List<Object> list)
    {
        ResultSet rs = null;
        Connection connection = getNewConnection();
        
        try
        {
            rs = fillPreparedStatement(connection.prepareStatement(query),
                            list).executeQuery();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }
        
        closeConnection(connection);

        return rs;
    }
    
    /**
     * Esegue una query di interrogazione parametrizzata.
     * 
     * @param connection la connessione da utilizzare.
     * @param query la query da eseguire.
     * @param list la lista di parametri da sostituire nella stringa al posto
     *        dei caratteri {@code ?}.
     * @return il risultato della query.
     * @throws DatabaseException in caso di {@code SQLException}.
     */

    public ResultSet executeQuery(
            Connection connection,
            String query,
            List<Object> list)
    {
        ResultSet rs = null;
        
        try
        {
            rs =
                    fillPreparedStatement(connection.prepareStatement(query),
                            list).executeQuery();
        }
        catch (SQLException e)
        {
            try { connection.close(); } catch (SQLException ex) { }
            throw new DatabaseException();
        }

        return rs;
    }

    /**
     * Sostituisce ordinatamente i caratteri {@code ?} della query interna al
     * {@code PreparedStatement} con gli elementi della lista specificata.
     * 
     * @param ps il {@code PreparedStatement} contenente la query.
     * @param list la lista di parametri da sostituire nella query.
     * @return il {@code PreparedStatement} a cui sono stati sostituiti i
     *         parametri della lista specificata.
     * @throws DatabaseException nel caso in cui avvenga una
     *         {@code SQLException} durante la sostituzione dei parametri nel
     *         {@code PreparedStatement} oppure nel caso in cui un parametro
     *         della lista non sia un tipo utilizzato nel database (ovvero,
     *         {@code String}, {@code Integer}, {@code Double} o {@code Short}).
     */

    private PreparedStatement fillPreparedStatement(
            PreparedStatement ps,
            List<Object> list) {
        for (int i = 0; i < list.size(); ++i)
            
        	try{
            	ps.setObject(i + 1, list.get(i));
            }catch (SQLException e1) {
            	try { ps.close(); } catch (SQLException ex) { }
            	throw new DatabaseException();
            }


        return ps;
    }
    
    /**
     * Inizia una nuova transazione.
     * 
     * @param conn la connessione da utilizzare per la transazione.
     * @throws DatabaseException se durante l'apertura della transazione avviene
     *         una {@code SQLException}.
     */
    
    public void beginTransaction(Connection conn)
    {
        try { conn.setAutoCommit(false); }
        catch (SQLException e)
        {
            try { conn.close(); } catch (SQLException ex) { }
            
            throw new DatabaseException();
        }
    }
    
    /**
     * Conclude la transazione. In caso di errori durante la {@code commit},
     * esegue una {@code rollback} e chiude la connessione.
     * 
     * @param conn la connessione su cui è stata aperta la transazione.
     * @throws DatbaseException se viene lanciata una {@code SQLException}
     *         durante la {@code commit}.
     */
    
    public void endTransaction(Connection conn)
    {
        try
        {
            conn.commit();
        }
        catch (SQLException e)
        {
            try { conn.rollback(); } catch (SQLException ex) { }
            try { conn.close(); } catch (SQLException exx) { }
            
            throw new DatabaseException();
        }
        
        closeConnection(conn);
    }

}

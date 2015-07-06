package it.univr.tests;

import it.univr.db.DataSourceLibro;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
/**
 * Testo le funzionalità del Datasource dei Libri
 */
public class DataSourceLibroTest {
	
	@Test
	public void testInserimentoNuovoLibro() {
		DataSourceLibro dsLib = new DataSourceLibro();
		
		List<Object> list = new ArrayList<Object>();
		
		list.add("12345");
		list.add("prova");
		list.add("prova");
		list.add("prova");
		list.add("prova");
		Date temp = new Date(1000000000L);
		list.add(temp);
		list.add("prova");
		list.add("italiano");
		list.add("pagine");
		list.add("foto");
		
		assert(dsLib.insertLibro(list));
		
	}
	
	@Test
	public void testCancellazioneLibro() {
		DataSourceLibro dsLib = new DataSourceLibro();
		
		List<Object> list = new ArrayList<Object>();
		
		list.add("12345");
		list.add("prova");
		int temp = dsLib.getCodiceLibro(list);
		
		list.clear();
		list.add(temp);
		
		assert(dsLib.deleteLibro(list));
		
	}

}

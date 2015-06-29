/**
 * 
 */
package mts.api;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class SITableRepository {
	private static SITableRepository singleton_instance;
	TreeMap<Integer,SITable> si_tables;

	private SITableRepository() {
		si_tables = new TreeMap<Integer, SITable>();
	}
	
	private static synchronized SITableRepository getInstance() {
		if (singleton_instance == null)
			singleton_instance = new SITableRepository();
		return singleton_instance;
	}
	
	/**
	 * @param table
	 * @return
	 */
	public static synchronized boolean addTable(SITable table) {
		if (getInstance().si_tables.put(new Integer(table.getUniqueID()), table) == null)
			return true;

		return false;
	}

	/**
	 * @param table
	 * @return
	 */
	public static synchronized boolean removeTable(SITable table) {
		if (getInstance().si_tables.remove(new Integer(table.getUniqueID())) == null)
			return false;

		return true;
	}
	
	/**
	 * @param unique_id
	 * @return
	 */
	public static synchronized SITable getTable(int unique_id) {
		return getInstance().si_tables.get(new Integer(unique_id)).clone(); 
	}

	/**
	 * @return
	 */
	public static synchronized SITable[] getAllTables() {
		Collection<SITable> col = getInstance().si_tables.values();
		SITable[] tables = new SITable[col.size()];
		Iterator<SITable> it = col.iterator();
		int n = 0;
		while(it.hasNext())
			tables[n++] = it.next().clone();
		return tables;
	}
	
	/**
	 * @return
	 */
	public static synchronized int getTableSize() {
		return getInstance().si_tables.size();
	}
	
	/**
	 * @return
	 */
	public static String dump() {
		String str = new String();
		SITable[] tables = getAllTables();
		for(int n=0; n < tables.length; n++) {
			str += ("UID:"+tables[n].getUniqueID()+" ,"+tables[n].getTableID()+"\n");
		}
		
		return str;
	}
}

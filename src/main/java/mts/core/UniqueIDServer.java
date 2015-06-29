/**
 * 
 */
package mts.core;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class UniqueIDServer {
	private static UniqueIDServer singleton_instance;
	private int next_id_value;
	
	private UniqueIDServer() {
		next_id_value = 1;
	}

	private static synchronized UniqueIDServer getInstance() {
		if (singleton_instance == null)
			singleton_instance = new UniqueIDServer();
		return singleton_instance;
	}

	public static synchronized int getUniqueID() {
		return getInstance().next_id_value++;
	}
}

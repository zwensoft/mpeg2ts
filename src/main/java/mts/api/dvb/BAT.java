/**
 * 
 */
package mts.api.dvb;

import java.util.Iterator;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface BAT extends SITable, DescriptorMethod {

	/**
	 * @return
	 */
	int getVersionNumber();

	/**
	 * @return
	 */
	int getBouquetId();
	
	/**
	 * @return
	 */
	int numTransportStreams();
	
	/**
	 * @param index
	 * @return
	 */
	BATTransportStream getTransportStreamAt(int index);
	
	/**
	 * @return
	 */
	Iterator<BATTransportStream> getTransportStreams();
	
	/**
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * @param bouquet_id
	 */
	void setBouquetId(int bouquet_id);
	
	/**
	 * @param ts
	 * @return
	 */
	boolean addTransportStream(BATTransportStream ts);
	
	/**
	 * @param ts
	 * @return
	 */
	boolean removeTransportStream(BATTransportStream ts);
	
	/**
	 * 
	 */
	void removeAllTransportStreams();
}

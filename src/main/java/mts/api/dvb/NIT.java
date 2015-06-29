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
public interface NIT extends SITable, DescriptorMethod {

	/**
	 * @return
	 */
	boolean isActual();
	
	/**
	 * @return
	 */
	int getNetworkId();
	
	/**
	 * @return
	 */
	int getVersionNumber();
	
	/**
	 * @return
	 */
	int numTransportStreams();
	
	/**
	 * @param index
	 * @return
	 */
	NITTransportStream getTransportStreamAt(int index);
	
	/**
	 * @return
	 */
	Iterator<NITTransportStream> getTransportStreams();
	
	/**
	 * @param isActual
	 */
	void setIsActual(boolean isActual);
	
	/**
	 * @param net_id
	 */
	void setNetworkId(int net_id);
	
	/**
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * @param ts
	 * @return
	 */
	boolean addTransportStream(NITTransportStream ts);
	
	/**
	 * @param ts
	 * @return
	 */
	boolean removeTransportStream(NITTransportStream ts);
	
	/**
	 * 
	 */
	void removeAllTransportStreams();
}

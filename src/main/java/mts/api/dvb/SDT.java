/**
 * 
 */
package mts.api.dvb;

import java.util.Iterator;

import mts.api.SITable;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface SDT extends SITable {

	boolean isActual();
	
	/**
	 * @return
	 */
	int getVersionNumber();
	
	/**
	 * @return
	 */
	int getTransportStreamId();
	
	/**
	 * @return
	 */
	int getOrgNetworkId();
	
	/**
	 * @return
	 */
	int numServices();
	
	/**
	 * @param index
	 * @return
	 */
	SDTService getServiceAt(int index);
	
	/**
	 * @return
	 */
	Iterator<SDTService> getServices();
	
	/**
	 * @param isActual
	 */
	void setIsActual(boolean isActual);
	
	/**
	 * 
	 */
	void setVersionNumber(int version);
	
	/**
	 * 
	 */
	void setTransportStreamId(int ts_id);
	
	/**
	 * 
	 */
	void setOrgNetworkId(int org_net_id);
	
	/**
	 * @param svc
	 * @return
	 */
	boolean addService(SDTService svc);
	
	/**
	 * @param svc
	 * @return
	 */
	boolean removeService(SDTService svc);
	
	/**
	 * @return
	 */
	boolean removeAllServices();
}

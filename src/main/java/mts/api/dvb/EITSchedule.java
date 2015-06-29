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
public interface EITSchedule extends SITable {

	/**
	 * @return
	 */
	boolean isActual();

	/**
	 * @return
	 */
	int getVersionNumber();
	
	/**
	 * @return
	 */
	int getServiceId();
	
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
	int numEvents();
	
	/**
	 * @param index
	 * @return
	 */
	DVBEITEvent getEventAt(int index);
	
	/**
	 * @return
	 */
	Iterator<DVBEITEvent> getEvents();
	
	/**
	 * @param isActual
	 */
	void setIsActual(boolean isActual);

	/**
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * @param service_id
	 */
	void setServiceId(int service_id);
	
	/**
	 * @param ts_id
	 */
	void setTransportStreamId(int ts_id);
	
	/**
	 * @param org_net_id
	 */
	void setOrgNetworkId(int org_net_id);

	/**
	 * @param event
	 * @return
	 */
	boolean addEvent(DVBEITEvent event);
	
	/**
	 * @param event
	 * @return
	 */
	boolean removeEvent(DVBEITEvent event);
}

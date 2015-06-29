/**
 * 
 */
package mts.api.dvb;

import mts.api.SITable;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface EITPF extends SITable {

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
	DVBEITEvent getPresentEvent();
	
	/**
	 * @return
	 */
	DVBEITEvent getFollowingEvent();
	
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
	 */
	void setPresentEvent(DVBEITEvent event);
	
	/**
	 * @param event
	 */
	void setFollowingEvent(DVBEITEvent event);
}

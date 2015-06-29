/**
 * 
 */
package mts.api.dvb;

import mts.api.descriptor.DescriptorMethod;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface SDTService extends ByteRepresentation, XMLRepresentation,
		DescriptorMethod {

	/**
	 * @return
	 */
	int getServiceId();
	
	/**
	 * @return
	 */
	int getEitSchedFlag();
	
	/**
	 * @return
	 */
	int getEitPfFlag();
	
	/**
	 * @return
	 */
	int getRunningStatus();
	
	/**
	 * @return
	 */
	int getFreeCaMode();
	
	/**
	 * @param service_id
	 */
	void setServiceId(int service_id);
	
	/**
	 * @param eit_sched_flag
	 */
	void setEitSchedFlag(int eit_sched_flag);
	
	/**
	 * @param eit_pf_flag
	 */
	void setEitPfFlag(int eit_pf_flag);
	
	/**
	 * @param run_status
	 */
	void setRunningStatus(int run_status);
	
	/**
	 * @param free_ca_mode
	 */
	void setFreeCaMode(int free_ca_mode);
}

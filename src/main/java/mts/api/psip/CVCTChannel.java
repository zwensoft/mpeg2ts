/**
 * 
 */
package mts.api.psip;

import mts.api.ServiceType;
import mts.api.descriptor.DescriptorMethod;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface CVCTChannel extends ByteRepresentation, XMLRepresentation,
		DescriptorMethod {
	/**
	 * return a value of 'short_name'.
	 * @return
	 */
	char[] getShortName();

	/**
	 * return a value of 'major_channel_number'.
	 * @return
	 */
	int getMajorChannelNumber();

	/**
	 * return a value of 'minor_channel_number'.
	 * @return
	 */
	int getMinorChannelNumber();

	/**
	 * return a value of 'modulation_mode'.
	 * @return
	 */
	int getModulationMode();

	/**
	 * return a value of 'carrier_frequency'.
	 * @return
	 */
	long getCarrierFrequency();

	/**
	 * return a value of 'channel_TSID'.
	 * @return
	 */
	int getChannelTSID();

	/**
	 * return a value of 'program_number'.
	 * @return
	 */
	int getProgramNumber();

	/**
	 * return a value of 'ETM_location'.
	 * @return
	 */
	int getETMLocation();

	/**
	 * return a value of 'access_controlled'.
	 * @return
	 */
	int getAccessControlled();

	/**
	 * return a value of 'hidden'.
	 * @return
	 */
	int getHidden();
	
	/**
	 * return a value of 'path_select'.
	 * @return
	 */
	int getPathSelect();

	/**
	 * return a value of 'out_of_band'.
	 * @return
	 */
	int getOutOfBand();

	/**
	 * return a value of 'hide_guide'.
	 * @return
	 */
	int getHideGuide();

	/**
	 * return a value of 'service_type'.
	 * @return
	 */
	ServiceType getServiceType();

	/**
	 * return a value of 'source_id'.
	 * @return
	 */
	int getSourceId();

	/**
	 * set a value of 'short_name'.
	 * @param name
	 */
	void setShortName(char[] name);

	/**
	 * set a value of 'major_channel_number'.
	 * @param number
	 */
	void setMajorChannelNumber(int number);

	/**
	 * set a value of 'minor_channel_number'.
	 * @param number
	 */
	void setMinorChannelNumber(int number);

	/**
	 * set a value of 'modulation_mode'.
	 * @param mode
	 */
	void setModulationMode(int mode);

	/**
	 * set a value of 'carrier_frequency'.
	 * @param freq
	 */
	void setCarrierFrequency(long freq);

	/**
	 * set a value of 'channel_TSID'.
	 * @param tsid
	 */
	void setChannelTSID(int tsid);

	/**
	 * set a value of 'program_number'.
	 * @param program_num
	 */
	void setProgramNumber(int program_num);

	/**
	 * set a value of 'ETM_location'.
	 * @param etm_loc
	 */
	void setETMLocation(int etm_loc);

	/**
	 * set a value of 'access_controlled'.
	 * @param ac
	 */
	void setAccessControlled(int ac);

	/**
	 * set a value of 'hidden'.
	 * @param hidden
	 */
	void setHidden(int hidden);

	/**
	 * set a value of 'path_select'.
	 * @param path_select
	 */
	void setPathSelect(int path_select);

	/**
	 * set a value of 'out_of_band'.
	 * @param oob
	 */
	void setOutOfBand(int oob);
	
	/**
	 * set a value of 'hide_guide'.
	 * @param hideguide
	 */
	void setHideGuide(int hideguide);

	/**
	 * set a value of 'service_type'.
	 * @param type
	 */
	void setServiceType(ServiceType type);

	/**
	 * set a value of 'source_id'.
	 * @param source_id
	 */
	void setSourceId(int source_id);
}

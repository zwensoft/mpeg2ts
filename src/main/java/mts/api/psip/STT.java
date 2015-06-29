/**
 * 
 */
package mts.api.psip;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface STT extends SITable, DescriptorMethod {
	/**
	 * return a value of 'version_number'.
	 * @return version_number
	 */
	int getVersionNumber();
	
	/**
	 * return a value of 'system_time'.
	 * @return
	 */
	long getSystemTime();

	/**
	 * return a value of 'GPS_UTC_offset'.
	 * @return
	 */
	int getGPS_UTC_Offset();

	/**
	 * return a value of 'daylight_savings'.
	 * @return
	 */
	int getDatlightSavings();

	/**
	 * set a value of 'version_number'. 
	 * @param version
	 */
	void setVersionNumber(int version);

	/**
	 * set a value of 'system_time'.
	 * @param time
	 */
	void setSystemTime(long time);

	/**
	 * set a value of 'GPS_UTC_offset'.
	 * @param offset
	 */
	void setGPS_UTC_Offset(int offset);

	/**
	 * set a value of 'daylight_savings'.
	 * @param value
	 */
	void setDatlightSavings(int value);
}

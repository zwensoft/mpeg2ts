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
public interface DVBEITEvent extends ByteRepresentation, XMLRepresentation,
		DescriptorMethod {
	
	/**
	 * @return
	 */
	int getEventId();
	
	/**
	 * @return
	 */
	long getStartTime();
	
	/**
	 * @return
	 */
	int getDuration();
	
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
	void setEventId(int event_id);
	
	/**
	 * @param start_time
	 */
	void setStartTime(long start_time);
	
	/**
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 */
	void setStartTime(int year, int month, int day,
			int hour, int minute, int second);
	
	/**
	 * @param duration
	 */
	void setDuration(int duration);
	
	/**
	 * @param hour
	 * @param minute
	 * @param second
	 */
	void setDuration(int hour, int minute, int second);
	
	/**
	 * @param run_status
	 */
	void setRunningStatus(int run_status);
	
	/**
	 * @param free_ca_mode
	 */
	void setFreeCaMode(int free_ca_mode);
}

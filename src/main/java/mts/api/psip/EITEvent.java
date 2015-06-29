/**
 * 
 */
package mts.api.psip;

import mts.api.descriptor.DescriptorMethod;
import mts.api.multistring.MultipleStringStructure;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface EITEvent extends ByteRepresentation, XMLRepresentation, DescriptorMethod {

	/**
	 * return a value of 'event_id'.
	 * @return
	 */
	int getEventId();

	/**
	 * return a value of 'start_time'.
	 * @return
	 */
	long getStartTime();

	/**
	 * return a value of 'ETM_location'.
	 * @return
	 */
	int getETMLocation();

	/**
	 * return a value of 'length_in_seconds'.
	 * @return
	 */
	int getLengthInSeconds();

	/**
	 * return a value of 'title_length'.
	 * @return
	 */
	int getTitleLength();
	
	/**
	 * return a value of 'title_text()'.
	 * @return
	 */
	MultipleStringStructure getTitleText();
	
	/**
	 * set a value of 'event_id'.
	 * @param event_id
	 */
	void setEventId(int event_id);

	/**
	 * set a value of 'start_time'.
	 * @param start_time
	 */
	void setStartTime(long start_time);

	/**
	 * set a value of 'ETM_location'.
	 * @param etm_location
	 */
	void setETMLocation(int etm_location);

	/**
	 * set a value of 'length_in_seconds'.
	 * @param length_in_seconds
	 */
	void setLengthInSeconds(int length_in_seconds);

	/**
	 * set a value of 'title_text()'.
	 * @param title_text
	 */
	void setTitleText(MultipleStringStructure title_text);
}

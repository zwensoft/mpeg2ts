/**
 * 
 */
package mts.api.psip;

import java.util.Iterator;

import mts.api.SITable;





/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface EIT extends SITable {
	/**
	 * return a value of 'source_id'.
	 * @return
	 */
	int getSourceId();
	
	/**
	 * return a value of 'version_number'.
	 * @return
	 */
	int getVersionNumber();
	
	/**
	 * return a value of 'num_events_in_section'.
	 * @return
	 */
	int getNumEventsInSection();

	/**
	 * return total byte size of EITEvents.
	 * @return
	 */
	int getEventsLength();
	
	/**
	 * return a EITEvent instance.
	 * @param index
	 * @return
	 */
	EITEvent getEventAt(int index);

	/**
	 * return all EITEvent instances.
	 * @return
	 */
	Iterator<EITEvent> getEvents();
	
	/**
	 * set a value of 'source_id'.
	 * @param source_id
	 */
	void setSourceId(int source_id);
	
	/**
	 * set a value of 'version_number'.
	 * @param version_number
	 */
	void setVersionNumber(int version_number);
	
	/**
	 * set a EITEvent instance.
	 * @param index
	 * @param event
	 * @return
	 */
	boolean setEventAt(int index, EITEvent event);
	
	/**
	 * add a EITEvent instance.
	 * @param event
	 * @return
	 */
	boolean addEvent(EITEvent event);

	/**
	 * add a EITEvent instance.
	 * @param index
	 * @param event
	 * @return
	 */
	boolean addEventAt(int index, EITEvent event);
	
	/**
	 * remove a EITEvent instance.
	 * @param event
	 * @return
	 */
	boolean removeEvent(EITEvent event);

	/**
	 * remove a EITEvent instance.
	 * @param index
	 * @return
	 */
	boolean removeEventAt(int index);

	/**
	 * remove all EITEvent instances.
	 */
	void removeAllEvents();
}

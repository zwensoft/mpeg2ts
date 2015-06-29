/**
 * 
 */
package mts.api.descriptor;

import mts.api.StreamType;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface DC_ServiceLocation extends DescriptorContent {
	/**
	 * return a value of 'PCR_PID'.
	 * @return
	 */
	int getPCR_PID();
	
	/**
	 * return a value of 'number_elements'.
	 * @return
	 */
	int getNumberElements();
	
	/**
	 * return a total byte size of elements.
	 * @return
	 */
	int getElementsLength();
	
	/**
	 * return a value of 'stream_type'.
	 * @param index
	 * @return
	 */
	StreamType getStreamTypeAt(int index);
	
	/**
	 * return a value of 'elementary_PID'.
	 * @param index
	 * @return
	 */
	int getElementaryPidAt(int index);
	
	/**
	 * return a value of 'ISO_639_language_code'.
	 * @param index
	 * @return
	 */
	int getLanguageCodeAt(int index);
	
	/**
	 * @param index
	 * @param stream_type
	 * @param elementary_PID
	 * @param lang_code
	 * @return
	 */
	boolean setElementAt(int index, StreamType stream_type, int elementary_PID, int lang_code);
	
	/**
	 * @param index
	 * @param stream_type
	 * @param elementary_PID
	 * @param lang_code
	 * @return
	 */
	boolean addElementAt(int index, StreamType stream_type, int elementary_PID, int lang_code);
	
	/**
	 * @param stream_type
	 * @param elementary_PID
	 * @param lang_code
	 * @return
	 */
	boolean addElement(StreamType stream_type, int elementary_PID, int lang_code);
	
	/**
	 * @param index
	 * @return
	 */
	boolean removeElementAt(int index);
	
	/**
	 * 
	 */
	void removeAllElements();
}

/**
 * 
 */
package mts.api.multistring;

import java.util.Iterator;

import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface MultipleStringData extends ByteRepresentation, XMLRepresentation {
	/**
	 * @return
	 */
	int getLanguageCode();
	
	/**
	 * @return
	 */
	int getNumberSegments();
	
	/**
	 * @param index
	 * @return
	 */
	StringSegment getStringSegmentAt(int index);
	
	/**
	 * @return
	 */
	int getStringSegmentsLength();
	
	/**
	 * @return
	 */
	Iterator<StringSegment> getStringSegments();
	
	/**
	 * @param ISO_639_language_code
	 */
	void setLanguageCode(int ISO_639_language_code);
	
	/**
	 * @param index
	 * @param segment
	 * @return
	 */
	boolean setStringSegmentAt(int index, StringSegment segment);
	
	/**
	 * @param index
	 * @param segment
	 * @return
	 */
	boolean addStringSegmentAt(int index, StringSegment segment);
	
	/**
	 * @param segment
	 * @return
	 */
	boolean addStringSegment(StringSegment segment);
	
	/**
	 * @param index
	 * @param segment
	 * @return
	 */
	boolean removeStringSegmentAt(int index, StringSegment segment);
	
	/**
	 * @param segment
	 * @return
	 */
	boolean removeStringSegment(StringSegment segment);
	
	/**
	 * 
	 */
	void removeAllStringSegments();
}

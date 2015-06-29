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
public interface MultipleStringStructure extends ByteRepresentation, XMLRepresentation {
	/**
	 * return a total number of strings defined.
	 * @return
	 */
	int getNumberStrings();
	
	/**
	 * @return
	 */
	int getAllStringDataLength();

	/**
	 * @param index
	 * @return
	 */
	MultipleStringData getStringDataAt(int index);
	
	/**
	 * @return
	 */
	Iterator<MultipleStringData> getAllStringData();
	
	/**
	 * @param index
	 * @param data
	 * @return
	 */
	boolean setStringDataAt(int index, MultipleStringData data);
	
	/**
	 * @param index
	 * @param data
	 * @return
	 */
	boolean addStringDataAt(int index, MultipleStringData data);
	
	/**
	 * @param data
	 * @return
	 */
	boolean addStringData(MultipleStringData data);
	
	/**
	 * @param index
	 * @return
	 */
	boolean removeStringDataAt(int index);
	
	/**
	 * @param data
	 * @return
	 */
	boolean removeStringData(MultipleStringData data);
	
	/**
	 * 
	 */
	void removeAllStringData();
}

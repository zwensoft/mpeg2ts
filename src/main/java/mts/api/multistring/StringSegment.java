/**
 * 
 */
package mts.api.multistring;

import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface StringSegment extends ByteRepresentation, XMLRepresentation {
	/**
	 * @return
	 */
	int getCompressionType();
	
	/**
	 * @return
	 */
	int getMode();
	
	/**
	 * @return
	 */
	int getCompressedStringNumberBytes();
	
	/**
	 * @return
	 */
	byte[] getCompressedStringByte();
	
	/**
	 * @param compression_type
	 */
	void setCompressionType(int compression_type);
	
	/**
	 * @param mode
	 */
	void setMode(int mode);
	
	/**
	 * @param compressed_string
	 */
	void setCompressedStringByte(byte[] compressed_string);

}

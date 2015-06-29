package mts.core;
/**
 * 
 */

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface ByteRepresentation {

	/**
	 * return a byte array size
	 * @return size in bytes
	 */
	int getSizeInBytes();

	/**
	 * return byte array
	 * @return byte array
	 */
	byte[] toByteArray();
}

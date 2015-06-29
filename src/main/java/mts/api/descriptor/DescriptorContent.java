/**
 * 
 */
package mts.api.descriptor;

import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface DescriptorContent extends ByteRepresentation,
		XMLRepresentation {
	/**
	 * @return
	 */
	String getNameString();
}

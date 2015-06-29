package mts.api.descriptor;

import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;
/**
 * 
 */

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface Descriptor extends ByteRepresentation, XMLRepresentation {
	/**
	 * return descriptor tag.
	 * @return DescriptorTag 
	 */
	DescriptorTag getDescriptorTag();
	
	/**
	 * return a value of 'descriptor_length field'.
	 * @return descriptor_length
	 */
	int getDescriptorLength();
	
	/**
	 * return a content.
	 * @return DescriptorContent instance
	 */
	DescriptorContent getContent();
	
	/**
	 * set a content of a Descriptor.
	 * @param content
	 */
	void setContent(DescriptorContent content);
}

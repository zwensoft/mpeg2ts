/**
 * 
 */
package mts.api.psi;

import mts.api.StreamType;
import mts.api.descriptor.DescriptorMethod;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface PMTStream extends ByteRepresentation, XMLRepresentation, DescriptorMethod {
	/**
	 * return a value of 'stream_type'.
	 * @return
	 */
	StreamType getStreamType();

	/**
	 * return a value of 'elementary_PID'.
	 * @return
	 */
	int getElementaryPID();

	/**
	 * set a value of 'stream_type'.
	 * @param type
	 */
	void setStreamType(StreamType type);

	/**
	 * set a value of 'elementary_PID'.
	 * @param pid
	 */
	void setElementaryPID(int pid);
}

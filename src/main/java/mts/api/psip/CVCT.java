/**
 * 
 */
package mts.api.psip;

import java.util.Iterator;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface CVCT extends SITable, DescriptorMethod {
	/**
	 * return a value of 'version_number'.
	 * @return version_number
	 */
	int getVersionNumber();
	
	/**
	 * return a value of 'transport_stream'.
	 * @return transport_stream_id
	 */
	int getTransportStreamId();

	/**
	 * return a value of 'num_channels_in_section'.
	 * @return
	 */
	int getNumChannels();

	/**
	 * return a CVCTChannel instance.
	 * @param index
	 * @return
	 */
	CVCTChannel getChannelAt(int index);

	/**
	 * return all CVCTChannel instances.
	 * @return
	 */
	Iterator<CVCTChannel> getChannels();

	/**
	 * set a value of 'version_number'.
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * set a value of 'transport_stream_id'.
	 * @param tsid
	 */
	void setTransportStreamId(int tsid);
	
	/**
	 * set a CVCTChannel instance.
	 * @param index
	 * @param channel
	 * @return
	 */
	boolean setChannelAt(int index, CVCTChannel channel);

	/**
	 * add a CVCTChannel instance.
	 * @param channel
	 * @return
	 */
	boolean addChannel(CVCTChannel channel);

	/**
	 * add a CVCTChannel instance.
	 * @param index
	 * @param channel
	 * @return
	 */
	boolean addChannelAt(int index, CVCTChannel channel);
}

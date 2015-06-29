/**
 * 
 */
package mts.api.psi;

import java.util.Iterator;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface PMT extends SITable, DescriptorMethod{
	/**
	 * return a value of 'version_number'.
	 * @return
	 */
	int getVersionNumber();

	/**
	 * return a value of 'program_number'.
	 * @return
	 */
	int getProgramNumber();

	/**
	 * return a value of 'PCR_PID'.
	 * @return
	 */
	int getPCR_PID();
	
	/**
	 * return a total number of PMTStreams.
	 * @return
	 */
	int getStreamSize();
	
	/**
	 * return a total byte size of PMTStreams.
	 * @return
	 */
	int getStreamsLength();
	
	/**
	 * return a PMTStream instance.
	 * @param index
	 */
	PMTStream getStreamAt(int index);

	/**
	 * return all PMTStream instances.
	 * @return
	 */
	Iterator<PMTStream> getStreams();

	/**
	 * set a value of 'version_number'
	 * @param version
	 */
	void setVersionNumber(int version);

	/**
	 * set a value of 'program_number'
	 * @param program_num
	 */
	void setProgramNumber(int program_num);

	/**
	 * set a value of 'PCR_PID'
	 * @param pcr_pid
	 */
	void setPCR_PID(int pcr_pid);

	/**
	 * set a PMTStream instance to .
	 * @param index
	 * @param stream
	 */
	boolean setStreamAt(int index, PMTStream stream);

	/**
	 * add a PMTStream instance.
	 * @param stream
	 */
	boolean addStream(PMTStream stream);

	/**
	 * add a PMTStream instance.
	 * @param index
	 * @param stream
	 */
	boolean addStreamAt(int index, PMTStream stream);

	/**
	 * remove a PMTStream instance.
	 * @param stream
	 */
	boolean removeStream(PMTStream stream);

	/**
	 * remove a PMTStream instance.
	 * @param index
	 * @param stream
	 */
	boolean removeStreamAt(int index, PMTStream stream);

	/**
	 * remove all PMTStream instances. 
	 */
	void removeAllStreams();
}

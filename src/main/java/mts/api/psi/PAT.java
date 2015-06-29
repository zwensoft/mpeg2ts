/**
 * 
 */
package mts.api.psi;

import java.util.Iterator;

import mts.api.SITable;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface PAT extends SITable {
	/**
	 * return 'version_number'
	 * @return version_number
	 */
	int getVersionNumber();

	/**
	 * return 'transport_stream_id'
	 * @return transport_stream_id
	 */
	int getTransportStreamId();

	/**
	 * return a number of programs described.
	 * @return number of programs described in this PAT
	 */
	int getProgramSize();

	/**
	 * return a PATProgram instance of a given index.
	 * @param index
	 */
	PATProgram getProgramAt(int index);

	/**
	 * return all PATPrograms instances.
	 * @return
	 */
	Iterator<PATProgram> getPrograms();
	
	/**
	 * set a value of 'version_number'.
	 * @param version
	 */
	void setVersionNumber(int version);

	/**
	 * set a value of 'transport_stream_id'.
	 * @param tsid transport_stream_id
	 */
	void setTransportStreamId(int tsid);

	/**
	 * set a PATProgram instance.
	 * @param index
	 * @param program
	 */
	boolean setProgramAt(int index, PATProgram program);

	/**
	 * add PATProgram.
	 * @param program
	 */
	boolean addProgram(PATProgram program);

	/**
	 * add PATProgram.
	 * @param index
	 * @param program
	 */
	boolean addProgramAt(int index, PATProgram program);

	/**
	 * remove PATProgram.
	 * @param program
	 */
	boolean removeProgram(PATProgram program);

	/**
	 * remove PATProgram. 
	 * @param index
	 */
	boolean removeProgramAt(int index);

	/**
	 * remove all PATPrograms.
	 */
	void removeAllPrograms();
}

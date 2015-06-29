/**
 * 
 */
package mts.api.psi;

import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface PATProgram extends ByteRepresentation, XMLRepresentation {
	/**
	 * return a value of 'program_number'.
	 * @return
	 */
	int getProgramNumber();

	/**
	 * return a value of 'network_PID' or 'program_map_PID'.
	 * @return
	 */
	int getPID();
	
	/**
	 * set a value of 'program_number'. 
	 * @param program_num
	 */
	void setProgramNumber(int program_num);

	/**
	 * set a value of 'network_PID' or 'program_map_PID'.
	 * @param pid
	 */
	void setPID(int pid);
}

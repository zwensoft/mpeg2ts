/**
 * 
 */
package mts.core.psi;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.psi.PATProgram;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class PATProgramDefaultImpl implements PATProgram {
	protected int program_number = 0;
	protected int pid = 0;

	public PATProgramDefaultImpl(int pn, int pid) {
		program_number = pn;
		this.pid = pid;
	}

	/* (non-Javadoc)
	 * @see API.PATProgram#getPID()
	 */
	@Override
	public int getPID() {
		return pid;
	}

	/* (non-Javadoc)
	 * @see API.PATProgram#getProgramNumber()
	 */
	@Override
	public int getProgramNumber() {
		return program_number;
	}

	/* (non-Javadoc)
	 * @see API.PATProgram#setPID(int)
	 */
	@Override
	public void setPID(int pid) {
		this.pid = pid;

	}

	/* (non-Javadoc)
	 * @see API.PATProgram#setProgramNumber(int)
	 */
	@Override
	public void setProgramNumber(int program_num) {
		this.program_number = program_num;

	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 4; // 32bits
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(32);
		os.writeFromLSB(program_number, 16);
		os.writeFromLSB((byte)0, 3);
		os.writeFromLSB(pid, 13);
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<PATProgram>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<program_number>" + program_number + "</program_number>\n");
		if (program_number == 0)
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<network_PID>" + pid + "</network_PID>\n");
		else
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<program_map_PID>" + pid + "</program_map_PID>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"</PATProgram>\n");
		return str;
	}

}

/**
 * 
 */
package mts.core.psi;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.SectionFactory;
import mts.api.TableID;
import mts.api.psi.PAT;
import mts.api.psi.PATProgram;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class PATDefaultImpl extends SITableAbstractImpl implements PAT {
	protected int version_number = 0;
	protected int transport_stream_id = 0;
	protected List<PATProgram> programs = new Vector<PATProgram>();
	protected long interval_millis = 100;

	public PATDefaultImpl(int version, int tsid) {
		version_number = version;
		transport_stream_id = tsid;
	}
	
	/* (non-Javadoc)
	 * @see API.PAT#addProgram(API.PATProgram)
	 */
	@Override
	public boolean addProgram(PATProgram program){
		programs.add(program);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PAT#addProgramAt(int, API.PATProgram)
	 */
	@Override
	public boolean addProgramAt(int index, PATProgram program){
		if (index < 0 || index > programs.size())
			return false;
		programs.add(index, program);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PAT#getProgramAt(int)
	 */
	@Override
	public PATProgram getProgramAt(int index){
		if ( index < 0 || index >= programs.size())
			return null;
		return programs.get(index);
	}

	/* (non-Javadoc)
	 * @see API.PAT#getPrograms()
	 */
	@Override
	public Iterator<PATProgram> getPrograms(){
		return programs.iterator();
	}

	/* (non-Javadoc)
	 * @see API.PAT#getProgramSize()
	 */
	@Override
	public int getProgramSize(){
		return programs.size();
	}

	/* (non-Javadoc)
	 * @see API.PAT#getTransportStreamId()
	 */
	@Override
	public int getTransportStreamId(){
		return transport_stream_id;
	}

	/* (non-Javadoc)
	 * @see API.PAT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber(){
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.PAT#removeAllPrograms()
	 */
	@Override
	public void removeAllPrograms() {
		programs.removeAll(programs);
		
	}

	/* (non-Javadoc)
	 * @see API.PAT#removeProgram(API.PATProgram)
	 */
	@Override
	public boolean removeProgram(PATProgram program){
		return programs.remove(program);
	}

	/* (non-Javadoc)
	 * @see API.PAT#removeProgramAt(int)
	 */
	@Override
	public boolean removeProgramAt(int index){
		if (index < 0 || index >= programs.size())
			return false;
		programs.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PAT#setProgramAt(int, API.PATProgram)
	 */
	@Override
	public boolean setProgramAt(int index, PATProgram program){
		if ( index < 0 || index >= programs.size())
			return false;
		programs.set(index, program);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PAT#setTransportStreamId(int)
	 */
	@Override
	public void setTransportStreamId(int tsid){
		transport_stream_id = tsid;
	}

	/* (non-Javadoc)
	 * @see API.PAT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version){
		version_number = version;
	}
	
	/* (non-Javadoc)
	 * @see API.SITable#getIntervalMillis()
	 */
	@Override
	public long getIntervalMillis() {
		return interval_millis;
	}
	
	/* (non-Javadoc)
	 * @see API.SITable#setIntervalMillis(long)
	 */
	@Override
	public void setIntervalMillis(long millisec) {
		interval_millis = millisec;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return TableID.PROGRAM_ASSOCIATION_TABLE;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x0;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableVersion()
	 */
	@Override
	public int getTableVersion() {
		return getVersionNumber();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<PAT_section>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.PROGRAM_ASSOCIATION_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<transport_stream_id>"+transport_stream_id+"</transport_stream_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		
		if (getProgramSize() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ProgramLoop>\n");
			Iterator<PATProgram> it = getPrograms();
			while(it.hasNext())
				str += it.next().toXMLString(base_space);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</ProgramLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+"</PAT_section>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		int max_stream_size_in_section = 1012;
		int total_section = 0;
		for(int stream_index = 0; stream_index < getProgramSize();) {
			int stream_size = 0;
			while(stream_index < getProgramSize() &&
				(stream_size+getProgramAt(stream_index).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getProgramAt(stream_index++).getSizeInBytes();
			total_section++;
		}
		return total_section;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		if (getTotalSectionNumber()>1)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		Section[] sections;
		int max_stream_size_in_section = 1012;
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createPATSection(this, transport_stream_id, sn, total_section-1);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < getProgramSize() &&
				(stream_size+getProgramAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getProgramAt(write_to_idx++).getSizeInBytes();

			int total_bits = stream_size*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getProgramAt(n).toByteArray());

			sections[sn].setPrivateData(os.toByteArray());

			write_from_idx = write_to_idx;
		}
		
		return sections;
	}

}

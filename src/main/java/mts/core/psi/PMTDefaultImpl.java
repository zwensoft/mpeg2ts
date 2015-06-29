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
import mts.api.descriptor.Descriptor;
import mts.api.psi.PMT;
import mts.api.psi.PMTStream;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class PMTDefaultImpl extends SITableAbstractImpl implements PMT {
	protected int table_pid = 0;
	protected int version_number = 0;
	protected int program_number = 0;
	protected int PCR_PID = 0;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected List<PMTStream> streams = new Vector<PMTStream>();
	protected long interval_millis = 400;

	public PMTDefaultImpl(int pid, int version, int program_num, int pcr_pid) {
		version_number = version;
		program_number = program_num;
		PCR_PID = pcr_pid;
		table_pid = pid;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return TableID.PROGRAM_MAP_TABLE;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return table_pid;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<PMT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.PROGRAM_MAP_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<program_number>"+program_number+"</program_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<PCR_PID>"+PCR_PID+"</PCR_PID>\n");

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}
		
		if (getStreamSize() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<PMTStreamLoop>\n");
			Iterator<PMTStream> it = getStreams();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</PMTStreamLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</PMT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		// PMT is a single section
		return 1;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		return false; // PMT is a single section
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		Section[] sections;
		int max_stream_size_in_section = 1008 - getDescriptorsLength();
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createPMTSection(this, program_number);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < getStreamSize() &&
				(stream_size+getStreamAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getStreamAt(write_to_idx++).getSizeInBytes();

			int total_bits = (4+getDescriptorsLength()+stream_size)*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0,3); // reserved
			os.writeFromLSB(PCR_PID,13);
			os.writeFromLSB(0, 4); // reserved
			os.writeFromLSB(getDescriptorsLength(), 12);
			if (getDescriptorSize() > 0) {
				Iterator<Descriptor> it = getDescriptors();
				while(it.hasNext())
					os.write(it.next().toByteArray());
			}
			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getStreamAt(n).toByteArray());

			sections[sn].setPrivateData(os.toByteArray());

			write_from_idx = write_to_idx;
		}
		
		return sections;
	}
	
	/* (non-Javadoc)
	 * @see API.PMT#addStream(API.PMTStream)
	 */
	@Override
	public boolean addStream(PMTStream stream) {
		streams.add(stream);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PMT#addStreamAt(int, API.PMTStream)
	 */
	@Override
	public boolean addStreamAt(int index, PMTStream stream) {
		if (index < 0 || index > streams.size())
			return false;
		streams.add(index, stream);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PMT#getPCR_PID()
	 */
	@Override
	public int getPCR_PID() {
		return PCR_PID;
	}

	/* (non-Javadoc)
	 * @see API.PMT#getProgramNumber()
	 */
	@Override
	public int getProgramNumber() {
		return program_number;
	}

	/* (non-Javadoc)
	 * @see API.PMT#getStreamAt(int)
	 */
	@Override
	public PMTStream getStreamAt(int index){
		if (index < 0 || index >= streams.size())
			return null;
		return streams.get(index);
	}

	/* (non-Javadoc)
	 * @see API.PMT#getStreams()
	 */
	@Override
	public Iterator<PMTStream> getStreams() {
		return streams.iterator();
	}

	/* (non-Javadoc)
	 * @see API.PMT#getStreamsLength()
	 */
	@Override
	public int getStreamsLength() {
		int byte_length = 0;
		Iterator<PMTStream> it = streams.iterator();
		while(it.hasNext())
			byte_length += it.next().getSizeInBytes();
		return byte_length;
	}
	
	/* (non-Javadoc)
	 * @see API.PMT#getStreamSize()
	 */
	@Override
	public int getStreamSize() {
		return streams.size();
	}

	/* (non-Javadoc)
	 * @see API.PMT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.PMT#removeAllStreams()
	 */
	@Override
	public void removeAllStreams() {
		streams.removeAll(streams);
	}

	/* (non-Javadoc)
	 * @see API.PMT#removeStream(API.PMTStream)
	 */
	@Override
	public boolean removeStream(PMTStream stream) {
		return streams.remove(stream);
	}

	/* (non-Javadoc)
	 * @see API.PMT#removeStreamAt(int, API.PMTStream)
	 */
	@Override
	public boolean removeStreamAt(int index, PMTStream stream) {
		if (index < 0 || index >= streams.size())
			return false;
		streams.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PMT#setPCR_PID(int)
	 */
	@Override
	public void setPCR_PID(int pcr_pid) {
		PCR_PID = pcr_pid;
	}

	/* (non-Javadoc)
	 * @see API.PMT#setProgramNumber(int)
	 */
	@Override
	public void setProgramNumber(int program_num) {
		this.program_number = program_num;
	}

	/* (non-Javadoc)
	 * @see API.PMT#setStreamAt(int, API.PMTStream)
	 */
	@Override
	public boolean setStreamAt(int index, PMTStream stream){
		if (index < 0 || index >= streams.size())
			return false;
		streams.set(index, stream);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.PMT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version) {
		version_number = version;		
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#addDescriptor(API.Descriptor)
	 */
	@Override
	public boolean addDescriptor(Descriptor desc) {
		descs.add(desc);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#addDescriptorAt(int, API.Descriptor)
	 */
	@Override
	public boolean addDescriptorAt(int index, Descriptor desc) {
		if (index < 0 || index > descs.size())
			return false;
		descs.add(index, desc);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorAt(int)
	 */
	@Override
	public Descriptor getDescriptorAt(int index){
		if (index < 0 || index >= descs.size())
			return null;
		return descs.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptors()
	 */
	@Override
	public Iterator<Descriptor> getDescriptors(){
		return descs.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorSize()
	 */
	@Override
	public int getDescriptorSize(){
		return descs.size();
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorsLength()
	 */
	@Override
	public int getDescriptorsLength(){
		int desc_length = 0;
		Iterator<Descriptor> it = descs.iterator();
		while(it.hasNext())
			desc_length += it.next().getSizeInBytes();
		return desc_length;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeAllDescriptors()
	 */
	@Override
	public void removeAllDescriptors(){
		descs.removeAll(descs);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeDescriptor(API.Descriptor)
	 */
	@Override
	public boolean removeDescriptor(Descriptor desc){
		return descs.remove(desc);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeDescriptorAt(int)
	 */
	@Override
	public boolean removeDescriptorAt(int index){
		if (index < 0 || index >= descs.size())
			return false;
		descs.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#setDescriptorAt(int, API.Descriptor)
	 */
	@Override
	public boolean setDescriptorAt(int index, Descriptor desc){
		if (index < 0 || index >= descs.size())
			return false;
		descs.set(index, desc);
		return true;
	}
}

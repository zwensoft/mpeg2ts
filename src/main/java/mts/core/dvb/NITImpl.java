/**
 * 
 */
package mts.core.dvb;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.SectionFactory;
import mts.api.TableID;
import mts.api.descriptor.Descriptor;
import mts.api.dvb.NIT;
import mts.api.dvb.NITTransportStream;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class NITImpl extends SITableAbstractImpl implements NIT {
	
	protected boolean b_actual;
	protected int version_number;
	protected int network_id;
	protected List<NITTransportStream> transport_streams = new Vector<NITTransportStream>();
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected long interval_millis = 500;

	/* (non-Javadoc)
	 * @see API.DVB.NIT#addTransportStream(API.DVB.NITTransportStream)
	 */
	@Override
	public boolean addTransportStream(NITTransportStream ts) {
		transport_streams.add(ts);
		return false;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#getNetworkId()
	 */
	@Override
	public int getNetworkId() {
		return network_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#getTransportStreamAt(int)
	 */
	@Override
	public NITTransportStream getTransportStreamAt(int index) {
		return transport_streams.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#getTransportStreams()
	 */
	@Override
	public Iterator<NITTransportStream> getTransportStreams() {
		return transport_streams.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#isActual()
	 */
	@Override
	public boolean isActual() {
		return b_actual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#numTransportStreams()
	 */
	@Override
	public int numTransportStreams() {
		return transport_streams.size();
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#removeAllTransportStreams()
	 */
	@Override
	public void removeAllTransportStreams() {
		transport_streams.removeAll(transport_streams);
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#removeTransportStream(API.DVB.NITTransportStream)
	 */
	@Override
	public boolean removeTransportStream(NITTransportStream ts) {
		return transport_streams.remove(ts);
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#setIsActual(boolean)
	 */
	@Override
	public void setIsActual(boolean isActual) {
		b_actual = isActual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#setNetworkId(int)
	 */
	@Override
	public void setNetworkId(int net_id) {
		network_id = net_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.NIT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version) {
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
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		if ( b_actual )
			return TableID.NETWORK_INFORMATION_TABLE_ACTUAL;
		else
			return TableID.NETWORK_INFORMATION_TABLE_OTHER;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x10;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableVersion()
	 */
	@Override
	public int getTableVersion() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.SITable#setIntervalMillis(long)
	 */
	@Override
	public void setIntervalMillis(long millisec) {
		interval_millis = millisec;
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<NIT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+getTableID()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<network_id>"+getNetworkId()+"</network_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		if (numTransportStreams() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<NITTransportStreamLoop>\n");
			Iterator<NITTransportStream> it = getTransportStreams();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</NITTransportStreamLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</NIT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		int max_stream_size_in_section = 1021 - (13 + getDescriptorsLength()); 
		int total_section = 0;
		for(int stream_index = 0; stream_index < numTransportStreams();) {
			int stream_size = 0;
			while(stream_index < numTransportStreams() &&
				(stream_size+getTransportStreamAt(stream_index).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getTransportStreamAt(stream_index++).getSizeInBytes();
			total_section++;
		}

		return total_section;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		if (getTotalSectionNumber()>1)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		Section[] sections;
		int max_stream_size_in_section = 1021 - (13 + getDescriptorsLength());
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createNITSection(this, network_id,
						sn, total_section-1);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < numTransportStreams() &&
				(stream_size+getTransportStreamAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getTransportStreamAt(write_to_idx++).getSizeInBytes();

			int total_bits = (2 + getDescriptorsLength() + 2 + stream_size)*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 4); // reserved
			os.writeFromLSB(getDescriptorsLength(), 12);
			if (getDescriptorSize() > 0) {
				Iterator<Descriptor> it = getDescriptors();
				while(it.hasNext())
					os.write(it.next().toByteArray());
			}

			os.writeFromLSB(0, 4); // reserved
			os.writeFromLSB(stream_size, 12);
			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getTransportStreamAt(n).toByteArray());

			sections[sn].setPrivateData(os.toByteArray());

			write_from_idx = write_to_idx;
		}

		return sections;
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

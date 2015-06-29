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
import mts.api.dvb.BAT;
import mts.api.dvb.BATTransportStream;
import mts.core.SITableAbstractImpl;



/**
 * @author saint
 *
 */
public class BATImpl extends SITableAbstractImpl implements BAT {
	
	protected int version_number;
	protected int bouquet_id;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected List<BATTransportStream> transport_streams = new Vector<BATTransportStream>();
	protected long interval_millis = 500;

	
	/* (non-Javadoc)
	 * @see API.DVB.BAT#addTransportStream(API.DVB.BATTransportStream)
	 */
	@Override
	public boolean addTransportStream(BATTransportStream ts) {
		return transport_streams.add(ts);
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#getBouquetId()
	 */
	@Override
	public int getBouquetId() {
		return bouquet_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#getTransportStreamAt(int)
	 */
	@Override
	public BATTransportStream getTransportStreamAt(int index) {
		return transport_streams.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#getTransportStreams()
	 */
	@Override
	public Iterator<BATTransportStream> getTransportStreams() {
		return transport_streams.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#numTransportStreams()
	 */
	@Override
	public int numTransportStreams() {
		return transport_streams.size();
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#removeAllTransportStreams()
	 */
	@Override
	public void removeAllTransportStreams() {
		transport_streams.removeAll(transport_streams);
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#removeTransportStream(API.DVB.BATTransportStream)
	 */
	@Override
	public boolean removeTransportStream(BATTransportStream ts) {
		return transport_streams.remove(ts);
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#setBouquetId(int)
	 */
	@Override
	public void setBouquetId(int bouquet_id) {
		this.bouquet_id = bouquet_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.BAT#setVersionNumber(int)
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
		return TableID.BOUQUET_ASSOCIATION_TABLE;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x11;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<BAT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+getTableID()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<bouquet_id>"+getBouquetId()+"</bouquet_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		if (numTransportStreams() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<BATTransportStreamLoop>\n");
			Iterator<BATTransportStream> it = getTransportStreams();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</BATTransportStreamLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</BAT>\n");
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
			sections[sn] = SectionFactory.createBATSection(this, bouquet_id,
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

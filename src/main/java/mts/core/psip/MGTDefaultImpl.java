/**
 * 
 */
package mts.core.psip;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.SectionFactory;
import mts.api.TableID;
import mts.api.descriptor.Descriptor;
import mts.api.psip.MGT;
import mts.api.psip.MGTTable;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class MGTDefaultImpl extends SITableAbstractImpl implements MGT {
	protected int version_number = 0;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected List<MGTTable> tables = new Vector<MGTTable>();
	protected long interval_millis = 150;
	
	/* (non-Javadoc)
	 * @see API.MGT#addTable(API.MGTTable)
	 */
	@Override
	public boolean addTable(MGTTable table) {
		tables.add(table);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MGT#addTableAt(int, API.MGTTable)
	 */
	@Override
	public boolean addTableAt(int index, MGTTable table) {
		if (index < 0 || index > tables.size())
			return false;
		tables.add(index, table);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MGT#getTableAt(int)
	 */
	@Override
	public MGTTable getTableAt(int index){
		if (index < 0 || index >= tables.size())
			return null;
		return tables.get(index);
	}

	/* (non-Javadoc)
	 * @see API.MGT#getTables()
	 */
	@Override
	public Iterator<MGTTable> getTables() {
		return tables.iterator();
	}

	/* (non-Javadoc)
	 * @see API.MGT#getTablesDefined()
	 */
	@Override
	public int getTablesDefined(){
		return tables.size();
	}

	/* (non-Javadoc)
	 * @see API.MGT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.MGT#removeAllTables()
	 */
	@Override
	public void removeAllTables() {
		tables.removeAll(tables);
	}

	/* (non-Javadoc)
	 * @see API.MGT#removeTable(API.MGTTable)
	 */
	@Override
	public boolean removeTable(MGTTable table) {
		return tables.remove(table);
	}

	/* (non-Javadoc)
	 * @see API.MGT#removeTableAt(int)
	 */
	@Override
	public boolean removeTableAt(int index) {
		if (index < 0 || index >= tables.size())
			return false;
		tables.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MGT#setTableAt(int, API.MGTTable)
	 */
	@Override
	public boolean setTableAt(int index, MGTTable table) {
		if (index < 0 || index >= tables.size())
			return false;
		tables.set(index, table);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MGT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version) {
		version_number = version;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return TableID.MASTER_GUIDE_TABLE;
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
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x1FFB;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableVersion()
	 */
	@Override
	public int getTableVersion() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<MGT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.MASTER_GUIDE_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		
		if (getTablesDefined() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<MGTTableLoop>\n");
			Iterator<MGTTable> it = getTables();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</MGTTableLoop>\n");
		}

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</MGT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		return false;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		Section[] sections;
		int max_stream_size_in_section = 1021 - (14 + getDescriptorsLength());
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createMGTSection(this);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < getTablesDefined() &&
				(stream_size+getTableAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getTableAt(write_to_idx++).getSizeInBytes();

			int total_bits = (3+stream_size+2+getDescriptorsLength())*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 8); // protocol_version: currently always '0'
			os.writeFromLSB(getTablesDefined(), 16);

			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getTableAt(n).toByteArray());

			os.writeFromLSB(0xFF, 4); // reserved: 1111
			os.writeFromLSB(getDescriptorsLength(), 12);
			if (getDescriptorSize() > 0) {
				Iterator<Descriptor> it = getDescriptors();
				while(it.hasNext())
					os.write(it.next().toByteArray());
			}

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

/**
 * 
 */
package mts.core.psip;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.TableType;
import mts.api.descriptor.Descriptor;
import mts.api.psip.MGTTable;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class MGTTableDefaultImpl implements MGTTable {
	protected TableType table_type = TableType.Terrestrial_VCT_with_current_next_indicator_1;
	protected int table_type_PID = 0;
	protected int table_type_version_number = 0;
	protected int number_bytes = 0;
	protected List<Descriptor> descs = new Vector<Descriptor>();

	/**
	 * @param type
	 */
	public MGTTableDefaultImpl(TableType type) {
		table_type = type;
	}
	
	/* (non-Javadoc)
	 * @see API.MGTTable#getNumberBytes()
	 */
	@Override
	public long getNumberBytes() {
		return number_bytes;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#getTableType()
	 */
	@Override
	public TableType getTableType() {
		return table_type;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#getTableTypePID()
	 */
	@Override
	public int getTableTypePID() {
		return table_type_PID;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#getTableTypeVersionNumber()
	 */
	@Override
	public int getTableTypeVersionNumber() {
		return table_type_version_number;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#setNumberBytes(int)
	 */
	@Override
	public void setNumberBytes(int bytes) {
		number_bytes = bytes;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#setTableType(API.TableType)
	 */
	@Override
	public void setTableType(TableType type) {
		table_type = type;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#setTableTypePID(int)
	 */
	@Override
	public void setTableTypePID(int pid) {
		table_type_PID = pid;
	}

	/* (non-Javadoc)
	 * @see API.MGTTable#setTableTypeVersionNumber(int)
	 */
	@Override
	public void setTableTypeVersionNumber(int version) {
		table_type_version_number = version;
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 11 + getDescriptorsLength();
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(table_type.getValue(), 16);
		os.writeFromLSB(0xFF, 3); // 111
		os.writeFromLSB(table_type_PID, 13);
		os.writeFromLSB(0xFF, 3); // 111 
		os.writeFromLSB(table_type_version_number, 5);
		os.writeFromLSB(number_bytes, 32);
		os.writeFromLSB(0xFF, 4); // 1111
		os.writeFromLSB(getDescriptorsLength(), 12);
		
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			while(it.hasNext())
				os.write(it.next().toByteArray());
		}
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<MGTTable>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_type>"+table_type.getValue()+"</table_type>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_type_PID>"+table_type_PID+"</table_type_PID>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_type_version_number>"+table_type_version_number+"</table_type_version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<number_bytes>"+number_bytes+"</number_bytes>\n");

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}
		str += (MyUTIL.whiteSpaceStr(base_space)+"</MGTTable>\n");

		return str;
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

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
import mts.api.psip.STT;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class STTDefaultImpl extends SITableAbstractImpl implements STT {
	protected int version_number = 0;
	protected long system_time = 0;
	protected int GPS_UTC_offset = 0;
	protected int daylight_savings = 0;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected long interval_millis = 1000;
	
	/* (non-Javadoc)
	 * @see API.STT#getDatlightSavings()
	 */
	@Override
	public int getDatlightSavings() {
		return daylight_savings;
	}

	/* (non-Javadoc)
	 * @see API.STT#getGPS_UTC_Offset()
	 */
	@Override
	public int getGPS_UTC_Offset() {
		return GPS_UTC_offset;
	}

	/* (non-Javadoc)
	 * @see API.STT#getSystemTime()
	 */
	@Override
	public long getSystemTime() {
		return system_time;
	}

	/* (non-Javadoc)
	 * @see API.STT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.STT#setDatlightSavings(int)
	 */
	@Override
	public void setDatlightSavings(int value) {
		daylight_savings = value;
	}

	/* (non-Javadoc)
	 * @see API.STT#setGPS_UTC_Offset(int)
	 */
	@Override
	public void setGPS_UTC_Offset(int offset) {
		GPS_UTC_offset = offset;
	}

	/* (non-Javadoc)
	 * @see API.STT#setSystemTime(long)
	 */
	@Override
	public void setSystemTime(long time) {
		system_time = time;
	}

	/* (non-Javadoc)
	 * @see API.STT#setVersionNumber(int)
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
		return TableID.SYSTEM_TIME_TABLE;
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
		return getVersionNumber();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<STT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.SYSTEM_TIME_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<system_time>"+system_time+"</system_time>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<GPS_UTC_offset>"+GPS_UTC_offset+"</GPS_UTC_offset>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<daylight_savings>"+daylight_savings+"</daylight_savings>\n");
		
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</STT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		return 1; // STT is a single section
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
		//int max_stream_size_in_section = 1021 - (17 + getDescriptorsLength());
		int total_section = getTotalSectionNumber();

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createSTTSection(this);

			int total_bits = (8+getDescriptorsLength())*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 8); // protocol_version: currently always '0'
			os.writeFromLSB((int)system_time, 32);
			os.writeFromLSB(GPS_UTC_offset, 8);
			os.writeFromLSB(daylight_savings, 16);
	
			if (getDescriptorSize() > 0) {
				Iterator<Descriptor> it = getDescriptors();
				while(it.hasNext())
					os.write(it.next().toByteArray());
			}

			sections[sn].setPrivateData(os.toByteArray());
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

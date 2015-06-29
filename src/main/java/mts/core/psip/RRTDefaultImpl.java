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
import mts.api.multistring.MultipleStringStructure;
import mts.api.psip.RRT;
import mts.api.psip.RRTDimension;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class RRTDefaultImpl extends SITableAbstractImpl implements RRT {
	protected int rating_region;
	protected int version_number = 0;
	protected MultipleStringStructure rating_region_name_text;
	protected List<RRTDimension> dimensions = new Vector<RRTDimension>();
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected long interval_millis = 60000;
	
	/* (non-Javadoc)
	 * @see API.RRT#addDimension(API.RRTDimension)
	 */
	@Override
	public boolean addDimension(RRTDimension dimension) {
		dimensions.add(dimension);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRT#addDimensionAt(int, API.RRTDimension)
	 */
	@Override
	public boolean addDimensionAt(int index, RRTDimension dimension) {
		if (index < 0 || index > dimensions.size())
			return false;
		dimensions.add(index, dimension);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRT#getDimensionAt(int)
	 */
	@Override
	public RRTDimension getDimensionAt(int index) {
		if (index < 0 || index >= dimensions.size())
			return null;
		return dimensions.get(index);
	}

	/* (non-Javadoc)
	 * @see API.RRT#getDimensions()
	 */
	@Override
	public Iterator<RRTDimension> getDimensions() {
		return dimensions.iterator();
	}

	/* (non-Javadoc)
	 * @see API.RRT#getDimensionsDefined()
	 */
	@Override
	public int getDimensionsDefined() {
		return dimensions.size();
	}

	/* (non-Javadoc)
	 * @see API.RRT#getDimensionsLength()
	 */
	@Override
	public int getDimensionsLength() {
		int byte_length = 0;
		Iterator<RRTDimension> it = dimensions.iterator();
		while(it.hasNext())
			byte_length += it.next().getSizeInBytes();
		return byte_length;
	}

	/* (non-Javadoc)
	 * @see API.RRT#getRatingRegion()
	 */
	@Override
	public int getRatingRegion() {
		return rating_region;
	}

	/* (non-Javadoc)
	 * @see API.RRT#getRatingRegionNameLength()
	 */
	@Override
	public int getRatingRegionNameLength() {
		if (rating_region_name_text == null)
			return 0;
		return rating_region_name_text.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.RRT#getRatingRegionNameText()
	 */
	@Override
	public MultipleStringStructure getRatingRegionNameText() {
		return rating_region_name_text;
	}

	/* (non-Javadoc)
	 * @see API.RRT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.RRT#removeAllDimensions()
	 */
	@Override
	public void removeAllDimensions() {
		dimensions.removeAll(dimensions);
	}

	/* (non-Javadoc)
	 * @see API.RRT#removeDimension(API.RRTDimension)
	 */
	@Override
	public boolean removeDimension(RRTDimension dimension) {
		return dimensions.remove(dimension);
	}

	/* (non-Javadoc)
	 * @see API.RRT#removeDimensionAt(int, API.RRTDimension)
	 */
	@Override
	public boolean removeDimensionAt(int index, RRTDimension dimension) {
		if (index < 0 || index >= dimensions.size())
			return false;
		dimensions.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRT#setDimensionAt(int, API.RRTDimension)
	 */
	@Override
	public boolean setDimensionAt(int index, RRTDimension dimension) {
		if (index < 0 || index >= dimensions.size())
			return false;
		dimensions.set(index, dimension);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRT#setRatingRegion(int)
	 */
	@Override
	public void setRatingRegion(int region) {
		rating_region = region;
	}

	/* (non-Javadoc)
	 * @see API.RRT#setRatingRegionNameText(API.MultipleStringStructure)
	 */
	@Override
	public void setRatingRegionNameText(MultipleStringStructure text) {
		rating_region_name_text = text;
	}

	/* (non-Javadoc)
	 * @see API.RRT#setVersionNumber(int)
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
		return TableID.RATING_REGION_TABLE;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x1FFB;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<RRT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.RATING_REGION_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<rating_region>"+rating_region+"</rating_region>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		
		if (rating_region_name_text != null)
			str += rating_region_name_text.toXMLString(base_space+2);
		
		if (getDimensionsDefined() > 0) {
			Iterator<RRTDimension> it = getDimensions();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<RatingDimensionLoop>\n");
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</RatingDimensionLoop>\n");
		}

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</RRT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		int max_stream_size_in_section = 1021 - (14+getRatingRegionNameLength()+getDescriptorsLength()); 
		int total_section = 0;
		for(int stream_index = 0; stream_index < getDimensionsDefined();) {
			int stream_size = 0;
			while(stream_index < getDimensionsDefined() &&
				(stream_size+getDimensionAt(stream_index).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getDimensionAt(stream_index++).getSizeInBytes();
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
		int max_stream_size_in_section = 1021 - (14+getRatingRegionNameLength()+getDescriptorsLength());
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createRRTSection(this, rating_region,
						sn, total_section-1);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < getDimensionsDefined() &&
				(stream_size+getDimensionAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getDimensionAt(write_to_idx++).getSizeInBytes();

			int total_bits = (2+getRatingRegionNameLength()+1+stream_size+2+getDescriptorsLength())*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 8); // protocol_version: currently always '0'
			os.writeFromLSB(getRatingRegionNameLength(), 8);
			if (rating_region_name_text != null)
				os.write(rating_region_name_text.toByteArray());
			os.writeFromLSB(getDimensionsDefined(), 8);

			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getDimensionAt(n).toByteArray());

			os.writeFromLSB(0xFF, 6); // 111111
			os.writeFromLSB(getDescriptorsLength(), 10);
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

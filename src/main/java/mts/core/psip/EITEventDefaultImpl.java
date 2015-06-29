/**
 * 
 */
package mts.core.psip;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.Descriptor;
import mts.api.multistring.MultipleStringStructure;
import mts.api.psip.EITEvent;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class EITEventDefaultImpl implements EITEvent {
	protected int event_id;
	protected long start_time;
	protected int ETM_location;
	protected int length_in_seconds;
	protected MultipleStringStructure title_text;
	protected List<Descriptor> descs = new Vector<Descriptor>();

	/* (non-Javadoc)
	 * @see API.EITEvent#getETMLocation()
	 */
	@Override
	public int getETMLocation() {
		return ETM_location;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#getEventId()
	 */
	@Override
	public int getEventId() {
		return event_id;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#getLengthInSeconds()
	 */
	@Override
	public int getLengthInSeconds() {
		return length_in_seconds;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return start_time;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#getTitleLength()
	 */
	@Override
	public int getTitleLength() {
		if (title_text == null)
			return 0;
		return title_text.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#getTitleText()
	 */
	@Override
	public MultipleStringStructure getTitleText() {
		return title_text;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#setETMLocation(int)
	 */
	@Override
	public void setETMLocation(int etm_location) {
		ETM_location = etm_location;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#setEventId(int)
	 */
	@Override
	public void setEventId(int event_id) {
		this.event_id = event_id;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#setLengthInSeconds(int)
	 */
	@Override
	public void setLengthInSeconds(int length_in_seconds) {
		this.length_in_seconds = length_in_seconds;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#setStartTime(long)
	 */
	@Override
	public void setStartTime(long start_time) {
		this.start_time = start_time;
	}

	/* (non-Javadoc)
	 * @see API.EITEvent#setTitleText(API.MultipleStringStructure)
	 */
	@Override
	public void setTitleText(MultipleStringStructure title_text) {
		this.title_text = title_text;
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 12 + getTitleLength() + getDescriptorsLength();
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(0x11, 2); // reserved 11
		os.writeFromLSB(event_id, 14);
		os.writeFromLSB((int)start_time, 32);
		os.writeFromLSB(0xFF, 2); // reserved 11
		os.writeFromLSB(ETM_location, 2);
		os.writeFromLSB(length_in_seconds, 20);
		os.writeFromLSB(getTitleLength(), 8);
		
		if (title_text != null)
			os.write(title_text.toByteArray());
		os.writeFromLSB(0xFF, 4); // reserved 1111

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
		str += (MyUTIL.whiteSpaceStr(base_space)+"<EITEvent>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<event_id>"+event_id+"</event_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<start_time>"+start_time+"</start_time>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ETM_location>"+ETM_location+"</ETM_location>\n");
		
		if (title_text != null)
			str += title_text.toXMLString(base_space+2); 

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}
		str += (MyUTIL.whiteSpaceStr(base_space)+"</EITEvent>\n");

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

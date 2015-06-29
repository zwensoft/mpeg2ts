/**
 * 
 */
package mts.core.dvb;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.Descriptor;
import mts.api.dvb.DVBEITEvent;



/**
 * @author saint
 *
 */
public class DVBEITEventImpl implements DVBEITEvent {

	int event_id;
	long start_time;
	int duration;
	int running_status;
	int free_CA_mode;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	
	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#getDuration()
	 */
	@Override
	public int getDuration() {
		return duration;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#getEventId()
	 */
	@Override
	public int getEventId() {
		return event_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#getFreeCaMode()
	 */
	@Override
	public int getFreeCaMode() {
		return free_CA_mode;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#getRunningStatus()
	 */
	@Override
	public int getRunningStatus() {
		return running_status;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return start_time;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setDuration(int)
	 */
	@Override
	public void setDuration(int duration) {
		this.duration = duration; 
	}
	
	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setDuration(int, int, int)
	 */
	@Override
	public void setDuration(int hour, int minute, int second) {
		duration = 0;
		duration |= (hour/10)<<20;
		duration |= (hour%10)<<16;
		duration |= (minute/10)<<12;
		duration |= (minute%10)<<8;
		duration |= (second/10)<<4;
		duration |= (second%10);
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setEventId(int)
	 */
	@Override
	public void setEventId(int event_id) {
		this.event_id = event_id; 
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setFreeCaMode(int)
	 */
	@Override
	public void setFreeCaMode(int free_ca_mode) {
		free_CA_mode = free_ca_mode;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setRunningStatus(int)
	 */
	@Override
	public void setRunningStatus(int run_status) {
		running_status = run_status;
	}

	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setStartTime(long)
	 */
	@Override
	public void setStartTime(long start_time) {
		this.start_time = start_time;
	}
	
	/* (non-Javadoc)
	 * @see API.DVB.DVBEITEvent#setStartTime(int, int, int, int, int, int)
	 */
	@Override
	public void setStartTime(int year, int month, int day,
			int hour, int minute, int second) {
		start_time = 0;
		start_time |= (hour/10)<<20;
		start_time |= (hour%10)<<16;
		start_time |= (minute/10)<<12;
		start_time |= (minute%10)<<8;
		start_time |= (second/10)<<4;
		start_time |= (second%10);
		
		long Y=year, M=month, D=day, L, MJD;
		if ( M == 1 || M == 2 )
			L = 1;
		else
			L = 0;
		MJD = 14956 + D + (int)((Y-L)*365.25) + (int)((M+1+L*12)*30.6001);
		start_time |= (MJD<<24);
	}
	
	
	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 12 + getDescriptorsLength();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		
		os.writeFromLSB(event_id, 16);
		os.writeFromLSB((int)(start_time>>8), 32);
		os.writeFromLSB((int)(start_time&0xFF), 8);
		os.writeFromLSB(duration, 24);
		os.writeFromLSB(running_status, 3);
		os.writeFromLSB(free_CA_mode, 1);

		os.writeFromLSB(getDescriptorsLength(), 12);
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			while(it.hasNext())
				os.write(it.next().toByteArray());
		}

		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<EITEvent>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<start_time>"+start_time+"</start_time>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<duration>"+duration+"</duration>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<running_status>"+running_status+"</running_status>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<free_CA_mode>"+free_CA_mode+"</free_CA_mode>\n");
		
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

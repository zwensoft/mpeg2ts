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
import mts.api.psip.EIT;
import mts.api.psip.EITEvent;
import mts.core.SITableAbstractImpl;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class EITDefaultImpl extends SITableAbstractImpl implements EIT {
	protected int table_pid = 0;
	protected int version_number = 0;
	protected int source_id;
	protected List<EITEvent> events = new Vector<EITEvent>();
	protected long interval_millis = 500;
	/**
	 * @param pid
	 */
	public EITDefaultImpl(int pid) {
		table_pid = pid;
	}
	
	/* (non-Javadoc)
	 * @see API.EIT#addEvent(API.EITEvent)
	 */
	@Override
	public boolean addEvent(EITEvent event) {
		events.add(event);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.EIT#addEventAt(int, API.EITEvent)
	 */
	@Override
	public boolean addEventAt(int index, EITEvent event) {
		if (index < 0 || index > events.size())
			return false;
		events.add(index, event);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.EIT#getEventAt(int)
	 */
	@Override
	public EITEvent getEventAt(int index) {
		if (index < 0 || index >= events.size())
			return null;
		return events.get(index);
	}

	/* (non-Javadoc)
	 * @see API.EIT#getEvents()
	 */
	@Override
	public Iterator<EITEvent> getEvents() {
		return events.iterator();
	}

	/* (non-Javadoc)
	 * @see API.EIT#getEventsLength()
	 */
	@Override
	public int getEventsLength() {
		int length = 0;
		Iterator<EITEvent> it = events.iterator();
		while(it.hasNext())
			length += it.next().getSizeInBytes();
		return length;
	}

	/* (non-Javadoc)
	 * @see API.EIT#getNumEventsInSection()
	 */
	@Override
	public int getNumEventsInSection() {
		return events.size();
	}

	/* (non-Javadoc)
	 * @see API.EIT#getSourceId()
	 */
	@Override
	public int getSourceId() {
		return source_id;
	}

	/* (non-Javadoc)
	 * @see API.EIT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.EIT#removeAllEvents()
	 */
	@Override
	public void removeAllEvents() {
		events.removeAll(events);		
	}

	/* (non-Javadoc)
	 * @see API.EIT#removeEvent(API.EITEvent)
	 */
	@Override
	public boolean removeEvent(EITEvent event) {
		return events.remove(event);
	}

	/* (non-Javadoc)
	 * @see API.EIT#removeEventAt(int)
	 */
	@Override
	public boolean removeEventAt(int index) {
		if (index < 0 || index >= events.size())
			return false;
		events.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.EIT#setEventAt(int, API.EITEvent)
	 */
	@Override
	public boolean setEventAt(int index, EITEvent event) {
		if (index < 0 || index >= events.size())
			return false;
		events.set(index, event);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.EIT#setSourceId(int)
	 */
	@Override
	public void setSourceId(int source_id) {
		this.source_id = source_id;		
	}

	/* (non-Javadoc)
	 * @see API.EIT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version_number) {
		this.version_number = version_number;		
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return TableID.EVENT_INFORMATION_TABLE;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<EIT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.EVENT_INFORMATION_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<source_id>"+source_id+"</source_id>\n");
		
		if (getNumEventsInSection() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<EITEventLoop>\n");
			Iterator<EITEvent> it = getEvents();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</EITEventLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</EIT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		int max_stream_size_in_section = 1021 - 11; 
		int total_section = 0;
		for(int stream_index = 0; stream_index < getNumEventsInSection();) {
			int stream_size = 0;
			while(stream_index < getNumEventsInSection() &&
				(stream_size+getEventAt(stream_index).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getEventAt(stream_index++).getSizeInBytes();
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
		int max_stream_size_in_section = 1021 - 11;
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createEITSection(this, source_id,
						sn, total_section-1);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < getNumEventsInSection() &&
				(stream_size+getEventAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getEventAt(write_to_idx++).getSizeInBytes();

			int total_bits = (2 + stream_size)*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 8); // protocol_version: currently always '0'
			os.writeFromLSB(getNumEventsInSection(), 8);

			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getEventAt(n).toByteArray());

			sections[sn].setPrivateData(os.toByteArray());

			write_from_idx = write_to_idx;
		}

		return sections;
	}

}

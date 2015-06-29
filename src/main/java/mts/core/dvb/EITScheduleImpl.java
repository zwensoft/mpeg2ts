/**
 * 
 */
package mts.core.dvb;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.TableID;
import mts.api.dvb.DVBEITEvent;
import mts.api.dvb.EITSchedule;
import mts.core.SITableAbstractImpl;



/**
 * @author saint
 *
 */
public class EITScheduleImpl extends SITableAbstractImpl implements EITSchedule {

	protected boolean b_actual;
	protected int version_number;
	protected int service_id;
	protected int transport_stream_id;
	protected int original_network_id;
	protected List<DVBEITEvent> events = new Vector<DVBEITEvent>();
	protected long interval_millis = 500;
	
	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#addEvent(API.DVB.DVBEITEvent)
	 */
	@Override
	public boolean addEvent(DVBEITEvent event) {
		return events.add(event);
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getEventAt(int)
	 */
	@Override
	public DVBEITEvent getEventAt(int index) {
		return events.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getEvents()
	 */
	@Override
	public Iterator<DVBEITEvent> getEvents() {
		return events.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getOrgNetworkId()
	 */
	@Override
	public int getOrgNetworkId() {
		return original_network_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getServiceId()
	 */
	@Override
	public int getServiceId() {
		return service_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getTransportStreamId()
	 */
	@Override
	public int getTransportStreamId() {
		return transport_stream_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#isActual()
	 */
	@Override
	public boolean isActual() {
		return b_actual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#numEvents()
	 */
	@Override
	public int numEvents() {
		return events.size();
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#removeEvent(API.DVB.DVBEITEvent)
	 */
	@Override
	public boolean removeEvent(DVBEITEvent event) {
		return events.remove(event);
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#setIsActual(boolean)
	 */
	@Override
	public void setIsActual(boolean isActual) {
		b_actual = isActual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#setOrgNetworkId(int)
	 */
	@Override
	public void setOrgNetworkId(int org_net_id) {
		original_network_id = org_net_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#setServiceId(int)
	 */
	@Override
	public void setServiceId(int service_id) {
		this.service_id = service_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#setTransportStreamId(int)
	 */
	@Override
	public void setTransportStreamId(int ts_id) {
		transport_stream_id = ts_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITSchedule#setVersionNumber(int)
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
		// TODO: current implementation only deals with '0'.
		if ( b_actual )
			return TableID.EVENT_INFORMATION_ACTUAL_SCHEDULE_0;
		else
			return TableID.EVENT_INFORMATION_OTHER_SCHEDULE_0;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x12;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<EIT_Schedule>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+getTableID()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<service_id>"+service_id+"</service_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<transport_stream_id>"+transport_stream_id+"</transport_stream_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<original_network_id>"+original_network_id+"</original_network_id>\n");
		
		if (numEvents() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<EventScheduleLoop>\n");
			Iterator<DVBEITEvent> it = getEvents();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</EventScheduleLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</EIT_Schedule>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		// TODO Auto-generated method stub
		return null;
	}

}

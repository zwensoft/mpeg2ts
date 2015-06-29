/**
 * 
 */
package mts.core.dvb;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.SectionFactory;
import mts.api.TableID;
import mts.api.dvb.DVBEITEvent;
import mts.api.dvb.EITPF;
import mts.core.SITableAbstractImpl;

/**
 * @author saint
 *
 */
public class EITPFImpl extends SITableAbstractImpl implements EITPF {

	protected boolean b_actual;
	protected int version_number;
	protected int service_id;
	protected int transport_stream_id;
	protected int original_network_id;
	protected DVBEITEvent present_event = null;
	protected DVBEITEvent following_event = null;
	protected long interval_millis = 500;
	
	
	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getFollowingEvent()
	 */
	@Override
	public DVBEITEvent getFollowingEvent() {
		return following_event;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getOrgNetworkId()
	 */
	@Override
	public int getOrgNetworkId() {
		return original_network_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getPresentEvent()
	 */
	@Override
	public DVBEITEvent getPresentEvent() {
		return present_event;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getServiceId()
	 */
	@Override
	public int getServiceId() {
		return service_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getTransportStreamId()
	 */
	@Override
	public int getTransportStreamId() {
		return transport_stream_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#isActual()
	 */
	@Override
	public boolean isActual() {
		return b_actual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setFollowingEvent(API.DVB.DVBEITEvent)
	 */
	@Override
	public void setFollowingEvent(DVBEITEvent event) {
		following_event = event;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setIsActual(boolean)
	 */
	@Override
	public void setIsActual(boolean isActual) {
		b_actual = isActual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setOrgNetworkId(int)
	 */
	@Override
	public void setOrgNetworkId(int org_net_id) {
		original_network_id = org_net_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setPresentEvent(API.DVB.DVBEITEvent)
	 */
	@Override
	public void setPresentEvent(DVBEITEvent event) {
		present_event = event;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setServiceId(int)
	 */
	@Override
	public void setServiceId(int service_id) {
		this.service_id = service_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setTransportStreamId(int)
	 */
	@Override
	public void setTransportStreamId(int ts_id) {
		transport_stream_id = ts_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.EITPF#setVersionNumber(int)
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
			return TableID.EVENT_INFORMATION_ACTUAL_PF;
		else
			return TableID.EVENT_INFORMATION_OTHER_PF;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return 0x0012;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<EIT_PF>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+getTableID()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<service_id>"+service_id+"</service_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<transport_stream_id>"+transport_stream_id+"</transport_stream_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<original_network_id>"+original_network_id+"</original_network_id>\n");
		
		if ( present_event != null ) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<PresentEvent>\n");
			str += present_event.toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</PresentEvent>\n");
		}
		if ( following_event != null ) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<FollowingEvent>\n");
			str += following_event.toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</FollowingEvent>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</EIT_PF>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		if ( present_event != null && following_event != null )
			return 2;
		if ( following_event == null )
			return 1;

		return 1;
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
		int total_section = getTotalSectionNumber();

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createDVBEITSection(this, service_id,
						sn, total_section-1);
			
			int total_bits;
			BitOutputStream os;
			if ( sn == 0 ) {
				total_bits = (6 + present_event.getSizeInBytes())*Byte.SIZE;
				os = new BitOutputStream(total_bits);
				os.writeFromLSB(transport_stream_id, 16);
				os.writeFromLSB(original_network_id, 16);
				os.writeFromLSB(sections.length-1, 8);
				os.write(present_event.toByteArray());
			}
			else {
				total_bits = (6 + following_event.getSizeInBytes())*Byte.SIZE;
				os = new BitOutputStream(total_bits);
				os.writeFromLSB(transport_stream_id, 16);
				os.writeFromLSB(original_network_id, 16);
				os.writeFromLSB(sections.length-1, 8);
				os.write(following_event.toByteArray());
			}

			sections[sn].setPrivateData(os.toByteArray());
		}

		return sections;
	}

}

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
import mts.api.dvb.SDT;
import mts.api.dvb.SDTService;
import mts.core.SITableAbstractImpl;



/**
 * @author saint
 *
 */
public class SDTImpl extends SITableAbstractImpl implements SDT {

	protected boolean b_actual;
	protected int version_number;
	protected int transport_stream_id;
	protected int original_network_id;
	protected List<SDTService> services = new Vector<SDTService>();
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected long interval_millis = 500;
	
	/* (non-Javadoc)
	 * @see API.DVB.SDT#addService(API.DVB.SDTService)
	 */
	@Override
	public boolean addService(SDTService svc) {
		return services.add(svc); 
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#getOrgNetworkId()
	 */
	@Override
	public int getOrgNetworkId() {
		return original_network_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#getServiceAt(int)
	 */
	@Override
	public SDTService getServiceAt(int index) {
		return services.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#getServices()
	 */
	@Override
	public Iterator<SDTService> getServices() {
		return services.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#getTransportStreamId()
	 */
	@Override
	public int getTransportStreamId() {
		return transport_stream_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#isActual()
	 */
	@Override
	public boolean isActual() {
		return b_actual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#numServices()
	 */
	@Override
	public int numServices() {
		return services.size();
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#removeAllServices()
	 */
	@Override
	public boolean removeAllServices() {
		return services.removeAll(services);
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#removeService(API.DVB.SDTService)
	 */
	@Override
	public boolean removeService(SDTService svc) {
		return services.remove(svc);
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#setIsActual(boolean)
	 */
	@Override
	public void setIsActual(boolean isActual) {
		b_actual = isActual;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#setOrgNetworkId(int)
	 */
	@Override
	public void setOrgNetworkId(int org_net_id) {
		original_network_id = org_net_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#setTransportStreamId(int)
	 */
	@Override
	public void setTransportStreamId(int ts_id) {
		transport_stream_id = ts_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDT#setVersionNumber(int)
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
			return TableID.SERVICE_DESCRIPTION_TABLE_ACTUAL;
		else
			return TableID.SERVICE_DESCRIPTION_TABLE_OTHER;
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
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<SDT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+getTableID()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<transport_stream_id>"+transport_stream_id+"</transport_stream_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<original_network_id>"+original_network_id+"</original_network_id>\n");
		
		if (numServices() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<SDTServiceLoop>\n");
			Iterator<SDTService> it = getServices();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</SDTServiceLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</SDT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see Core.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		int max_stream_size_in_section = 1021 - (12); 
		int total_section = 0;
		for(int stream_index = 0; stream_index < numServices();) {
			int stream_size = 0;
			while(stream_index < numServices() &&
				(stream_size+getServiceAt(stream_index).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getServiceAt(stream_index++).getSizeInBytes();
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
		int max_stream_size_in_section = 1021 - (12);
		int total_section = getTotalSectionNumber(), write_from_idx = 0;

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createSDTSection(this, transport_stream_id,
						sn, total_section-1);

			int write_to_idx = write_from_idx;
			int stream_size = 0;
			while(write_to_idx < numServices() &&
				(stream_size+getServiceAt(write_to_idx).getSizeInBytes()) < max_stream_size_in_section)
				stream_size += getServiceAt(write_to_idx++).getSizeInBytes();

			int total_bits = (3 + stream_size)*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(original_network_id, 16);
			os.writeFromLSB(0, 8); //reserved
			for(int n=write_from_idx; n < write_to_idx; n++)
				os.write(getServiceAt(n).toByteArray());

			sections[sn].setPrivateData(os.toByteArray());

			write_from_idx = write_to_idx;
		}

		return sections;
	}

}

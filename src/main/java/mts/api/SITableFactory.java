package mts.api;

import mts.api.dvb.DVBEITEvent;
import mts.api.dvb.EITPF;
import mts.api.dvb.EITSchedule;
import mts.api.dvb.NIT;
import mts.api.dvb.NITTransportStream;
import mts.api.dvb.SDT;
import mts.api.dvb.SDTService;
import mts.api.psi.PAT;
import mts.api.psi.PATProgram;
import mts.api.psi.PMT;
import mts.api.psi.PMTStream;
import mts.api.psip.CVCT;
import mts.api.psip.CVCTChannel;
import mts.api.psip.EIT;
import mts.api.psip.EITEvent;
import mts.api.psip.ETT;
import mts.api.psip.MGT;
import mts.api.psip.MGTTable;
import mts.api.psip.RRT;
import mts.api.psip.RRTDimension;
import mts.api.psip.RRTDimensionValue;
import mts.api.psip.STT;
import mts.api.psip.TVCT;
import mts.api.psip.TVCTChannel;
/**
 * 
 */
import mts.core.dvb.DVBEITEventImpl;
import mts.core.dvb.EITPFImpl;
import mts.core.dvb.EITScheduleImpl;
import mts.core.dvb.NITImpl;
import mts.core.dvb.NITTransportStreamImpl;
import mts.core.dvb.SDTImpl;
import mts.core.dvb.SDTServiceImpl;
import mts.core.psi.PATDefaultImpl;
import mts.core.psi.PATProgramDefaultImpl;
import mts.core.psi.PMTDefaultImpl;
import mts.core.psi.PMTStreamDefaultImpl;
import mts.core.psip.CVCTChannelDefaultImpl;
import mts.core.psip.CVCTDefaultImpl;
import mts.core.psip.EITDefaultImpl;
import mts.core.psip.EITEventDefaultImpl;
import mts.core.psip.ETTDefaultImpl;
import mts.core.psip.MGTDefaultImpl;
import mts.core.psip.MGTTableDefaultImpl;
import mts.core.psip.RRTDefaultImpl;
import mts.core.psip.RRTDimensionDefaultImpl;
import mts.core.psip.RRTDimensionValueDefaultImpl;
import mts.core.psip.STTDefaultImpl;
import mts.core.psip.TVCTChannelDefaultImpl;
import mts.core.psip.TVCTDefaultImpl;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class SITableFactory {
	/**
	 * @param table_version
	 * @param transport_stream_id
	 * @return
	 */
	public static PAT createPAT(int table_version, int transport_stream_id) {
		return new PATDefaultImpl(table_version, transport_stream_id);
	}

	/**
	 * @param program_number
	 * @param pid
	 * @return
	 */
	public static PATProgram createPATProgram(int program_number, int pid) {
		return new PATProgramDefaultImpl(program_number, pid);
	}

	/**
	 * @param table_pid
	 * @param table_version
	 * @param program_num
	 * @param pcr_pid
	 * @return
	 */
	public static PMT createPMT(int table_pid, int table_version, int program_num, int pcr_pid) {
		return new PMTDefaultImpl(table_pid, table_version, program_num, pcr_pid);
	}
	
	/**
	 * @param stream_type
	 * @param elementary_pid
	 * @return
	 */
	public static PMTStream createPMTStream(StreamType stream_type, int elementary_pid) {
		return new PMTStreamDefaultImpl(stream_type,elementary_pid);
	}
	
	/**
	 * @param table_version
	 * @return
	 */
	public static MGT createMGT(int table_version) {
		MGT mgt = new MGTDefaultImpl();
		mgt.setVersionNumber(table_version);
		return mgt;
	}
	
	/**
	 * @param type
	 * @param table_pid
	 * @param table_version
	 * @param table_num_bytes
	 * @return
	 */
	public static MGTTable createMGTTable(TableType type, int table_pid, int table_version, int table_num_bytes) {
		MGTTable table = new MGTTableDefaultImpl(type);
		table.setTableTypePID(table_pid);
		table.setTableTypeVersionNumber(table_version);
		table.setNumberBytes(table_num_bytes);
		return table;
	}
	
	/**
	 * @param table_version
	 * @param transport_stream_id
	 * @return
	 */
	public static TVCT createTVCT(int table_version, int transport_stream_id) {
		TVCT tvct = new TVCTDefaultImpl();
		tvct.setVersionNumber(table_version);
		tvct.setTransportStreamId(transport_stream_id);
		return tvct;
	}
	
	/**
	 * @param short_name
	 * @param major_num
	 * @param minor_num
	 * @param modulation_mode
	 * @param frequency
	 * @param program_num
	 * @param stype
	 * @param source_id
	 * @return
	 */
	public static TVCTChannel createTVCTChannel(char[] short_name, int major_num, int minor_num,
			int modulation_mode, int frequency, int program_num, ServiceType stype,
			int source_id) {
		TVCTChannel channel = new TVCTChannelDefaultImpl();
		channel.setShortName(short_name);
		channel.setMajorChannelNumber(major_num);
		channel.setMinorChannelNumber(minor_num);
		channel.setModulationMode(modulation_mode);
		channel.setCarrierFrequency(frequency);
		channel.setProgramNumber(program_num);
		channel.setETMLocation(1); // '01' ETM located in the PTC carrying this PSIP
		channel.setAccessControlled(0); // not restricted
		channel.setHidden(0); // not hidden
		channel.setHideGuide(0); // guide(0)
		channel.setServiceType(stype);
		channel.setSourceId(source_id);

		return channel;
	}
	
	/**
	 * @param table_version
	 * @param transport_stream_id
	 * @return
	 */
	public static CVCT createCVCT(int table_version, int transport_stream_id) {
		CVCT cvct = new CVCTDefaultImpl();
		cvct.setVersionNumber(table_version);
		cvct.setTransportStreamId(transport_stream_id);
		return cvct;
	}
	
	/**
	 * @param short_name
	 * @param major_num
	 * @param minor_num
	 * @param modulation_mode
	 * @param frequency
	 * @param program_num
	 * @param stype
	 * @param source_id
	 * @return
	 */
	public static CVCTChannel createCVCTChannel(char[] short_name, int major_num, int minor_num,
			int modulation_mode, int frequency, int program_num, ServiceType stype,
			int source_id) {
		CVCTChannel channel = new CVCTChannelDefaultImpl();
		channel.setShortName(short_name);
		channel.setMajorChannelNumber(major_num);
		channel.setMinorChannelNumber(minor_num);
		channel.setModulationMode(modulation_mode);
		channel.setCarrierFrequency(frequency);
		channel.setProgramNumber(program_num);
		channel.setETMLocation(1); // '01' ETM located in the PTC carrying this PSIP
		channel.setAccessControlled(0); // not restricted
		channel.setHidden(0); // not hidden
		channel.setPathSelect(0); // path 1
		channel.setOutOfBand(0); // this VC is carried within a tuned multiplex.
		channel.setHideGuide(0); // guide(0)
		channel.setServiceType(stype);
		channel.setSourceId(source_id);

		return channel;
	}
	
	/**
	 * @param table_version
	 * @param system_time
	 * @param gps_utc_offset
	 * @param daylight_savings
	 * @return
	 */
	public static STT createSTT(int table_version, long system_time, int gps_utc_offset, int daylight_savings) {
		STT stt = new STTDefaultImpl();
		stt.setVersionNumber(table_version);
		stt.setSystemTime(system_time);
		stt.setGPS_UTC_Offset(gps_utc_offset);
		stt.setDatlightSavings(daylight_savings);
		return stt;
	}
	
	/**
	 * @param table_version
	 * @param rating_region
	 * @return
	 */
	public static RRT createRRT(int table_version, int rating_region) {
		RRT rrt = new RRTDefaultImpl();
		rrt.setVersionNumber(table_version);
		rrt.setRatingRegion(rating_region);
		return rrt;
	}
	
	/**
	 * @param graduated_scale
	 * @return
	 */
	public static RRTDimension createRRTDimension(int graduated_scale) {
		RRTDimension rrtdim = new RRTDimensionDefaultImpl();
		rrtdim.setGraduatedScale(graduated_scale);
		return rrtdim;
	}
	
	/**
	 * @return
	 */
	public static RRTDimensionValue createRRTDimensionValue() {
		RRTDimensionValue dimval = new RRTDimensionValueDefaultImpl();
		return dimval;
	}
	
	/**
	 * @param table_pid
	 * @param table_version
	 * @param source_id
	 * @return
	 */
	public static EIT createEIT(int table_pid, int table_version, int source_id) {
		EIT eit = new EITDefaultImpl(table_pid);
		eit.setVersionNumber(table_version);
		eit.setSourceId(source_id);
		return eit;
	}

	/**
	 * @param event_id
	 * @param start_time
	 * @param length_in_seconds
	 * @return
	 */
	public static EITEvent createEITEvent(int event_id,	long start_time, int length_in_seconds) {
		EITEvent event = new EITEventDefaultImpl();
		event.setEventId(event_id);
		event.setStartTime(start_time);
		event.setLengthInSeconds(length_in_seconds);
		event.setETMLocation(0x1);
		return event;
	}
	
	/**
	 * @param table_pid
	 * @param ETT_table_id_extension
	 * @param ETM_id
	 * @return
	 */
	public static ETT createETT(int table_pid, int ETT_table_id_extension, long ETM_id) {
		ETT ett = new ETTDefaultImpl(table_pid);
		ett.setETTTableIdExtension(ETT_table_id_extension);
		ett.setETMId(ETM_id);
		return ett;
	}
	
	/**
	 * @param b_actual
	 * @param network_id
	 * @return
	 */
	public static NIT createNIT(boolean b_actual, int table_version, int network_id) {
		NIT nit = new NITImpl();
		nit.setIsActual(b_actual);
		nit.setNetworkId(network_id);
		nit.setVersionNumber(table_version);
		return nit;
	}
	
	/**
	 * @param ts_id
	 * @param org_net_id
	 * @return
	 */
	public static NITTransportStream createNITTransportStream(int ts_id, int org_net_id) {
		NITTransportStream nitts = new NITTransportStreamImpl();
		nitts.setTransportStreamId(ts_id);
		nitts.setOrgNetworkId(org_net_id);
		return nitts;
	}
	
	/**
	 * @param b_actual
	 * @param table_version
	 * @param ts_id
	 * @param org_net_id
	 * @return
	 */
	public static SDT createSDT(boolean b_actual, int table_version, int ts_id, int org_net_id) {
		SDT sdt = new SDTImpl();
		sdt.setIsActual(b_actual);
		sdt.setVersionNumber(table_version);
		sdt.setTransportStreamId(ts_id);
		sdt.setOrgNetworkId(org_net_id);
		return sdt;
	}
	
	/**
	 * @param service_id
	 * @param eit_pf_flag
	 * @param eit_sched_flag
	 * @param run_status
	 * @param ca_mode
	 * @return
	 */
	public static SDTService createSDTService(int service_id, int eit_pf_flag, int eit_sched_flag,
			int run_status, int ca_mode) {
		SDTService sdtsvc = new SDTServiceImpl();
		sdtsvc.setServiceId(service_id);
		sdtsvc.setEitPfFlag(eit_pf_flag);
		sdtsvc.setEitSchedFlag(eit_sched_flag);
		sdtsvc.setRunningStatus(run_status);
		sdtsvc.setFreeCaMode(ca_mode);
		return sdtsvc;
	}
	
	/**
	 * @param b_actual
	 * @param table_version
	 * @param service_id
	 * @param ts_id
	 * @param org_net_id
	 * @return
	 */
	public static EITPF createEITPF(boolean b_actual, int table_version, int service_id,
			int ts_id, int org_net_id) {
		EITPF eit = new EITPFImpl();
		eit.setIsActual(b_actual);
		eit.setVersionNumber(table_version);
		eit.setServiceId(service_id);
		eit.setTransportStreamId(ts_id);
		eit.setOrgNetworkId(org_net_id);
		return eit;
	}
	
	/**
	 * @param b_actual
	 * @param table_version
	 * @param service_id
	 * @param ts_id
	 * @param org_net_id
	 * @return
	 */
	public static EITSchedule createEITSchedule(boolean b_actual, int table_version, int service_id,
			int ts_id, int org_net_id) {
		EITSchedule eit = new EITScheduleImpl();
		eit.setIsActual(b_actual);
		eit.setVersionNumber(table_version);
		eit.setServiceId(service_id);
		eit.setTransportStreamId(ts_id);
		eit.setOrgNetworkId(org_net_id);
		return eit;
	}
	
	/**
	 * @param event_id
	 * @param start_time
	 * @param duration
	 * @param run_status
	 * @param free_ca_mode
	 * @return
	 */
	public static DVBEITEvent createDVBEITEvent(int event_id, long start_time,
			int duration, int run_status, int free_ca_mode) {
		DVBEITEvent event = new DVBEITEventImpl();
		event.setEventId(event_id);
		event.setStartTime(start_time);
		event.setDuration(duration);
		event.setRunningStatus(run_status);
		event.setFreeCaMode(free_ca_mode);
		return event;
	}
}

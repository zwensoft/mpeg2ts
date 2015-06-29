/**
 * 
 */
package mts;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import mts.api.SITableFactory;
import mts.api.SITableRepository;
import mts.api.ServiceType;
import mts.api.StreamType;
import mts.api.TableType;
import mts.api.psi.PAT;
import mts.api.psi.PMT;
import mts.api.psip.MGT;
import mts.api.psip.TVCT;
import mts.api.psip.TVCTChannel;
import mts.api.transport.TransportStreamProducer;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TestApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long stream_seconds = 15;
		long stream_length_ms = 1000 * stream_seconds;
		PAT pat;
		PMT pmt1, pmt2;
		MGT mgt;
		TVCT tvct;
		TVCTChannel tvct_chan1, tvct_chan2;
		TransportStreamProducer ts_producer;
		OutputStream os = null;
		
		int pat_ver = 0x1, pmt1_ver = 0x2, pmt2_ver = 0x3, mgt_ver = 0x4, tvct_ver = 0x8;
		int tsid = 0xABC;
		int program_num1 = 0x35, program_num2 = 0x65;
		int pmt1_pid = 1001/*3E9*/, pmt2_pid = 1002/*3EA*/;
		int pmt1_stream1_pid = 0x101, pmt1_stream2_pid = 0x102, pmt2_stream1_pid = 0x201, pmt2_stream2_pid = 0x202;
		int source_id1 = 0x501, source_id2 = 0x502;
		
		pmt1 = SITableFactory.createPMT(pmt1_pid, pmt1_ver, program_num1, 0);
		pmt1.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Video, pmt1_stream1_pid));
		pmt1.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Audio, pmt1_stream2_pid));
		
		pmt2 = SITableFactory.createPMT(pmt2_pid, pmt2_ver, program_num2, 0);
		pmt2.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Video, pmt2_stream1_pid));
		pmt2.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Audio, pmt2_stream2_pid));
		
		pat = SITableFactory.createPAT(pat_ver, tsid);
		pat.addProgram(SITableFactory.createPATProgram(pmt1.getProgramNumber(), pmt1.getTablePID()));
		pat.addProgram(SITableFactory.createPATProgram(pmt2.getProgramNumber(), pmt2.getTablePID()));
		
		tvct = SITableFactory.createTVCT(tvct_ver, tsid);
		tvct_chan1 = SITableFactory.createTVCTChannel((new String("prog1")).toCharArray(), 
				11, 1, 4, 0x1101, pmt1.getProgramNumber(), ServiceType.ATSC_DIGITAL_TELEVISION, source_id1);
		tvct_chan2 = SITableFactory.createTVCTChannel((new String("prog2")).toCharArray(), 
				9, 1, 4, 0x0901, pmt2.getProgramNumber(), ServiceType.ATSC_DIGITAL_TELEVISION, source_id2);
		tvct.addChannel(tvct_chan1);
		tvct.addChannel(tvct_chan2);
		
		mgt = SITableFactory.createMGT(mgt_ver);
		mgt.addTable(SITableFactory.createMGTTable(TableType.Terrestrial_VCT_with_current_next_indicator_1, tvct.getTablePID(),
				tvct.getTableVersion(), 0/* don no this-_-*/));
		
		pat.setEndTime(stream_length_ms);
		pmt1.setEndTime(stream_length_ms);
		pmt2.setEndTime(stream_length_ms);
		mgt.setEndTime(stream_length_ms);
		tvct.setEndTime(stream_length_ms);
		
		SITableRepository.addTable(pat);
		SITableRepository.addTable(pmt1);
		SITableRepository.addTable(pmt2);
		SITableRepository.addTable(mgt);
		SITableRepository.addTable(tvct);
		
		//System.out.println(SITableRepository.dump());
		
		try {
			os = new FileOutputStream("example.ts");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long bitrate = 19400000;
		ts_producer = new TransportStreamProducer("ATSC_Streamer",
				bitrate, stream_length_ms, 10, os, true);
		System.out.println(ts_producer.getBitrate());
		ts_producer.addSchedule(pat);
		ts_producer.addSchedule(pmt1);
		ts_producer.addSchedule(pmt2);
		ts_producer.addSchedule(mgt);
		ts_producer.addSchedule(tvct);
		System.out.println("Expected file size: " + ts_producer.getBitrate()/8*stream_seconds);
		ts_producer.start();
	}
}

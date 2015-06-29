package mts;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import mts.api.SITableFactory;
import mts.api.SITableRepository;
import mts.api.ServiceType;
import mts.api.StreamType;
import mts.api.TableType;
import mts.api.descriptor.DC_ServiceLocation;
import mts.api.descriptor.Descriptor;
import mts.api.descriptor.DescriptorFactory;
import mts.api.descriptor.DescriptorTag;
import mts.api.psi.PAT;
import mts.api.psi.PMT;
import mts.api.psip.MGT;
import mts.api.psip.TVCT;
import mts.api.psip.TVCTChannel;
import mts.api.transport.TransportStreamProducer;



public class ATSCTestStreamGen {
	
	class Location
	{
		int ts_id = 0;
		int num_channels = 0;
		int major_number = 0;
		PAT pat = null;
		PMT pmts[] = null;
		MGT mgt = null;
		TVCT tvct = null;
		DC_ServiceLocation svc_loc_desc = null; 
		
		public Location(int tsid, int n_ch, int major_num) {
			ts_id = tsid;
			num_channels = n_ch;
			major_number = major_num;
			
			Random ver_factory = new Random();
			pat = SITableFactory.createPAT(ver_factory.nextInt(32), ts_id);
			tvct = SITableFactory.createTVCT(ver_factory.nextInt(32), ts_id);
			pmts = new PMT[num_channels];
			int stream_pid1 = 500, stream_pid2 = 600;
			for(int n=0; n < num_channels; n++) {
				pmts[n] = SITableFactory.createPMT(200+n, ver_factory.nextInt(32), n+1, 100+n);
				pmts[n].addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Video, stream_pid1));
				pmts[n].addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Audio, stream_pid2));
				pat.addProgram(SITableFactory.createPATProgram(pmts[n].getProgramNumber(), pmts[n].getTablePID()));
				
				String short_name = "CH"+Integer.toString(major_num)+"-"+Integer.toString(n+1);
				TVCTChannel tvct_chan = SITableFactory.createTVCTChannel(short_name.toCharArray(),
					major_num, n+1, 4, 0, pmts[n].getProgramNumber(), ServiceType.ATSC_DIGITAL_TELEVISION, m_global_source_id++);
				tvct_chan.setChannelTSID(ts_id);
				svc_loc_desc = DescriptorFactory.createServiceLocation();
				svc_loc_desc.addElement(StreamType.ISO_IEC_11172_Video, stream_pid1, 0x6b6f72);
				svc_loc_desc.addElement(StreamType.ISO_IEC_11172_Audio, stream_pid2, 0x6b6f72);
				Descriptor desc = DescriptorFactory.createDescriptor(DescriptorTag.SERVICE_LOCATION);
				desc.setContent(svc_loc_desc);
				tvct_chan.addDescriptor(desc);
				tvct.addChannel(tvct_chan);
				stream_pid1++;
				stream_pid2++;
			}

			mgt = SITableFactory.createMGT(ver_factory.nextInt(32));
			mgt.addTable(SITableFactory.createMGTTable(TableType.Terrestrial_VCT_with_current_next_indicator_1, tvct.getTablePID(),
					tvct.getTableVersion(), 0/* don no this-_-*/));
		}
		
		public String fileName() {
			String str = "atsc";
			str += "_ts(0x"+Integer.toHexString(ts_id)+")";
			str += "_ch("+Integer.toString(major_number)+".1~"+Integer.toString(num_channels)+")";
			return str;
		}
		
		public void fileGenerate() {
			SITableRepository.addTable(pat);
			SITableRepository.addTable(mgt);
			SITableRepository.addTable(tvct);
			for(int n=0; n < pmts.length; n++)
				SITableRepository.addTable(pmts[n]);

			pat.setEndTime(m_stream_length_ms);
			for(int n=0; n < pmts.length; n++)
				pmts[n].setEndTime(m_stream_length_ms);
			mgt.setEndTime(m_stream_length_ms);
			tvct.setEndTime(m_stream_length_ms);
			
			System.out.println(SITableRepository.dump());
			
			File xmlFile = new File(fileName()+".xml");
			FileWriter xml_out = null; 
			OutputStream os = null;
			try {
				os = new FileOutputStream(fileName()+".ts");
				xml_out = new FileWriter(xmlFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				xml_out.write(mgt.toXMLString(0));
				xml_out.write("\n\n");
				xml_out.write(tvct.toXMLString(0));
				xml_out.write("\n\n");
				xml_out.write(pat.toXMLString(0));
				xml_out.write("\n\n");
				for(int n=0; n < pmts.length; n++)
					xml_out.write(pmts[n].toXMLString(0));
				xml_out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			TransportStreamProducer ts_producer;
			ts_producer = new TransportStreamProducer("ATSC_Streamer",
					m_bitrate, m_stream_length_ms, 10, os, true);
			System.out.println(ts_producer.getBitrate());
			ts_producer.addSchedule(pat);
			for(int n=0; n < pmts.length; n++)
				ts_producer.addSchedule(pmts[n]);
			ts_producer.addSchedule(mgt);
			ts_producer.addSchedule(tvct);
			System.out.println("Expected file size: " + ts_producer.getBitrate()/8*m_stream_seconds);
			ts_producer.start();
			
			ts_producer.join();
			
			SITableRepository.removeTable(pat);
			SITableRepository.removeTable(mgt);
			SITableRepository.removeTable(tvct);
			for(int n=0; n < pmts.length; n++)
				SITableRepository.removeTable(pmts[n]);
		}
	}

	public static int NUM_LOCATIONS = 50;	
	Location m_locations[] = null;
	protected int m_global_source_id = 1; 
	public static long m_stream_seconds = 15;
	public static long m_stream_length_ms = 1000 * m_stream_seconds;
	public static long m_bitrate = 19400000;
	
	public ATSCTestStreamGen() {
		Random rand = new Random();
		m_locations = new Location[NUM_LOCATIONS];
		for(int n=0; n < NUM_LOCATIONS; n++) {
			m_locations[n] = new Location(n+1, rand.nextInt(n+1)+1, n+1);
			System.out.println(m_locations[n].fileName());
		}
	}
	
	public Location getLocation(int index) {
		return m_locations[index];
	}
	
	
	public static void main(String[] args) {
		if ( args.length < 2 )
		{
			System.out.println();
			System.out.println("Usage:  java -jar atsc_ts_generator.jar <num_streams> <duration_secs> [bitrate]");
			System.out.println();
			System.out.println("        <num_streams> : the number of streams to generate");
			System.out.println("        <duration_secs> : stream length in seconds");
			System.out.println("        [duration_secs] : bitrates(optional). default is '19400000'.");
			System.out.println();
			return;
		}
		
		ATSCTestStreamGen gen = new ATSCTestStreamGen();
		NUM_LOCATIONS = Integer.valueOf(args[0]).intValue();
		m_stream_seconds = Integer.valueOf(args[1]).intValue();
		if ( args.length > 2 )
			m_bitrate = Integer.valueOf(args[2]).intValue();
		m_stream_length_ms = 1000 * m_stream_seconds;
		for(int n=0; n < NUM_LOCATIONS; n++)
		{
			Location loc = gen.getLocation(n);
			loc.fileGenerate();
		}
	}
}

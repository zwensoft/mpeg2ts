/**
 * 
 */
package mts.api.transport;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import mts.api.SITable;
import mts.api.SITableRepository;
import mts.api.Section;





/**
 * 
 * TODO:
 *  - bandwidth
 *  - continuity field
 *  - PCR_PID
 * 
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TransportStreamProducer implements Runnable {
	private String name;
	private long bitrate_per_sec;
	private static final int DEFAULT_SCHEDULE_TIME_GRANULARITY_MILLISEC = 100;
	private TreeMap<Long,Integer> schedule_tree = new TreeMap<Long,Integer>();
	private long schedule_time_granularity_ms = DEFAULT_SCHEDULE_TIME_GRANULARITY_MILLISEC;
	private long current_time_ms = 0;
	private long end_time_ms = 0;
	private long next_timeout_ms = 0;
	private int num_of_null_packets_for_idle = 0;
	private int total_written_packets = 0;
	private OutputStream output_pipe = null;
	private TreeMap<Integer,Integer> continuity_counter_server = new TreeMap<Integer,Integer>();
	
	// variables for dummy PCR packets
	private boolean bPCR_generate = false;
	private long program_clock_reference_base = 0; // 33 bit field
	private long program_clock_reference_extension = 0;
	public static final int DUMMY_PCR_PID = 0x0909;
	private int last_pcr_sent = 0;
	
	private volatile boolean threadSuspended = false;
	private volatile Thread runningThread = null;

	private byte[] null_packet_bytes;

	public TransportStreamProducer(String name, long bitrate_per_sec,
			long running_time_ms, long schedule_time_granularity_ms, OutputStream os, boolean bGenerateDummyPCR) {
		this.name = new String(name);
		num_of_null_packets_for_idle = (int)((bitrate_per_sec * schedule_time_granularity_ms)/(188*1000*8));
		this.bitrate_per_sec = (188*1000*8*num_of_null_packets_for_idle) / schedule_time_granularity_ms;
		end_time_ms = running_time_ms;
		this.schedule_time_granularity_ms = schedule_time_granularity_ms;
		output_pipe = os;
		null_packet_bytes = TransportPacketFactory.createMPEGNullPacket().toByteArray();
		bPCR_generate = bGenerateDummyPCR;
	}

	public String getName() {
		return name;
	}

	public long getBitrate() {
		return bitrate_per_sec;
	}
	
	public int getNumPktsForSchedule() {
		return num_of_null_packets_for_idle;
	}
	
	public synchronized boolean addSchedule(SITable table) {
		if (schedule_tree.containsValue(new Integer(table.getUniqueID()))) {
			return false;
		}

		long schedule_key = table.getIntervalMillis()+table.getStartTime();
		while (schedule_tree.containsKey(new Long(schedule_key)))
			schedule_key += schedule_time_granularity_ms;
		schedule_tree.put(new Long(schedule_key), new Integer(table.getUniqueID()));
		
		updateNextTimeout();
		return true;
	}
	
	private synchronized boolean addScheduleRuntime(SITable table) {
		if (schedule_tree.containsValue(new Integer(table.getUniqueID()))) {
			return false;
		}

		long schedule_key = table.getIntervalMillis()+current_time_ms;
		while (schedule_tree.containsKey(new Long(schedule_key)))
			schedule_key += schedule_time_granularity_ms;
		schedule_tree.put(new Long(schedule_key), new Integer(table.getUniqueID()));
		
		updateNextTimeout();
		return true;
	}

	public synchronized boolean removeSchedule(SITable table) {
		int table_unique_id = table.getUniqueID();
		Iterator<Map.Entry<Long,Integer>> it = schedule_tree.entrySet().iterator();
		while(it.hasNext()) { // O(n) in the worst case.
			Map.Entry<Long,Integer> entry = it.next();
			if (entry.getValue().intValue() == table_unique_id) {
				schedule_tree.remove(entry.getKey());
				return true;
			}
		}

		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		Thread thisThread = Thread.currentThread();
		while (thisThread == runningThread && current_time_ms < end_time_ms ) {
			synchronized(this) {
				while(threadSuspended && runningThread == thisThread) {
					try { wait();} catch (InterruptedException e){}
				}
			}

			if ( (current_time_ms/1000) != ((current_time_ms+schedule_time_granularity_ms)/1000))
				System.out.println((current_time_ms/1000+1) + " sec..");
			current_time_ms += schedule_time_granularity_ms;

			if (schedule_tree.size() > 0 && current_time_ms > next_timeout_ms) {
				int written_packets = 0;
				while(schedule_tree.size() > 0 && current_time_ms > next_timeout_ms) {
					Integer val = schedule_tree.pollFirstEntry().getValue();
					SITable table = SITableRepository.getTable(val.intValue());
					if (table != null) {
						if (current_time_ms < table.getEndTime()) {
							written_packets += writeTable(table);
							addScheduleRuntime(table); // update schedule position
						}
						updateNextTimeout();
					}
				}
				writeNullPacket(num_of_null_packets_for_idle-written_packets);
			}else {
				doIdleWork();
			}
		}
		System.out.println("TS generation is done.");
	}

	private void updateNextTimeout() {
		if (!schedule_tree.isEmpty())
			next_timeout_ms = schedule_tree.firstKey().longValue();
	}

	private void doIdleWork() {
		writeNullPacket(num_of_null_packets_for_idle);
	}

	private void writeNullPacket(int ntimes) {
		try {
			for (int n=0; n < ntimes; n++)
				output_pipe.write(null_packet_bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		total_written_packets += ntimes;
		checkPCRSend();
	}

	private int writeTable(SITable table) {
		int written_packets = 0;
		Section[] sections = table.toSection();
		//System.out.println("Writing: "+table.getTableID());
		for(int s=0; s < sections.length; s++) {
			TransportPacket[] packets = TransportPacketGenerator.generatePackets(sections[s]);
			for(int p=0; p < packets.length; p++) {
				updateContinuityCounter(packets[p]);
				byte[] data_bytes = packets[p].toByteArray();
				try {
					output_pipe.write(data_bytes);
					written_packets++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		total_written_packets += written_packets;
		checkPCRSend();
		return written_packets;
	}
	
	private void writePCRPacket(long pcr_base, long pcr_ext) {
		try {
			output_pipe.write(TransportPacketFactory.createEmptyPCRPacket(DUMMY_PCR_PID, pcr_base, pcr_ext).toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		total_written_packets++;
	}
	
	private void checkPCRSend() {
		if (!bPCR_generate)
			return;

		if ( (total_written_packets - last_pcr_sent) < 1024)
			return;
		
		long new_program_clock_reference_base =
			program_clock_reference_base +
			(188*(total_written_packets-last_pcr_sent)*27000)/bitrate_per_sec;
		writePCRPacket(new_program_clock_reference_base, 0);
		program_clock_reference_base = new_program_clock_reference_base;
	}
	
	private int updateContinuityCounter(TransportPacket packet) {
		if (packet.getPID() == 0x1FFF) // null packet
			return 0;
		boolean needIncrement = true;
		if (packet.getAdaptationFieldControl() == 0x0 ||
			packet.getAdaptationFieldControl() == 0x2 )
			needIncrement = false;

		Integer key = new Integer(packet.getPID());
		if ( continuity_counter_server.containsKey(key) ) {
			Integer val = continuity_counter_server.get(key).intValue();
			if (needIncrement) val = (val+1) % 16;
			continuity_counter_server.put(key, val);
			packet.setContinuityCounter(val.intValue());
		}
		else {
			continuity_counter_server.put(key, new Integer(0));
			packet.setContinuityCounter(0);
		}
		return 0;
	}
	
	public synchronized void start() {
		if (runningThread != null)
			return;

		threadSuspended = false;
		runningThread = new Thread(this);
		runningThread.start();
	}

	public synchronized void stop() {
		runningThread = null;
    }

	public synchronized void pause() {
		if (threadSuspended)
			return;
		threadSuspended = true;
	}

	public synchronized void resume() {
		if (threadSuspended == true)
			return;
		
		threadSuspended = true;
		notify();
	}
	
	public void join() {
		try {
			runningThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void debugDump() {
		Iterator<Map.Entry<Long,Integer>> it = schedule_tree.entrySet().iterator();
		System.out.println("CurrentTime: "+current_time_ms);
		while(it.hasNext()) {
			Map.Entry<Long,Integer> entry = it.next();
			SITable table = SITableRepository.getTable(entry.getValue().intValue());
			if (table == null)
				System.out.println("timeout: "+entry.getKey()+", but deleted in repository.");
			else
				System.out.println("timeout: "+entry.getKey()+", "+table.getTableID()+" "+table.getUniqueID());
		}
		System.out.println();
	}
}

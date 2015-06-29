package mts.api.transport;

import mts.api.SITable;
import mts.api.Section;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TransportPacketGenerator {
	private static TransportPacket[] allocPackets(int data_bytes, int pid) {
		TransportPacket firstPkt = TransportPacketFactory.createMPEGStartPacket(pid);
		int remain_bytes = data_bytes - firstPkt.getAvailableDataSizeInBytes();
		int required_packet_num = 1;
		
		if (remain_bytes > 0) {
			TransportPacket followingPkt = TransportPacketFactory.createMPEGPacket(pid);
			while (remain_bytes > 0) {
				remain_bytes -= followingPkt.getAvailableDataSizeInBytes();
				required_packet_num++;
			}
		}

		TransportPacket []pkts = new TransportPacket[required_packet_num];
		pkts[0] = firstPkt;
		for(int n=1; n < pkts.length; n++)
			pkts[n] = TransportPacketFactory.createMPEGPacket(pid);

		return pkts;
	}
	
	public static TransportPacket[] generatePackets(Section section) {
		byte[] section_bytes = section.getSectionBytes();
		int section_bytes_len = section_bytes.length;
		int written_bytes = 0;
		TransportPacket[] packets = allocPackets(section_bytes_len, section.getSITable().getTablePID());

		for(int n=0; n < packets.length && written_bytes < section_bytes_len; n++) {
			byte[] packet_data_byte = new byte[packets[n].getAvailableDataSizeInBytes()];
			for(int bf=0; bf < packet_data_byte.length; bf++)
				packet_data_byte[bf] = (byte)0xFF;
			for(int w=0; w < packet_data_byte.length && written_bytes < section_bytes_len; w++)
				packet_data_byte[w] = section_bytes[written_bytes++];
			packets[n].setDataByte(packet_data_byte);
		}

		return packets;
	}

	public static TransportPacket[] generatePackets(Section[] sections) {
		return null;
	}
	
	public static TransportPacket[] generatePackets(SITable table) {
		return generatePackets(table.toSection());
	}
}

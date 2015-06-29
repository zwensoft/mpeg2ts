package mts.api.transport;

import mts.api.BitOutputStream;
import mts.core.MPEGTransportPacketImpl;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TransportPacketFactory {
	public static TransportPacket createMPEGPacket(int pid) {
		TransportPacket packet = new MPEGTransportPacketImpl();
		packet.setSyncByte(0x47); // 0100 0111
		packet.setTransportErrorIndicator(0);
		packet.setPayloadUnitStartIndicator(0);
		packet.setTransportPriority(0);
		packet.setPID(pid);
		packet.setTransportScramblingControl(0);
		packet.setAdaptationFieldControl(1); // no adaptation
		packet.setContinuityCounter(0); // I don't know what it does..
		return packet;
	}

	public static TransportPacket createMPEGStartPacket(int pid) {
		TransportPacket packet = createMPEGPacket(pid);
		packet.setPayloadUnitStartIndicator(1);
		return packet;
	}
	
	public static TransportPacket createMPEGNullPacket() {
		TransportPacket packet = new MPEGTransportPacketImpl();
		packet.setSyncByte(0x47); // 0100 0111
		packet.setPayloadUnitStartIndicator(0);
		packet.setPID(0x1FFF);
		packet.setTransportScramblingControl(0);
		packet.setAdaptationFieldControl(1);
		return packet;
	}
	
	public static TransportPacket createEmptyPCRPacket(int pid, long program_clock_reference_base,
			long program_clock_reference_extension) {
		TransportPacket packet = new MPEGTransportPacketImpl();
		packet.setSyncByte(0x47); // 0100 0111
		packet.setTransportErrorIndicator(0);
		packet.setPayloadUnitStartIndicator(0);
		packet.setTransportPriority(0);
		packet.setPID(pid);
		packet.setTransportScramblingControl(0);
		packet.setAdaptationFieldControl(2); // adaptation_field only!
		
		BitOutputStream os = new BitOutputStream(7*Byte.SIZE);
		os.writeFromLSB(7, 8);
		os.writeFromLSB((byte)((program_clock_reference_base>>25)&0x00FF), 8);
		os.writeFromLSB((byte)((program_clock_reference_base>>17)&0x00FF), 8);
		os.writeFromLSB((byte)((program_clock_reference_base>>9)&0x00FF), 8);
		os.writeFromLSB((byte)((program_clock_reference_base>>1)&0x00FF), 8);
		os.writeFromLSB((byte)((program_clock_reference_base)&0x0001), 1);
		os.writeFromLSB(0, 6);
		os.writeFromLSB((byte)((program_clock_reference_extension>>1)&0x00FF), 8);
		os.writeFromLSB((byte)((program_clock_reference_extension)&0x0001), 1);
		packet.setAdaptationFieldByte(os.toByteArray());

		return packet;
	}
}

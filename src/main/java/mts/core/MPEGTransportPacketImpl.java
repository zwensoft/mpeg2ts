/**
 * 
 */
package mts.core;

import mts.api.BitOutputStream;
import mts.api.transport.TransportPacket;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class MPEGTransportPacketImpl implements TransportPacket {

	protected int sync_byte = 0;
	protected int transport_error_indicator = 0;
	protected int payload_unit_start_indicator = 0;
	protected int transport_priority = 0;
	protected int PID = 0;
	protected int transport_scrambling_control = 0;
	protected int adaptation_field_control = 0;
	protected int continuity_counter = 0;
	protected int pointer_field = 0;
	protected byte[] adaptation_field_byte = null;
	protected byte[] data_byte = null;

	/* (non-Javadoc)
	 * @see API.TransportPacket#getAdaptationFieldControl()
	 */
	@Override
	public int getAdaptationFieldControl() {
		return adaptation_field_control;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getAvailableDataSizeInBytes()
	 */
	@Override
	public int getAvailableDataSizeInBytes() {
		if (adaptation_field_control == 0x1) {
			if (payload_unit_start_indicator == 1)
				return getSizeInBytes()- 4/*header*/- 1/*pointer_field*/;
			else
				return getSizeInBytes()- 4/*header*/; 
		}else {
			// TODO: adaptaion_field() has not been implemented yet.
			throw new UnsupportedOperationException();
		}
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getContinuityCounter()
	 */
	@Override
	public int getContinuityCounter() {
		return continuity_counter;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getDataByte()
	 */
	@Override
	public byte[] getDataByte() {
		return data_byte;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getPID()
	 */
	@Override
	public int getPID() {
		return PID;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getPayloadUnitStartIndicator()
	 */
	@Override
	public int getPayloadUnitStartIndicator() {
		return payload_unit_start_indicator;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getSyncByte()
	 */
	@Override
	public int getSyncByte() {
		return sync_byte;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getTransportErrorIndicator()
	 */
	@Override
	public int getTransportErrorIndicator() {
		return transport_error_indicator;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getTransportPriority()
	 */
	@Override
	public int getTransportPriority() {
		return transport_priority;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#getTransportScramblingControl()
	 */
	@Override
	public int getTransportScramblingControl() {
		return transport_scrambling_control;
	}
	
	/* (non-Javadoc)
	 * @see API.Transport.TransportPacket#getAdaptationFieldByte()
	 */
	@Override
	public byte[] getAdaptationFieldByte() {
		return adaptation_field_byte;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setAdaptationFieldControl(int)
	 */
	@Override
	public void setAdaptationFieldControl(int adaptation_field_control) {
		this.adaptation_field_control = adaptation_field_control;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setContinuityCounter(int)
	 */
	@Override
	public void setContinuityCounter(int continuity_counter) {
		this.continuity_counter = continuity_counter;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setDataByte(byte[])
	 */
	@Override
	public void setDataByte(byte[] data_byte) {
		this.data_byte = data_byte;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setPID(int)
	 */
	@Override
	public void setPID(int pid) {
		PID = pid;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setPayloadUnitStartIndicator(int)
	 */
	@Override
	public void setPayloadUnitStartIndicator(int payload_unit_start_indicator) {
		this.payload_unit_start_indicator = payload_unit_start_indicator;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setSyncByte(int)
	 */
	@Override
	public void setSyncByte(int sync_byte) {
		this.sync_byte = sync_byte;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setTransportErrorIndicator(int)
	 */
	@Override
	public void setTransportErrorIndicator(int transport_error_indicator) {
		this.transport_error_indicator = transport_error_indicator;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setTransportPriority(int)
	 */
	@Override
	public void setTransportPriority(int transport_priority) {
		this.transport_priority = transport_priority;
	}

	/* (non-Javadoc)
	 * @see API.TransportPacket#setTransportScramblingControl(int)
	 */
	@Override
	public void setTransportScramblingControl(int transport_scrambling_control) {
		this.transport_scrambling_control = transport_scrambling_control;
	}
	
	/* (non-Javadoc)
	 * @see API.Transport.TransportPacket#setAdaptationFieldByte(byte[])
	 */
	@Override
	public void setAdaptationFieldByte(byte[] adaptation_field_byte) {
		this.adaptation_field_byte = adaptation_field_byte;		
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 188; // MPEG Transport Packet has a fixed size(188bytes)
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(sync_byte, 8);
		os.writeFromLSB(transport_error_indicator, 1);
		os.writeFromLSB(payload_unit_start_indicator, 1);
		os.writeFromLSB(transport_priority, 1);
		os.writeFromLSB(PID, 13);
		os.writeFromLSB(transport_scrambling_control, 2);
		os.writeFromLSB(adaptation_field_control, 2);
		os.writeFromLSB(continuity_counter, 4);
		
		if ((adaptation_field_control & 0x2) != 0) {
			if (adaptation_field_byte == null)
				os.writeFromLSB(0, 8); // adaptation_field_length:8bit
			else
				os.write(adaptation_field_byte);
		}
		if ((adaptation_field_control & 0x1) != 0) {
			if (payload_unit_start_indicator == 1)
				os.writeFromLSB(pointer_field, 8);
			if (data_byte != null)
				os.write(data_byte);
		}

		return os.toByteArray();
	}

}

/**
 * 
 */
package mts.api.transport;

import mts.core.ByteRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface TransportPacket extends ByteRepresentation {
	int getSyncByte();

	int getTransportErrorIndicator();

	int getPayloadUnitStartIndicator();

	int getTransportPriority();

	int getPID();
	
	int getTransportScramblingControl();
	
	int getAdaptationFieldControl();
	
	int getContinuityCounter();
	
	byte[] getAdaptationFieldByte();
	
	byte[] getDataByte();
	
	int getAvailableDataSizeInBytes();
	
	void setSyncByte(int sync_byte);
	
	void setTransportErrorIndicator(int transport_error_indicator);
	
	void setPayloadUnitStartIndicator(int payload_unit_start_indicator);
	
	void setTransportPriority(int transport_priority);
	
	void setPID(int pid);
	
	void setTransportScramblingControl(int transport_scrambling_control);
	
	void setAdaptationFieldControl(int adaptation_field_control);
	
	void setContinuityCounter(int continuity_counter);
	
	void setAdaptationFieldByte(byte[] adaptation_field_byte);
	
	void setDataByte(byte[] data_byte);
}

/**
 * 
 */
package mts.api.descriptor;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public enum DescriptorTag {
	STUFFING(0x80),
	AC3_AUDIO(0x81),
	CAPTION_SERVICE(0x86),
	CONTENT_ADVISORY(0x87),
	EXTENDED_CHANNEL_NAME(0xA0),
	SERVICE_LOCATION(0xA1),
	TIMESHIFTED_SERVICE(0xA2),
	COMPONENT_NAME(0xA3),
	DCC_DEPARTING_REQUEST(0xA8),
	DCC_ARRIVING_REQUEST(0xA9),
	REDISTRIBUTION_CONTROL(0xAA),
	ATSC_PRIVATE_INFORMATION(0xAD),
	CONTENT_IDENTIFIER(0xB6),
	GENRE(0xAB);
	
	
	private int value;

	private DescriptorTag(int x) {
		value = x;
	}
	
	public int getValue() {
		return value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Enum#toString()
	 */
	public String toString() {
		switch(value) {
		case 0x80:
			return "STUFFING";
		case 0x81:
			return "AC3_AUDIO";
		case 0x86:
			return "CAPTION_SERVICE";
		case 0x87:
			return "CONTENT_ADVISORY";
		case 0xA0:
			return "EXTENDED_CHANNEL_NAME";
		case 0xA1:
			return "SERVICE_LOCATION";
		case 0xA2:
			return "TIMESHIFTED_SERVICE";
		case 0xA3:
			return "COMPONENT_NAME";
		case 0xA8:
			return "DCC_DEPARTING_REQUEST";
		case 0xA9:
			return "DCC_ARRIVING_REQUEST";
		case 0xAA:
			return "REDISTRIBUTION_CONTROL";
		case 0xAD:
			return "ATSC_PRIVATE_INFORMATION";
		case 0xB6:
			return "CONTENT_IDENTIFIER";
		default:
			return "UNKNOWN";
		}
	}
}

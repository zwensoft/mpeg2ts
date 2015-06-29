package mts.api;


/**
 * @author Administrator
 * @version 1.0
 * @created 30-Dec-2008 3:26:52 PM
 */
public enum ServiceType {
	/**
	 * reserved value.
	 */
	RESERVED(0x00),
	/**
	 * analog television.
	 */
	ANALOG_TELEVISION(0x01),
	/**
	 * digital television.
	 */
	ATSC_DIGITAL_TELEVISION(0x02),
	/**
	 * atsc audio service.
	 */
	ATSC_AUDIO(0x03),
	/**
	 * atsc data only service
	 */
	ATSC_DATA_ONLY_SERVICE(0x04);
	
	private int value;
	private ServiceType(int x) {
		value = x;
	}
	
	/**
	 * @return
	 */
	public int getValue() {
		return value;
	}
}
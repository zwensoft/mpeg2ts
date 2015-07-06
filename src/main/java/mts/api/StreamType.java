package mts.api;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public enum StreamType {
	/**
	 * 
	 */
	ISO_IEC_Reserved(0x00),
	/**
	 * 
	 */
	ISO_IEC_11172_Video(0x01),
	/**
	 * 
	 */
	ISO_IEC_13818_2_Video(0x02),
	/**
	 * 
	 */
	ISO_IEC_11172_Audio(0x03),
	/**
	 * 
	 */
	ISO_IEC_13818_3_Audio(0x04),
	/**
	 * 
	 */
	ISO_IEC_13818_1_private_sections(0x05),
	/**
	 * 
	 */
	ISO_IEC_13818_1_PES_packets_containing_private_data(0x06),
	/**
	 * 
	 */
	ISO_IEC_13522_MHEG(0x07),
	/**
	 * 
	 */
	ISO_IEC_13818_1_Annex_A_DSM_CC(0x08),
	/**
	 * H264
	 */
	AVC(0x1b),
	/**
	 * MPEG-2-AAC
	 */
	ADTS(0x0f);

	private int value;

	private StreamType(int x) {
		value = x;
	}
	
	/**
	 * @return
	 */
	public int getValue() {
		return value;
	}

}
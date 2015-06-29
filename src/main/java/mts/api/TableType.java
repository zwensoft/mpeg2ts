package mts.api;


/**
 * @author Administrator
 * @version 1.0
 * @created 30-Dec-2008 3:26:52 PM
 */
public enum TableType {
	/**
	 * 
	 */
	Terrestrial_VCT_with_current_next_indicator_1(0x0000),
	/**
	 * 
	 */
	Terrestrial_VCT_with_current_next_indicator_0(0x0001),
	/**
	 * 
	 */
	Cable_VCT_with_current_next_indicator_1(0x0002),
	/**
	 * 
	 */
	Cable_VCT_with_current_next_indicator_0(0x0003),
	/**
	 * 
	 */
	Channel_ETT(0x0004),
	/**
	 * 
	 */
	DCCSCT(0x0005),
	/**
	 * 
	 */
	EIT0(0x0100),
	/**
	 * 
	 */
	EIT1(0x0101),
	/**
	 * 
	 */
	EIT2(0x0102),
	EIT3(0x0103),
	EIT4(0x0104),
	EIT5(0x0105),
	EIT6(0x0106),
	EIT7(0x0107),
	EIT8(0x0108),
	ETT0(0x0200),
	ETT1(0x0201),
	ETT2(0x0202),
	ETT3(0x0203), ETT4(0x0204),
	ETT5(0x0205), ETT6(0x0206),	ETT7(0x0207), ETT8(0x0208);

	private int value; 
	private TableType(int val) {
		value = val;
	}
	
	public int getValue() {
		return value;
	}
}
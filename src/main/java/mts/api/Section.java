package mts.api;
/**
 * 
 */

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface Section {
	/**
	 * return correspondent SITable for this Section.
	 * @return SITable
	 */
	SITable getSITable();
	/**
	 * return table_id of this section.
	 * @return TableID
	 */
	TableID getTableID();

	/**
	 * return a value of 'section_syntax_indicator'
	 * @return
	 */
	int getSectionSyntaxIndicator();
	/**
	 * return a value of 'private_indicator'
	 * @return
	 */
	int getPrivateIndicator();
	/**
	 * return a value of first 'reserved' (2bits)
	 * @return
	 */
	int getReserved1();
	/**
	 * return a value of 'private_section_length'
	 * @return
	 */
	int getPrivateSectionLength();
	
	/**
	 * return a value of 'table_id_extension'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getTableIdExtension();
	/**
	 * return a value of 'reserved'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getReserved2();
	/**
	 * return a value of 'version_number'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getVersionNumber();
	/**
	 * return a value of 'current_next_indicator'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getCurrentNextIndicator();
	/**
	 * return a value of 'section_number'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getSectionNumber();
	/**
	 * return a value of 'last_section_number'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	int getLastSectionNumber();
	/**
	 * return a value of 'private_data_byte'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	byte[] getPrivateDataBytes();
	/**
	 * return a value of 'CRC_32'
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @return
	 */
	long getCRC32();

	/**
	 * return total byte size of this section. 
	 * @return
	 */
	int getSectionSizeInByte();
	/**
	 * return byte array of this section.
	 * @return
	 */
	byte[] getSectionBytes();

	/**
	 * set a value of 'private_indicator'.
	 * @param value
	 */
	void setPrivateIndicator(int value);
	/**
	 * set a value of 'reserved'.
	 * @param value
	 */
	void setReserved1(int value);
	/**
	 * set a value of 'table_id_extension'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param table_id_ext
	 */
	void setTableIdExtension(int table_id_ext);
	/**
	 * set a value of 'reserved'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param value
	 */
	void setReserved2(int value);
	/**
	 * set a value of 'version_number'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param version_num
	 */
	void setVersionNumber(int version_num);
	/**
	 * set a value of 'current_next_indicator'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param cur_nxt_ind
	 */
	void setCurrentNextIndicator(int cur_nxt_ind);
	/**
	 * set a value of 'section_number'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param sec_num
	 */
	void setSectionNumber(int sec_num);
	/**
	 * set a value of 'last_section_number'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param last_sec_num
	 */
	void setLastSectionNumber(int last_sec_num);
	/**
	 * set a value of 'private_data_byte'.
	 * Exception will be occurred if this is called when 'section_syntax_indicator' is '0'.
	 * @param data
	 */
	void setPrivateData(byte[] data);
}

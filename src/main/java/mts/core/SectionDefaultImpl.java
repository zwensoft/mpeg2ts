/**
 * 
 */
package mts.core;


import crc32.Crc32Mpeg2;
import mts.api.BitOutputStream;
import mts.api.SITable;
import mts.api.Section;
import mts.api.TableID;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class SectionDefaultImpl implements Section {
	protected SITable correspondent_table = null;
	protected TableID table_id = TableID.FORBIDDEN; // 8

	int section_syntax_indicator = 0; // 1
	int private_indicator = 0; // 1
	int reserved1 = 0; // 2
	// private_section_length : 12  dynamically calculated

	// belows are used when section_syntax_indicator value is '1'
	int table_id_extension = 0; // 16
	int reserved2 = 0; // 2
	int version_number = 0; // 5 
	int current_next_indicator = 0; // 1
	int section_number = 0; // 8
	int last_section_number = 0; // 8
	byte[] private_data_bytes = null;
	// CRC_32 : 32   dynamically calculated
	
	public SectionDefaultImpl(SITable table, int ssi) {
		if (table == null)
			throw new NullPointerException();
		
		correspondent_table = table;
		table_id = correspondent_table.getTableID();
		section_syntax_indicator = ssi;
	}

	/* (non-Javadoc)
	 * @see API.Section#getCRC32()
	 */
	@Override
	public long getCRC32() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();

		BitOutputStream os = new BitOutputStream(getSectionSizeInByte()*Byte.SIZE);
		os.writeFromLSB(table_id.getValue(), 8);
		os.writeFromLSB(section_syntax_indicator, 1);
		os.writeFromLSB(private_indicator, 1);
		os.writeFromLSB(reserved1, 2);
		os.writeFromLSB(getPrivateSectionLength(), 12);
		os.writeFromLSB(table_id_extension, 16);
		os.writeFromLSB(reserved2, 2);
		os.writeFromLSB(version_number, 5);
		os.writeFromLSB(current_next_indicator, 1);
		os.writeFromLSB(section_number, 8);
		os.writeFromLSB(last_section_number, 8);
		os.write(getPrivateDataBytes());

		Crc32Mpeg2 crc = new Crc32Mpeg2();
		byte[] byte_array = os.toByteArray();
		crc.update(byte_array, 0, byte_array.length-4);
		return crc.getValue();
	}

	/* (non-Javadoc)
	 * @see API.Section#getCurrentNextIndicator()
	 */
	@Override
	public int getCurrentNextIndicator() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		return current_next_indicator;
	}

	/* (non-Javadoc)
	 * @see API.Section#getLastSectionNumber()
	 */
	@Override
	public int getLastSectionNumber() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		return last_section_number;
	}

	/* (non-Javadoc)
	 * @see API.Section#getPrivateDataBytes()
	 */
	@Override
	public byte[] getPrivateDataBytes() {
		return private_data_bytes;
	}

	/* (non-Javadoc)
	 * @see API.Section#getPrivateIndicator()
	 */
	@Override
	public int getPrivateIndicator() {
		return private_indicator;
	}

	/* (non-Javadoc)
	 * @see API.Section#getPrivateSectionLength()
	 */
	@Override
	public int getPrivateSectionLength() {
		int length = 0;

		if (section_syntax_indicator != 0)
			length += 9;

		if (private_data_bytes != null)
			length += private_data_bytes.length;

		return length;
	}

	/* (non-Javadoc)
	 * @see API.Section#getReserved1()
	 */
	@Override
	public int getReserved1() {
		return reserved1;
	}

	/* (non-Javadoc)
	 * @see API.Section#getReserved2()
	 */
	@Override
	public int getReserved2() {
		return reserved2;
	}

	/* (non-Javadoc)
	 * @see API.Section#getSITable()
	 */
	@Override
	public SITable getSITable() {
		return correspondent_table;
	}

	/* (non-Javadoc)
	 * @see API.Section#getSectionBytes()
	 */
	@Override
	public byte[] getSectionBytes() {
		BitOutputStream os = new BitOutputStream(getSectionSizeInByte()*Byte.SIZE);
		os.writeFromLSB(table_id.getValue(), 8);
		os.writeFromLSB(section_syntax_indicator, 1);
		os.writeFromLSB(private_indicator, 1);
		os.writeFromLSB(reserved1, 2);
		os.writeFromLSB(getPrivateSectionLength(), 12);
		if (getSectionSyntaxIndicator() != 0) {
			os.writeFromLSB(table_id_extension, 16);
			os.writeFromLSB(reserved2, 2);
			os.writeFromLSB(version_number, 5);
			os.writeFromLSB(current_next_indicator, 1);
			os.writeFromLSB(section_number, 8);
			os.writeFromLSB(last_section_number, 8);
		}

		os.write(getPrivateDataBytes());

		if (getSectionSyntaxIndicator() != 0) {
			/*
			 * SHIT JAVA CRC32! Should use Crc32Mpeg2!!!!
			CRC32 crc = new CRC32();
			byte[] byte_array = os.toByteArray();
			crc.update(byte_array, 0, byte_array.length-4);
			os.writeFromLSB((int)(crc.getValue()&0x00000000FFFFFFFF), 32);
			System.out.println(MyUTIL.toBinaryString(crc.getValue()));
			System.out.println(MyUTIL.toBinaryString(byte_array[byte_array.length-4])+
					MyUTIL.toBinaryString(byte_array[byte_array.length-3])+
					MyUTIL.toBinaryString(byte_array[byte_array.length-2])+
					MyUTIL.toBinaryString(byte_array[byte_array.length-1])
					);
			*/
			Crc32Mpeg2 crc = new Crc32Mpeg2();
			byte[] byte_array = os.toByteArray();
			crc.update(byte_array, 0, byte_array.length-4);
			os.writeFromLSB((int)(crc.getValue()&0x00000000FFFFFFFF), 32);
		}

		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.Section#getSectionNumber()
	 */
	@Override
	public int getSectionNumber() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		return section_number;
	}

	/* (non-Javadoc)
	 * @see API.Section#getSectionSizeInByte()
	 */
	@Override
	public int getSectionSizeInByte() {
		return getPrivateSectionLength() + 3;
	}

	/* (non-Javadoc)
	 * @see API.Section#getSectionSyntaxIndicator()
	 */
	@Override
	public int getSectionSyntaxIndicator() {
		return section_syntax_indicator;
	}

	/* (non-Javadoc)
	 * @see API.Section#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return table_id;
	}

	/* (non-Javadoc)
	 * @see API.Section#getTableIdExtension()
	 */
	@Override
	public int getTableIdExtension() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		return table_id_extension;
	}

	/* (non-Javadoc)
	 * @see API.Section#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.Section#setCurrentNextIndicator(int)
	 */
	@Override
	public void setCurrentNextIndicator(int cur_nxt_ind) {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		current_next_indicator = cur_nxt_ind;		
	}

	/* (non-Javadoc)
	 * @see API.Section#setLastSectionNumber(int)
	 */
	@Override
	public void setLastSectionNumber(int last_sec_num) {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		last_section_number = last_sec_num;		
	}


	/* (non-Javadoc)
	 * @see API.Section#setPrivateData(byte[])
	 */
	@Override
	public void setPrivateData(byte[] data) {
		private_data_bytes = data;
	}

	/* (non-Javadoc)
	 * @see API.Section#setPrivateIndicator(int)
	 */
	@Override
	public void setPrivateIndicator(int value) {
		private_indicator = value;
	}

	/* (non-Javadoc)
	 * @see API.Section#setReserved1(int)
	 */
	@Override
	public void setReserved1(int value) {
		reserved1 = value;
	}

	/* (non-Javadoc)
	 * @see API.Section#setReserved2(int)
	 */
	@Override
	public void setReserved2(int value) {
		reserved2 = value;
	}

	/* (non-Javadoc)
	 * @see API.Section#setSectionNumber(int)
	 */
	@Override
	public void setSectionNumber(int sec_num) {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		section_number = sec_num;
	}

	/* (non-Javadoc)
	 * @see API.Section#setTableIdExtension(int)
	 */
	@Override
	public void setTableIdExtension(int table_id_ext) {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		table_id_extension = table_id_ext;		
	}

	/* (non-Javadoc)
	 * @see API.Section#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version_num) {
		if (getSectionSyntaxIndicator() == 0)
			throw new UnsupportedOperationException();
		version_number = version_num;		
	}
}

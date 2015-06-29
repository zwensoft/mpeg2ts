package mts.api;

import mts.core.SectionDefaultImpl;

/**
 * @author SungHun Park (sainthh@tvstorm.com)
 *
 */
public class SectionFactory {

	/**
	 * @param table
	 * @param transport_stream_id
	 * @param section_num
	 * @param last_section_num
	 * @return
	 */
	public static Section createPATSection(SITable table, int transport_stream_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(0);
		section.setTableIdExtension(transport_stream_id);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}

	/**
	 * @param table
	 * @param program_number
	 * @return
	 */
	public static Section createPMTSection(SITable table, int program_number) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(0);
		section.setTableIdExtension(program_number);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		// PMT is a single section
		section.setSectionNumber(0);
		section.setLastSectionNumber(0);
		return section;
	}
	
	/**
	 * @param table
	 * @return
	 */
	public static Section createMGTSection(SITable table) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3); //
		section.setTableIdExtension(0x0000);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		// MGT is a single section
		section.setSectionNumber(0);
		section.setLastSectionNumber(0);
		return section;
	}

	/**
	 * @param table
	 * @param transport_stream_id
	 * @param section_num
	 * @param last_section_num
	 * @return
	 */
	public static Section createTVCTSection(SITable table, int transport_stream_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3); //
		section.setTableIdExtension(transport_stream_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @param transport_stream_id
	 * @param section_num
	 * @param last_section_num
	 * @return
	 */
	public static Section createCVCTSection(SITable table, int transport_stream_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3); //
		section.setTableIdExtension(transport_stream_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @return
	 */
	public static Section createSTTSection(SITable table) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3); //	
		section.setTableIdExtension(0x0000);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(0);
		section.setLastSectionNumber(0);
		return section;
	}
	
	/**
	 * @param table
	 * @param rating_region
	 * @param section_num
	 * @param last_section_num
	 * @return
	 */
	public static Section createRRTSection(SITable table, int rating_region,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension((0xFF<<8)|rating_region);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @param source_id
	 * @param section_num
	 * @param last_section_num
	 * @return
	 */
	public static Section createEITSection(SITable table, int source_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(source_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @param table_id_extension
	 * @return
	 */
	public static Section createETTSection(SITable table, int table_id_extension) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(table_id_extension);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		return section;
	}
	
	/**
	 * @param table
	 * @param network_id
	 * @return
	 */
	public static Section createNITSection(SITable table, int network_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(network_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	
	/**
	 * @param table
	 * @param network_id
	 * @return
	 */
	public static Section createBATSection(SITable table, int bouquet_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(bouquet_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @param ts_id
	 * @return
	 */
	public static Section createSDTSection(SITable table, int ts_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(ts_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
	/**
	 * @param table
	 * @param ts_id
	 * @return
	 */
	public static Section createDVBEITSection(SITable table, int service_id,
			int section_num, int last_section_num) {
		Section section = new SectionDefaultImpl(table, 1);
		section.setPrivateIndicator(1);
		section.setReserved1(0x3);
		section.setTableIdExtension(service_id);
		section.setReserved2(0x3);
		section.setVersionNumber(table.getTableVersion());
		section.setCurrentNextIndicator(1); // default is current!
		section.setSectionNumber(section_num);
		section.setLastSectionNumber(last_section_num);
		return section;
	}
	
}

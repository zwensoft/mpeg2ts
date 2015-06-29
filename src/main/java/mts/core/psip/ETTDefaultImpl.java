/**
 * 
 */
package mts.core.psip;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.Section;
import mts.api.SectionFactory;
import mts.api.TableID;
import mts.api.multistring.MultipleStringStructure;
import mts.api.psip.ETT;
import mts.core.SITableAbstractImpl;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class ETTDefaultImpl extends SITableAbstractImpl implements ETT {
	protected int table_pid = 0;
	protected int version_number = 0;
	protected int ETT_table_id_extension;
	protected long ETM_id;
	protected MultipleStringStructure extended_text_message;
	protected long interval_millis = 3000;
	
	/**
	 * @param pid
	 */
	public ETTDefaultImpl(int pid) {
		table_pid = pid;
	}
	
	/* (non-Javadoc)
	 * @see API.ETT#getETMId()
	 */
	@Override
	public long getETMId() {
		return ETM_id;
	}

	/* (non-Javadoc)
	 * @see API.ETT#getETTTableIdExtension()
	 */
	@Override
	public int getETTTableIdExtension() {
		return ETT_table_id_extension;
	}

	/* (non-Javadoc)
	 * @see API.ETT#getExtendedTextMessage()
	 */
	@Override
	public MultipleStringStructure getExtendedTextMessage() {
		return extended_text_message;
	}

	/* (non-Javadoc)
	 * @see API.ETT#getExtendedTextMessageSize()
	 */
	@Override
	public int getExtendedTextMessageSize() {
		if (extended_text_message == null)
			return 0;
		return extended_text_message.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.ETT#getVersionNumber()
	 */
	@Override
	public int getVersionNumber() {
		return version_number;
	}

	/* (non-Javadoc)
	 * @see API.ETT#setETMId(long)
	 */
	@Override
	public void setETMId(long ETM_id) {
		this.ETM_id = ETM_id;
	}

	/* (non-Javadoc)
	 * @see API.ETT#setETTTableIdExtension(int)
	 */
	@Override
	public void setETTTableIdExtension(int ETT_table_id_extension) {
		this.ETT_table_id_extension = ETT_table_id_extension;
	}

	/* (non-Javadoc)
	 * @see API.ETT#setExtendedTextMessage(API.MultipleStringStructure)
	 */
	@Override
	public void setExtendedTextMessage(MultipleStringStructure text) {
		extended_text_message = text;
	}

	/* (non-Javadoc)
	 * @see API.ETT#setVersionNumber(int)
	 */
	@Override
	public void setVersionNumber(int version_number) {
		this.version_number = version_number;
	}
	
	/* (non-Javadoc)
	 * @see API.SITable#getIntervalMillis()
	 */
	@Override
	public long getIntervalMillis() {
		return interval_millis;
	}
	
	/* (non-Javadoc)
	 * @see API.SITable#setIntervalMillis(long)
	 */
	@Override
	public void setIntervalMillis(long millisec) {
		interval_millis = millisec;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableID()
	 */
	@Override
	public TableID getTableID() {
		return TableID.EVENT_TEXT_TABLE;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTablePID()
	 */
	@Override
	public int getTablePID() {
		return table_pid;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getTableVersion()
	 */
	@Override
	public int getTableVersion() {
		return getVersionNumber();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();

		str += (MyUTIL.whiteSpaceStr(base_space)+ "<ETT>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<table_id>"+TableID.EVENT_INFORMATION_TABLE.getValue()+"</table_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<version_number>"+version_number+"</version_number>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ETT_table_id_extension>"+ETT_table_id_extension+"</ETT_table_id_extension>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ETM_id>"+ETM_id+"</ETM_id>\n");

		if (extended_text_message != null)
			str += extended_text_message.toXMLString(base_space+2);

		str += (MyUTIL.whiteSpaceStr(base_space)+ "</ETT>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#getTotalSectionNumber()
	 */
	@Override
	public int getTotalSectionNumber() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#isMultiSection()
	 */
	@Override
	public boolean isMultiSection() {
		return false;
	}

	/* (non-Javadoc)
	 * @see API.SectionRepresentation#toSection()
	 */
	@Override
	public Section[] toSection() {
		Section[] sections;
		int total_section = getTotalSectionNumber();

		sections = new Section[total_section];

		for(int sn=0; sn < sections.length; sn++) {
			sections[sn] = SectionFactory.createETTSection(this, ETT_table_id_extension);

			int total_bits = (5+getExtendedTextMessageSize())*Byte.SIZE;
			BitOutputStream os = new BitOutputStream(total_bits);
			os.writeFromLSB(0, 8); // protocol_version: currently always '0'
			os.writeFromLSB((int)ETM_id, 32);
			
			if (extended_text_message != null)
				os.write(extended_text_message.toByteArray());

			sections[sn].setPrivateData(os.toByteArray());
		}

		return sections;
	}

}

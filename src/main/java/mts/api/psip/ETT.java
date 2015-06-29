/**
 * 
 */
package mts.api.psip;

import mts.api.SITable;
import mts.api.multistring.MultipleStringStructure;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface ETT extends SITable {
	/**
	 * return a value of 'version_number'.
	 * @return
	 */
	int getVersionNumber();
	
	/**
	 * return a value of 'ETT_table_id_extension'.
	 * @return
	 */
	int getETTTableIdExtension();
	
	/**
	 * return a value of 'ETM_id'.
	 * @return
	 */
	long getETMId();
	
	/**
	 * return a byte length of 'extended_text_message()'.
	 * @return
	 */
	int getExtendedTextMessageSize();

	/**
	 * return a value of 'extended_text_message()'.
	 * @return
	 */
	MultipleStringStructure getExtendedTextMessage();
	
	/**
	 * set a value of 'version_number'.
	 * @param version_number
	 */
	void setVersionNumber(int version_number);
	
	/**
	 * set a value of 'ETT_table_id_extension'.
	 * @param ETT_table_id_extension
	 */
	void setETTTableIdExtension(int ETT_table_id_extension);
	
	/**
	 * set a value of 'ETM_id'.
	 * @param ETM_id
	 */
	void setETMId(long ETM_id);
	
	/**
	 * set a value of 'extended_text_message()'.
	 * @param text
	 */
	void setExtendedTextMessage(MultipleStringStructure text);
}

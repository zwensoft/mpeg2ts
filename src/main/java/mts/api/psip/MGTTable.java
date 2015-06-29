/**
 * 
 */
package mts.api.psip;

import mts.api.TableType;
import mts.api.descriptor.DescriptorMethod;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface MGTTable extends ByteRepresentation, XMLRepresentation, DescriptorMethod {
	/**
	 * return a value of 'table_type'.
	 * @return
	 */
	TableType getTableType();

	/**
	 * return a value of 'table_type_PID'.
	 * @return
	 */
	int getTableTypePID();

	/**
	 * return a value of 'table_type_version_number'.
	 * @return
	 */
	int getTableTypeVersionNumber();

	/**
	 * return a value of 'number_bytes'.
	 * @return
	 */
	long getNumberBytes();

	/**
	 * set a value of 'table_type'.
	 * @param type
	 */
	void setTableType(TableType type);

	/**
	 * set a value of 'table_type_PID'.
	 * @param pid
	 */
	void setTableTypePID(int pid);

	/**
	 * set a value of 'table_type_version_number'.
	 * @param version
	 */
	void setTableTypeVersionNumber(int version);

	/**
	 * set a value of 'number_bytes'.
	 * @param bytes
	 */
	void setNumberBytes(int bytes);
}

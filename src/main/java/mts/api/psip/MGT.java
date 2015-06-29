/**
 * 
 */
package mts.api.psip;

import java.util.Iterator;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface MGT extends SITable, DescriptorMethod {
	/**
	 * return a value of 'version_number'.
	 * @return version_number
	 */
	int getVersionNumber();
	
	/**
	 * return a value of 'tables_defined'.
	 * @return
	 */
	int getTablesDefined();

	/**
	 * return a MGTTable instance.
	 * @param index
	 */
	MGTTable getTableAt(int index);

	/**
	 * return all MGTTable instances.
	 * @return
	 */
	Iterator<MGTTable> getTables();

	/**
	 * set a value of 'version_number'. 
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * set a MGTTable instance.
	 * @param index
	 * @param table
	 */
	boolean setTableAt(int index, MGTTable table);

	/**
	 * add a MGTTable instance.
	 * @param table
	 */
	boolean addTable(MGTTable table);

	/**
	 * add a MGTTable instance.
	 * @param index
	 * @param table
	 */
	boolean addTableAt(int index, MGTTable table);

	/**
	 * remove a MGTTable instance.
	 * @param table
	 */
	boolean removeTable(MGTTable table);

	/**
	 * remove a MGTTable instance.
	 * @param index
	 */
	boolean removeTableAt(int index);

	/**
	 * remove all MGTTable instances.
	 */
	void removeAllTables();
}

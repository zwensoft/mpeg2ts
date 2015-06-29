/**
 * 
 */
package mts.api.psip;

import java.util.Iterator;

import mts.api.multistring.MultipleStringStructure;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface RRTDimension extends ByteRepresentation, XMLRepresentation {
	/**
	 * return a value of 'dimension_name_length'.
	 * @return
	 */
	int getDimensionNameLength();
	
	/**
	 * return a value of 'dimension_name_text()'.
	 * @return
	 */
	MultipleStringStructure getDimensionNameText();
	
	/**
	 * return a value of 'graduated_scale'.
	 * @return
	 */
	int getGraduatedScale();
	
	/**
	 * return a value of 'values_defined'.
	 * @return
	 */
	int getValuesDefined();
	
	/**
	 * return total bytes of RRTDimensionValues.
	 * @return
	 */
	int getValuesLength();
	
	/**
	 * return a RRTDimensionValue instance.
	 * @param index
	 */
	RRTDimensionValue getValueAt(int index);

	/**
	 * return all RRTDimensionValue instances.
	 * @return
	 */
	Iterator<RRTDimensionValue> getValues();

	/**
	 * set a value of 'dimension_name_text()'.
	 * @param text
	 */
	void setDimensionNameText(MultipleStringStructure text);
	
	/**
	 * set a value of 'graduated_scale'.
	 * @param scale
	 */
	void setGraduatedScale(int scale);
	
	/**
	 * set a RRTDimensionValue instance.
	 * @param index
	 * @param stream
	 */
	boolean setValueAt(int index, RRTDimensionValue value);

	/**
	 * add a RRTDimensionValue instance.
	 * @param stream
	 */
	boolean addValue(RRTDimensionValue value);

	/**
	 * add a RRTDimensionValue instance.
	 * @param index
	 * @param stream
	 */
	boolean addValueAt(int index, RRTDimensionValue value);

	/**
	 * remove a RRTDimensionValue instance.
	 * @param stream
	 */
	boolean removeValue(RRTDimensionValue value);

	/**
	 * remove a RRTDimensionValue instance.
	 * @param index
	 * @param stream
	 */
	boolean removeValueAt(int index, RRTDimensionValue value);

	/**
	 * remove all RRTDimensionValue instances.
	 */
	void removeAllValues();
}

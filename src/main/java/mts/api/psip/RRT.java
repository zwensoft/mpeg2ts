/**
 * 
 */
package mts.api.psip;

import java.util.Iterator;

import mts.api.SITable;
import mts.api.descriptor.DescriptorMethod;
import mts.api.multistring.MultipleStringStructure;




/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface RRT extends SITable, DescriptorMethod {
	/**
	 * return a value of 'rating_region'.
	 * @return
	 */
	int getRatingRegion();

	/**
	 * return a value of 'version_number'.
	 * @return
	 */
	int getVersionNumber();

	/**
	 * return a value of 'rating_region_name_length'.
	 * @return
	 */
	int getRatingRegionNameLength();
	
	/**
	 * return a value of 'rating_region_name_text()'.
	 * @return
	 */
	MultipleStringStructure getRatingRegionNameText();

	/**
	 * return a value of 'dimensions_defined'.
	 * @return
	 */
	int getDimensionsDefined();

	/**
	 * return total bytes of dimensions.
	 * @return
	 */
	int getDimensionsLength();

	/**
	 * return a RRTDimension instance.
	 * @param index
	 * @return
	 */
	RRTDimension getDimensionAt(int index);

	/**
	 * return all RRTDimension instances.
	 * @return
	 */
	Iterator<RRTDimension> getDimensions();

	/**
	 * set a value of 'rating_region'.
	 * @param region
	 */
	void setRatingRegion(int region);
	
	/**
	 * set a value of 'version_number'.
	 * @param version
	 */
	void setVersionNumber(int version);
	
	/**
	 * set a value of 'rating_region_name_text()'.
	 * @param text
	 */
	void setRatingRegionNameText(MultipleStringStructure text);
	
	/**
	 * set a RRTDimension instance.
	 * @param index
	 * @param dimension
	 * @return
	 */
	boolean setDimensionAt(int index, RRTDimension dimension);

	/**
	 * add a RRTDimension instance.
	 * @param dimension
	 * @return
	 */
	boolean addDimension(RRTDimension dimension);

	/**
	 * add a RRTDimension instance.
	 * @param index
	 * @param dimension
	 * @return
	 */
	boolean addDimensionAt(int index, RRTDimension dimension);

	/**
	 * remove a RRTDimension instance.
	 * @param dimension
	 * @return
	 */
	boolean removeDimension(RRTDimension dimension);

	/**
	 * remove a RRTDimension instance.
	 * @param index
	 * @param dimension
	 * @return
	 */
	boolean removeDimensionAt(int index, RRTDimension dimension);

	/**
	 * remove all RRTDimension instances.
	 */
	void removeAllDimensions();
}

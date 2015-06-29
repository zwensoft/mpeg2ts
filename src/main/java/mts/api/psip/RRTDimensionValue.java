/**
 * 
 */
package mts.api.psip;

import mts.api.multistring.MultipleStringStructure;
import mts.core.ByteRepresentation;
import mts.core.XMLRepresentation;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface RRTDimensionValue extends ByteRepresentation, XMLRepresentation {
	/**
	 * return a value of 'abbrev_rating_value_length'.
	 * @return
	 */
	int getAbbrevRatingValueLength();
	
	/**
	 * return a value of 'abbrev_rating_value_text()'.
	 * @return
	 */
	MultipleStringStructure getAbbrevRatingValueText();

	/**
	 * return a value of 'rating_value_length'.
	 * @return
	 */
	int getRatingValueLength();
	
	/**
	 * return a value of 'rating_value_text'.
	 * @return
	 */
	MultipleStringStructure getRatingValueText();
	
	/**
	 * set a value of 'abbrev_rating_value_text()'.
	 * @param text
	 */
	void setAbbrevRatingValueText(MultipleStringStructure text);
	
	/**
	 * set a value of 'rating_value_text()'.
	 * @param text
	 */
	void setRatingValueText(MultipleStringStructure text);
}

/**
 * 
 */
package mts.core.psip;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.multistring.MultipleStringStructure;
import mts.api.psip.RRTDimensionValue;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class RRTDimensionValueDefaultImpl implements RRTDimensionValue {
	protected MultipleStringStructure abbrev_rating_value_text;
	protected MultipleStringStructure rating_value_text;

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#getAbbrevRatingValueLength()
	 */
	@Override
	public int getAbbrevRatingValueLength() {
		if (abbrev_rating_value_text == null)
			return 0;
		return abbrev_rating_value_text.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#getAbbrevRatingValueText()
	 */
	@Override
	public MultipleStringStructure getAbbrevRatingValueText() {
		return abbrev_rating_value_text;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#getRatingValueLength()
	 */
	@Override
	public int getRatingValueLength() {
		if (rating_value_text == null)
			return 0;
		return rating_value_text.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#getRatingValueText()
	 */
	@Override
	public MultipleStringStructure getRatingValueText() {
		return rating_value_text;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#setAbbrevRatingValueText(API.MultipleStringStructure)
	 */
	@Override
	public void setAbbrevRatingValueText(MultipleStringStructure text) {
		abbrev_rating_value_text = text;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimensionValue#setRatingValueText(API.MultipleStringStructure)
	 */
	@Override
	public void setRatingValueText(MultipleStringStructure text) {
		rating_value_text = text;
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 2 + abbrev_rating_value_text.getSizeInBytes() + rating_value_text.getSizeInBytes();
	}


	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(getAbbrevRatingValueLength(), 8);
		os.write(abbrev_rating_value_text.toByteArray());
		os.writeFromLSB(getRatingValueLength(), 8);
		os.write(rating_value_text.toByteArray());
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<RRTDimensionValue>\n");

		if(abbrev_rating_value_text != null)
			str += abbrev_rating_value_text.toXMLString(base_space+2);
		if(rating_value_text != null)
			str += rating_value_text.toXMLString(base_space+2);

		str += (MyUTIL.whiteSpaceStr(base_space)+"</RRTDimensionValue>\n");
		return str;
	}
}

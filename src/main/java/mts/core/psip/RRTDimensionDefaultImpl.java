/**
 * 
 */
package mts.core.psip;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.multistring.MultipleStringStructure;
import mts.api.psip.RRTDimension;
import mts.api.psip.RRTDimensionValue;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class RRTDimensionDefaultImpl implements RRTDimension {
	protected MultipleStringStructure dimension_name_text;
	protected int graduated_scale = 0;
	protected List<RRTDimensionValue> values = new Vector<RRTDimensionValue>();

	/* (non-Javadoc)
	 * @see API.RRTDimension#addValue(API.RRTDimensionValue)
	 */
	@Override
	public boolean addValue(RRTDimensionValue value) {
		values.add(value);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#addValueAt(int, API.RRTDimensionValue)
	 */
	@Override
	public boolean addValueAt(int index, RRTDimensionValue value) {
		if (index < 0 || index > values.size())
			return false;
		values.add(index, value);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getDimensionNameLength()
	 */
	@Override
	public int getDimensionNameLength() {
		if (dimension_name_text == null)
			return 0;

		return dimension_name_text.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getDimensionNameText()
	 */
	@Override
	public MultipleStringStructure getDimensionNameText() {
		return dimension_name_text;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getGraduatedScale()
	 */
	@Override
	public int getGraduatedScale() {
		return graduated_scale; 
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getValueAt(int)
	 */
	@Override
	public RRTDimensionValue getValueAt(int index) {
		if (index < 0 || index >= values.size())
			return null;
		return values.get(index);
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getValues()
	 */
	@Override
	public Iterator<RRTDimensionValue> getValues() {
		return values.iterator();
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getValuesDefined()
	 */
	@Override
	public int getValuesDefined() {
		return values.size();
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#getValuesLength()
	 */
	@Override
	public int getValuesLength() {
		int values_length = 0;
		Iterator<RRTDimensionValue> it = values.iterator();
		while(it.hasNext())
			values_length += it.next().getSizeInBytes();
		return values_length;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#removeAllValues()
	 */
	@Override
	public void removeAllValues() {
		values.removeAll(values);
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#removeValue(API.RRTDimensionValue)
	 */
	@Override
	public boolean removeValue(RRTDimensionValue value) {
		return values.remove(value);
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#removeValueAt(int, API.RRTDimensionValue)
	 */
	@Override
	public boolean removeValueAt(int index, RRTDimensionValue value) {
		if (index < 0 || index >= values.size())
			return false;
		values.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#setDimensionNameText(API.MultipleStringStructure)
	 */
	@Override
	public void setDimensionNameText(MultipleStringStructure text) {
		dimension_name_text = text;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#setGraduatedScale(int)
	 */
	@Override
	public void setGraduatedScale(int scale) {
		graduated_scale = scale;
	}

	/* (non-Javadoc)
	 * @see API.RRTDimension#setValueAt(int, API.RRTDimensionValue)
	 */
	@Override
	public boolean setValueAt(int index, RRTDimensionValue value) {
		if (index < 0 || index >= values.size())
			return false;
		values.set(index, value);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 1+dimension_name_text.getSizeInBytes()+1+getValuesLength(); 
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(getDimensionNameLength(), 8);
		os.write(dimension_name_text.toByteArray());
		os.writeFromLSB(0xFF, 3);
		os.writeFromLSB(graduated_scale, 1);
		
		if(getValuesDefined() > 0) {
			Iterator<RRTDimensionValue> it = getValues();
			while(it.hasNext())
				os.write(it.next().toByteArray());
		}

		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<RRTDimension>\n");
		
		str += dimension_name_text.toXMLString(base_space+2);
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<graduated_scale>"+graduated_scale+"</graduated_scale>\n");
		if (getValuesDefined() > 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<RRTDimensionValueLoop>\n");
			Iterator<RRTDimensionValue> it = getValues();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</RRTDimensionValueLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+"</RRTDimension>\n");
		return str;
	}

}

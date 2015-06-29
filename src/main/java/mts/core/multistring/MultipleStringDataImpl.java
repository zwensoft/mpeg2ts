/**
 * 
 */
package mts.core.multistring;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.multistring.MultipleStringData;
import mts.api.multistring.StringSegment;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class MultipleStringDataImpl implements MultipleStringData {

	protected int ISO_639_language_code;
	List<StringSegment> segments = new Vector<StringSegment>();

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#addStringSegment(API.MultiString.StringSegment)
	 */
	@Override
	public boolean addStringSegment(StringSegment segment) {
		return segments.add(segment);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#addStringSegmentAt(int, API.MultiString.StringSegment)
	 */
	@Override
	public boolean addStringSegmentAt(int index, StringSegment segment) {
		if (index < 0 || index > segments.size())
			return false;
		segments.add(index, segment);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#getLanguageCode()
	 */
	@Override
	public int getLanguageCode() {
		return ISO_639_language_code;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#getNumberSegments()
	 */
	@Override
	public int getNumberSegments() {
		return segments.size();
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#getStringSegmentAt(int)
	 */
	@Override
	public StringSegment getStringSegmentAt(int index) {
		if (index < 0 || index >= segments.size())
			return null;
		return segments.get(index);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#getStringSegments()
	 */
	@Override
	public Iterator<StringSegment> getStringSegments() {
		return segments.iterator();
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#removeAllStringSegments()
	 */
	@Override
	public void removeAllStringSegments() {
		segments.removeAll(segments);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#removeStringSegment(API.MultiString.StringSegment)
	 */
	@Override
	public boolean removeStringSegment(StringSegment segment) {
		return segments.remove(segment);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#removeStringSegmentAt(int, API.MultiString.StringSegment)
	 */
	@Override
	public boolean removeStringSegmentAt(int index, StringSegment segment) {
		if (index < 0 || index >= segments.size())
			return false;
		segments.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#setLanguageCode(int)
	 */
	@Override
	public void setLanguageCode(int ISO_639_language_code) {
		this.ISO_639_language_code = ISO_639_language_code;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#setStringSegmentAt(int, API.MultiString.StringSegment)
	 */
	@Override
	public boolean setStringSegmentAt(int index, StringSegment segment) {
		if (index < 0 || index >= segments.size())
			return false;
		segments.set(index, segment);
		return true;
	}
	
	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 4 + getStringSegmentsLength();
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringData#getStringSegmentsLength()
	 */
	@Override
	public int getStringSegmentsLength() {
		int tot_len = 0;
		if (getNumberSegments()>0) {
			Iterator<StringSegment> it = getStringSegments();
			while(it.hasNext())
				tot_len += it.next().getSizeInBytes();
		}
		return tot_len;
	}
	
	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(ISO_639_language_code, 24);
		os.writeFromLSB(getNumberSegments(), 8);
		if (getNumberSegments() > 0) {
			Iterator<StringSegment> it = getStringSegments();
			while(it.hasNext())
				os.write(it.next().toByteArray());
		}
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<StringData>\n");

		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ISO_639_language_code>"+ISO_639_language_code+"</ISO_639_language_code>\n");
		if (getNumberSegments() > 0) {
			Iterator<StringSegment> it = getStringSegments();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+2);
		}
		str += (MyUTIL.whiteSpaceStr(base_space)+ "</StringData>\n");
		return str;
	}

}

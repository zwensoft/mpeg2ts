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
import mts.api.multistring.MultipleStringStructure;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class MultipleStringStructureImpl implements MultipleStringStructure {
	List<MultipleStringData> strings = new Vector<MultipleStringData>();
	
	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#addStringData(API.MultiString.MultipleStringData)
	 */
	@Override
	public boolean addStringData(MultipleStringData data) {
		return strings.add(data);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#addStringDataAt(int, API.MultiString.MultipleStringData)
	 */
	@Override
	public boolean addStringDataAt(int index, MultipleStringData data) {
		if (index < 0 || index > strings.size())
			return false;
		strings.add(index, data);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#getAllStringData()
	 */
	@Override
	public Iterator<MultipleStringData> getAllStringData() {
		return strings.iterator();
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#getNumberStrings()
	 */
	@Override
	public int getNumberStrings() {
		return strings.size();
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#getStringDataAt(int)
	 */
	@Override
	public MultipleStringData getStringDataAt(int index) {
		if (index < 0 || index >= strings.size())
			return null;
		return strings.get(index);
	}
	
	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#getStringDataLength()
	 */
	@Override
	public int getAllStringDataLength() {
		int tot_length = 0;
		if (getNumberStrings() > 0) {
			Iterator<MultipleStringData> it = getAllStringData();
			while(it.hasNext())
				tot_length += it.next().getSizeInBytes();
		}
		return tot_length;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#removeAllStringData()
	 */
	@Override
	public void removeAllStringData() {
		strings.remove(strings);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#removeStringData(API.MultiString.MultipleStringData)
	 */
	@Override
	public boolean removeStringData(MultipleStringData data) {
		return strings.remove(data);
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#removeStringDataAt(int)
	 */
	@Override
	public boolean removeStringDataAt(int index) {
		if (index < 0 || index >= strings.size())
			return false;
		strings.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.MultipleStringStructure#setStringDataAt(int, API.MultiString.MultipleStringData)
	 */
	@Override
	public boolean setStringDataAt(int index, MultipleStringData data) {
		if  (index < 0 || index >= strings.size())
			return false;
		strings.set(index,data);
		return true;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 1 + getAllStringDataLength();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(getNumberStrings(), 8);
		if(getNumberStrings() > 0) {
			Iterator<MultipleStringData> it = getAllStringData();
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
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<MultipleStringStructure>\n");
		if (getNumberStrings() > 0) {
			Iterator<MultipleStringData> it = getAllStringData();
			while(it.hasNext())
				str += it.next().toXMLString(base_space+2);
		}
		str += (MyUTIL.whiteSpaceStr(base_space)+ "</MultipleStringStructure>\n");
		return str;
	}

}

/**
 * 
 */
package mts.core.multistring;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.multistring.StringSegment;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class StringSegmentImpl implements StringSegment {
	protected int compression_type;
	protected int mode;
	protected byte[] compressed_string_byte = null;
	
	
	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#getCompressedStringByte()
	 */
	@Override
	public byte[] getCompressedStringByte() {
		return compressed_string_byte;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#getCompressedStringNumberBytes()
	 */
	@Override
	public int getCompressedStringNumberBytes() {
		if (compressed_string_byte == null)
			return 0;
		return compressed_string_byte.length;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#getCompressionType()
	 */
	@Override
	public int getCompressionType() {
		return compression_type;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#getMode()
	 */
	@Override
	public int getMode() {
		return mode;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#setCompressedStringByte(byte[])
	 */
	@Override
	public void setCompressedStringByte(byte[] compressed_string) {
		compressed_string_byte = compressed_string;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#setCompressionType(int)
	 */
	@Override
	public void setCompressionType(int compression_type) {
		this.compression_type = compression_type;
	}

	/* (non-Javadoc)
	 * @see API.MultiString.StringSegment#setMode(int)
	 */
	@Override
	public void setMode(int mode) {
		this.mode = mode;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 3+ getCompressedStringNumberBytes();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(compression_type, 8);
		os.writeFromLSB(mode, 8);
		os.writeFromLSB(getCompressedStringNumberBytes(), 8);
		if (getCompressedStringNumberBytes() > 0)
			os.write(getCompressedStringByte());
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+ "<StringSegment>\n");

		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<compression_type>"+compression_type+"</compression_type>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<mode>"+mode+"</mode>\n");
		if (getCompressedStringNumberBytes()> 0) {
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"TODO: print compressed string to a readable character type\n");
		}
		
		str += (MyUTIL.whiteSpaceStr(base_space)+ "</StringSegment>\n");
		return str;
	}

}

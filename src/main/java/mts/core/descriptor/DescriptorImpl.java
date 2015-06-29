/**
 * 
 */
package mts.core.descriptor;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.Descriptor;
import mts.api.descriptor.DescriptorContent;
import mts.api.descriptor.DescriptorTag;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class DescriptorImpl implements Descriptor {
	protected DescriptorTag descriptor_tag;
	protected DescriptorContent descriptor_content;
	
	public DescriptorImpl(DescriptorTag tag) {
		descriptor_tag = tag;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.Descriptor#getContent()
	 */
	@Override
	public DescriptorContent getContent() {
		return descriptor_content;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.Descriptor#getDescriptorLength()
	 */
	@Override
	public int getDescriptorLength() {
		if (descriptor_content==null)
			return 0;
		return descriptor_content.getSizeInBytes();
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.Descriptor#getDescriptorTag()
	 */
	@Override
	public DescriptorTag getDescriptorTag() {
		return descriptor_tag;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.Descriptor#setContent(API.Descriptor.DescriptorContent)
	 */
	@Override
	public void setContent(DescriptorContent content) {
		descriptor_content = content;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 2 + getDescriptorLength();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(descriptor_tag.getValue(), 8);
		os.writeFromLSB(getDescriptorLength(), 8);
		if (descriptor_content != null) {
			os.write(descriptor_content.toByteArray());
		}
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		if (descriptor_content == null)
			str += (MyUTIL.whiteSpaceStr(base_space)+"<Descriptor>\n");
		else
			str += (MyUTIL.whiteSpaceStr(base_space)+"<"+descriptor_content.getNameString()+"_Descriptor>\n");
		
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<descriptor_tag>"+descriptor_tag+"</descriptor_tag>\n");
		if (descriptor_content != null)
			str += descriptor_content.toXMLString(base_space+2);

		if (descriptor_content == null)
			str += (MyUTIL.whiteSpaceStr(base_space)+"<Descriptor>\n");
		else
			str += (MyUTIL.whiteSpaceStr(base_space)+"<"+descriptor_content.getNameString()+"_Descriptor>\n");

		return str;
	}

}

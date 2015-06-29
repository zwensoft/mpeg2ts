/**
 * 
 */
package mts.core.descriptor;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.StreamType;
import mts.api.descriptor.DC_ServiceLocation;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class ServiceLocationImpl implements DC_ServiceLocation {
	protected int PCR_PID;
	List<ServiceElement> elements = new Vector<ServiceElement>();

	class ServiceElement {
		protected StreamType stream_type;
		protected int elementary_PID;
		protected int ISO_639_language_code;
		
		ServiceElement(StreamType st, int pid, int lang) {
			stream_type = st;
			elementary_PID = pid;
			ISO_639_language_code = lang;
		}
	}
	
	/* (non-Javadoc)
	 * @see API.Descriptor.DescriptorContent#getNameString()
	 */
	@Override
	public String getNameString() {
		return "Service_Location";
	}
	
	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#addElement(int, int, int)
	 */
	@Override
	public boolean addElement(StreamType stream_type, int elementary_PID,
			int lang_code) {
		return elements.add(new ServiceElement(stream_type, elementary_PID, lang_code));
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#addElementAt(int, int, int, int)
	 */
	@Override
	public boolean addElementAt(int index, StreamType stream_type, int elementary_PID,
			int lang_code) {
		if (index < 0 || index > elements.size())
			return false;
		elements.add(index, new ServiceElement(stream_type, elementary_PID, lang_code));
		return true;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getElementaryPidAt(int)
	 */
	@Override
	public int getElementaryPidAt(int index) {
		if (index < 0 || index >= elements.size())
			return -1;
		return elements.get(index).elementary_PID;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getElementsLength()
	 */
	@Override
	public int getElementsLength() {
		return getNumberElements() * 6;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getLanguageCodeAt(int)
	 */
	@Override
	public int getLanguageCodeAt(int index) {
		if (index < 0 || index >= elements.size())
			return -1;
		return elements.get(index).ISO_639_language_code;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getNumberElements()
	 */
	@Override
	public int getNumberElements() {
		return elements.size();
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getPCR_PID()
	 */
	@Override
	public int getPCR_PID() {
		return PCR_PID;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#getStreamTypeAt(int)
	 */
	@Override
	public StreamType getStreamTypeAt(int index) {
		if (index < 0 || index >= elements.size())
			return StreamType.ISO_IEC_Reserved;
		return elements.get(index).stream_type;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#removeAllElements()
	 */
	@Override
	public void removeAllElements() {
		elements.remove(elements);
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#removeElementAt(int)
	 */
	@Override
	public boolean removeElementAt(int index) {
		if  (index < 0 || index >= elements.size())
			return false;
		elements.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ServiceLocation#setElementAt(int, int, int, int)
	 */
	@Override
	public boolean setElementAt(int index, StreamType stream_type, int elementary_PID,
			int lang_code) {
		if (index < 0 || index >= elements.size())
			return false;
		elements.set(index, new ServiceElement(stream_type, elementary_PID, lang_code));
		return true;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 3 + getElementsLength();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(0xFF, 3);
		os.writeFromLSB(PCR_PID, 13);
		os.writeFromLSB(getNumberElements(), 8);
		if (getNumberElements() > 0) {
			Iterator<ServiceElement> it = elements.iterator();
			while(it.hasNext()) {
				ServiceElement element = it.next();
				os.writeFromLSB(element.stream_type.getValue(), 8);
				os.writeFromLSB(0xFF, 3);
				os.writeFromLSB(element.elementary_PID, 13);
				os.writeFromLSB(element.ISO_639_language_code, 24);
			}
		}
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<PCR_PID>"+PCR_PID+"</PCR_PID>\n");
		if (getNumberElements() > 0) {
			Iterator<ServiceElement> it = elements.iterator();
			while(it.hasNext()) {
				ServiceElement element = it.next();
				str += (MyUTIL.whiteSpaceStr(base_space+2)+"<stream_type>"+element.stream_type+"</stream_type>\n");
				str += (MyUTIL.whiteSpaceStr(base_space+2)+"<elementary_PID>"+element.elementary_PID+"</elementary_PID>\n");
				str += (MyUTIL.whiteSpaceStr(base_space+2)+"<ISO_639_language_code>"+element.ISO_639_language_code+"</ISO_639_language_code>\n");
			}
		}
		return str;
	}

}

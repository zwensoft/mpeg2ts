/**
 * 
 */
package mts.core.psi;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.StreamType;
import mts.api.descriptor.Descriptor;
import mts.api.psi.PMTStream;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class PMTStreamDefaultImpl implements PMTStream {
	protected StreamType stream_type = StreamType.ISO_IEC_Reserved;
	protected int elementary_PID = 0;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	
	public PMTStreamDefaultImpl(StreamType type, int pid) {
		stream_type = type;
		elementary_PID = pid;
	}
	
	/* (non-Javadoc)
	 * @see API.PMTStream#getElementaryPID()
	 */
	@Override
	public int getElementaryPID() {
		return elementary_PID;
	}

	/* (non-Javadoc)
	 * @see API.PMTStream#getStreamType()
	 */
	@Override
	public StreamType getStreamType() {
		return stream_type;
	}

	/* (non-Javadoc)
	 * @see API.PMTStream#setElementaryPID(int)
	 */
	@Override
	public void setElementaryPID(int pid) {
		elementary_PID = pid;
	}

	/* (non-Javadoc)
	 * @see API.PMTStream#setStreamType(API.StreamType)
	 */
	@Override
	public void setStreamType(StreamType type) {
		stream_type = type;
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 5 + getDescriptorsLength();
	}

	/* (non-Javadoc)
	 * @see API.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(stream_type.getValue(), 8);
		os.writeFromLSB(0,3); // reserved
		os.writeFromLSB(elementary_PID, 13);
		os.writeFromLSB(0,4); // reserved
		os.writeFromLSB(getDescriptorsLength(), 12);
		
		Iterator<Descriptor> it = getDescriptors();
		while (it.hasNext())
			os.write(it.next().toByteArray());

		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see API.XMLRepresentation#toXML(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<PMTStream>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<stream_type>"+stream_type.getValue()+"</stream_type>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<elementary_PID>"+elementary_PID+"</elementary_PID>\n");

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}

		str += (MyUTIL.whiteSpaceStr(base_space)+"</PMTStream>\n");
		return str;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#addDescriptor(API.Descriptor)
	 */
	@Override
	public boolean addDescriptor(Descriptor desc) {
		descs.add(desc);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#addDescriptorAt(int, API.Descriptor)
	 */
	@Override
	public boolean addDescriptorAt(int index, Descriptor desc) {
		if (index < 0 || index > descs.size())
			return false;
		descs.add(index, desc);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorAt(int)
	 */
	@Override
	public Descriptor getDescriptorAt(int index){
		if (index < 0 || index >= descs.size())
			return null;
		return descs.get(index);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptors()
	 */
	@Override
	public Iterator<Descriptor> getDescriptors(){
		return descs.iterator();
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorSize()
	 */
	@Override
	public int getDescriptorSize(){
		return descs.size();
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#getDescriptorsLength()
	 */
	@Override
	public int getDescriptorsLength(){
		int desc_length = 0;
		Iterator<Descriptor> it = descs.iterator();
		while(it.hasNext())
			desc_length += it.next().getSizeInBytes();
		return desc_length;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeAllDescriptors()
	 */
	@Override
	public void removeAllDescriptors(){
		descs.removeAll(descs);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeDescriptor(API.Descriptor)
	 */
	@Override
	public boolean removeDescriptor(Descriptor desc){
		return descs.remove(desc);
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#removeDescriptorAt(int)
	 */
	@Override
	public boolean removeDescriptorAt(int index){
		if (index < 0 || index >= descs.size())
			return false;
		descs.remove(index);
		return true;
	}

	/* (non-Javadoc)
	 * @see API.DescriptorMethod#setDescriptorAt(int, API.Descriptor)
	 */
	@Override
	public boolean setDescriptorAt(int index, Descriptor desc){
		if (index < 0 || index >= descs.size())
			return false;
		descs.set(index, desc);
		return true;
	}
}

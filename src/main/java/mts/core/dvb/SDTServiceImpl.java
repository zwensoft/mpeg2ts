/**
 * 
 */
package mts.core.dvb;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.Descriptor;
import mts.api.dvb.SDTService;



/**
 * @author saint
 *
 */
public class SDTServiceImpl implements SDTService {

	protected int service_id;
	protected int EIT_schedule_flag;
	protected int EIT_present_following_flag;
	protected int running_status;
	protected int free_CA_mode;
	protected List<Descriptor> descs = new Vector<Descriptor>();
	protected long interval_millis = 500;
	
	/* (non-Javadoc)
	 * @see API.DVB.SDTService#getEitPfFlag()
	 */
	@Override
	public int getEitPfFlag() {
		return EIT_present_following_flag;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#getEitSchedFlag()
	 */
	@Override
	public int getEitSchedFlag() {
		return EIT_schedule_flag;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#getFreeCaMode()
	 */
	@Override
	public int getFreeCaMode() {
		return free_CA_mode;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#getRunningStatus()
	 */
	@Override
	public int getRunningStatus() {
		return running_status;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#getServiceId()
	 */
	@Override
	public int getServiceId() {
		return service_id;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#setEitPfFlag(int)
	 */
	@Override
	public void setEitPfFlag(int eit_pf_flag) {
		EIT_present_following_flag = eit_pf_flag;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#setEitSchedFlag(int)
	 */
	@Override
	public void setEitSchedFlag(int eit_sched_flag) {
		EIT_schedule_flag = eit_sched_flag;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#setFreeCaMode(int)
	 */
	@Override
	public void setFreeCaMode(int free_ca_mode) {
		free_CA_mode = free_ca_mode;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#setRunningStatus(int)
	 */
	@Override
	public void setRunningStatus(int run_status) {
		running_status = run_status;
	}

	/* (non-Javadoc)
	 * @see API.DVB.SDTService#setServiceId(int)
	 */
	@Override
	public void setServiceId(int service_id) {
		this.service_id = service_id;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 5+ getDescriptorsLength();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		
		os.writeFromLSB(service_id, 16);
		os.writeFromLSB(0, 6);
		os.writeFromLSB(EIT_schedule_flag, 1);
		os.writeFromLSB(EIT_present_following_flag, 1);
		os.writeFromLSB(running_status, 3);
		os.writeFromLSB(free_CA_mode, 1);
		os.writeFromLSB(getDescriptorsLength(), 12);

		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
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
		str += (MyUTIL.whiteSpaceStr(base_space)+"<SDTService>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<service_id>"+service_id+"</service_id>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<EIT_schedule_flag>"+EIT_schedule_flag+"</EIT_schedule_flag>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<EIT_pf_flag>"+EIT_present_following_flag+"</EIT_pf_flag>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<running_status>"+running_status+"</running_status>\n");
		str += (MyUTIL.whiteSpaceStr(base_space+2)+"<free_CA_mode>"+free_CA_mode+"</free_CA_mode>\n");
		
		if (getDescriptorSize() > 0) {
			Iterator<Descriptor> it = getDescriptors();
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<DescriptorLoop>\n");
			while (it.hasNext())
				str += it.next().toXMLString(base_space+4);
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"</DescriptorLoop>\n");
		}
		str += (MyUTIL.whiteSpaceStr(base_space)+"</SDTService>\n");
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

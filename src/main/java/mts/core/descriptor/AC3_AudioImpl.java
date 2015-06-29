/**
 * 
 */
package mts.core.descriptor;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.DC_AC3Audio;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class AC3_AudioImpl implements DC_AC3Audio {
	protected int sample_rate_code;
	protected int bsid;
	protected int bit_rate_code;
	protected int surround_mode;
	protected int bsmod;
	protected int num_channels;
	protected int full_svc;
	protected int langcod;
	protected int langcod2;
	protected int mainid;
	protected int asvcflags;
	protected int textlen;
	protected int text_code;
	protected char[] text;
	protected byte[] additional_info;

	/* (non-Javadoc)
	 * @see API.Descriptor.DescriptorContent#getNameString()
	 */
	@Override
	public String getNameString() {
		return "AC3_Audio";
	}
	
	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getAdditionalInfo()
	 */
	@Override
	public byte[] getAdditionalInfo() {
		return additional_info;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getAsvcFlags()
	 */
	@Override
	public int getAsvcFlags() {
		return asvcflags;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getBitRateCode()
	 */
	@Override
	public int getBitRateCode() {
		return bit_rate_code;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getBsid()
	 */
	@Override
	public int getBsid() {
		return bsid;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getBsmod()
	 */
	@Override
	public int getBsmod() {
		return bsmod;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getFullSvc()
	 */
	@Override
	public int getFullSvc() {
		return full_svc;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getLangCod()
	 */
	@Override
	public int getLangCod() {
		return langcod;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getLangCod2()
	 */
	@Override
	public int getLangCod2() {
		return langcod2;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getMainId()
	 */
	@Override
	public int getMainId() {
		return mainid;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getNumChannels()
	 */
	@Override
	public int getNumChannels() {
		return num_channels;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getSampleRateCode()
	 */
	@Override
	public int getSampleRateCode() {
		return sample_rate_code;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getSurroundMode()
	 */
	@Override
	public int getSurroundMode() {
		return surround_mode;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getText()
	 */
	@Override
	public char[] getText() {
		return text;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getTextCode()
	 */
	@Override
	public int getTextCode() {
		return text_code;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#getTextLen()
	 */
	@Override
	public int getTextLen() {
		return textlen;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setAdditionalInfo(byte[])
	 */
	@Override
	public void setAdditionalInfo(byte[] val) {
		additional_info = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setAsvcFlags(int)
	 */
	@Override
	public void setAsvcFlags(int val) {
		asvcflags = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setBitRateCode(int)
	 */
	@Override
	public void setBitRateCode(int val) {
		bit_rate_code = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setBsid(int)
	 */
	@Override
	public void setBsid(int val) {
		bsid = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setBsmod(int)
	 */
	@Override
	public void setBsmod(int val) {
		bsmod = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setFullSvc(int)
	 */
	@Override
	public void setFullSvc(int val) {
		full_svc = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setLangCod(int)
	 */
	@Override
	public void setLangCod(int val) {
		langcod = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setLangCod2(int)
	 */
	@Override
	public void setLangCod2(int val) {
		langcod2 = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setMainId(int)
	 */
	@Override
	public void setMainId(int val) {
		mainid = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setNumChannels(int)
	 */
	@Override
	public void setNumChannels(int val) {
		num_channels = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setSampleRateCode(int)
	 */
	@Override
	public void setSampleRateCode(int val) {
		sample_rate_code = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setSurroundMode(int)
	 */
	@Override
	public void setSurroundMode(int val) {
		surround_mode = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setText(char[])
	 */
	@Override
	public void setText(char []val) {
		text = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setTextCode(int)
	 */
	@Override
	public void setTextCode(int val) {
		text_code = val;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_AC3Audio#setTextLen(int)
	 */
	@Override
	public void setTextLen(int val) {
		textlen = val;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		int tot_bytes = 4;
		if (num_channels == 0)
			tot_bytes += 1;
		tot_bytes += 1;
		tot_bytes += 1; // textlen, text_code
		tot_bytes += textlen;
		tot_bytes += additional_info.length;
		return tot_bytes;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		os.writeFromLSB(sample_rate_code, 3);
		os.writeFromLSB(bsid, 5);
		os.writeFromLSB(bit_rate_code, 6);
		os.writeFromLSB(surround_mode, 2);
		os.writeFromLSB(bsmod, 3);
		os.writeFromLSB(num_channels, 4);
		os.writeFromLSB(full_svc, 1);
		
		os.writeFromLSB(langcod, 8);
		if (num_channels==0)
			os.writeFromLSB(langcod2, 8);
		if (bsmod<2) {
			os.writeFromLSB(mainid, 3);
			os.writeFromLSB(0, 5); //reserved
		}else {
			os.writeFromLSB(asvcflags, 8);
		}
		
		os.writeFromLSB(textlen, 7);
		os.writeFromLSB(text_code, 1);
		if (text_code == 1) { // 1byte ISO Latin-1 alphabet(ISO8559-1)
			for(int n=0; n < text.length; n++)
				os.write((byte)text[n]);
		}else {
			for(int n=0; n < text.length; n++)
				os.write(text[n]);
		}
		
		if (additional_info != null)
			os.write(additional_info);

		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		str += (MyUTIL.whiteSpaceStr(base_space)+"<sample_rate_code>"+sample_rate_code+"</sample_rate_code>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<bsid>"+bsid+"</bsid>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<bit_rate_code>"+bit_rate_code+"</bit_rate_code>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<surround_mode>"+surround_mode+"</surround_mode>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<bsmod>"+bsmod+"</bsmod>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<num_channels>"+num_channels+"</num_channels>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<full_svc>"+full_svc+"</full_svc>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<langcod>"+langcod+"</langcod>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<langcod2>"+langcod2+"</langcod2>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<mainid>"+mainid+"</mainid>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<asvcflags>"+asvcflags+"</asvcflags>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<textlen>"+textlen+"</textlen>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<text_code>"+text_code+"</text_code>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<text>"+(new String(text))+"</text>\n");
		str += (MyUTIL.whiteSpaceStr(base_space)+"<textlen>"+textlen+"</textlen>\n");
		return str;
	}

}

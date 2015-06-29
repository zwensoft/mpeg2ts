/**
 * 
 */
package mts.core.descriptor;

import java.util.List;
import java.util.Vector;

import mts.api.BitOutputStream;
import mts.api.MyUTIL;
import mts.api.descriptor.DC_ISO639Language;



/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class ISO_639_LanguageImpl implements DC_ISO639Language {
	protected List<Integer> ISO_639_language_codes = new Vector<Integer>();
	protected List<Integer> audio_types = new Vector<Integer>();

	/* (non-Javadoc)
	 * @see API.Descriptor.DescriptorContent#getNameString()
	 */
	@Override
	public String getNameString() {
		return "ISO639_Language";
	}
	
	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#addLanguage(int, int)
	 */
	@Override
	public boolean addLanguage(int ISO_639_language_code, int audio_type) {
		ISO_639_language_codes.add(new Integer(ISO_639_language_code));
		audio_types.add(new Integer(audio_type));
		return true;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#addLanguageAt(int, int, int)
	 */
	@Override
	public boolean addLanguageAt(int index, int ISO_639_language_code,
			int audio_type) {
		if (index < 0 || index > ISO_639_language_codes.size())
			return false;
		ISO_639_language_codes.add(index, new Integer(ISO_639_language_code));
		audio_types.add(index, new Integer(audio_type));
		return true;
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#getAudioTypeAt(int)
	 */
	@Override
	public int getAudioTypeAt(int index) {
		if (index < 0 || index >= audio_types.size())
			return -1;
		return audio_types.get(index).intValue();
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#getLanguageCodeAt(int)
	 */
	@Override
	public int getLanguageCodeAt(int index) {
		if (index < 0 || index >= ISO_639_language_codes.size())
			return -1;
		return ISO_639_language_codes.get(index).intValue();
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#getLanguagesDefined()
	 */
	@Override
	public int getLanguagesDefined() {
		return ISO_639_language_codes.size();
	}

	/* (non-Javadoc)
	 * @see API.Descriptor.DC_ISO639Language#setLanguageAt(int, int, int)
	 */
	@Override
	public boolean setLanguageAt(int index, int ISO_639_language_code,
			int audio_type) {
		if (index < 0 || index >= ISO_639_language_codes.size())
			return false;
		ISO_639_language_codes.set(index, new Integer(ISO_639_language_code));
		return true;
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#getSizeInBytes()
	 */
	@Override
	public int getSizeInBytes() {
		return 4 * ISO_639_language_codes.size();
	}

	/* (non-Javadoc)
	 * @see Core.ByteRepresentation#toByteArray()
	 */
	@Override
	public byte[] toByteArray() {
		if (getLanguagesDefined() == 0)
			return null;

		BitOutputStream os = new BitOutputStream(getSizeInBytes()*Byte.SIZE);
		for(int n=0; n < getLanguagesDefined(); n++) {
			os.writeFromLSB(getLanguageCodeAt(n), 24);
			os.writeFromLSB(getAudioTypeAt(n), 8);
		}
		return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see Core.XMLRepresentation#toXMLString(int)
	 */
	@Override
	public String toXMLString(int base_space) {
		String str = new String();
		for(int n=0; n < getLanguagesDefined(); n++) {
			int lang_code = getLanguageCodeAt(n);
			char first = (char)((lang_code & 0x00FF0000) >> 16);
			char second = (char)((lang_code & 0x0000FF00) >> 8);
			char third = (char)(lang_code & 0x000000FF);
			str += (MyUTIL.whiteSpaceStr(base_space)+"<ISO_639_language_code>"+
					String.valueOf(first)+String.valueOf(second)+String.valueOf(third)+
					"</ISO_639_language_code>\n");
			str += (MyUTIL.whiteSpaceStr(base_space+2)+"<audio_type>"+getAudioTypeAt(n)+"</audio_type>\n"); 
		}
		return str;
	}

}

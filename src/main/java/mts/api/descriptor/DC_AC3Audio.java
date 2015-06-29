/**
 * 
 */
package mts.api.descriptor;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface DC_AC3Audio extends DescriptorContent {
	/**
	 * return a value of 'sample_rate_code'.
	 * @return
	 */
	int getSampleRateCode();
	
	/**
	 * return a value of 'bsid'.
	 * @return
	 */
	int getBsid();
	
	/**
	 * return a value of 'bit_rate_code'.
	 * @return
	 */
	int getBitRateCode();
	
	/**
	 * return a value of 'surround_mode'.
	 * @return
	 */
	int getSurroundMode();
	
	/**
	 * return a value of 'bsmod'.
	 * @return
	 */
	int getBsmod();
	
	/**
	 * return a value of 'num_channels'.
	 * @return
	 */
	int getNumChannels();
	
	/**
	 * return a value of 'full_svc'.
	 * @return
	 */
	int getFullSvc();
	
	/**
	 * return a value of 'langcod'.
	 * @return
	 */
	int getLangCod();

	/**
	 * return a value of 'langcod2'.
	 * @return
	 */
	int getLangCod2();
	
	/**
	 * return a value of 'mainid'.
	 * @return
	 */
	int getMainId();
	
	/**
	 * return a value of 'asvcflags'.
	 * @return
	 */
	int getAsvcFlags();
	
	/**
	 * return a value of 'textlen'.
	 * @return
	 */
	int getTextLen();
	
	/**
	 * return a value of 'text_code'.
	 * @return
	 */
	int getTextCode();
	
	/**
	 * return a value of 'text[]'.
	 * @return
	 */
	char[] getText();
	
	/**
	 * return a value of 'additional_info[]'.
	 * @return
	 */
	byte[] getAdditionalInfo();

	
	/**
	 * set a value of 'sample_rate_code'.
	 * @param val
	 */
	void setSampleRateCode(int val);
	
	/**
	 * set a value of 'bsid'.
	 * @param val
	 */
	void setBsid(int val);
	
	/**
	 * set a value of 'bit_rate_code'.
	 * @param val
	 */
	void setBitRateCode(int val);
	
	/**
	 * set a value of 'surround_mode'.
	 * @param val
	 */
	void setSurroundMode(int val);
	
	/**
	 * set a value of 'bsmod'.
	 * @param val
	 */
	void setBsmod(int val);
	
	/**
	 * set a value of 'num_channels'.
	 * @param val
	 */
	void setNumChannels(int val);
	
	/**
	 * set a value of 'full_svc'.
	 * @param val
	 */
	void setFullSvc(int val);
	
	/**
	 * set a value of 'langcod'.
	 * @param val
	 */
	void setLangCod(int val);

	/**
	 * set a value of 'langcod2'.
	 * @param val
	 */
	void setLangCod2(int val);
	
	/**
	 * set a value of 'mainid'.
	 * @param val
	 */
	void setMainId(int val);
	
	/**
	 * set a value of 'asvcflags'.
	 * @param val
	 */
	void setAsvcFlags(int val);
	
	/**
	 * set a value of 'textlen'.
	 * @param val
	 */
	void setTextLen(int val);
	
	/**
	 * set a value of 'text_code'.
	 * @param val
	 */
	void setTextCode(int val);
	
	/**
	 * set a value of 'text[]'.
	 * @param val
	 */
	void setText(char[] val);
	
	/**
	 * set a value of 'additional_info[]'.
	 * @param val
	 */
	void setAdditionalInfo(byte[] val);	
}

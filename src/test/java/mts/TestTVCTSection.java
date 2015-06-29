/**
 * 
 */
package mts;

import java.io.FileOutputStream;
import java.io.IOException;

import mts.api.SITableFactory;
import mts.api.Section;
import mts.api.ServiceType;



import flavor.BuffBitstream;
import flavor.IBitstream;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TestTVCTSection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* This example is to show how to verify your implementation of SI tables
		 *  by using 'flavor'(Formal Language for Audio-Visual Object Representation).
		 */
		mts.api.psip.TVCT table;
		mts.api.psip.TVCTChannel tvct_chan1, tvct_chan2;
		int tvct_ver = 0x5;
		int tsid = 0xABC;
		int program_num1 = 0x35, program_num2 = 0x65;
		int source_id1 = 0x501, source_id2 = 0x502;
		
		table = SITableFactory.createTVCT(tvct_ver, tsid);
		tvct_chan1 = SITableFactory.createTVCTChannel((new String("prog1")).toCharArray(), 
				11, 1, 1, 0x1101, program_num1, ServiceType.ATSC_DIGITAL_TELEVISION, source_id1);
		tvct_chan2 = SITableFactory.createTVCTChannel((new String("prog2")).toCharArray(), 
				9, 1, 1, 0x0901, program_num2, ServiceType.ATSC_DIGITAL_TELEVISION, source_id2);
		table.addChannel(tvct_chan1);
		table.addChannel(tvct_chan2);
		
		Section [] sections = table.toSection();
		for(int sn=0; sn < sections.length; sn++) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("temp", false);
				fos.write(sections[sn].getSectionBytes());
				fos.close();

				IBitstream stream = new BuffBitstream("temp", IBitstream.BS_INPUT);
				flavor.Generated.TVCT parser = new flavor.Generated.TVCT();
				parser.get(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

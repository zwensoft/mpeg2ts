package mts;

import java.io.FileOutputStream;
import java.io.IOException;


import mts.api.*;

import flavor.*;

public class TestPMTSection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* This example is to show how to verify your implementation of SI tables
		 *  by using 'flavor'(Formal Language for Audio-Visual Object Representation).
		 */
		mts.api.psi.PMT table;
		int program_num2 = 0x65;
		int pmt2_ver = 0x3;
		int pmt2_pid = 1002/*3EA*/;
		int pmt2_stream1_pid = 0x201, pmt2_stream2_pid = 0x202;
		
		table = SITableFactory.createPMT(pmt2_pid, pmt2_ver, program_num2, 0x0ABC);
		table.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Video, pmt2_stream1_pid));
		table.addStream(SITableFactory.createPMTStream(StreamType.ISO_IEC_11172_Audio, pmt2_stream2_pid));
		
		Section [] sections = table.toSection();
		for(int sn=0; sn < sections.length; sn++) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("temp", false);
				fos.write(sections[sn].getSectionBytes());
				fos.close();

				IBitstream stream = new BuffBitstream("temp", IBitstream.BS_INPUT);
				flavor.Generated.PMT parser = new flavor.Generated.PMT();
				parser.get(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

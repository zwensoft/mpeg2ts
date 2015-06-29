/**
 * 
 */
package mts;

import java.io.FileOutputStream;
import java.io.IOException;


import mts.api.*;

import flavor.*;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class TestPatSection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/* This example is to show how to verify your implementation of SI tables
		 *  by using 'flavor'(Formal Language for Audio-Visual Object Representation).
		 */

		/* First part : generate table with API */
		mts.api.psi.PAT table = SITableFactory.createPAT(15, 0xABC);
		mts.api.psi.PATProgram program1 = SITableFactory.createPATProgram(0xC0, 0xD0);
		mts.api.psi.PATProgram program2 = SITableFactory.createPATProgram(0xE0, 0xF0);
		table.addProgram(program1);
		table.addProgram(program2);
		
		Section [] sections = table.toSection();
		for(int sn=0; sn < sections.length; sn++) {
			FileOutputStream fos;
			try {
				fos = new FileOutputStream("temp", false);
				fos.write(sections[sn].getSectionBytes());
				fos.close();

				IBitstream stream = new BuffBitstream("temp", IBitstream.BS_INPUT);
				flavor.Generated.PAT pat_parser = new flavor.Generated.PAT();
				pat_parser.get(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

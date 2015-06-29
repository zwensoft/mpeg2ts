package mts.core;

import mts.api.Section;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface SectionRepresentation {
	/**
	 * check whether a table is multisection or not. 
	 * @return
	 */
	boolean isMultiSection();

	/**
	 * max_stream_size_in_section = 1012 - ((followings of 'section length') + desc loops.)
	 * return total section number
	 * @return
	 */
	int getTotalSectionNumber();

	/**
	 * generate section from a table.
	 * @return
	 */
	Section[] toSection();
}


/**
 * 
 */
package mts.core;

import mts.api.SITable;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public abstract class SITableAbstractImpl implements SITable {
	protected long start_time = 0;
	protected long end_time = 0;
	protected int unique_id = UniqueIDServer.getUniqueID();

	/* (non-Javadoc)
	 * @see API.SITable#getUniqueID()
	 */
	@Override
	public int getUniqueID() {
		return unique_id;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getDuration()
	 */
	@Override
	public long getDuration() {
		return end_time - start_time;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getEndTime()
	 */
	@Override
	public long getEndTime() {
		return end_time;
	}

	/* (non-Javadoc)
	 * @see API.SITable#getStartTime()
	 */
	@Override
	public long getStartTime() {
		return start_time;
	}

	/* (non-Javadoc)
	 * @see API.SITable#setEndTime(long)
	 */
	@Override
	public void setEndTime(long etime) {
		end_time = etime;
		if (end_time < start_time)
			start_time = end_time;
	}

	/* (non-Javadoc)
	 * @see API.SITable#setStartTime(long)
	 */
	@Override
	public void setStartTime(long stime) {
		start_time = stime;
		if (end_time < start_time)
			end_time = start_time;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SITable clone() {
        SITable clone = null;
		try {
			clone = (SITable)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
        return clone;
	}
}

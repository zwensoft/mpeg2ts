/**
 * 
 */
package mts.api.descriptor;

import java.util.Iterator;


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public interface DescriptorMethod {
	/**
	 * return a number of 'descriptor()'s defined.  
	 * @return
	 */
	public int getDescriptorSize();

	/**
	 * return total bytes of 'descriptor()'s.
	 * @return
	 */
	public int getDescriptorsLength();

	/**
	 * return a 'descriptor()' of a given index.
	 * @param index
	 */
	public Descriptor getDescriptorAt(int index);

	/**
	 * return all 'descriptor()'s.
	 * @return
	 */
	public Iterator<Descriptor> getDescriptors();

	/**
	 * set a 'descriptor()' to .
	 * @param index
	 * @param desc
	 */
	public boolean setDescriptorAt(int index, Descriptor desc);

	/**
	 * add one 'descriptor()'.
	 * @param desc
	 */
	public boolean addDescriptor(Descriptor desc);

	/**
	 * add one 'descriptor()'.
	 * @param index
	 * @param desc
	 */
	public boolean addDescriptorAt(int index, Descriptor desc);
	
	/**
	 * remove 'descriptor()' instance.
	 * @param desc
	 */
	public boolean removeDescriptor(Descriptor desc);

	/**
	 * remove 'descriptor()' instance.
	 * @param index
	 */
	public boolean removeDescriptorAt(int index);

	/**
	 * remove all 'descriptor()' instances.
	 */
	public void removeAllDescriptors();
}

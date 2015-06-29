package mts.api;



/**
 * 
 */


/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class BitOutputStream {
	/**
	 * bit mask to extract bits from a byte.
	 * ex) extract 4~7 bits from a byte. (bit '0' is a MSB, bit '7' is a LSB)
	 *     extracted = byte_data & mask[4][7]; 
	 */
	private static final byte mask[][] = {
		{(byte)0x80,(byte)0xC0,(byte)0xE0,(byte)0xF0,(byte)0xF8,(byte)0xFC,(byte)0xFE,(byte)0xFF},
		{0,0x80>>1,0xC0>>1,0xE0>>1,0xF0>>1,0xF8>>1,0xFC>>1,0xFE>>1},
		{0,0,0x80>>2,0xC0>>2,0xE0>>2,0xF0>>2,0xF8>>2,0xFC>>2},
		{0,0,0,0x80>>3,0xC0>>3,0xE0>>3,0xF0>>3,0xF8>>3},
		{0,0,0,0,0x80>>4,0xC0>>4,0xE0>>4,0xF0>>4},
		{0,0,0,0,0,0x80>>5,0xC0>>5,0xE0>>5},
		{0,0,0,0,0,0,0x80>>6,0xC0>>6},
		{0,0,0,0,0,0,0,0x80>>7},
	};

	/**
	 * bitstream data
	 */
	private byte[] byte_array;
	/**
	 * total bits in this stream
	 */
	private int total_bits;
	/**
	 * current writing position 
	 */
	private int current_bit_pos;
	/**
	 * mark position
	 */
	private int mark_bit_pos;

	/**
	 * Constructor.
	 * @param total_bits total bits in this stream
	 */
	public BitOutputStream(int total_bits) {
		if (total_bits <= 0)
			throw new IllegalArgumentException();

		this.total_bits = total_bits;
		if ((total_bits & 0x7) != 0)
			byte_array = new byte[(total_bits>>3)+1];
		else
			byte_array = new byte[total_bits>>3];
		init();
	}

	/**
	 * Constructor.
	 * @param buf 
	 */
	public BitOutputStream(byte[] buf) {
		if (buf == null)
			throw new NullPointerException();

		total_bits = (buf.length << 3);
		byte_array = buf;
		init();
	}

	private void init() {
		current_bit_pos = 0;
		mark_bit_pos = -1;
	}

	/**
	 * mark current position
	 */
	public void mark() {
		mark_bit_pos = current_bit_pos;
	}

	/**
	 * check whether 'mark()' is supported or not.
	 * @return true
	 */
	public boolean markSupported() {
		return true;
	}

	/**
	 * reset writing position to marked position.  
	 */
	public void reset() {
		if (mark_bit_pos != -1) {
			current_bit_pos = mark_bit_pos;
		}
		mark_bit_pos = -1;
	}
	
	/**
	 * return current writing position
	 * @return current writing position
	 */
	public int getPos() {
		return current_bit_pos;
	}
	
	/**
	 * return total bits of this stream
	 * @return
	 */
	public int getSize() {
		return total_bits;
	}
	
	/**
	 * return stream byte array
	 * @return byte_array
	 */
	public byte[] toByteArray() {
		return byte_array;
	}
	
	/**
	 * write 'bitlen' bits of data to this stream from its MSB.
	 * @param bdata
	 * @param bitlen
	 * @return
	 */
	public int writeFromMSB(byte bdata, int bitlen) {
		return write(bdata, 0, bitlen-1);
	}

	/**
	 * write 'bitlen' bits of data to this stream from its MSB.
	 * @param sdata
	 * @param bitlen
	 * @return
	 */
	public int writeFromMSB(short sdata, int bitlen) {
		int shift = Short.SIZE - Byte.SIZE;
		byte bdata = 0;
		int temp = 0, written = 0;
		while(bitlen >= Byte.SIZE) {
			bdata = (byte)((sdata >>> shift) & 0xFF);
			temp = write(bdata, 0, Byte.SIZE-1);
			if (temp < 0)
				return written;
			written += temp;
			bitlen -= Byte.SIZE;
			shift -= Byte.SIZE;
		}

		if (bitlen > 0) {
			bdata = (byte)((sdata >>> shift) & 0xFF);
			temp = write(bdata, 0, bitlen-1);
			if (temp < 0)
				return written;
			written += temp;
		}

		return written;
	}
	
	/**
	 * write 'bitlen' bits of data to this stream from its MSB.
	 * @param idata
	 * @param bitlen
	 * @return
	 */
	public int writeFromMSB(int idata, int bitlen) {
		int shift = Integer.SIZE - Byte.SIZE;
		byte bdata = 0;
		int temp = 0, written = 0;
		while(bitlen >= Byte.SIZE) {
			bdata = (byte)((idata >>> shift) & 0xFF);
			temp = write(bdata, 0, Byte.SIZE-1);
			if (temp < 0)
				return written;
			written += temp;
			bitlen -= Byte.SIZE;
			shift -= Byte.SIZE;
		}

		if (bitlen > 0) {
			bdata = (byte)((idata >>> shift) & 0xFF);
			temp = write(bdata, 0, bitlen-1);
			if (temp < 0)
				return written;
			written += temp;
		}

		return written;
	}
	
	/**
	 * write 'bitlen' bits of data to this stream from its LSB.
	 * @param bdata
	 * @param bitlen
	 * @return
	 */
	public int writeFromLSB(byte bdata, int bitlen) {
		return write(bdata, 8-bitlen, 7);
	}

	/**
	 * write 'bitlen' bits of data to this stream from its LSB.
	 * @param idata
	 * @param bitlen
	 * @return
	 */
	public int writeFromLSB(int idata, int bitlen) {
		idata = idata << (Integer.SIZE - bitlen);
		return writeFromMSB(idata, bitlen);
	}
	
	/**
	 * write 'bitlen' bits of data to this stream from its LSB.
	 * @param sdata
	 * @param bitlen
	 * @return
	 */
	public int writeFromLSB(short sdata, int bitlen) {
		sdata = (short)(sdata << (Short.SIZE - bitlen));
		return writeFromLSB(sdata, bitlen);
	}
	
	/**
	 * write 'from~to' bits of data to this stream.
	 * @param bdata
	 * @param from
	 * @param to
	 * @return
	 */
	public int write(byte bdata, int from, int to) {
		if (from < 0 || to < 0 || from > to ) {
			throw new IllegalArgumentException();
		}
		
		if (current_bit_pos >= total_bits)
			return -1;
		
		int pos_in_byte = current_bit_pos & 0x7;
		int nbits_to_write;
		if ((total_bits-current_bit_pos) >= (to-from+1))
			nbits_to_write = to - from + 1;
		else {
			nbits_to_write = total_bits - current_bit_pos;
			to = from + nbits_to_write - 1;
		}

		if (from >= pos_in_byte) {
			byte_array[current_bit_pos>>3] |= 
				((bdata & mask[from][to])<<(from-pos_in_byte));
			current_bit_pos += nbits_to_write;
		}else {
			byte_array[current_bit_pos>>3] |= 
				(((bdata & mask[from][to])& 0xFF) >>> (pos_in_byte-from));

			int written_bits = Math.min(nbits_to_write, 8-pos_in_byte);
			current_bit_pos += written_bits;
			
			if (written_bits < nbits_to_write)
				write(bdata, from+written_bits,to);
		}

		return nbits_to_write;
	}

	/**
	 * write data to this stream.
	 * @param bdata
	 * @return
	 */
	public int write(byte bdata) {
		return write(bdata, 0, 7);
	}
	
	/**
	 * write data to this stream.
	 * @param bdata
	 * @return
	 */
	public int write(byte[] bdata) {
		if (bdata == null)
			throw new NullPointerException();
		
		int written = 0, temp = 0;
		for(int n=0; n < bdata.length; n++) {
			temp = write(bdata[n]);
			if (temp < 0)
				return written;
			written += temp;
		}
		return written;
	}

	/**
	 * write data to this stream.
	 * @param cdata
	 * @return
	 */
	public int write(char cdata) {
		byte b1 = (byte)((cdata & 0xFF00)>>8);
		byte b2 = (byte)(cdata & 0xFF);
		int written1 = 0;
		int written2 = 0;

		written1 = write(b1);
		if (written1 < 0)
			return -1;

		written2 = write(b2);
		if (written2 < 0)
			return written1;

		return written1+written2;
	}

	/**
	 * write data to this stream.
	 * @param sdata
	 * @return
	 */
	public int write(short sdata) {
		byte b1 = (byte)((sdata & 0xFF00)>>8);
		byte b2 = (byte)(sdata & 0xFF);
		int written1 = 0;
		int written2 = 0;

		written1 = write(b1);
		if (written1 < 0)
			return -1;

		written2 = write(b2);
		if (written2 < 0)
			return written1;

		return written1+written2;
	}
	
	/**
	 * write data to this stream.
	 * @param idata
	 * @return
	 */
	public int write(int idata) {
		short s1 = (short)((idata & 0xFFFF0000)>>16);
		short s2 = (short)(idata & 0xFFFF);
		int written1 = 0;
		int written2 = 0;

		written1 = write(s1);
		if (written1 < 0)
			return -1;

		written2 = write(s2);
		if (written2 < 0)
			return written1;

		return written1+written2;
	}

	/**
	 * skip bits
	 * @param skip_bits
	 * @return
	 */
	public int skip(int skip_bits) {
		if ( skip_bits < 0)
			return 0;

		if ( skip_bits > total_bits - current_bit_pos)
			skip_bits = total_bits - current_bit_pos;

		current_bit_pos += skip_bits;
		
		return skip_bits;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		String str = new String();
		str = "(size["+total_bits+"],pos["+current_bit_pos+"]: ";
		
		if (byte_array == null) {
			str += "EMPTY";
		}else {
			int count = 0;
			for(int n=0; n < byte_array.length; n++) {
				for(int s=Byte.SIZE-1; s >= 0; s--) {
					if (((byte_array[n]>>s) & 0x1) != 0)
						str += "1";
					else
						str += "0";
					if( (++count) >= total_bits)
						break;
				}

				if( count >= total_bits)
					break;
				str += "_";
			}
		}

		str += ")";
		return str;
	}
}

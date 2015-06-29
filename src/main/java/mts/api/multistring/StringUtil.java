/**
 * 
 */
package mts.api.multistring;

import java.nio.charset.Charset;

/**
 * @author SungHun Park (dr.superchamp@gmail.com)
 *
 */
public class StringUtil {
	/**
	 * Convert utf16(big-endian) byte array to utf8 byte array
	 * @param utf16
	 * @return
	 */
	public static byte[] UTF16BEToUTF8(byte[] utf16) {
		return (new String(utf16, Charset.forName("UTF-16BE"))).getBytes(Charset.forName("UTF-8"));
	}

	/**
	 * Convert utf8 byte array to utf16(big-endian) byte array 
	 * @param utf16
	 * @return
	 */
	public static byte[] UTF8ToUTF16(byte[] utf16) {
		return (new String(utf16, Charset.forName("UTF-8"))).getBytes(Charset.forName("UTF-16BE"));
	}
	
	/**
	 * Convert String to utf8 byte array
	 * @param str
	 * @return
	 */
	public static byte[] StringToUTF8(String str) {
		return str.getBytes(Charset.forName("UTF-8"));
	}
	
	/**
	 * Convert String to utf16(big-endian) byte array
	 * @param str
	 * @return
	 */
	public static byte[] StringToUTF16BE(String str) {
		return str.getBytes(Charset.forName("UTF-16BE"));
	}
	
}

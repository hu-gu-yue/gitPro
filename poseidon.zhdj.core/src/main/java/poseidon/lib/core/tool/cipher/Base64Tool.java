package poseidon.lib.core.tool.cipher;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Tool {

	public static String enc(String s) {
		if (s == null) {
			return null;
		}
		return (new BASE64Encoder()).encode(s.getBytes());
	}

	public static String dec(String s) {
		if (s == null) {
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b);
		} catch (Exception e) {
			return null;
		}
	}

	public static byte[] decToByte(String key) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(key);
	}

	public static String encFromByte(byte[] key) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(key);
	}
}

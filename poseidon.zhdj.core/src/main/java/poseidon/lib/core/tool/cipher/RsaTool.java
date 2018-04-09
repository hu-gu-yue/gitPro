package poseidon.lib.core.tool.cipher;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import poseidon.lib.core.tool.file.FileTool;

public class RsaTool {

	private final static Logger log = LoggerFactory.getLogger(RsaTool.class);

	public static final String KEY_ALGORITHM = "RSA";
	public static RSAPublicKey publicKey;
	public static RSAPrivateKey privateKey;
	public static String publicKeyText;
	public static String privateKeyText;

	public static void loadPublicKey(String fileName) {
		publicKeyText = FileTool.load(fileName);
	}

	public static void loadPrivateKey(String fileName) {
		privateKeyText = FileTool.load(fileName);
	}

	public static void genKey() throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		publicKey = (RSAPublicKey) keyPair.getPublic();
		privateKey = (RSAPrivateKey) keyPair.getPrivate();
		publicKeyText = Base64Tool.encFromByte(publicKey.getEncoded());
		privateKeyText = Base64Tool.encFromByte(privateKey.getEncoded());

	}

	public static PublicKey getPublicKeyFromBase64(String base64KeyText) throws Exception {
		byte[] keyBytes = Base64Tool.decToByte(base64KeyText);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		return publicKey;
	}

	public static PrivateKey getPrivateKeyFromBase64(String base64KeyText) throws Exception {
		byte[] keyBytes = Base64Tool.decToByte(base64KeyText);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static String enc(String publicKey, String text) throws Exception {
		RSAPublicKey pbk = (RSAPublicKey) getPublicKeyFromBase64(publicKey);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pbk);
		byte[] dataEncode = cipher.doFinal(text.getBytes("UTF-8"));
		String result = Base64Tool.encFromByte(dataEncode);
		return result;
	}

	public static String dec(String privateKeyText, String text) throws Exception {
		RSAPrivateKey pbk = (RSAPrivateKey) getPrivateKeyFromBase64(privateKeyText);
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, pbk);
		byte[] dataDecode = cipher.doFinal(Base64Tool.decToByte(text));
		String originalContent = new String(dataDecode);
		return originalContent;
	}

}

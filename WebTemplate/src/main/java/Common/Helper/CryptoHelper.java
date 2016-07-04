package Common.Helper;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class CryptoHelper {
	private static final String _cipherAlgorithm = "DES";
	private static final String STANDARD_KEY = "shinwoo123!";

	/**
	 * <p>
	 * 주어진 데이터로, 해당 알고리즘에 사용할 비밀키(SecretKey)를 생성한다.
	 * </p>
	 * 
	 * @param algorithm : DES
	 * @return keyData
	 **/
	public static byte[] generateKey() {
		byte[] desKey = new byte[8];
		byte[] bkey = STANDARD_KEY.getBytes();

		if (bkey.length < desKey.length) {
			System.arraycopy(bkey, 0, desKey, 0, bkey.length);
			for (int i = bkey.length; i < desKey.length; i++)
				desKey[i] = 0;
		} else {
			System.arraycopy(bkey, 0, desKey, 0, desKey.length);
		}
		
		return desKey;
	}

	/**
	 * <p>
	 * 주어진 String 데이터로, 암호화를 진행한다.
	 * </p>
	 * 
	 * @param original_Value : String
	 * @return Encrypt String
	 **/
	public static String getEncrypt(String text) {
		String encrypted;

		try {
			SecretKeySpec ks = new SecretKeySpec(generateKey(), _cipherAlgorithm);
			Cipher cipher = Cipher.getInstance(_cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, ks);
			byte[] encryptedBytes = cipher.doFinal(text.getBytes());
			encrypted = new String(Base64Coder.encode(encryptedBytes));
		} catch (Exception e) {
			e.printStackTrace();
			encrypted = text;
		}

		return encrypted;
	}

	/**
	 * <p>
	 * 암호화된 String 데이터로, 복호화를 진행한다.
	 * </p>
	 * 
	 * @param Encrypt : String
	 * @return original_Value String
	 **/
	public static String getDecrypt(String text) {
		String decrypted;

		try {
			SecretKeySpec ks = new SecretKeySpec(generateKey(), _cipherAlgorithm);
			Cipher cipher = Cipher.getInstance(_cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, ks);
			byte[] decryptedBytes = cipher.doFinal(Base64Coder.decode(text));
			decrypted = new String(decryptedBytes);
		} catch (Exception e) {
			decrypted = text;
		}

		return decrypted;
	}
}

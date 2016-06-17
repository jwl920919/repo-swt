package com.shinwootns.common.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptoUtils {

	//region AES Base Key
	private static String _baseAES128Key = "gotlsdnxldpsdptm"; 					// 128 bit (16 bytes)
	private static String _baseAES256Key = "gogotlsdnxldpsdptmtlsdnxldpsdptm";	// 256 bit (32 bytes)
	//endregion

	//region AES 128
	public static String Encode_AES128(String plainText) throws Exception {

		String key = _baseAES128Key;

		if (plainText == null || plainText.isEmpty())
			return "";
		else
			return AES128Encrypt(key, plainText);
	}

	public static String Encode_AES128(String key, String plainText) throws Exception {

		if (key == null || key.isEmpty() || key.length() != 16)
			throw new Exception("Invalid AES128 key.");

		if (plainText == null || plainText.isEmpty())
			return "";

		return AES128Encrypt(key, plainText);
	}

	public static String Decode_AES128(String cipherText) throws Exception {

		String key = _baseAES128Key;

		if (cipherText == null || cipherText.isEmpty())
			return "";

		return AES128Decrypt(key, cipherText);
	}

	public static String Decode_AES128(String key, String cipherText) throws Exception {

		if (key == null || key.isEmpty())
			throw new Exception("Invalid AES128-Key.");

		if (key.length() != 16)
			throw new Exception("Invalid AES128-key length.");

		if (cipherText == null || cipherText.isEmpty())
			return "";

		return AES128Decrypt(key, cipherText);
	}

	private static String AES128Encrypt(String AES128Key, String message) throws Exception {

		byte[] keyBytes = new byte[16];
		byte[] b = AES128Key.getBytes("UTF-8");

		int len = Math.min(keyBytes.length, b.length);

		System.arraycopy(b, 0, keyBytes, 0, len);

		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, skeySpec);
		byte encrypted[] = cipher.doFinal(message.getBytes());

		return Base64.encodeBase64String(encrypted);
	}

	private static String AES128Decrypt(String AES128Key, String encrypted) throws Exception {

		byte[] keyBytes = new byte[16];
		byte[] b = AES128Key.getBytes("UTF-8");

		int len = Math.min(keyBytes.length, b.length);

		System.arraycopy(b, 0, keyBytes, 0, len);

		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, skeySpec);
		byte original[] = cipher.doFinal(Base64.decodeBase64(encrypted));
		String originalString = new String(original);

		return originalString;
	}
	// endregion

	//region AES 256  (Disabled)
	
	// 참고) java.security.InvalidKeyException 발생하는 이유 및 해결 방법
	//  
	// AES256 은 기본적으로 미국 자국 내에서만 이용 가능. 
	// 해결하기 위해서는 아래 2개의 jar 파일을 아래 경로에 덮어쓰기해야 함. 
	//  
	// # local_policy.jar, US_export_policy.jar
	//
	// - {JDK 경로}\jre\lib\security
	// - {JRE 경로}\lib\security
	// 
	// # Jar 다운로드 경로
	// - jdk8 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 8)
	//   http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html
	//
	// - jdk7  (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 7)
	//   http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html
	//
	// - jdk6 (Java Cryptography Extension (JCE) Unlimited Strength Jurisdiction Policy Files 6)
	//   http://www.oracle.com/technetwork/java/javase/downloads/jce-6-download-429243.html
	/*
	public static String Encode_AES256(String plainText) throws Exception {

		String key = baseAES256Key;

		if (plainText == null || plainText.isEmpty())
			return "";
		else
			return AES256Encrypt(key, plainText);
	}

	public static String Encode_AES256(String key, String plainText) throws Exception {

		if (key == null || key.isEmpty() || key.length() != 32)
			throw new Exception("Invalid AES256 key.");

		if (plainText == null || plainText.isEmpty())
			return "";

		return AES256Encrypt(key, plainText);
	}

	public static String Decode_AES256(String cipherText) throws Exception {

		String key = baseAES256Key;

		if (cipherText == null || cipherText.isEmpty())
			return "";

		return AES256Decrypt(key, cipherText);
	}

	public static String Decode_AES256(String key, String cipherText) throws Exception {

		if (key == null || key.isEmpty() || key.length() != 32)
			throw new Exception("Invalid AES256-Key.");

		if (cipherText == null || cipherText.isEmpty())
			return "";

		return AES256Decrypt(key, cipherText);
	}

	private static String AES256Encrypt(String key, String message) throws Exception {

		byte[] keyBytes = new byte[32];
		byte[] b = key.getBytes("UTF-8");

		int len = Math.min(keyBytes.length, b.length);

		System.arraycopy(b, 0, keyBytes, 0, len);

		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, skeySpec);

		byte encrypted[] = cipher.doFinal(message.getBytes());

		return Base64.encodeBase64String(encrypted);
	}

	private static String AES256Decrypt(String key, String encrypted) throws Exception {

		byte[] keyBytes = new byte[32];
		byte[] b = key.getBytes("UTF-8");

		int len = Math.min(keyBytes.length, b.length);

		System.arraycopy(b, 0, keyBytes, 0, len);

		SecretKeySpec skeySpec = new SecretKeySpec(keyBytes, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, skeySpec);

		byte original[] = cipher.doFinal(Base64.decodeBase64(encrypted));

		String originalString = new String(original);

		return originalString;
	}
	*/
	// endregion
}

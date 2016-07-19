package com.shinwootns.ipm;

import org.junit.Test;

import com.shinwootns.common.utils.CryptoUtils;

public class TestCrypt {
	
	@Test
	public void TestCrpt() {
		String data = "infoblox";
		
		try {
			String encrypted = CryptoUtils.Encode_AES128(data);
			
			System.out.println("encrypted: "+encrypted);
			
			String decrypted = CryptoUtils.Decode_AES128(encrypted);
			
			System.out.println("decrypted: "+decrypted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

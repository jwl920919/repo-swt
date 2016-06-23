package test.utils;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.IPRange;
import com.shinwootns.common.utils.LogUtils;
import com.shinwootns.common.utils.NetworkUtils;

public class TestUtils {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
	@Test
	public void testTestUtils() {
		
		BasicConfigurator.configure();
		
		//==========================================
		// NetworkUtils.
		//==========================================

		// ipToNumber
		System.out.println("===========================");
		String ipAddress = "192.168.0.12";
		long ipNum = NetworkUtils.ipToLong(ipAddress);
		String newIP = NetworkUtils.longToIP(ipNum);
		
		System.out.println("IP = " + ipAddress);
		System.out.println("Num = " + ipNum);
		System.out.println("IP = " + newIP);
		
		// Network, netmask
		System.out.println("===========================");
		IPRange iprange = NetworkUtils.getIPRange("192.168.1.0", "255.255.255.128");
		
		System.out.println("Start IP = " + iprange.getStartIPToString());
		System.out.println("end IP = " + iprange.getEndIPToString());
		
		// Network, bits
		System.out.println("===========================");
		iprange = NetworkUtils.getIPRange("192.168.1.0", 24);
		
		System.out.println("Start IP = " + iprange.getStartIPToString());
		System.out.println("end IP = " + iprange.getEndIPToString());
		
		
		//==========================================
		// CrytoUtil.
		//==========================================
		
		try
		{
			String plainText = "This is target message. blabla~~~";
			
			// AES128
			String AES128Key = "1234567890123456";					//128 bit (16 bytes)
			
			System.out.println("===========================");
			System.out.println("AES128");
			System.out.println("===========================");
			System.out.println("PLAIN   : " +plainText);
			String cryptText = CryptoUtils.Encode_AES128(AES128Key, plainText);
			System.out.println("CRYPT   : " + cryptText);
			String restoreText = CryptoUtils.Decode_AES128(AES128Key, cryptText);
			System.out.println("RESTORE : " + restoreText);
			
			// AES256
			// InvalidKeyException 예외 발생 시, CyrptoUtils 주석 참고
			/*
			String AES256Key = "12345678901234567890123456789012";	//256 bit (32 bytes)
			
			System.out.println("===========================");
			System.out.println("AES256");
			System.out.println("===========================");
			System.out.println("PLAIN   : " +plainText);
			cryptText = CryptoUtils.Encode_AES256(AES256Key, plainText);
			System.out.println("CRYPT   : " + cryptText);
			restoreText = CryptoUtils.Decode_AES256(AES256Key, cryptText);
			System.out.println("RESTORE : " + restoreText);
			*/
		}
		catch(Exception ex)
		{
			LogUtils.WriteLog(_logger, Level.ERROR, ex);
		}
	}

}

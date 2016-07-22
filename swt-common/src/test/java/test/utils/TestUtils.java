package test.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.shinwootns.common.utils.CollectionUtils;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.IPv4Range;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.common.utils.NetworkUtils.InterfaceIP;
import com.shinwootns.common.utils.NetworkUtils.InterfaceInfo;

public class TestUtils {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
	
	/*
	@Test
	public void testNetworkUtilsIPv4() {

		// ipToNumber
		System.out.println("===========================");
		String ipAddress = "192.168.0.12";
		long ipNum = NetworkUtils.IPv4ToLong(ipAddress);
		String newIP = NetworkUtils.longToIPv4(ipNum);

		System.out.println("IP = " + ipAddress);
		System.out.println("Num = " + ipNum);
		System.out.println("IP = " + newIP);

		// Network, netmask
		System.out.println("===========================");
		IPv4Range iprange = NetworkUtils.getIPV4Range("192.168.1.0", "255.255.255.128");

		System.out.println("Start IP = " + iprange.getStartIPToString());
		System.out.println("end IP = " + iprange.getEndIPToString());

		// Network, bits
		System.out.println("===========================");
		iprange = NetworkUtils.getIPV4Range("192.168.1.0", 24);

		System.out.println("Start IP = " + iprange.getStartIPToString());
		System.out.println("end IP = " + iprange.getEndIPToString());

	}
	*/

	/*
	@Test
	public void testNetworkUtilsIPv6() {

		System.out.println("===========================");
		List<InterfaceInfo> listInterface = NetworkUtils.getInterfaceInfo();

		for(InterfaceInfo inf : listInterface) {
			
			for(InterfaceIP ipInfo : inf.listIPAddr) {
				System.out.println( String.format("[%2d] %-6s %-20s %-50s: [%s] %s"
						, inf.index
						, inf.name
						, inf.macAddr
						, inf.displayName
						, ipInfo.ipType
						, ipInfo.ipaddr) );
			}
		}
		System.out.println("===========================");
	}
	*/


	/*
	@Test
	public void testCryptoUtil() {

		try {
			String plainText = "This is target message. blabla~~~";

			// AES128
			String AES128Key = "1234567890123456"; // 128 bit (16 bytes)

			System.out.println("===========================");
			System.out.println("AES128");
			System.out.println("===========================");
			System.out.println("PLAIN   : " + plainText);
			String cryptText = CryptoUtils.Encode_AES128(AES128Key, plainText);
			System.out.println("CRYPT   : " + cryptText);
			String restoreText = CryptoUtils.Decode_AES128(AES128Key, cryptText);
			System.out.println("RESTORE : " + restoreText);

			// AES256 - InvalidKeyException 예외 발생 시, CyrptoUtils 주석 참고
			
//			String AES256Key = "12345678901234567890123456789012"; //256 bit (32 bytes)
//			
//			System.out.println("===========================");
//			System.out.println("AES256");
//			System.out.println("===========================");
//			System.out.println("PLAIN   : " +plainText); cryptText =
//			CryptoUtils.Encode_AES256(AES256Key, plainText);
//			System.out.println("CRYPT   : " + cryptText); restoreText =
//			CryptoUtils.Decode_AES256(AES256Key, cryptText);
//			System.out.println("RESTORE : " + restoreText);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}*/
	
	@Test
	public void testCollectionUtils() {
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("A", 93);
		map.put("B", 83);
		map.put("C", 98);
		map.put("D", 73);
		map.put("E", 100);
		map.put("F", 88);
		
		LinkedHashMap<String, Integer> linkedMap = CollectionUtils.sortByValue(map);
		
		System.out.println(linkedMap.toString());
		
		LinkedHashMap<String, Integer> linkedMap2 = CollectionUtils.sortByValueDesc(map);
		
		System.out.println(linkedMap2.toString());
		
	}

}

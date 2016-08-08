package test.utils;

import java.math.BigInteger;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.utils.CollectionUtils;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.NetworkUtils;
import com.shinwootns.common.utils.ip.IPAddr;
import com.shinwootns.common.utils.ip.IPNetwork;
import com.shinwootns.common.utils.ip.ipv6.IPv6AddressRange;
import com.shinwootns.common.utils.ip.ipv6.IPv6Network;

public class TestUtils {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	/*
	@Test
	public void testNetworkUtilsIPv4() {

		System.out.println("--------------------------------------------");
		
		try {
			
			System.out.println(new IPAddr("192.168.1.50"));					// IPv4
			System.out.println(new IPAddr("fe80::226:2dff:fefa:cd1f"));		// IPv6
			System.out.println(new IPAddr("::ffff:192.168.0.1"));			// IPv4 (Mapped IPv6)
			
			System.out.println("--------------------------------------------");
			
			// IPv4 network
			String data = "192.168.0.0/24";
			IPNetwork network = new IPNetwork(data);
			
			System.out.println(data);
			System.out.println("NETWORK : " + network);
			System.out.println("START   : " + network.getStartIP());
			System.out.println("END     : " +network.getEndIP());
			System.out.println("COUNT   : " +network.getIPCount());
			
			System.out.println("--------------------------------------------");
			
			// IPv6 network
			data = "2001:0db8:85a3::8a2e:0370:7334/24";
			network = new IPNetwork(data);
			
			System.out.println(data);
			System.out.println("NETWORK : " + network);
			System.out.println("START   : " + network.getStartIP());
			System.out.println("END     : " +network.getEndIP());
			System.out.println("COUNT   : " +network.getIPCount());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	    
	
//		// ipToNumber
//		System.out.println("===========================");
//		String ipAddress = "192.168.0.12";
//		long ipNum = NetworkUtils.IPv4ToLong(ipAddress);
//		String newIP = NetworkUtils.longToIPv4(ipNum);
//
//		System.out.println("IP = " + ipAddress);
//		System.out.println("Num = " + ipNum);
//		System.out.println("IP = " + newIP);
//
//		// Network, netmask
//		System.out.println("===========================");
//		IPv4Range iprange = NetworkUtils.getIPV4Range("192.168.1.0", "255.255.255.128");
//
//		System.out.println("Start IP = " + iprange.getStartIPToString());
//		System.out.println("end IP = " + iprange.getEndIPToString());
//
//		// Network, bits
//		System.out.println("===========================");
//		iprange = NetworkUtils.getIPV4Range("192.168.1.0", 24);
//
//		System.out.println("Start IP = " + iprange.getStartIPToString());
//		System.out.println("end IP = " + iprange.getEndIPToString());
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
	
	/*
	@Test
	public void testCollectionUtils() throws Exception {
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("A", 93);
		map.put("B", 83);
		map.put("C", 98);
		map.put("D", 73);
		map.put("E", 100);
		map.put("F", 88);
		
		// Sort Map Asc
		LinkedHashMap<String, Integer> linkedMap = CollectionUtils.sortMapByValue(map);
		
		System.out.println(linkedMap.toString());
		
		
		// Sort Map Desc
		LinkedHashMap<String, Integer> linkedMap2 = CollectionUtils.sortMapByValueDesc(map);
		
		System.out.println(linkedMap2.toString());
		
	}
	 */
}

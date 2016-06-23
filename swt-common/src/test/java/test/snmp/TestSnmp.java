package test.snmp;

import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;

public class TestSnmp {

	private final Logger _logger = Logger.getLogger(this.getClass());

	@Test
	public void testSnmp() {
		
		BasicConfigurator.configure();

		_logger.info("Test Start...");

		try {

			//SnmpUtil snmpUtil = new SnmpUtil("127.0.0.1", "public");
			SnmpUtil snmpUtil = new SnmpUtil("192.168.1.11", "public");
			//SnmpUtil snmpUtil = new SnmpUtil("192.168.1.80", "public");

			// ===================================================================
			// SnmpGet
			System.out.println("[GET]------------------------------------------------------");

			SnmpResult getValue = snmpUtil.snmpGet(2, ".1.3.6.1.2.1.1.2.0", 1000, 3);

			System.out.println(
					getValue.getOidString() + " : " + getValue.getSyntaxString() + " = " + getValue.getValueString());

			// ===================================================================
			// SnmpWalk#1 - System
			System.out.println("[WALK]------------------------------------------------------");

			LinkedList<SnmpResult> listResult = snmpUtil.snmpWalk(2, ".1.3.6.1.2.1.1", 1000, 3);

			// Result
			for (SnmpResult result : listResult) {
				System.out.println(
						result.getOidString() + " : " + result.getSyntaxString() + " = " + result.getValueString());
			}

			listResult.clear();

			// ===================================================================
			// SnmpWalk#2 - Interface
			System.out.println("[WALK]------------------------------------------------------");
			
			listResult = snmpUtil.snmpWalk(1, ".1.3.6.1.2.1.2", 1000, 3);

			// Result
			for (SnmpResult result : listResult) {

				// PhyAddress -> HexString
				if (result.getOidString().startsWith("1.3.6.1.2.1.2.2.1.6")) {

					System.out.println(result.getOidString() + " : " + result.getSyntaxString() + " = "
							+ result.getValueHexString(':', 6));
				} else {

					// Others
					System.out.println(result.getOidString() + " : " + result.getSyntaxString() + " = "
							+ result.getValueString("EUC-KR"));
				}
			}
			System.out.println("------------------------------------------------------");

			listResult.clear();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			_logger.error(e.getMessage(), e);
		}

		_logger.info("Test End.");
	}

}

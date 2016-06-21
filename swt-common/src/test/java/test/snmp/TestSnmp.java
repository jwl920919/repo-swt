package test.snmp;

import java.util.LinkedList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.shinwootns.common.snmp.SnmpResult;
import com.shinwootns.common.snmp.SnmpUtil;

public class TestSnmp {

	private static final Logger logger = Logger.getLogger(TestSnmp.class);

	public static void main(String[] args) {
		
		BasicConfigurator.configure();

		logger.info("Test Start...");

		try {

			//SnmpUtil snmpUtil = new SnmpUtil("127.0.0.1", "public", logger);
			//SnmpUtil snmpUtil = new SnmpUtil("192.168.1.28", "public",logger);
			SnmpUtil snmpUtil = new SnmpUtil("192.168.1.80", "public", logger);

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
			logger.error(e.getMessage(), e);
		}

		logger.info("Test End.");
	}

}

package com.shinwootns.data.status;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.LinkedList;

public class LeaseIpStatus {

	public LinkedList<IpStatus> ASSIGNMENTIPSTATUS = new LinkedList<IpStatus>();  
	
	public void addIPStatus(BigDecimal total, BigDecimal staticip, BigDecimal leaseip, BigDecimal unusedip) {
		IpStatus ipStatus = new IpStatus();
		ipStatus.total = total;
		ipStatus.staticip = staticip;
		ipStatus.leaseip = leaseip;
		ipStatus.unusedip = unusedip;
		
		ASSIGNMENTIPSTATUS.add(ipStatus);
	}
	
	public class IpStatus {
		public BigDecimal total;
		public BigDecimal staticip;
		public BigDecimal leaseip;
		public BigDecimal unusedip;
	}
}

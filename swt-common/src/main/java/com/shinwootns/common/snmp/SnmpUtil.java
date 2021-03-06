package com.shinwootns.common.snmp;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TreeEvent;
import org.snmp4j.util.TreeUtils;

public class SnmpUtil {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	private int _port = 161;
	private String _ip = "127.0.0.1";
	private String _community = "public";

	public SnmpUtil(String ip, String community) {
		this._ip = ip;
		this._community = community;
	}

	// Default Port = 161
	void setPort(int port) {
		this._port = port;
	}

	void setCommunity(String community) {
		this._community = community;
	}
	
	//region SnmpGet
	public SnmpResult snmpGet(int version, String oidString, int timeout, int retryCnt) {

		Snmp snmp = null;
		TransportMapping transport = null;
		SnmpResult result = null;
		PDU pdu = null;

		try {
			Address targetAddress = GenericAddress.parse("udp:" + _ip + "/" + _port);

			CommunityTarget target = new CommunityTarget();
			// Address
			target.setAddress(targetAddress);
			// Community
			target.setCommunity(new OctetString(_community));
			// Retry Count
			target.setRetries(retryCnt);
			// Time-Out
			target.setTimeout(timeout);

			// SNMP Version
			if (version == 3)
				target.setVersion(SnmpConstants.version3);
			else if (version == 2)
				target.setVersion(SnmpConstants.version2c);
			else
				target.setVersion(SnmpConstants.version1);

			// OID
			OID oid = null;
			try {
				oid = new OID(oidString);
			} catch (RuntimeException ex) {
				// System.out.println("OID is not specified correctly.");
				// System.exit(1);
			}

			// Create PDU object
			pdu = new PDU();
			pdu.add(new VariableBinding(oid));
			pdu.setType(PDU.GET);
			pdu.setRequestID(new Integer32(1));
			
			transport = new DefaultUdpTransportMapping();

			// Create Snmp
			snmp = new Snmp(transport);

			// Listen
			transport.listen();

			// Send Request
			ResponseEvent response = snmp.get(pdu, target);

			// Process Response
			if (response != null) {

				// System.out.println("Got Response from Agent");
				PDU responsePDU = response.getResponse();

				if (responsePDU != null) {

					int errorStatus = responsePDU.getErrorStatus();
					// int errorIndex = responsePDU.getErrorIndex();
					// String errorStatusText =
					// responsePDU.getErrorStatusText();

					if (errorStatus == PDU.noError) {

						//VariableBinding[] varBindings = (VariableBinding[]) responsePDU.getVariableBindings();
						//VariableBinding varBinding = responsePDU.getVariableBindings();
						Vector vec = responsePDU.getVariableBindings();

						if (vec != null) {
							for (Object obj : vec) {
								if (obj != null) {
									VariableBinding var = (VariableBinding) obj; 
									result = new SnmpResult(var);
									break;
								}
							}
						}
						
						responsePDU.clear();
					}
				} else {
					// System.out.println("Error: Response PDU is null");
				}
			}
		} catch (Exception e) {
			if (_logger != null)
				_logger.debug(e.getMessage(), e);
			else
				e.printStackTrace();
		} finally {
			
			if (pdu != null) {
				try {
					pdu.clear();
				} catch (Exception e) {}
				finally {
				}
			}
			
			if (snmp != null)
			{
				try {
					snmp.close();
				} catch (Exception e) {}
				finally {
					snmp = null;
				}
			}
			
			if (transport != null) {
				try {
					transport.close();
				} catch (Exception e) {}
				finally {
					transport = null;
				}
			}
		}
		

		return result;
	}
	//endregion

	//region SnmpWalk
	public LinkedList<SnmpResult> snmpWalk(int version, String oidString, int timeout, int retryCnt) {

		DefaultUdpTransportMapping transport = null;
		Snmp snmp = null;
		
		LinkedList<SnmpResult> listResult = new LinkedList<SnmpResult>();

		try {
			Address targetAddress = GenericAddress.parse("udp:" + _ip + "/" + _port);

			CommunityTarget target = new CommunityTarget();
			// Address
			target.setAddress(targetAddress);
			// Community
			target.setCommunity(new OctetString(_community));
			// Retry Count
			target.setRetries(retryCnt);
			// Time-Out
			target.setTimeout(timeout);

			// SNMP Version
			if (version == 3)
				target.setVersion(SnmpConstants.version3);
			else if (version == 2)
				target.setVersion(SnmpConstants.version2c);
			else
				target.setVersion(SnmpConstants.version1);

			// OID
			OID oid = null;
			try {
				oid = new OID(oidString);
			} catch (RuntimeException ex) {
				// System.out.println("OID is not specified correctly.");
				// System.exit(1);
			}

			// Create Transport
			transport = new DefaultUdpTransportMapping();

			// Create Snmp
			snmp = new Snmp(transport);

			// Listen
			transport.listen();
			
			TreeUtils treeUtils = new TreeUtils(snmp, new DefaultPDUFactory());
			
			// Get Subtree
			List<TreeEvent> events = treeUtils.getSubtree(target, oid);

			if (events == null || events.size() == 0) {
				System.out.println("No result returned.");
				System.exit(1);
			}

			// Get results
			for (TreeEvent event : events) {

				if (event == null)
					continue;

				if (event.isError() == false) {

					VariableBinding[] varBindings = event.getVariableBindings();

					if (varBindings == null) {
						// System.out.println("No result returned.");
						continue;
					}

					for (VariableBinding var : varBindings) {

						if (var != null)
							listResult.add(new SnmpResult(var));
					}
				} else {
					// System.err.println("oid [" + oid + "] " +
					// event.getErrorMessage());
				}
			}

			// Close
			snmp.close();

		} catch (Exception e) {
			if (_logger != null)
				_logger.debug(e.getMessage(), e);
			else
				e.printStackTrace();
		} finally {
			if (snmp != null)
			{
				try {
					snmp.close();
				} catch (Exception e) {}
				finally {
					snmp = null;
				}
			}
			
			if (transport != null) {
				try {
					transport.close();
				} catch (Exception e) {}
				finally {
					transport = null;
				}
			}
		}

		return listResult;
	}
	//endregion
}

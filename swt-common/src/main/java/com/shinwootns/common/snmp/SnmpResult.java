package com.shinwootns.common.snmp;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.SMIConstants;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.ibm.icu.text.CharsetDetector;
import com.ibm.icu.text.CharsetMatch;

public class SnmpResult {
	
	private final Logger _logger = LoggerFactory.getLogger(getClass());

	private VariableBinding _varBind = null;

	public SnmpResult(VariableBinding varBindings) {
		_varBind = varBindings;
	}

	//region getOID
	public String getOidString() {
		if (_varBind == null)
			return "";
		
		return _varBind.getOid().toString();
	}

	public OID getOid() {
		if (_varBind == null)
			return null;

		return _varBind.getOid();
	}
	//endregion

	//region getSyntax
	//
	// "OCTET STRING","OBJECT
	// IDENTIFIER","TimeTicks","Integer32","Gauge","Counter", ...
	public String getSyntaxString() {
		if (_varBind == null)
			return "";

		return _varBind.getVariable().getSyntaxString().toString();
	}

	public int getSyntaxNumber() {
		return _varBind.getVariable().getSyntax();
	}
	//endregion

	//region getValue - String
	public String getValueString() {

		try {

			Variable value = _varBind.getVariable();

			if (value.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING
					|| value.getSyntax() == SMIConstants.SYNTAX_BITS) {


				OctetString ostring = (OctetString) value;

				// To Bytes
				byte[] bytes = ostring.toByteArray();
				
				// Charset Detector
				CharsetDetector dt = new CharsetDetector();
				dt.setText(bytes);
				
				// Detect
				CharsetMatch mc = dt.detect();
				
				if (mc == null)
					return value.toString();

				String sValue = mc.getString();

				// Remove Null String
				sValue = sValue.replace("\0", "");

				return sValue;

			} else {
				return value.toString();
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}
	
	public String getValueString(String encoding) {

		try {

			Variable value = _varBind.getVariable();

			if (value.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING
					|| value.getSyntax() == SMIConstants.SYNTAX_BITS) {


				OctetString ostring = (OctetString) value;

				// To Bytes
				byte[] bytes = ostring.toByteArray();
				
				// Change Encoding
				String sValue = new String(bytes, encoding);
				
				// Remove Null String
				sValue = sValue.replace("\0", "");

				return sValue;

			} else {
				return value.toString();
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	} 
	
	public static boolean isPureAscii(String v) {
	    byte bytearray []  = v.getBytes();
	    CharsetDecoder d = Charset.forName("US-ASCII").newDecoder();
	    try {
	      CharBuffer r = d.decode(ByteBuffer.wrap(bytearray));
	      r.toString();
	    }
	    catch(CharacterCodingException e) {
	      return false;
	    }
	    return true;
	  }

	public String getValueHexString(char seperator) {

		try {
			Variable value = _varBind.getVariable();

			if (value.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING
					|| value.getSyntax() == SMIConstants.SYNTAX_BITS) {

				OctetString ostring = (OctetString) value;
				return ostring.toHexString(seperator);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}

	public String getValueHexString(char seperator, int count) {

		try {
			Variable value = _varBind.getVariable();

			if (value.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING
					|| value.getSyntax() == SMIConstants.SYNTAX_BITS) {

				OctetString ostring = (OctetString) value;
				return ostring.substring(0, Math.min(count, ostring.length())).toHexString(seperator);
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return null;
	}
	//endregion

	//region getValue - Number
	public Double getValueNumber() {
		if (_varBind == null)
			return null;

		try {

			Variable value = _varBind.getVariable();

			if (value == null)
				return null;

			// 32 bit
			if (value.getSyntax() == SMIConstants.SYNTAX_INTEGER || value.getSyntax() == SMIConstants.SYNTAX_INTEGER32
					|| value.getSyntax() == SMIConstants.SYNTAX_COUNTER32
					|| value.getSyntax() == SMIConstants.SYNTAX_GAUGE32
					|| value.getSyntax() == SMIConstants.SYNTAX_TIMETICKS) {
				return (double) value.toLong();
			}
			// 64 bit
			else if (value.getSyntax() == SMIConstants.SYNTAX_COUNTER64) {
				return (double) value.toLong();
			}
			// Octet String
			else if (value.getSyntax() == SMIConstants.SYNTAX_OCTET_STRING
					|| value.getSyntax() == SMIConstants.SYNTAX_BITS) {

				String sValue = value.toString();
				return Double.parseDouble(sValue);
			}

		} catch (Exception ex) {
			// System.out.println(ex.getMessage());
		}

		return null;
	}
	//endregion
}

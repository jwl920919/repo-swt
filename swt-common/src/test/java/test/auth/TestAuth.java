package test.auth;

import java.util.Vector;

import org.apache.log4j.BasicConfigurator;
import org.junit.Test;

import com.shinwootns.common.auth.UserAuthenticatorLDAP;

public class TestAuth {

	@Test
	public void TestLdap() {
		
		System.out.println("Test Ldap... Start");
		
		UserAuthenticatorLDAP ldap = new UserAuthenticatorLDAP();
		
		try {
			
			BasicConfigurator.configure();
			
			String host = "192.168.1.80";
			int port = 389;
			String bindDn="admin", bindPw="shinwoo123!";
			String baseDN = "DC=shinwootns-dev,DC=com";
			String groupClass = "";
			
			// Bind check
			if ( ldap.LdapBindCheck(host, port, false, "active-directory", bindDn, bindPw) )  {
				System.out.println("LdapBindCheck().... ok");
				
				
				// Get Base info
				Vector vec = ldap.LdapRetrieveBase(host, port, false, "", bindDn, bindPw);
				
				System.out.println(vec.toString());
				
				//ldap.LdapRetrieveGroup(host, port, false, "active-directory", bindDn, bindPw, baseDN, groupClass)
			}
			else {
				System.out.println("LdapBindCheck().... fail");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Test Ldap... End");
	}
}

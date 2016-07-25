package test.auth;

import java.util.Hashtable;
import java.util.Vector;

import org.apache.log4j.BasicConfigurator;
import com.shinwootns.common.auth.LdapGroup;
import com.shinwootns.common.auth.LdapUserGroupAttr;
import com.shinwootns.common.auth.AuthCheckerLDAP;

public class TestAuth {

	/*
	@Test
	public void TestLdap() {
		
		AuthCheckerLDAP ldap = new AuthCheckerLDAP();
		
		try {
			
			BasicConfigurator.configure();
			
			String host = "192.168.1.80";
			int port = 389;
			boolean ssl = false;
			String bindDn = "admin";
			String bindPw = "shinwoo123!";
			
			// Bind check
			if ( ldap.connect(host, port, ssl, bindDn, bindPw) )  {
				
				// Set BaseDn
				ldap.setBaseDn("DC=shinwootns-dev,DC=com");
				
				// UserGroup Attribute
				LdapUserGroupAttr userGroupAttr = new LdapUserGroupAttr();
				
				userGroupAttr.setUserClass("person");
				userGroupAttr.setUserAttr("sAMAccountName");
				userGroupAttr.setUserNameAttr("displayName");
				userGroupAttr.setUserMemberOfAttr("memberOf");
				
				userGroupAttr.setGroupClass("group");
				userGroupAttr.setGroupMemberAttr("name");
				userGroupAttr.setGroupMemberAttr("member");
				
				ldap.setUserGroupAttr(userGroupAttr);
				
				// Get Naming Contexts
				Vector vec = ldap.getNamingContexts();
				
				System.out.println("\n### getNamingContexts");
				for(int i=0; i<vec.size();i++)
					System.out.println(vec.get(i).toString());
				
				vec.clear();
				
				// Get Group
				String baseDN = "DC=shinwootns-dev,DC=com";
				String groupClass = "group";
				Hashtable<String,LdapGroup> groups = ldap.getLdapGroups(true, baseDN, groupClass);
				
				System.out.println("\n### getLdapGroup");
				for(LdapGroup group : groups.values())
					System.out.println(group.toString());
				
				vec.clear();
				
				// Check authentication
				System.out.println("\n### checkUserAuth");
				
				// valid user
				String userid1 = "admin";
				String passwd1 = "shinwoo123!";
				boolean result1 = ldap.checkUserAuth(userid1, passwd1);
				
				
				// invalid password
				String userid2 = "admin";
				String passwd2 = "12341234";
				boolean result2 = ldap.checkUserAuth(userid2, passwd2);
				
				
				// invalid user
				String userid3 = "unknown";
				String passwd3 = "12341234";
				boolean result3 = ldap.checkUserAuth(userid3, passwd3);
				
				System.out.println("\n=> Test valid user: id="+userid1+", result=" + result1);
				System.out.println("=> Test invalid password: id="+userid2+", result=" + result2);
				System.out.println("=> Test not exist user: id="+userid3+", result=" + result3);
				
			}
			else {
				System.out.println("LdapBindCheck.... failed");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ldap.close();
	}
	*/
}

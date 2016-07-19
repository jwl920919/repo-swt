package com.shinwootns.common.auth;

import com.sun.security.auth.module.Krb5LoginModule;

import java.io.File;
import java.io.FileWriter;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.*;
import javax.net.ssl.*;

import org.apache.log4j.Logger;

public class UserAuthenticatorLDAP {

	private final static Logger _logger = Logger.getLogger(UserAuthenticatorLDAP.class);
	
	private static final int ADS_GROUP_TYPE_GLOBAL_GROUP = 2;
	private static final int ADS_GROUP_TYPE_DOMAIN_LOCAL_GROUP = 4;
	private static final int ADS_GROUP_TYPE_UNIVERSAL_GROUP = 8;
	private static final int ADS_GROUP_TYPE_SECURITY_ENABLED = 0x80000000;
	
	private static boolean s_sslInitialized = false;
	
	private String _host;
	private Integer _port;
	private Boolean _ssl;
	private String _type;
	private String _bindDn;
	private String _bindPw;
	private String _baseDn;
	private String _userClass;
	private String _userAttr;
	private String _userNameAttr;
	private String _userMemberofAttr;
	private String _groupClass;
	private String _groupNameAttr;
	private String _groupMemberAttr;
	private Boolean _kerberos;
	private String _kerberosHost;
	private Integer _kerberosPort;
	private String _kerberosRealm;
	
	Hashtable _groups;
	
	public String GetType() {
		return "ldap";
	}
	
	public int GetId() {
		return 2;
	}

	public UserAuthenticatorLDAP(Integer id, String name)
    {
        //super(id, name);
        _groups = new Hashtable();
    }

	/*
	public boolean LoadConfiguration(Connection conn)
        throws Exception
    {
        boolean result = false;
        Statement stmt = null;
        ResultSet rs = null;
        
        //_logger.info((new StringBuilder("ldap : load, id=")).append(super.GetId()).toString());
        
        try
        {
            stmt = conn.createStatement();
            
            
            StringBuilder sb = new StringBuilder();
            
            sb.append("select host,port,ssl,type,bind_dn,bind_pw,base_dn,user_class");
            sb.append(" ,user_attr,user_name_attr,user_memberof_attr");
            sb.append(" ,group_class,group_name_attr,group_member_attr");
            sb.append(" ,kerberos,kerberos_host,kerberos_port,kerberos_realm");
            sb.append(" from tb_auth_ldap_member where id=") ;
            sb.append(GetId());
            
            rs = stmt.executeQuery( sb.toString() );
            
            if(rs.next())
            {
                _host = rs.getString("host");
                _port = Integer.valueOf(rs.getInt("port"));
                _ssl = Boolean.valueOf(rs.getBoolean("ssl"));
                _type = rs.getString("type");
                _bindDn = rs.getString("bind_dn");
                //_bindPw = Password.Decode(rs.getString("bind_pw"));
                _baseDn = rs.getString("base_dn");
                _userClass = rs.getString("user_class");
                _userAttr = rs.getString("user_attr");
                _userNameAttr = rs.getString("user_name_attr");
                _userMemberofAttr = rs.getString("user_memberof_attr");
                _groupClass = rs.getString("group_class");
                _groupNameAttr = rs.getString("group_name_attr");
                _groupMemberAttr = rs.getString("group_member_attr");
                _kerberos = Boolean.valueOf(rs.getBoolean("kerberos"));
                _kerberosHost = rs.getString("kerberos_host");
                _kerberosPort = Integer.valueOf(rs.getInt("kerberos_port"));
                _kerberosRealm = rs.getString("kerberos_realm");
                rs.close();
                
                for(rs = stmt.executeQuery( 
                		(new StringBuilder("select group_dn,group_name from tb_auth_ldap_group where id=")).append(GetId()).toString())
                		; rs.next(); )
                {
                	_groups.put(rs.getString("group_dn"), rs.getString("group_name"));			
                }
                
                result = true;
                
                _logger.info((new StringBuilder("ldap : ")).append(_host).append(", ").append(_port).append(", ").append(_ssl).append(", ").append(_type).append(", ").append(_bindDn).append(", ").append(_baseDn).append(", ").append(_userClass).append(", ").append(_userAttr).append(", ").append(_userNameAttr).append(", ").append(_userMemberofAttr).append(", ").append(_groupClass).append(", ").append(_groupNameAttr).append(", ").append(_groupMemberAttr).append(", ").append(_kerberos).append(", ").append(_kerberosHost).append(", ").append(_kerberosPort).append(", ").append(_kerberosRealm).toString());
                _logger.info((new StringBuilder("ldap groups : ")).append(_groups.size()).toString());
            } else
            {
                _logger.info((new StringBuilder("ldap : not found record, id=")).append(GetId()).toString());
            }
            //break MISSING_BLOCK_LABEL_805;
        }
        catch(Exception e)
        {
            _logger.error(e.getMessage(), e);
        }
        
        if(rs != null)
            try
            {
                rs.close();
            }
            catch(Exception e)
            {
                _logger.error(e.getMessage(), e);
            }
        if(stmt != null)
            try
            {
                stmt.close();
            }
            catch(Exception e)
            {
                _logger.error(e.getMessage(), e);
            }
        //break MISSING_BLOCK_LABEL_859;
        //Exception exception;
        //exception;
        
        
        try
        {
        	if(rs != null)
        		rs.close();
        }
        catch(Exception e) {
            _logger.error(e.getMessage(), e);
        }
    
        try
        {
        	if(stmt != null)
        		stmt.close();
        }
        catch(Exception e) {
            _logger.error(e.getMessage());
        }
        //throw exception;
    
        try
        {
        	if(rs != null)
        		rs.close();
        }
        catch(Exception e) {
            _logger.error(e.getMessage(), e);
        }
    
        try
        {
        	if(stmt != null)
        		stmt.close();
        }
        catch(Exception e) {
            _logger.error(e.getMessage(), e);
        }
        
        return result;
    }
    */
	
	public boolean Authenticate(String user_id, String pass_wd) throws Exception {
		
		_logger.info("authenticate : " + user_id);
		
		boolean result = false;
		
		if (user_id == null || user_id.length() == 0 || pass_wd == null || pass_wd.length() == 0) {
			_logger.error("userid or passwd is null");
			return false;
		}
		
		LdapContext ldapContext = Connect(_host, _port, _ssl, _bindDn, _bindPw);
		
		if (ldapContext == null)
			throw new Exception("unable to connect to the ldap server");
		
		
		try {
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(2);
			searchControls.setReturningAttributes(new String[] { _userMemberofAttr });
			
			String filterExpr = String.format("(&(objectClass=%s)(%s=%s))", _userClass, _userAttr, user_id);
			
			NamingEnumeration searchResults = ldapContext.search(_baseDn, filterExpr,searchControls);
			
			while (searchResults.hasMoreElements()) {
				
				SearchResult searchResult = (SearchResult) searchResults.next();
				
				String user_dn = searchResult.getNameInNamespace();
				
				_logger.info((new StringBuilder("find user : ")).append(user_dn).toString());
				
				if (_groups.size() > 0) {
					
					Attribute memberOfAttribute = searchResult.getAttributes().get(_userMemberofAttr);
					
					if (memberOfAttribute == null) {
						_logger.info((new StringBuilder("invalid memberOf attribute : ")).append(user_dn).toString());
						continue;
					}
					
					boolean isGroupMember = false;
					String memberOf;
					
					NamingEnumeration memberOfElements = memberOfAttribute.getAll();
					
					while(memberOfElements.hasMoreElements()) {
						
						memberOf = (String)memberOfElements.next();
						
						_logger.info((new StringBuilder("unmatch memberOf attribute : ")).append(memberOf).toString());
						
						if (!_groups.containsKey(memberOf))
							continue;
						
						isGroupMember = true;
						break;
					}

					if (!isGroupMember)
						continue;
				}
				
				if (_kerberos.booleanValue()) {
					result = KerberosAuthenticate(_kerberosHost, _kerberosPort.intValue(), _kerberosRealm, user_id, pass_wd);
				}
				else {
					result = SimpleAuthenticate(_host, _port.intValue(), _ssl, user_dn, pass_wd);
				}
				
				if (result) {
					_logger.info((new StringBuilder("authentication success : ")).append(user_dn).toString());
				}
				else {
					_logger.info((new StringBuilder("authentication failure : ")).append(user_dn).toString());
				}
				
				if (result)
					break;
			}
			
		} catch (PartialResultException e) {
			_logger.info("Partial Result");
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			throw e;
		}
		
		if (ldapContext != null) {
			try {
				ldapContext.close();
			} catch (Exception e) {
				_logger.error(e.getMessage(), e);
			}
		}
		
		return result;
	}

	public boolean Authenticate(String userid, String passwd, String macAddress) throws Exception {
		return Authenticate(userid, passwd);
	}

	private static LdapContext Connect(String host, Integer port, Boolean ssl, String bindDn, String bindPw) {
		
		LdapContext ldapContext = null;
		
		try {
			Hashtable<String, String> properties = new Hashtable<String, String>();
			
			properties.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			properties.put("java.naming.security.authentication", "simple");
			
			// URL
			properties.put("java.naming.provider.url", String.format("ldaps://%s:%d/", host, port));
			
			// SSL
			if (ssl.booleanValue()) {

				properties.put("java.naming.security.protocol", "ssl");
				
				// Initialize SSL
				InitializeSSL();
			}
			
			properties.put("java.naming.security.principal", bindDn);
			properties.put("java.naming.security.credentials", bindPw);
			
			ldapContext = new InitialLdapContext(properties, null);
			
		} catch (AuthenticationException e) {
			_logger.error(e);
		} catch (NamingException e) {
			_logger.error(e);
		} catch (Exception e) {
			_logger.error(e);
		}
		
		return ldapContext;
	}

	public static boolean LdapBindCheck(String host, Integer port, Boolean ssl, String type, String bindDn, String bindPw) throws Exception {
		
		boolean result = false;
		
		LdapContext ldapContext = Connect(host, port, ssl, bindDn, bindPw);
		
		if (ldapContext != null)
			result = true;
		
		try {
			
			if (ldapContext != null)
				ldapContext.close();
			
		} catch (Exception e) {
			_logger.error(e);
		} finally {
			ldapContext = null;
		}
		
		return result;
	}

	public static Vector LdapRetrieveBase(String host, Integer port, Boolean ssl, String type, String bindDn, String bindPw) throws Exception {
		
		Vector<String> result = new Vector<String>();
		
		LdapContext ldapContext = Connect(host, port, ssl, bindDn, bindPw);
		
		if (ldapContext == null)
			throw new Exception("unable to connect to the ldap server");
		
		try {
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(0);
			searchControls.setReturningAttributes(new String[] { "namingContexts" });
			
			NamingEnumeration<?> searchResults = ldapContext.search("", "objectClass=*", searchControls);
			
			SearchResult entry = (SearchResult) searchResults.next();
			
			Attribute namingContextsAttribute = entry.getAttributes().get("namingContexts");
			
			String namingContext;
			
			NamingEnumeration<?> namingContexts = namingContextsAttribute.getAll();
			
			while(namingContexts.hasMoreElements()) {
				
				namingContext = (String)namingContexts.next();
				
				result.add(namingContext);
			}

		} catch (Exception e) {
			_logger.error(e);
			throw new Exception(e.getMessage());
		}
		try {
			ldapContext.close();
		} catch (Exception e) {
			_logger.error(e);
		}
		return result;
	}

	
	public static Vector LdapRetrieveGroup(String host, Integer port, Boolean ssl, String type, String bindDn, String bindPw, String baseDn, String groupClass) 
			throws Exception 
	{
		
		Vector<Hashtable<String, String>> result = new Vector<Hashtable<String, String>>();
		
		LdapContext ldapContext = Connect(host, port, ssl, bindDn, bindPw);
		
		if (ldapContext == null)
			throw new Exception("unable to connect to the ldap server");
		
		try {
			int pageSize = 100;
			byte cookie[] = null;
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(2);
			
			if ("active-directory".equals(type))
				searchControls.setReturningAttributes(new String[] { "groupType" });
			else
				searchControls.setReturningAttributes(new String[0]);
			
			do {
				if (cookie == null) {
					ldapContext.setRequestControls(new Control[] { new PagedResultsControl(pageSize, true) });
				} else {
					ldapContext.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, true) });
					cookie = null;
				}
				
				Hashtable<String, String> hashtable;
				
				for (NamingEnumeration searchResults = ldapContext.search(baseDn,
						(new StringBuilder("objectClass=")).append(groupClass).toString(),
						searchControls); searchResults.hasMoreElements(); result.add(hashtable)) {
					
					SearchResult entry = (SearchResult) searchResults.next();
					
					String group_dn = entry.getNameInNamespace();
					String group_name = "";
					
					LdapName ldapName = new LdapName(group_dn);
					
					for (Iterator iterator = ldapName.getRdns().iterator(); iterator.hasNext();) {
						
						Rdn rdn = (Rdn) iterator.next();
						
						if ("dc".equals(rdn.getType().toLowerCase()))
							group_name = (new StringBuilder()).append(rdn.getValue())
									.append(group_name.length() != 0 ? "." : "").append(group_name).toString();
						else
							group_name = (new StringBuilder(String.valueOf(group_name)))
									.append(group_name.length() != 0 ? "/" : "").append(rdn.getValue()).toString();
					}

					String group_type = "";
					
					if ("active-directory".equals(type)) {
						
						Attribute groupTypeAttribute = entry.getAttributes().get("groupType");
						
						if (groupTypeAttribute != null) {
							int adGroupType = Integer.parseInt(groupTypeAttribute.get().toString());
							if ((adGroupType & 2) == 2)
								group_type = "Global";
							if ((adGroupType & 4) == 4)
								group_type = "Local";
							if ((adGroupType & 8) == 8)
								group_type = "Universal";
						}
					}
					
					hashtable = new Hashtable();
					hashtable.put("dn", group_dn);
					hashtable.put("name", group_name);
					hashtable.put("type", group_type);
				}

				Control controls[] = ldapContext.getResponseControls();
				if (controls != null) {
					for (int i = 0; i < controls.length; i++)
						if (controls[i] instanceof PagedResultsResponseControl) {
							PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
							cookie = prrc.getCookie();
						}

				}
			} while (cookie != null);
			
		} catch (PartialResultException e) {
			_logger.info("Partial Result");
		} catch (Exception e) {
			_logger.error(e);
			throw new Exception(e.getMessage());
		}
		
		try {
			ldapContext.close();
		} catch (Exception e) {
			_logger.error(e);
		}
		
		return result;
	}

	public static boolean SimpleAuthenticate(String host, int port, Boolean ssl, String user_dn, String user_pw) {
		_logger.info((new StringBuilder("Simple Authenticate : ")).append(host).append(", ").append(port).append(", ")
				.append(ssl).append(", ").append(user_dn).toString());
		boolean result = false;
		try {
			Hashtable properties = new Hashtable();
			properties.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			properties.put("java.naming.security.authentication", "simple");
			if (ssl.booleanValue()) {
				properties.put("java.naming.provider.url",
						(new StringBuilder("ldaps://")).append(host).append(":").append(port).append("/").toString());
				properties.put("java.naming.security.protocol", "ssl");
				InitializeSSL();
			} else {
				properties.put("java.naming.provider.url",
						(new StringBuilder("ldap://")).append(host).append(":").append(port).append("/").toString());
			}
			properties.put("java.naming.security.principal", user_dn);
			properties.put("java.naming.security.credentials", user_pw);
			LdapContext ldapContext = new InitialLdapContext(properties, null);
			ldapContext.close();
			result = true;
		} catch (Exception e) {
			_logger.error(e);
		}
		return result;
	}

	private static boolean KerberosAuthenticate(String kerberosHost, int kerberosPort, String kerberosRealm,
			String user_id, String pass_wd) {
		_logger.info((new StringBuilder("Kerberos Authenticate : ")).append(kerberosHost).append(", ")
				.append(kerberosPort).append(", ").append(kerberosRealm).append(", ").append(user_id).toString());
		boolean result = false;
		if (!KerberosConfigurationSetting(kerberosHost, kerberosPort, kerberosRealm)) {
			_logger.error("Kerberos configuration setting failure");
			return false;
		}
		try {
			Krb5LoginModule krb5LoginModule = new Krb5LoginModule();
			Map sharedState = new HashMap();
			Map options = new HashMap();
			options.put("refreshKrb5Config", "true");
			options.put("useFirstPass", "true");
			sharedState.put("javax.security.auth.login.name", user_id);
			sharedState.put("javax.security.auth.login.password", pass_wd.toCharArray());
			krb5LoginModule.initialize(null, null, sharedState, options);
			krb5LoginModule.login();
			result = true;
		} catch (Exception e) {
			_logger.error(e);
		}
		return result;
	}

	private static boolean KerberosConfigurationSetting(String kdcHost, int kdcPort, String realmName) {
		boolean result = false;
		try {
			if (kdcPort == 88) {
				System.setProperty("java.security.krb5.realm", realmName);
				System.setProperty("java.security.krb5.kdc", kdcHost);
			} else {
				StringBuilder sb = new StringBuilder();
				sb.append("[libdefaults]").append("\n\t");
				sb.append("default_realm = ").append(realmName).append("\n");
				sb.append("[realms]").append("\n\t");
				sb.append(realmName).append(" = {").append("\n\t\t");
				sb.append("kdc = ").append(kdcHost).append(":").append(kdcPort).append("\n\t}\n");
				File krb5Conf = File.createTempFile("smartportal_krb5_client", ".conf");
				krb5Conf.deleteOnExit();
				FileWriter fw = new FileWriter(krb5Conf);
				fw.write(sb.toString());
				fw.close();
				String krb5ConfPath = krb5Conf.getAbsolutePath();
				System.setProperty("java.security.krb5.conf", krb5ConfPath);
			}
			result = true;
		} catch (Exception e) {
			_logger.error(e);
		}
		return result;
	}

	private static void InitializeSSL() {
		if (s_sslInitialized)
			return;
		try {
			X509TrustManager trustAllManager = new X509TrustManager() {

				public void checkClientTrusted(X509Certificate ax509certificate[], String s) {
				}

				public void checkServerTrusted(X509Certificate ax509certificate[], String s) {
				}

				public X509Certificate[] getAcceptedIssuers() {
					return new X509Certificate[0];
				}

			};
			TrustManager trustManagers[] = { trustAllManager };
			SSLContext sslContext = SSLContext.getInstance("SSL");
			sslContext.init(null, trustManagers, null);
			SSLContext.setDefault(sslContext);
			s_sslInitialized = true;
		} catch (Exception e) {
			System.err.println(e);
		}
	}
}

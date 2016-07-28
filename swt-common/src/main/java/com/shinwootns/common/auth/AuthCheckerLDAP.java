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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthCheckerLDAP extends AuthChecker {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	// Binding Info
	private String _host = "";
	private int _port = 389;				// Default: 389
	private boolean _isSSL = false;
	private String _bindDn = "";
	private String _bindPw = "";
	private String _baseDn = "";
	
	// User&Group Attribute
	private LdapUserGroupAttr _userGroupAttr = new LdapUserGroupAttr();
	
	// Kerberos Info
	private LdapKeberos _kerberos = new LdapKeberos();

	private LdapContext _ldapContext = null;
	private boolean _sslInitialized = false;
	
	private Hashtable<String, LdapGroup> _groups = new Hashtable<String, LdapGroup>();		// <group_dn, group_name>
	
	public void setLdapGroup(Hashtable<String,LdapGroup> groups) {
		this._groups.putAll(groups);
	}
	
	public void setBaseDn(String baseDn) {
		this._baseDn = baseDn;
	}
	
	public void setUserGroupAttr(LdapUserGroupAttr userGroupAttr) {
		this._userGroupAttr = userGroupAttr;
	}
	
	public void setKeberosInfo(LdapKeberos kerberos) {
		this._kerberos = kerberos;
	}
	
	
	//region Connect
	public boolean connect(String host, int port, boolean isSSL, String bindDn, String bindPw) {
		
		close();
		
		this._host = host;
		this._port = port;				// Default: 389
		this._isSSL = isSSL;
		this._bindDn = bindDn;
		this._bindPw = bindPw;
		
		try {
			Hashtable<String, String> properties = new Hashtable<String, String>();
			
			properties.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			properties.put("java.naming.security.authentication", "simple");
			
			// SSL
			if (this._isSSL) {
				
				// URL
				properties.put("java.naming.provider.url", String.format("ldaps://%s:%d/", this._host, this._port));

				properties.put("java.naming.security.protocol", "ssl");
				
				// Initialize SSL
				InitializeSSL();
			}
			else {
				// URL
				properties.put("java.naming.provider.url", String.format("ldap://%s:%d/", this._host, this._port));
			}
			
			properties.put("java.naming.security.principal", this._bindDn);
			properties.put("java.naming.security.credentials", this._bindPw);
			
			if ( (this._ldapContext = new InitialLdapContext(properties, null)) != null )
				return true;
			
		} catch (AuthenticationException e) {
			_logger.error(e.getMessage(), e);
		} catch (NamingException e) {
			_logger.error(e.getMessage(), e);
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
		
		return false;
	}
	//endregion
	
	//region Close
	public void close() {
		if (this._ldapContext != null) {
			try {
				this._ldapContext.close();
			} catch (Exception e) {
				//_logger.error(e.getMessage(), e);
			}finally {
				this._ldapContext = null;
			}
		}
	}
	//endregion
	

	//region Get NamingContexts
	public Vector getNamingContexts() throws Exception {
		
		if (this._ldapContext == null)
			throw new Exception("LdapContext is null !!!");
		
		Vector<String> result = new Vector<String>();
		
		//LdapContext ldapContext = Connect(host, port, ssl, bindDn, bindPw);
		
		try {
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.OBJECT_SCOPE);
			searchControls.setReturningAttributes(new String[] { "namingContexts" });
			
			NamingEnumeration<?> searchResults = this._ldapContext.search("", "objectClass=*", searchControls);
			
			SearchResult entry = (SearchResult) searchResults.next();
			
			Attribute namingContextsAttribute = entry.getAttributes().get("namingContexts");
			
			String namingContext;
			
			NamingEnumeration<?> namingContexts = namingContextsAttribute.getAll();
			
			while(namingContexts.hasMoreElements()) {
				
				namingContext = (String)namingContexts.next();
				
				result.add(namingContext);
			}

		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}

		return result;
	}
	//endregion

	//region Get LdapGroup
	public Hashtable<String,LdapGroup> getLdapGroups(boolean isActiveDirectory, String baseDn, String groupClass) 
			throws Exception 
	{
		if (this._ldapContext == null)
			throw new Exception("LdapContext is null !!!");
		
		Hashtable<String,LdapGroup> result = new Hashtable<String,LdapGroup>(); 
		
		try {
			
			int pageSize = 100;
			byte cookie[] = null;
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			
			if (isActiveDirectory)
				searchControls.setReturningAttributes(new String[] { "groupType" });
			else
				searchControls.setReturningAttributes(new String[0]);
			
			do {
				
				if (cookie == null) {
					this._ldapContext.setRequestControls(new Control[] { new PagedResultsControl(pageSize, true) });
				} else {
					this._ldapContext.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, true) });
					cookie = null;
				}
				
				NamingEnumeration<?> searchResults = this._ldapContext.search(baseDn, (new StringBuilder("objectClass=")).append(groupClass).toString(), searchControls);
				
				while(searchResults.hasMoreElements())
				{
					SearchResult entry = (SearchResult)searchResults.next();
					
					// Group DN
					String group_dn = entry.getNameInNamespace();
					String group_name = "";
					
					
					// Group Name
					LdapName ldapName = new LdapName(group_dn);
					
					for (Iterator<?> iterator = ldapName.getRdns().iterator(); iterator.hasNext();) {
						
						Rdn rdn = (Rdn) iterator.next();
						
						if ("dc".equals(rdn.getType().toLowerCase())) {
							group_name = (new StringBuilder()).append(rdn.getValue())
									.append(group_name.length() != 0 ? "." : "").append(group_name).toString();
						}
						else {
							group_name = (new StringBuilder(String.valueOf(group_name)))
									.append(group_name.length() != 0 ? "/" : "").append(rdn.getValue()).toString();
						}
					}

					// Group Type
					String group_type = "";
					
					if (isActiveDirectory) {
						
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
					
					if ( result.containsKey(group_dn) == false ) {
						
						LdapGroup group = new LdapGroup();
						
						group.setGroupDn(group_dn);
						group.setGroupName(group_name);
						group.setGroupType(group_type);
						
						result.put(group_dn, group);						
					}
				}

				Control controls[] = this._ldapContext.getResponseControls();
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
			_logger.error(e.getMessage(), e);
			throw new Exception(e.getMessage());
		}
		
		return result;
	}
	//endregion

	
	//region Check UserAuth
	@Override
	public boolean checkUserAuth(String user_id, String pass_wd) throws Exception {
		
		if (this._ldapContext == null)
			throw new Exception("LdapContext is null !!!");
		
		_logger.info("authenticate : " + user_id);
			
		if (user_id == null || user_id.length() == 0 || pass_wd == null || pass_wd.length() == 0) {
			_logger.error("userid or passwd is empty");
			return false;
		}

		boolean result = false;
		
		try {
			
			SearchControls searchControls = new SearchControls();
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			searchControls.setReturningAttributes(new String[] { this._userGroupAttr.getUserMemberOfAttr() });
			
			String filterExpr = String.format("(&(objectClass=%s)(%s=%s))"
					, this._userGroupAttr.getUserClass()
					, this._userGroupAttr.getUserAttr()
					, user_id);
			
			NamingEnumeration searchResults = _ldapContext.search(this._baseDn, filterExpr,searchControls);
			
			while (searchResults.hasMoreElements()) {
				
				SearchResult searchResult = (SearchResult) searchResults.next();
				
				String user_dn = searchResult.getNameInNamespace();
				
				_logger.info((new StringBuilder("find user : ")).append(user_dn).toString());
				
				if (_groups.size() > 0) {
					
					Attribute memberOfAttribute = searchResult.getAttributes().get(this._userGroupAttr.getUserMemberOfAttr());
					
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
				
				if (this._kerberos.isKerberos()) {
					
					result = KerberosAuthenticate(
							this._kerberos.getKerberosHost(), 
							this._kerberos.getKerberosPort(),
							this._kerberos.getKerberosRealm(),
							user_id,
							pass_wd
					);
				}
				else {
					result = checkSimpleUserAuth(user_dn, pass_wd);
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
		
		return result;
	}
	
	@Override
	public boolean checkUserAuth(String userid, String passwd, String macAddress) throws Exception {
		return checkUserAuth(userid, passwd);
	}
	//endregion	
	
	private boolean checkSimpleUserAuth(String user_dn, String user_pw) throws Exception {
		
		if (this._ldapContext == null)
			throw new Exception("LdapContext is null !!!");
		
		_logger.info((new StringBuilder("checkSimpleUserAuth : ")).append(user_dn).toString());
		
		boolean result = false;
		
		try {
			Hashtable<String,String> properties = new Hashtable<String,String>();
			properties.put("java.naming.factory.initial", "com.sun.jndi.ldap.LdapCtxFactory");
			properties.put("java.naming.security.authentication", "simple");
			
			if (this._isSSL) {
				
				// ldaps://~
				properties.put("java.naming.provider.url",
						(new StringBuilder("ldaps://")).append(this._host).append(":").append(this._port).append("/").toString());
				
				properties.put("java.naming.security.protocol", "ssl");
				
				this.InitializeSSL();
				
			} else {
				
				// ldap://~
				properties.put("java.naming.provider.url",
						(new StringBuilder("ldap://")).append(this._host).append(":").append(this._port).append("/").toString());
				
			}
			properties.put("java.naming.security.principal", user_dn);
			properties.put("java.naming.security.credentials", user_pw);
			
			LdapContext ldapContext = new InitialLdapContext(properties, null);
			
			ldapContext.close();
			
			result = true;
			
		} catch (Exception e) {
			//_logger.error(e.getMessage(), e);
			_logger.error(e.getMessage());
		}
		return result;
	}

	private boolean KerberosAuthenticate(String kerberosHost, int kerberosPort, String kerberosRealm, String user_id, String pass_wd) {
		
		_logger.info((new StringBuilder("Kerberos Authenticate : ")).append(kerberosHost).append(", ")
				.append(kerberosPort).append(", ").append(kerberosRealm).append(", ").append(user_id).toString());
		
		boolean result = false;
		
		if (!KerberosConfigurationSetting(kerberosHost, kerberosPort, kerberosRealm)) {
			_logger.error("Kerberos configuration setting failure");
			return false;
		}
		
		try {
			Map<String, String> options = new HashMap<String, String>();
			options.put("refreshKrb5Config", "true");
			options.put("useFirstPass", "true");

			Map sharedState = new HashMap();
			sharedState.put("javax.security.auth.login.name", user_id);
			sharedState.put("javax.security.auth.login.password", pass_wd.toCharArray());

			Krb5LoginModule krb5LoginModule = new Krb5LoginModule();
			krb5LoginModule.initialize(null, null, sharedState, options);
			krb5LoginModule.login();
			
			result = true;
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
		
		return result;
	}

	private boolean KerberosConfigurationSetting(String kdcHost, int kdcPort, String realmName) {
		
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
			_logger.error(e.getMessage(), e);
		}
		return result;
	}

	private void InitializeSSL() {
		
		if (this._sslInitialized)
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
			
			_sslInitialized = true;
			
		} catch (Exception e) {
			_logger.error(e.getMessage(), e);
		}
	}
}

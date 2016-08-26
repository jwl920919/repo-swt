package com.shinwootns.ipm.service.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.auth.AuthCheckerLDAP;
import com.shinwootns.common.auth.LdapUserGroupAttr;
import com.shinwootns.common.http.HttpClient;
import com.shinwootns.common.utils.CryptoUtils;
import com.shinwootns.common.utils.JsonUtils;
import com.shinwootns.data.api.AuthParam;
import com.shinwootns.data.api.AuthResult;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.data.entity.DeviceInsight;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.config.ApplicationProperty;
import com.shinwootns.ipm.data.SharedData;
import com.shinwootns.ipm.data.mapper.AuthMapper;
import com.shinwootns.ipm.data.mapper.DataMapper;

public class AuthCheckHandler {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public AuthCheckHandler() {
	}
	
	//region [public] Check Login
	public AuthResult checkLogin(AuthParam param, AuthResult result) {

		// AuthMapper
		AuthMapper authMapper = SpringBeanProvider.getInstance().getAuthMapper();
		if (authMapper == null) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] AuthMapper is null.");
			return result;
		}
		
		// DataMapper
		DataMapper dataMapper = SpringBeanProvider.getInstance().getDataMapper();
		if (dataMapper == null) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] DataMapper is null.");
			return result;
		}
		
		ApplicationProperty appProperty = SpringBeanProvider.getInstance().getApplicationProperty();
		if (appProperty == null) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] ApplicationProperty is null.");
			return result;
		}
		
		// Load AuthSetup list
		List<AuthSetup> listAuthSetup = authMapper.selectAuthSetup();
		if (listAuthSetup == null || listAuthSetup.size() == 0) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] AuthSetup is empty.");
			return result;
		}
		
		// Check Auth
		int index = 0;
		int total = listAuthSetup.size();
		
		for(AuthSetup setup : listAuthSetup) {
			
			result.setAuthType(setup.getAuthType());
			result.setSetupName(setup.getSetupName());
			
			++index;
			
			// Global Setup
			if (setup.getSiteId() == 0) {
				
				// ldap
				if (setup.getAuthType().toLowerCase().equals("ldap")) {
					checkLdap(authMapper, setup, param, result);
				}
				// radius
				else if (setup.getAuthType().toLowerCase().equals("radius")) {
					checkRadius(authMapper, setup, param, result);
				}
				// esb
				else if (setup.getAuthType().toLowerCase().equals("esb")) {
					checkEsb(authMapper, setup, param, result);
				}

				// Check Result
				if (result.isIsCheck() == true && result.isIsLogin() == true) {
					return result;
				}
			}
			// Site Setup
			else if (setup.getSiteId() > 0) {
				
				// Find Master Insight
				DeviceInsight insight = dataMapper.selectInsightMaster(setup.getSiteId());
				
				if (insight != null) {

					StringBuilder url = new StringBuilder();
					url.append("http://").append(insight.getIpaddr()).append(":").append(insight.getPort()).append("/api");
					
					HttpClient restClient = new HttpClient();
					try {
						
						if (restClient.Connect_Https(url.toString(), appProperty.security_user, CryptoUtils.Decode_AES128(appProperty.security_password)) == false) {
							
							result.setIsCheck(false);
							result.setMessage(
									(new StringBuilder())
									.append("[ERROR] Failed connect master insight. ")
									.append(insight.getHost())
									.append("(").append(insight.getIpaddr()).append(")")
									.toString());
							
							return result;
						}
						
					} catch (Exception e) {
						_logger.error(e.getMessage(), e);
						result.setIsCheck(false);
						result.setMessage(e.getMessage());
						return result; 
					}
					
					// [Site] Insight Check URL
					StringBuilder checkUrl = new StringBuilder();
					checkUrl.append("http://").append(insight.getIpaddr()).append(":").append(insight.getPort())
					.append("/api/check_auth")
					.append("?userid=").append(param.getUserId())
					.append("&setupid=").append(setup.getSetupId());
					
					if (param.getPassword() != null)
						checkUrl.append("&password=").append(param.getPassword());
					
					if (param.getMacAddr() != null)
						checkUrl.append("&macaddr=").append(param.getMacAddr());
					
					// Get
					String output = restClient.Get(checkUrl.toString());
					
					// Deserialize
					result = (AuthResult)JsonUtils.deserialize(output, AuthResult.class);
					
					restClient.Close();
					
					// Check Result
					if (result.isIsCheck() == true && result.isIsLogin() == true) {
						return result;
					}
				}
				else {
					result.setIsCheck(false);
					result.setMessage("[ERROR] AuthSetup is empty.");
					return result;
				}
			}
		}
		
		return result;
	}
	//endregion
	
	//region Check Ldap
	private void checkLdap(AuthMapper authMapper, AuthSetup setup, AuthParam param, AuthResult result) {

		// Get AuthSetupLdap
		AuthSetupLdap setupLdap = authMapper.selectAuthSetupLdap(setup.getSetupId());
		
		if (setupLdap == null) {
			result.setIsCheck(false);
			result.setMessage(
				(new StringBuilder())
				.append("Failed load ldap setup. setup_id=").append(setup.getSetupId())
				.toString()
			);
			return;
		}
		
		//setupLdap.
		AuthCheckerLDAP ldap = new AuthCheckerLDAP();
		
		try {
			
			// Bind check
			if ( ldap.connect(setupLdap.getHost(), setupLdap.getPort(), setupLdap.getSslEnable(), setupLdap.getBindDomain(), setupLdap.getBindPasswd()) )  {
				
				// Set BaseDn
				ldap.setBaseDn(setupLdap.getBaseDomain());
				
				// UserGroup Attribute
				LdapUserGroupAttr userGroupAttr = new LdapUserGroupAttr();
				
				userGroupAttr.setUserClass(setupLdap.getUserClass());				// person
				userGroupAttr.setUserAttr(setupLdap.getUserAttr());					// sAMAccountName
				userGroupAttr.setUserNameAttr(setupLdap.getUserNameAttr());			// displayName
				userGroupAttr.setUserMemberOfAttr(setupLdap.getUserMemberofAttr());	// memberOf
				
				userGroupAttr.setGroupClass(setupLdap.getGroupClass());				// group
				userGroupAttr.setGroupNameAttr(setupLdap.getGroupNameAttr());		// name
				userGroupAttr.setGroupMemberAttr(setupLdap.getGroupMemberAttr());	// member							
				
				ldap.setUserGroupAttr(userGroupAttr);
				
				
				if ( ldap.checkUserAuth(param.getUserId(), param.getPassword())) {
					result.setIsCheck(true);
					result.setIsLogin(true);
					result.setMessage("Login OK.");
					return;
				}
				else {
					result.setIsCheck(true);
					result.setIsLogin(false);
					result.setMessage("Login failed.");
					return;
				}
			}
			else {
				result.setIsCheck(false);
				result.setMessage(
						(new StringBuilder())
						.append("Failed connect LDAP (")
						.append("host=").append(setupLdap.getHost())
						.append(", port=").append(setupLdap.getPort())
						.append(", ssl=").append(setupLdap.getSslEnable())
						.append(", bind_dn=").append(setupLdap.getBindDomain())
						.append(")")
						.toString());
				return;
			}
				
		} catch(Exception ex) {
			
			_logger.error(ex.getMessage(), ex);
			
			result.setIsCheck(false);
			result.setMessage(ex.getMessage());
		}
	}
	//endregion
	
	//region Check Radius
	private void checkRadius(AuthMapper authMapper, AuthSetup setup, AuthParam param, AuthResult result) {

		// Get AuthSetupRadius
		AuthSetupRadius setupRadius = authMapper.selectAuthSetupRadius(setup.getSetupId());
		
		if (setupRadius == null) {
			result.setIsCheck(false);
			result.setMessage(
				(new StringBuilder())
				.append("Failed load radius Setup. setup_id=").append(setup.getSetupId())
				.toString()
			);
			return;
		}
		
		
		// ...
	}
	//endregion

	//region Check ESB
	private void checkEsb(AuthMapper authMapper, AuthSetup setup, AuthParam param, AuthResult result) {

		// Get AuthSetupEsb
		AuthSetupEsb setupEsb = authMapper.selectAuthSetupEsb(setup.getSetupId());
		
		if (setupEsb == null) {
			result.setIsCheck(false);
			result.setMessage(
				(new StringBuilder())
				.append("Failed load radius Setup. setup_id=").append(setup.getSetupId())
				.toString()
			);
			return;
		}
		
		
		// ...
	}
	//endregion
}

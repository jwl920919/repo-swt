package com.shinwootns.ipm.insight.service.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.auth.AuthCheckerLDAP;
import com.shinwootns.common.auth.LdapUserGroupAttr;
import com.shinwootns.data.auth.AuthParam;
import com.shinwootns.data.auth.AuthResult;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.ipm.insight.SpringBeanProvider;
import com.shinwootns.ipm.insight.data.mapper.AuthMapper;

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
		
		AuthSetup setup = authMapper.selectAuthSetup(param.getSetupId());
		if (setup == null) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] Failed get AutuSetup, etup_id=" + param.getSetupId());
			return result;
		}
				
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

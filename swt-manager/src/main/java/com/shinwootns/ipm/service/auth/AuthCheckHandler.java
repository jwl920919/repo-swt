package com.shinwootns.ipm.service.auth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shinwootns.common.auth.AuthCheckerLDAP;
import com.shinwootns.common.auth.LdapUserGroupAttr;
import com.shinwootns.data.entity.AuthSetup;
import com.shinwootns.data.entity.AuthSetupEsb;
import com.shinwootns.data.entity.AuthSetupLdap;
import com.shinwootns.data.entity.AuthSetupRadius;
import com.shinwootns.ipm.SpringBeanProvider;
import com.shinwootns.ipm.data.mapper.AuthMapper;

public class AuthCheckHandler {

	private final Logger _logger = LoggerFactory.getLogger(getClass());
	
	public AuthCheckHandler() {
	}
	
	public AuthResult checkLogin(AuthParam param) {
		AuthResult result = new AuthResult();
		
		AuthMapper authMapper = SpringBeanProvider.getInstance().getAuthMapper();
		if (authMapper == null) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] AuthMapper is null.");
			return result;
		}
		
		List<AuthSetup> listAuthSetup = authMapper.selectAuthSetup();
		if (listAuthSetup == null || listAuthSetup.size() == 0) {
			result.setIsCheck(false);
			result.setMessage("[ERROR] AuthSetup is empty.");
			return result;
		}
		
		// Check Auth
		for(AuthSetup setup : listAuthSetup) {
			
			// Glolbal
			if (setup.getSiteId() == 0) {
				
				// ldap
				if (setup.getAuthType().toLowerCase().equals("ldap")) {
					CheckLdap(authMapper, setup, param, result);
				}
				// radius
				else if (setup.getAuthType().toLowerCase().equals("radius")) {
					//AuthSetupRadius setupRadius = authMapper.selectAuthSetupRadius(setup.getSetupId());
					// ...
				}
				// esb
				else if (setup.getAuthType().toLowerCase().equals("esb")) {
					//AuthSetupEsb setupEsb = authMapper.selectAuthSetupEsb(setup.getSetupId());
					// ...
				}
				
			}
			// Site
			else if (setup.getSiteId() > 0) {
				
				// ...
			}
		}
		
		return result;
	}
	
	private void CheckLdap(AuthMapper authMapper, AuthSetup setup, AuthParam param, AuthResult result) {

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
}

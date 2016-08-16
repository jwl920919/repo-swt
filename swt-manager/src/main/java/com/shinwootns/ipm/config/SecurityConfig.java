package com.shinwootns.ipm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.shinwootns.common.utils.CryptoUtils;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	ApplicationProperty appProperty;
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		String pwd = CryptoUtils.Decode_AES128(appProperty.security_password);
		
        auth
            .inMemoryAuthentication()
                .withUser(appProperty.security_user).password(pwd).roles("USER");
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				// .antMatchers(HttpMethod.GET, "/api/**").authenticated()
				// .antMatchers(HttpMethod.POST, "/api/**").authenticated()
				// .antMatchers(HttpMethod.PUT, "/api/**").authenticated()
				// .antMatchers(HttpMethod.DELETE, "/api/**").authenticated()
				// .anyRequest().permitAll()
				.antMatchers(HttpMethod.GET, "/help/**").permitAll().anyRequest().authenticated().and().httpBasic()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}

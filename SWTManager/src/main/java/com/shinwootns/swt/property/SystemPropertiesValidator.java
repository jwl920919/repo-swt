package com.shinwootns.swt.property;

import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SystemPropertiesValidator implements Validator {

	final Pattern versionPattern = Pattern.compile("^[0-9]{1,5}\\.[0-9]{1,5}\\.[0-9]{1,5}$");
	
	@Override
	public boolean supports(Class<?> type) {
		return type == SystemProperties.class;
	}

	@Override
	public void validate(Object obj, Errors errors) {

		// Check empty
		ValidationUtils.rejectIfEmpty(errors, "name", "system.name");
		ValidationUtils.rejectIfEmpty(errors, "version", "system.version");

		SystemProperties properties = (SystemProperties) obj;
		
		// Check version
		if ( properties.getVersion() == null
				|| this.versionPattern.matcher(properties.getVersion()).matches() == false
				|| checkVersion(properties.getVersion()) == false ) 
		{
			errors.rejectValue("version", "Invalid version.");
		}
	}

	private boolean checkVersion(String license) {
		
		
		
		return true;
	}
}

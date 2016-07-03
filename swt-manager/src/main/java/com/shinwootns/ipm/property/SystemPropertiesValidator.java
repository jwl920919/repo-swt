package com.shinwootns.ipm.property;

import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class SystemPropertiesValidator implements Validator {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
	private final Pattern versionPattern = Pattern.compile("^[0-9]{1,5}\\.[0-9]{1,5}\\.[0-9]{1,5}$");
	
	@Override
	public boolean supports(Class<?> type) {
		return type == SystemProperties.class;
	}

	@Override
	public void validate(Object obj, Errors errors) {
		
		_logger.info("Validate property.... start");
		
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
		
		_logger.info("Validate property.... end");
	}

	private boolean checkVersion(String license) {
		
		// ...
		
		return true;
	}
}
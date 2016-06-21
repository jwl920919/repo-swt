package com.shinwootns.swt;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

	@RequestMapping("/")
	public String greeting(String name) {
		String output = "Hi " + name + " !!!";
		return output;
	}
}

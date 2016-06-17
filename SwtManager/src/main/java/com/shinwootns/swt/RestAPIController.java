package com.shinwootns.swt;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestAPIController {
	
	//private static final String template = "Hello, %s!";
    //private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public String greeting(String name) {
    	String output = "Hi " + name;
        return output;
    }
}

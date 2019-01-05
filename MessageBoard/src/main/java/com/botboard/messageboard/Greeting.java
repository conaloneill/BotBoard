package com.botboard.messageboard;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;


@RestController
public class Greeting {
	
	@ApiIgnore
	@RequestMapping(value = "/greetingMessage", method = RequestMethod.GET)
	public String greeting() {
		return "Hello from Greeting class";
	}
}

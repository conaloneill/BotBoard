package com.botboard.bitcoin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BitcoinController {
	
	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getPrice() {
		
		final String url = "https://blockchain.info/tobtc?currency=EUR&value=1";
		
		String result = restTemplate.getForObject(url, String.class);
		return new ResponseEntity("1 Euro is worth: " + result + " BTC.", HttpStatus.OK);
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
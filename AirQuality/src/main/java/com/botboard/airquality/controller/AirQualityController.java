package com.botboard.airquality.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class AirQualityController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@ApiOperation(value = "Get the AirQuality based on the nearest city to your IP location",
			tags = "AirQuality Bot")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> airQuality() throws JSONException {
		
		
		final String url = "http://api.airvisual.com/v2/nearest_city?key=593xwuGnoGHH6oZ7T";
		String result = restTemplate.getForObject(url, String.class);
		JSONObject obj = new JSONObject(result);
		
		JSONObject data = obj.getJSONObject("data");
		
		
		String city = data.getString("city");
		
		JSONObject current = data.getJSONObject("current");
		
		JSONObject pollution = current.getJSONObject("pollution");
		
		int aqi = pollution.getInt("aqius");
		
		return new ResponseEntity("The Air Quality Index value (US system) for: " + city + " is: " + aqi, HttpStatus.OK);
		
	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}

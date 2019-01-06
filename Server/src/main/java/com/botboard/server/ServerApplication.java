package com.botboard.server;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaServer
@SpringBootApplication
@RestController
public class ServerApplication {
	
	
	@ApiOperation(value = "Base Eureka Server, displays information about the Eureka server instance",
			tags = "Eureka Server", httpMethod = "GET")
	@ApiResponse(code = 200, message = "Success_OK.")
	@RequestMapping(value = "/")
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
	
}


package com.sb.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@RestController
public class ClientController {
	
	@Autowired
	private EurekaClient client;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;

	@GetMapping("/discovery")
	public String callService() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		InstanceInfo instanceInfo = client.getNextServerFromEureka("application-service", false);
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(instanceInfo.getHomePageUrl());
		ResponseEntity<String> response =
				restTemplate.getForEntity(uriComponentsBuilder.toUriString(), String.class);
		return "Response from Client::::" + response.getBody();
	}

}

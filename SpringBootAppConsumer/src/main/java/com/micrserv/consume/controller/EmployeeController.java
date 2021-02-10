package com.micrserv.consume.controller;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



@RestController
public class EmployeeController {
	
	@Value("${server.port}")
	private int port;
	
	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	public RestTemplate restTemplate;
	
	@Autowired
	LoadBalancerClient loadBalancer;
	
	@RequestMapping(value="/consume/ping",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public String getEmployeeConsume()
	{
		System.out.println("Employee Consumer......using port:::"+port);
		
		//when load balancer not used..................
		
		/*List<ServiceInstance> instances=discoveryClient.getInstances("SpringBootApp");
		ServiceInstance serviceInstance=instances.get(0);*/
		
		//.................................................................
		
		
		//code for load balancer
		
		//when load balancer is used consumer have load balancer logic and producer have multiple instances
		
		ServiceInstance serviceInstance=loadBalancer.choose("SpringBootApp");
		
		String baseUrl=serviceInstance.getUri().toString();
	    baseUrl=baseUrl+"/employee/all";
	    
         ////////////////////////////
	    
		ResponseEntity<String> response=null;
		try{
		response=restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(),String.class);
		}catch (Exception ex)
		{
			System.out.println(ex);
		}
		System.out.println(response.getBody());

		
		return response.getBody();
	}
	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}
}

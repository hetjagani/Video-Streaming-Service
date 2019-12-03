package com.cc.load_balancer.controller;

import java.util.ArrayList;

import javax.websocket.server.PathParam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@RestController
public class RequestMapper {
	
	ArrayList<String> serverList = new ArrayList<>()
						{
							{
									add("http://10.20.24.100:8080/");
									add("http://10.20.24.101:8080/");
									add("http://10.20.24.102:8080/");
									add("http://10.20.24.103:8080/");
									add("http://10.20.24.104:8080/");
							}
						};
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity findServer(@RequestParam("path") String video_path) {
		float minLoad = 100;
		String minLoadServer=serverList.get(0);
		String path = "actuator/metrics/system.cpu.usage";
		Float temp;
		
		for (String serv : serverList) {
			try {
				RestTemplate restTemplate = new RestTemplate();
				
			    String result = restTemplate.getForObject(serv+path, String.class);
//			    System.out.println(serv+path + "\t"+ result);
			    JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
			    temp = Float.parseFloat(jsonObject.get("measurements").
			    		toString().split(",", 2)[1]
			    		.split(":", 2)[1].replaceAll("}]", ""));
			    //System.out.println(jsonObject.get("measurements").getAsJsonObject()[0]);
			    if(temp < minLoad) {
			    	minLoad = temp;
			    	minLoadServer = serv;
			    }
		    }catch (ResourceAccessException e) {
				System.out.println(serv + " is down...");
			}
		}
		System.out.println(minLoadServer + video_path + "->" + String.valueOf(minLoad));
		return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY)
				.header("Location", minLoadServer + video_path).body(null);
//		return minLoadServer + "\n" + String.valueOf(minLoad);
	}
}

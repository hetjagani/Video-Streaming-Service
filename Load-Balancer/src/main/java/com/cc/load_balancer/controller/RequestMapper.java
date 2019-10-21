package com.cc.load_balancer.controller;

import java.util.ArrayList;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RequestMapper {
	
	ArrayList<String> serverList = new ArrayList<>()
						{
							{
									add("http://localhost:8080/");
									//add("");
							}
						};
	
	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public String findServer(){
		float minLoad = 100;
		String minLoadServer=serverList.get(0);
		String path = "actuator/metrics/system.load.average.1m";
		Float temp;
		try {
			for (String serv : serverList) { 		      
				RestTemplate restTemplate = new RestTemplate();
				
			    String result = restTemplate.getForObject(serv+path, String.class);
			    System.out.println(serv+path + "\n"+ result);
			    JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
			    temp = Float.parseFloat(jsonObject.get("measurements").
			    		toString().split(",", 2)[1]
			    		.split(":", 2)[1].replaceAll("}]", ""));
			    if(temp < minLoad) {
			    	minLoad = temp;
			    	minLoadServer = serv;
			    }
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return minLoadServer + "\n" + String.valueOf(minLoad);
	}
}

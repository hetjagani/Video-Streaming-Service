package com.cloud.vss.resource;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cloud.vss.model.Alien;

@Path("aliens")
public class AlienResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Alien> getAlien() {
		System.out.println("getAlien() called");
		Alien a1 = new Alien();
		
		a1.setName("HET JAGANI");
		a1.setId(1);
		a1.setPoints(500);
		
		Alien a2 = new Alien();
		
		a2.setName("RIYA JAGANI");
		a2.setId(2);
		a2.setPoints(300);
		
		List<Alien> aliens = Arrays.asList(a1, a2);
		
		return aliens;
	}
	
	@POST
	@Path("cAlien")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Alien createAlien(Alien a) {
		System.out.println(a);
		return a;
	}
	
}

package com.cloud.vss.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cloud.vss.data.VideoDB;
import com.cloud.vss.model.Video;

@Path("videos")
public class VideoResource {
	
	VideoDB database = new VideoDB("/home/het/workspace/DB");
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Video> getAllVideos() {
		List<Video> list = database.getVideos();
	
		for(Video v: list) {
			System.out.println(v);
		}
		return list;
	}
}

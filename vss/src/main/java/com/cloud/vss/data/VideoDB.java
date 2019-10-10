package com.cloud.vss.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.cloud.vss.model.Video;

public class VideoDB {
	
	private List<Video> videos;
	private String baseDir;
	
	public VideoDB(String baseDir) {
		this.baseDir = baseDir;
		this.videos = new ArrayList<Video>();
		
		File bDir = new File(baseDir);
		File[] fList = bDir.listFiles();
		for(File f:fList) {
			String fpath = f.getName();
			this.videos.add(new Video(baseDir + "/" + fpath + "/" + fpath + ".mp4"));
		}
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}
}

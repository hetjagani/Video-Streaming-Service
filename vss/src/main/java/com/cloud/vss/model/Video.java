package com.cloud.vss.model;

import java.io.Serializable;
import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

import uk.co.caprica.vlcjinfo.Duration;
import uk.co.caprica.vlcjinfo.MediaInfo;
import uk.co.caprica.vlcjinfo.Section;

@XmlRootElement
public class Video {
	private int videoID;
	private String videoName;
	private String videoDescription;
	private String videoPath;		// Paths must be absolute path to the video file which is inside the video dir
	private String videoFormat;
	private int[] videoSize;		// <width, height>
	private long videoDuration;		// in milliseconds
	private int frameRate;
	private static int videoNr = 0;
	
	public Video() {
		
	}
	
	public Video(String path) {
		this.videoPath = path;
		
		String[] tokens = path.split("[/]");
		String fname = tokens[tokens.length - 1];
		String[] tokens1 = fname.split("[.]");
		this.videoName = tokens1[0];
//		System.out.println(this.videoName);
		
		this.videoDescription = "This is video no "+videoNr;
//		System.out.println(this.videoDescription);
		
		this.videoID = videoNr;
//		System.out.println(this.videoID);
		
		MediaInfo medInfo = MediaInfo.mediaInfo(path);
		Section gen = medInfo.first("General");
		
		this.videoFormat = gen.value("Format");
//		System.out.println(this.videoFormat);
		
		this.videoSize = new int[2]; 
		Section video = medInfo.first("Video");
		this.videoSize[0] = video.integer("Width");
		this.videoSize[1] = video.integer("Height");
//		System.out.println(this.videoSize[0] + " X " + this.videoSize[1]);
		
		this.videoDuration = video.duration("Duration").asMilliSeconds();
//		System.out.println(this.videoDuration);
		
		Float frate = Float.parseFloat(video.value("Frame rate").split("\\s")[0]);
		this.frameRate = Math.round(frate);
//		System.out.println(this.frameRate);
	
		videoNr += 1;
	}
	
	
	
	public int getVideoID() {
		return videoID;
	}

	public void setVideoID(int videoID) {
		this.videoID = videoID;
	}

	public String getVideoName() {
		return videoName;
	}

	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}

	public String getVideoDescription() {
		return videoDescription;
	}

	public void setVideoDescription(String videoDescription) {
		this.videoDescription = videoDescription;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public String getVideoFormat() {
		return videoFormat;
	}

	public void setVideoFormat(String videoFormat) {
		this.videoFormat = videoFormat;
	}

	public int[] getVideoSize() {
		return videoSize;
	}

	public void setVideoSize(int[] videoSize) {
		this.videoSize = videoSize;
	}

	public long getVideoDuration() {
		return videoDuration;
	}

	public void setVideoDuration(long videoDuration) {
		this.videoDuration = videoDuration;
	}

	public int getFrameRate() {
		return frameRate;
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}

	public static int getVideoNr() {
		return videoNr;
	}

	public static void setVideoNr(int videoNr) {
		Video.videoNr = videoNr;
	}

	@Override
	public String toString() {
		return "Video [videoID=" + videoID + ", videoName=" + videoName + ", videoDescription=" + videoDescription
				+ ", videoPath=" + videoPath + ", videoFormat=" + videoFormat + ", videoSize="
				+ Arrays.toString(videoSize) + ", videoDuration=" + videoDuration + ", frameRate=" + frameRate + "]";
	}
	
}


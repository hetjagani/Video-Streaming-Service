package com.cc.boot.model;

public class MovieInfo {
	
	private int id;
	private String name;
	private String genre;
	private int duration;
	private float rating;
	private String release_year;
	private int nb_frames;
	private float fps;
	private String description;
	private String filepath;
	private int height;
	private int width;
	private String format;
	private float filesize;
	
	public MovieInfo(int id, String name, String genre, int duration, float rating, String release_year, int nb_frames,
			float fps, String description, String filepath, int height, int width, String format, float filesize) {
		super();
		this.id = id;
		this.name = name;
		this.genre = genre;
		this.duration = duration;
		this.rating = rating;
		this.release_year = release_year;
		this.nb_frames = nb_frames;
		this.fps = fps;
		this.description = description;
		this.filepath = filepath;
		this.height = height;
		this.width = width;
		this.format = format;
		this.filesize = filesize;
	}
	
	

	@Override
	public String toString() {
		return "MovieInfo [id=" + id + ", name=" + name + ", genre=" + genre + ", duration=" + duration + ", rating="
				+ rating + ", release_year=" + release_year + ", nb_frames=" + nb_frames + ", fps=" + fps
				+ ", description=" + description + ", filepath=" + filepath + ", height=" + height + ", width=" + width
				+ ", format=" + format + ", filesize=" + filesize + "]";
	}


	public MovieInfo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getRelease_year() {
		return release_year;
	}

	public void setRelease_year(String release_year) {
		this.release_year = release_year;
	}

	public int getNb_frames() {
		return nb_frames;
	}

	public void setNb_frames(int nb_frames) {
		this.nb_frames = nb_frames;
	}

	public float getFps() {
		return fps;
	}

	public void setFps(float fps) {
		this.fps = fps;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public float getFilesize() {
		return filesize;
	}

	public void setFilesize(float filesize) {
		this.filesize = filesize;
	}
}

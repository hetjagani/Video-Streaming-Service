package com.cc.boot.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.cc.boot.model.MovieInfo;
import java.sql.DriverManager;  
import java.sql.Connection;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  

@Service
public class MovieService {
	
	/*private List<MovieInfo> movieList = new ArrayList<>(Arrays.asList(new MovieInfo(1, "Avatar", "Action", 120, 4), 
			new MovieInfo(2, "Star wars", "Drama", 110, 3),
			new MovieInfo(3, "Avengers", "Action", 150, 4)));*/
	
	public List<MovieInfo> getMovies(){
		List<MovieInfo> movieList = new ArrayList<>();  
        try {  
        	ResultSet rs = connectToDB();
            while (rs.next()) {
   
            	MovieInfo movie = new MovieInfo(rs.getInt("id"), 
            			rs.getString("name"), 
            			rs.getString("genre"),
            			rs.getInt("duration"),
            			rs.getFloat("rating"),
            			rs.getString("release_year"),
            			rs.getInt("total_frames"),
            			rs.getFloat("fps"),
            			rs.getString("description"),
            			rs.getString("filepath"),
            			rs.getInt("height"),
            			rs.getInt("width"),
            			rs.getString("format"),
            			rs.getFloat("filesize"));
                movieList.add(movie);
                System.out.println(movie.toString());
            }
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }
		return movieList;
	}
	
	public MovieInfo getMovie(int id) {
		MovieInfo movie; 
        try {  
        	ResultSet rs = connectToDB();
            while (rs.next()) {
            	if(rs.getInt("id")==id) {
            		movie = new MovieInfo(rs.getInt("id"), 
                			rs.getString("name"), 
                			rs.getString("genre"),
                			rs.getInt("duration"),
                			rs.getFloat("rating"),
                			rs.getString("release_year"),
                			rs.getInt("total_frames"),
                			rs.getFloat("fps"),
                			rs.getString("description"),
                			rs.getString("filepath"),
                			rs.getInt("height"),
                			rs.getInt("width"),
                			rs.getString("format"),
                			rs.getFloat("filesize"));
            		return movie;
            	}
            }
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }
		return null;
	}

	public ResponseEntity<ResourceRegion> getStream(int movieId, HttpHeaders headers) {
        String ext = "", name = "";
		try {
			ResultSet rs = connectToDB();
            while (rs.next()) {
            	if(rs.getInt("id")==movieId) {
            		ext = rs.getString("format");
            		name = rs.getString("name").replaceAll("\\s+","_");
            		break;
            	}
            }
			UrlResource video = new UrlResource("file:/home/server1/dist/media/" + name + "." + ext);
			long videoLen = video.contentLength();
			List<HttpRange> range = headers.getRange();
			System.out.println(range);
			ResourceRegion region;
			if(range.isEmpty() != true) {
				long start = range.get(0).getRangeStart(videoLen);
				long end = range.get(0).getRangeEnd(videoLen);
				long rangeLength = (Long.valueOf(1048576)<(end - start + 1))?1048576:(end - start + 1);
				region = new ResourceRegion(video, start, rangeLength);
			}
			else {
				long rangeLength = (Long.valueOf(1048576)<videoLen)?1048576:videoLen;
				region = new ResourceRegion(video, 0, rangeLength);
			}
			System.out.println(region);
			return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
					.header("keep-alive", "true")
					.contentType(MediaTypeFactory
							.getMediaType(video)
							.orElse(MediaType.APPLICATION_OCTET_STREAM))
					.body(region);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	
//		return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
//				.header("Location", "http://10.20.131.199:8080/movies/stream/2").body(null);
	}
	
	public static ResultSet connectToDB() {
		String url = "jdbc:sqlite:/home/server1/dist/databases/project"; 
		try {
			Connection conn = DriverManager.getConnection(url);    
            Statement stmt = conn.createStatement();
            String sqlQuery = "SELECT * FROM movies";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return rs;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}

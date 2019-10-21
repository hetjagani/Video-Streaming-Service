package com.cc.boot.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cc.boot.model.MovieInfo;
import com.cc.boot.service.MovieService;

@RestController
public class MovieController {

	@Autowired
	private MovieService movieServ;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String sendGreeting() {
		return "Welcome to MyApp";
	}
	
	@RequestMapping(value = "/movies", method = RequestMethod.GET)
	public List<MovieInfo> getMoviesInfo() {
		return movieServ.getMovies();
	}
	
	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.GET)
	public MovieInfo getMovieInfo(@PathVariable("movieId")  int id) {
		return movieServ.getMovie(id);
	}
	
	@RequestMapping(value = "/movies/stream/{movieId}", method = RequestMethod.GET)
	public ResponseEntity<ResourceRegion> streamMovie(@PathVariable("movieId") int movieId,
			@RequestHeader HttpHeaders headers) {
		return movieServ.getStream(movieId, headers);
	}
	
//	@RequestMapping(value = "/movies", method = RequestMethod.POST)
//	public void addMovieInfo(@RequestBody  MovieInfo movie) {
//		movieServ.addMovie(movie);
//	}
//	
//	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.PUT)
//	public void updateMovieInfo(@PathVariable("movieId")  int id, @RequestBody MovieInfo movie) {
//		movieServ.updateMovie(id, movie);
//	}
//	
//	@RequestMapping(value = "/movies/{movieId}", method = RequestMethod.DELETE)
//	public void removeMovieInfo(@PathVariable("movieId")  int id) {
//		movieServ.removeMovie(id);
//	}
}

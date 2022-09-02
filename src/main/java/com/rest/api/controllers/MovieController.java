package com.rest.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rest.api.exception.NotFoundException;
import com.rest.api.models.Director;
import com.rest.api.models.Movie;
import com.rest.api.repositories.DirectorRepository;
import com.rest.api.repositories.MovieRepository;

@RestController
@RequestMapping("/movies")
public class MovieController {

	@Autowired
	DirectorRepository directorRepository;

	@Autowired
	MovieRepository movieRepository;


	public void movieValidation(int movieId) {
		if (!movieRepository.existsById(movieId)) {
			throw new NotFoundException("Movie " + movieId + " Not found");
		}
	}

	@PostMapping("")
	public Movie createMovie(@Valid @RequestBody Movie movie) {
		return movieRepository.save(movie);
	}

	@GetMapping("")
	List<Movie> getMovies() {
		return movieRepository.findAll();
	}

	@PutMapping("/{id}")
	public Movie updateMovie(@PathVariable(value = "id") int movieId, @Valid @RequestBody Movie movieRequest) {
		this.movieValidation(movieId);
		Movie movie = movieRepository.findById(movieId);
		movie.setTitle(movieRequest.getTitle());
		movie.setRelease_date(movieRequest.getRelease_date());
		return movieRepository.save(movie);

	}

	@GetMapping("/{id}")
	public Movie getMovie(@PathVariable(value = "id") int movieId) {
		this.movieValidation(movieId);
		return movieRepository.findById(movieId);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteMovie(@PathVariable("id") int movieId) {
		this.movieValidation(movieId);
		Movie movie = movieRepository.findById(movieId);
		for (Director director : movie.getDirectors())
			movie.deleteDirectors(director);

		movieRepository.deleteById(movieId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}/directors")
	public Movie putMoviesDirectors(
			@Valid @PathVariable(value = "id") int movieId,
			@RequestBody HashMap<String, ArrayList<Integer>> directors) {
		this.movieValidation(movieId);
		ArrayList<Integer> directorIds = directors.get("directors");

		Movie movie = movieRepository.findById(movieId);
		for (int directorId : directorIds) {
			if (!directorRepository.existsById(directorId))
				throw new NotFoundException("Director " + directorId + " Not found");

			movie.addDirectors(directorRepository.findById(directorId));
		}
		return movieRepository.save(movie);
	}

	@DeleteMapping("/{id}/directors")
	public Movie deleteMoviesDirectors(@Valid @PathVariable(value = "id") int movieId,
									   @RequestBody HashMap<String, ArrayList<Integer>> directors) {
		this.movieValidation(movieId);
		ArrayList<Integer> directorIds = directors.get("directors");

		Movie movie = movieRepository.findById(movieId);
		for (int directorId : directorIds) {
			if (!directorRepository.existsById(directorId))
				throw new NotFoundException("Director " + directorId + " Not found");

			movie.deleteDirectors(directorRepository.findById(directorId));
		}
		return movieRepository.save(movie);
	}

}

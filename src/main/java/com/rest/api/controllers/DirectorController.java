package com.rest.api.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.rest.api.repositories.DirectorRepository;
import com.rest.api.repositories.MovieRepository;

@RestController
@RequestMapping("/directors")
public class DirectorController {

	@Autowired
	DirectorRepository directorRepository;

	@Autowired
	MovieRepository movieRepository;
	Logger logger = LoggerFactory.getLogger(DirectorController.class);

	public void directorValidation(int directorId) {
		if (!directorRepository.existsById(directorId)) {
			throw new NotFoundException("director " + directorId + " Not found");
		}
	}

	@PostMapping("")
	public Director createDirector(@Valid @RequestBody Director director) {
		logger.info("{}", director);
		return directorRepository.save(director);
	}

	@GetMapping("")
	List<Director> getDirectors() {
		return directorRepository.findAll();
	}

	@PutMapping("/{id}")
	public Director updateDirector(@PathVariable(value = "id") int directorId, @Valid @RequestBody Director directorRequest) {
		this.directorValidation(directorId);
		Director director = directorRepository.findById(directorId);
		director.setAge(directorRequest.getAge());
		director.setName(directorRequest.getName());
		director.setGender(director.getGender());
		return directorRepository.save(director);

	}

	@GetMapping("/{id}")
	public Director getDirector(@PathVariable("id") int directorId) {
		directorValidation(directorId);
		return directorRepository.findById(directorId);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteDirector(@PathVariable("id") int directorId) {
		this.directorValidation(directorId);

		directorRepository.deleteById(directorId);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}/movies")
	public Director directorMovies(@Valid @PathVariable(value="id") int directorId, @RequestBody HashMap<String, ArrayList<Integer>> movies) {
		this.directorValidation(directorId);
		ArrayList<Integer> movieIds= movies.get("movies");
		
		Director director = directorRepository.findById(directorId);
		for (int movieId : movieIds) {
			if (!movieRepository.existsById(movieId))
				throw new NotFoundException("Movie " + movieId + " Not found");

			director.addMovies(movieRepository.findById(movieId));
		}
		return directorRepository.save(director);
	}

	@DeleteMapping("/{id}/movies")
	public Director deleteDirectorMovies(@Valid @PathVariable(value = "id") int directorId,
										 @RequestBody HashMap<String, ArrayList<Integer>> movies) {
		ArrayList<Integer> movieIds = movies.get("movies");
		this.directorValidation(directorId);

		Director director = directorRepository.findById(directorId);
		for (int movieId : movieIds) {
			if (!movieRepository.existsById(movieId))
				throw new NotFoundException("Movie " + movieId + " Not found");
			director.deleteMovies(movieRepository.findById(movieId));
		}
		return directorRepository.save(director);
	}

}

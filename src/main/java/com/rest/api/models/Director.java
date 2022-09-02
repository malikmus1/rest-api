package com.rest.api.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Director {

	@Id
	@GeneratedValue
	int id;
	
	@Column(nullable = false)
	String name;

	@Column(nullable = false)
	String gender;

	@Column(nullable = false)
	int age;

	@JsonIgnoreProperties("directors")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "director_movie", joinColumns = { @JoinColumn(name = "director_id") }, inverseJoinColumns = {
			@JoinColumn(name = "movie_id") })
	Set<Movie> movies = new HashSet<Movie>();
	public Director() {
	}

	public Director(String name, String gender, int age) {
		super();
		this.name = name;
		this.gender = gender;
		this.age = age;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	public void addMovies(Movie movie) {
		this.getMovies().add(movie);
		movie.getDirectors().add(this);
	}

	public void deleteMovies(Movie movie) {
		this.getMovies().remove(movie);
		movie.getDirectors().remove(this);
	}

	@Override
	public String toString() {
		return "Director [id=" + id + ", name=" + name + ", Gender=" + gender + ", age=" + age + "]";
	}
}

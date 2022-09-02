package com.rest.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rest.api.models.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
	Director findById(int directorId);
}

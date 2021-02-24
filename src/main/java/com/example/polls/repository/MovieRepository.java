package com.example.polls.repository;

import com.example.polls.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    public Optional<Movie> findByMovieId(Long id);
}

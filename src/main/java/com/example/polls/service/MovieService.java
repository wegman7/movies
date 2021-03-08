package com.example.polls.service;

import com.example.polls.model.Movie;
import com.example.polls.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("movieService")
public class MovieService {

    Logger logger = LoggerFactory.getLogger(MovieService.class);

    @Autowired
    private MovieRepository movieRepository;

    public Set<Movie> getMoviesFromMovieTagIds(Set<Long> movieTagIds) {
        Set<Movie> movieTags = new HashSet<>();
        for (Long movieTagId : movieTagIds) {
            Optional<Movie> movie = movieRepository.findByMovieId(movieTagId);
            if (movie.isEmpty()) {
                Movie newMovie = new Movie(movieTagId);
                movieRepository.save(newMovie);
                movieTags.add(newMovie);
            } else {
                movieTags.add(movie.get());
            }
        }
        return movieTags;
    }

    public Movie getMovieFromMovieTagId(Long id) {
        Optional<Movie> movie = movieRepository.findByMovieId(id);
        Movie newMovie;
        if (movie.isEmpty()) {
            newMovie = new Movie(id);
            movieRepository.save(newMovie);
            return newMovie;
        } else {
            return movie.get();
        }
    }
}

package com.example.polls.controller;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Movie;
import com.example.polls.model.Profile;
import com.example.polls.model.User;
import com.example.polls.payload.AddMoviesToListRequest;
import com.example.polls.payload.CreateProfileRequest;
import com.example.polls.repository.ProfileRepository;
import com.example.polls.service.MovieService;
import com.example.polls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private ProfileRepository profileRepository;

    @PostMapping
    public ResponseEntity<Profile> create(
            @RequestHeader("Authorization") String jwt,
            @Valid @RequestBody CreateProfileRequest createProfileRequest) {
        User user = userService.getUserFromJwt(jwt);
        Set<Movie> likedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getLikedMovieIds());
        Set<Movie> watchedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getWatchedMovieIds());
        Set<Movie> watchlist = movieService.getMoviesFromMovieTagIds(createProfileRequest.getWatchlistIds());
        Set<Movie> recommendedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getRecommendedMovieIds());

        Profile profile = new Profile(user, likedMovies, watchedMovies, watchlist, recommendedMovies);
        profileRepository.save(profile);
        return new ResponseEntity<Profile>(profile, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Profile>> retrieve() {
        return new ResponseEntity<List<Profile>>(profileRepository.findAll(), HttpStatus.OK);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Profile> update(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateProfileRequest createProfileRequest) {
        User user = userService.getUserFromJwt(jwt);

        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", id));
        if (user != profile.getUser()) {
            throw new BadRequestException("You are not authorized to change this profile!");
        }

        Set<Movie> likedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getLikedMovieIds());
        Set<Movie> watchedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getWatchedMovieIds());
        Set<Movie> watchlist = movieService.getMoviesFromMovieTagIds(createProfileRequest.getWatchlistIds());
        Set<Movie> recommendedMovies = movieService.getMoviesFromMovieTagIds(createProfileRequest.getRecommendedMovieIds());

        profile.setLikedMovies(likedMovies);
        profile.setWatchedMovies(watchedMovies);
        profile.setWatchlist(watchlist);
        profile.setRecommendedMovies(recommendedMovies);
        profileRepository.save(profile);

        return new ResponseEntity<Profile>(profile, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(
            @RequestHeader("Authorization") String jwt,
            @PathVariable("id") Long id) {
        User user = userService.getUserFromJwt(jwt);

        Profile profile = profileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Profile", "Id", id));
        if (user != profile.getUser()) {
            throw new BadRequestException("You are not authorized to delete this profile!");
        }

        profileRepository.delete(profile);
        return new ResponseEntity<String>("Successfully deleted profile", HttpStatus.OK);
    }

    @PostMapping(path = "/add-movie")
    public ResponseEntity<String> addMovie(
            @RequestHeader("Authorization") String jwt,
            @Valid @RequestBody AddMoviesToListRequest addMoviesToListRequest) {
        User user = userService.getUserFromJwt(jwt);
        Profile profile = profileRepository.findByUser(user);
        Movie movie;

        Long likedMovieId = addMoviesToListRequest.getLikedMovieId();
        Long recommendedMovieId = addMoviesToListRequest.getRecommendedMovieId();
        Long watchedMovieId = addMoviesToListRequest.getWatchedMovieId();
        Long watchlistId = addMoviesToListRequest.getWatchlistMovieId();

        if (likedMovieId != null) {
            movie = movieService.getMovieFromMovieTagId(likedMovieId);
            if (profile.getLikedMovies().contains(movie)) {
                throw new BadRequestException("Already liked!");
            }
            profile.addLikedMovie(movie);
        } else if (recommendedMovieId != null) {
            movie = movieService.getMovieFromMovieTagId(recommendedMovieId);
            if (profile.getRecommendedMovies().contains(movie)) {
                throw new BadRequestException("Already recommended!");
            }
            profile.addRecommendedMovie(movie);
        } else if (watchedMovieId != null) {
            movie = movieService.getMovieFromMovieTagId(watchedMovieId);
            if (profile.getWatchedMovies().contains(movie)) {
                throw new BadRequestException("Already on watched list!");
            }
            profile.addWatchedMovie(movie);
        } else if (watchlistId != null) {
            movie = movieService.getMovieFromMovieTagId(watchlistId);
            if (profile.getWatchlist().contains(movie)) {
                throw new BadRequestException("Already on watchlist!");
            }
            profile.addWatchlist(movie);
        }
        profileRepository.save(profile);

        return new ResponseEntity<>("Successfully added movies.", HttpStatus.OK);
    }
}

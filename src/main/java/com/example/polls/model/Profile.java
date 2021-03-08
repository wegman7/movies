package com.example.polls.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @ManyToMany
    private Set<User> followers;

    @ManyToMany
    private Set<Movie> likedMovies;

    @ManyToMany
    private Set<Movie> watchedMovies;

    @ManyToMany
    private Set<Movie> watchlist;

    @ManyToMany
    private Set<Movie> recommendedMovies;

    public Profile() {}

    public Profile(User user) {
        this.user = user;
    }

    public Profile(User user, Set<Movie> likedMovies, Set<Movie> watchedMovies, Set<Movie> watchlist, Set<Movie> recommendedMovies) {
        this.user = user;
        this.likedMovies = likedMovies;
        this.watchedMovies = watchedMovies;
        this.watchlist = watchlist;
        this.recommendedMovies = recommendedMovies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<Movie> getLikedMovies() {
        return likedMovies;
    }

    public void setLikedMovies(Set<Movie> likedMovies) {
        this.likedMovies = likedMovies;
    }

    public Set<Movie> getWatchedMovies() {
        return watchedMovies;
    }

    public void setWatchedMovies(Set<Movie> watchedMovies) {
        this.watchedMovies = watchedMovies;
    }

    public Set<Movie> getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(Set<Movie> watchlist) {
        this.watchlist = watchlist;
    }

    public Set<Movie> getRecommendedMovies() {
        return recommendedMovies;
    }

    public void setRecommendedMovies(Set<Movie> recommendedMovies) {
        this.recommendedMovies = recommendedMovies;
    }

    public void addLikedMovie(Movie movie) {
        this.likedMovies.add(movie);
    }

    public void addRecommendedMovie(Movie movie) {
        this.recommendedMovies.add(movie);
    }

    public void addWatchedMovie(Movie movie) {
        this.watchedMovies.add(movie);
    }

    public void addWatchlist(Movie movie) {
        this.watchlist.add(movie);
    }
}

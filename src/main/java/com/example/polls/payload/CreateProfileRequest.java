package com.example.polls.payload;

import java.util.Set;

public class CreateProfileRequest {

    private Set<Long> likedMovieIds;

    private Set<Long> watchedMovieIds;

    private Set<Long> watchlistIds;

    private Set<Long> recommendedMovieIds;

    public Set<Long> getLikedMovieIds() {
        return likedMovieIds;
    }

    public void setLikedMovieIds(Set<Long> likedMovieIds) {
        this.likedMovieIds = likedMovieIds;
    }

    public Set<Long> getWatchedMovieIds() {
        return watchedMovieIds;
    }

    public void setWatchedMovieIds(Set<Long> watchedMovieIds) {
        this.watchedMovieIds = watchedMovieIds;
    }

    public Set<Long> getWatchlistIds() {
        return watchlistIds;
    }

    public void setWatchlistIds(Set<Long> watchlistIds) {
        this.watchlistIds = watchlistIds;
    }

    public Set<Long> getRecommendedMovieIds() {
        return recommendedMovieIds;
    }

    public void setRecommendedMovieIds(Set<Long> recommendedMovieIds) {
        this.recommendedMovieIds = recommendedMovieIds;
    }
}

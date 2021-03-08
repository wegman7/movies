package com.example.polls.payload;

import java.util.Set;

public class AddMoviesToListRequest {

    private Long likedMovieId;

    private Long watchedMovieId;

    private Long watchlistMovieId;

    private Long recommendedMovieId;

    public Long getLikedMovieId() {
        return likedMovieId;
    }

    public void setLikedMovieId(Long likedMovieId) {
        this.likedMovieId = likedMovieId;
    }

    public Long getWatchedMovieId() {
        return watchedMovieId;
    }

    public void setWatchedMovieId(Long watchedMovieId) {
        this.watchedMovieId = watchedMovieId;
    }

    public Long getWatchlistMovieId() {
        return watchlistMovieId;
    }

    public void setWatchlistMovieId(Long watchlistMovieId) {
        this.watchlistMovieId = watchlistMovieId;
    }

    public Long getRecommendedMovieId() {
        return recommendedMovieId;
    }

    public void setRecommendedMovieId(Long recommendedMovieId) {
        this.recommendedMovieId = recommendedMovieId;
    }
}

package com.example.polls.payload;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CreateRoomRequest {

    @NotBlank
    private String name;

    private Set<Long> movieTagIds;

    private Set<Long> userTagIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getMovieTagIds() {
        return movieTagIds;
    }

    public void setMovieTagIds(Set<Long> movieTagIds) {
        this.movieTagIds = movieTagIds;
    }

    public Set<Long> getUserTagIds() {
        return userTagIds;
    }

    public void setUserTagIds(Set<Long> userTagIds) {
        this.userTagIds = userTagIds;
    }
}

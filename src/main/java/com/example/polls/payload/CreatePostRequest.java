package com.example.polls.payload;

import com.example.polls.model.Movie;
import com.example.polls.model.Room;
import com.example.polls.model.User;

import javax.validation.constraints.NotBlank;
import java.util.Set;

public class CreatePostRequest {

    @NotBlank
    private String content;

    private Set<Long> movieTagIds;

    private Set<Long> userTagIds;

    private Long roomId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}

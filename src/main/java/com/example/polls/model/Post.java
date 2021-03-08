package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "post")
public class Post extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 300)
    private String content;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @ManyToMany
    private Set<Movie> movieTags;

    @ManyToMany
    private Set<User> userTags;

    // even though I'm not using this (no getters and setters), this is necessary for deleting comments
    // when parent post is deleted
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private Set<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    @ManyToMany
    private Set<User> likes;

    public Post() {}

    public Post(@NotBlank @Size(max = 300) String content, User user) {
        this.content = content;
        this.user = user;
    }

    public Post(@NotBlank @Size(max = 300) String content, User user, Set<Movie> movieTags, Set<User> userTags, Room room) {
        this.content = content;
        this.user = user;
        this.movieTags = movieTags;
        this.userTags = userTags;
        if (room != null) {
            this.room = room;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Movie> getMovieTags() {
        return movieTags;
    }

    public void setMovieTags(Set<Movie> movieTags) {
        this.movieTags = movieTags;
    }

    public Set<User> getUserTags() {
        return userTags;
    }

    public void setUserTags(Set<User> userTags) {
        this.userTags = userTags;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public void addLike(User user) {
        this.likes.add(user);
    }
}

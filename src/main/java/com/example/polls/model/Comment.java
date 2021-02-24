package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne
    private User user;

    @ManyToMany
    private Set<Movie> movieTags;

    @ManyToMany
    private Set<User> userTags;

    public Comment() {}

    public Comment(@NotBlank String content, Post post, User user, Set<Movie> movieTags, Set<User> userTags) {
        this.content = content;
        this.post = post;
        this.user = user;
        this.movieTags = movieTags;
        this.userTags = userTags;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
}

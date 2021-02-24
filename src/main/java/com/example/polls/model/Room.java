package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ManyToMany
    private Set<Movie> movieTags;

    @ManyToMany
    private Set<User> userTags;

    @OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
    private Set<Post> posts;

    @ManyToOne
    private User user;

    public Room() {}

    public Room(@NotBlank String name, Set<Movie> movieTags, Set<User> userTags, User user) {
        this.name = name;
        this.movieTags = movieTags;
        this.userTags = userTags;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

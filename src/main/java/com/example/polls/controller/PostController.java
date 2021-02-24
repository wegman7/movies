package com.example.polls.controller;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Movie;
import com.example.polls.model.Post;
import com.example.polls.model.Room;
import com.example.polls.model.User;
import com.example.polls.payload.CreatePostRequest;
import com.example.polls.repository.MovieRepository;
import com.example.polls.repository.PostRepository;
import com.example.polls.repository.RoomRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import com.example.polls.service.MovieService;
import com.example.polls.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/post")
public class PostController {

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<Post> create(@Valid @RequestBody CreatePostRequest postRequest, @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(postRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(postRequest.getUserTagIds());
        Room room;
        if (postRequest.getRoomId() != null) {
            room = roomRepository.findById(postRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", postRequest.getRoomId()));
        } else {
            room = null;
        }

        Post post = new Post(postRequest.getContent(), user, movieTags, userTags, room);
        postRepository.save(post);

        return new ResponseEntity<Post>(post, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Post>> retrieve() {
        List<Post> posts = postRepository.findAll();
        Collections.reverse(posts);
        return new ResponseEntity<List<Post>>(posts, HttpStatus.OK);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Post> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreatePostRequest postRequest,
            @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        if (user != post.getUser()) {
            throw new BadRequestException("You are not authorized to update this post!");
        }

        Room room;
        if (postRequest.getRoomId() != null) {
            room = roomRepository.findById(postRequest.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room", "Id", postRequest.getRoomId()));
            logger.info(room.getName());
        } else {
            room = null;
        }

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(postRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(postRequest.getUserTagIds());

        post.setContent(postRequest.getContent());
        post.setMovieTags(movieTags);
        post.setUserTags(userTags);
        post.setRoom(room);
        postRepository.save(post);

        return new ResponseEntity<Post>(post, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));
        if (user != post.getUser()) {
            throw new BadRequestException("You are not authorized to update this post!");
        }

        postRepository.deleteById(id);
        return new ResponseEntity<String>("Successfully deleted post.", HttpStatus.OK);
    }
}

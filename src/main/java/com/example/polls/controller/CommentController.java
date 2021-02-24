package com.example.polls.controller;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.*;
import com.example.polls.payload.CreateCommentRequest;
import com.example.polls.payload.CreatePostRequest;
import com.example.polls.repository.CommentRepository;
import com.example.polls.repository.MovieRepository;
import com.example.polls.repository.PostRepository;
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
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/comment")
public class CommentController {

    Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

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

    @PostMapping
    public ResponseEntity<Comment> create(@Valid @RequestBody CreateCommentRequest commentRequest, @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(commentRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(commentRequest.getUserTagIds());
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", commentRequest.getPostId()));

        Comment comment = new Comment(commentRequest.getContent(), post, user, movieTags, userTags);
        commentRepository.save(comment);

        return new ResponseEntity<Comment>(comment, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Comment>> retrieve() {
        return new ResponseEntity<List<Comment>>(commentRepository.findAll(), HttpStatus.OK);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Comment> update(
            @PathVariable("id") Long id,
            @Valid @RequestBody CreateCommentRequest commentRequest,
            @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        if (user != comment.getUser()) {
            throw new BadRequestException("You are not authorized to update this comment!");
        }

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(commentRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(commentRequest.getUserTagIds());
        Post post = postRepository.findById(commentRequest.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", commentRequest.getPostId()));

        comment.setContent(commentRequest.getContent());
        comment.setMovieTags(movieTags);
        comment.setUserTags(userTags);
        commentRepository.save(comment);

        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) {
        User user = userService.getUserFromJwt(jwt);

        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        if (user != comment.getUser()) {
            throw new BadRequestException("You are not authorized to update this comment!");
        }

        commentRepository.deleteById(id);
        return new ResponseEntity<String>("Successfully deleted comment.", HttpStatus.OK);
    }
}

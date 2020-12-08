package com.example.polls.controller;

import com.example.polls.model.Post;
import com.example.polls.payload.CreateOrUpdatePostRequest;
import com.example.polls.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping
    public void create(@Valid @RequestBody CreateOrUpdatePostRequest post, @RequestHeader("Authorization") String jwt) {
        postService.create(post.getTitle(), post.getContent(), jwt);
    }

    @PutMapping(path = "{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody CreateOrUpdatePostRequest post, @RequestHeader("Authorization") String jwt) {
        postService.update(id, post.getTitle(), post.getContent(), jwt);
    }

    @GetMapping
    public List<Post> findAll() {
        return postService.findAll();
    }

    @GetMapping(path = "{id}")
    public Optional<Post> findListsById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Long id) {
        postService.deleteById(id);
    }

    @GetMapping(value = "/my-posts")
    public List<Post> findListsByUser(@RequestHeader("Authorization") String jwt) {
        return postService.findByUser(jwt);
    }
}

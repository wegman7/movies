package com.example.polls.service;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Post;
import com.example.polls.model.User;
import com.example.polls.repository.PostRepository;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("postService")
public class PostService {

    Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public void create(String title, String content, String jwt) {
        jwt = jwt.substring(7, jwt.length());
        Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).get();

        Post post = new Post(title, content, user);
        postRepository.save(post);
    }

    public void update(Long id, String title, String content, String jwt) {
        Optional<Post> post_optional = postRepository.findById(id);
        Post post = post_optional.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

        jwt = jwt.substring(7, jwt.length());
        Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).get();

        // if user is not the user of this post, we cannot let them update it
        if (user != post.getUser()) {
            throw new BadRequestException("You are not authorized to update this post!");
        }

        post.setTitle(title);
        post.setContent(content);
        postRepository.save(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public void deleteById(Long id) { postRepository.deleteById(id);}

    public List<Post> findByUser(String jwt) {
        jwt = jwt.substring(7, jwt.length());
        Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        return postRepository.findByUser_Id(userId);
    }
}

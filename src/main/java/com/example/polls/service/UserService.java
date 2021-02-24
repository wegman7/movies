package com.example.polls.service;

import com.example.polls.model.User;
import com.example.polls.repository.UserRepository;
import com.example.polls.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service("userService")
public class UserService {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserRepository userRepository;

    public User getUserFromJwt(String jwt) {
        jwt = jwt.substring(7, jwt.length());
        Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        User user = userRepository.findById(userId).get();
        return user;
    }

    public Set<User> getUsersFromUserTagIds(Set<Long> userTagIds) {
        Set<User> users = new HashSet<>();
        for (Long userTagId : userTagIds) {
            Optional<User> user = userRepository.findById(userTagId);
            if (user.isPresent()) {
                users.add(user.get());
            }
        }
        return users;
    }
}

package com.example.polls.service;

import com.example.polls.model.UserList;
import com.example.polls.model.UserListItem;
import com.example.polls.repository.UserListItemRepository;
import com.example.polls.repository.UserListRepository;
import com.example.polls.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userListService")
public class UserListService {

    Logger logger = LoggerFactory.getLogger(UserListService.class);

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private UserListItemRepository userListItemRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    public List<UserList> findAll() {
        return userListRepository.findAll();
    }

    public List<UserList> findByUser(String jwt) {
        jwt = jwt.substring(7, jwt.length());
        Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        return userListRepository.findByUser_Id(userId);
    }

    public Optional<UserListItem> findItemsByListId(Long id) {
        return userListItemRepository.findByUserList_UserListId(id);
    }
}

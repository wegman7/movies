package com.example.polls.controller;

import com.example.polls.model.UserList;
import com.example.polls.model.UserListItem;
import com.example.polls.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-list")
public class UserListController {

    @Autowired
    UserListService userListService;

    @GetMapping
    public List<UserList> findAll() {
        return userListService.findAll();
    }

    @GetMapping(value = "/my-lists")
    public List<UserList> findListsByUser(@RequestHeader("Authorization") String jwt) {
        return userListService.findByUser(jwt);
    }
}

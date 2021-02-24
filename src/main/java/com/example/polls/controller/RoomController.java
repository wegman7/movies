package com.example.polls.controller;

import com.example.polls.exception.BadRequestException;
import com.example.polls.exception.ResourceNotFoundException;
import com.example.polls.model.Movie;
import com.example.polls.model.Post;
import com.example.polls.model.Room;
import com.example.polls.model.User;
import com.example.polls.payload.CreateRoomRequest;
import com.example.polls.repository.RoomRepository;
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
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/room")
public class RoomController {

    private Logger logger = LoggerFactory.getLogger(RoomController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private RoomRepository roomRepository;

    @PostMapping
    public ResponseEntity<Room> create(@RequestHeader("Authorization") String jwt, @Valid @RequestBody CreateRoomRequest createRoomRequest) {
        User user = userService.getUserFromJwt(jwt);

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(createRoomRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(createRoomRequest.getUserTagIds());

        Room room = new Room(createRoomRequest.getName(), movieTags, userTags, user);
        roomRepository.save(room);

        return new ResponseEntity<Room>(room, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Room>> retrieve() {
        return new ResponseEntity<List<Room>>(roomRepository.findAll(), HttpStatus.OK);
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity<Room> update(
            @RequestHeader("Authorization") String jwt,
            @Valid @RequestBody CreateRoomRequest createRoomRequest,
            @PathVariable("id") Long id) {
        User user = userService.getUserFromJwt(jwt);

        Set<Movie> movieTags = movieService.getMoviesFromMovieTagIds(createRoomRequest.getMovieTagIds());
        Set<User> userTags = userService.getUsersFromUserTagIds(createRoomRequest.getUserTagIds());

        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", id));
        room.setName(createRoomRequest.getName());
        room.setMovieTags(movieTags);
        room.setUserTags(userTags);

        if (user != room.getUser()) {
            throw new BadRequestException("You are not authorized to update this room!");
        }

        roomRepository.save(room);
        return new ResponseEntity<Room>(room, HttpStatus.OK);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String jwt, @PathVariable("id") Long id) {
        User user = userService.getUserFromJwt(jwt);

        Room room = roomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Room", "Id", id));

        if (user != room.getUser()) {
            throw new BadRequestException("You are not authorized to update this room!");
        }

        roomRepository.delete(room);
        return new ResponseEntity<String>("Successfully deleted room.", HttpStatus.OK);
    }
}

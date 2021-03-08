package com.example.polls.controller;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/movies")
public class MovieController {

    Logger logger = LoggerFactory.getLogger(MovieController.class);

    @Value("${app.tmdbApiAccessKey}")
    private String apiKey;

    @GetMapping("/search/movie/{query}")
    public ResponseEntity<String> searchMovies(@PathVariable("query") String query) throws IOException {
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + apiKey + "&query=" + query.replace(" ", "+");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new ResponseEntity<String>(response.body().string(), HttpStatus.OK);
        }
    }

    @GetMapping("/search/person/{query}")
    public ResponseEntity<String> searchPeople(@PathVariable("query") String query) throws IOException {
        String url = "https://api.themoviedb.org/3/search/person?api_key=" + apiKey + "&query=" + query.replace(" ", "+");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new ResponseEntity<String>(response.body().string(), HttpStatus.OK);
        }
    }

    @GetMapping("/search/multi/{query}")
    public ResponseEntity<String> searchMulti(@PathVariable("query") String query) throws IOException {
        String url = "https://api.themoviedb.org/3/search/multi?api_key=" + apiKey + "&query=" + query.replace(" ", "+");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new ResponseEntity<String>(response.body().string(), HttpStatus.OK);
        }
    }

    @GetMapping("/trending")
    public ResponseEntity<String> getTrending() throws IOException {
        String url = "https://api.themoviedb.org/3/trending/movie/week?api_key=" + apiKey;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return new ResponseEntity<String>(response.body().string(), HttpStatus.OK);
        }
    }
}
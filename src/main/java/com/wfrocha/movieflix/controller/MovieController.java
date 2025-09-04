package com.wfrocha.movieflix.controller;

import com.wfrocha.movieflix.controller.request.MovieRequest;
import com.wfrocha.movieflix.entity.Movie;
import com.wfrocha.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<Movie> save(@RequestBody MovieRequest movieRequest) {
        Movie movie = new Movie();
        movie.setTitle(movieRequest.title());
        movie.setDescription(movieRequest.description());
        movie.setRating(movieRequest.rating());


        Movie savedMovie = movieService.save(movie);
        return ResponseEntity.ok(savedMovie);
    }

}

package com.wfrocha.movieflix.controller;

import com.wfrocha.movieflix.controller.request.MovieRequest;
import com.wfrocha.movieflix.controller.response.MovieResponse;
import com.wfrocha.movieflix.entity.Movie;
import com.wfrocha.movieflix.mapper.MovieMapper;
import com.wfrocha.movieflix.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/movieflix/movie")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    // Criar filme
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest request) {
        Movie movie = MovieMapper.toMovie(request);
        Movie savedMovie = movieService.save(movie);
        MovieResponse response = MovieMapper.toMovieResponse(savedMovie);

        return ResponseEntity
                .created(URI.create("/movieflix/movie/" + savedMovie.getId()))
                .body(response);
    }

    // Listar todos os filmes
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies() {
        List<Movie> movies = movieService.findAll();
        List<MovieResponse> responses = MovieMapper.toMovieResponseList(movies);
        return ResponseEntity.ok(responses);
    }

    // Buscar filme por ID
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return ResponseEntity.ok(MovieMapper.toMovieResponse(movie));
    }

    // Atualizar filme
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id,
                                                     @RequestBody MovieRequest request) {
        Movie updatedMovie = movieService.update(id, request);
        return ResponseEntity.ok(MovieMapper.toMovieResponse(updatedMovie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long id) {
        return movieService.findById(id)
                .map(movie -> {
                    movieService.deleteById(id);
                    return ResponseEntity.ok("Filme com ID " + id + " deletado com sucesso!");
                })
                .orElseGet(() -> ResponseEntity.status(404)
                        .body("Filme com ID " + id + " não encontrado"));
    }
}

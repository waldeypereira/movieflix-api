package com.wfrocha.movieflix.service;

import com.wfrocha.movieflix.entity.Category;
import com.wfrocha.movieflix.entity.Movie;
import com.wfrocha.movieflix.entity.Streaming;
import com.wfrocha.movieflix.repository.CategoryRepository;
import com.wfrocha.movieflix.repository.MovieRepository;
import com.wfrocha.movieflix.repository.StreamingRepository;
import com.wfrocha.movieflix.controller.request.MovieRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    private final CategoryRepository categoryRepository;
    private final StreamingRepository streamingRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(long id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        // Carrega categorias e streamings completos antes de salvar
        List<Category> categories = movie.getCategories().stream()
                .map(cat -> categoryRepository.findById(cat.getId())
                        .orElseThrow(() -> new RuntimeException("Category not found: " + cat.getId())))
                .toList();

        List<Streaming> streamings = movie.getStreamings().stream()
                .map(st -> streamingRepository.findById(st.getId())
                        .orElseThrow(() -> new RuntimeException("Streaming not found: " + st.getId())))
                .toList();

        movie.setCategories(categories);
        movie.setStreamings(streamings);

        return movieRepository.save(movie);
    }

    public Movie update(long id, MovieRequest request) {
        return movieRepository.findById(id).map(existing -> {
            // Atualiza categorias e streamings
            List<Category> categories = request.categories().stream()
                    .map(catId -> categoryRepository.findById(catId)
                            .orElseThrow(() -> new RuntimeException("Category not found: " + catId)))
                    .toList();

            List<Streaming> streamings = request.streamings().stream()
                    .map(stId -> streamingRepository.findById(stId)
                            .orElseThrow(() -> new RuntimeException("Streaming not found: " + stId)))
                    .toList();

            // Atualiza campos existentes via setters
            existing.setTitle(request.title());
            existing.setDescription(request.description());
            existing.setReleaseDate(request.releaseDate());
            existing.setRating(request.rating());
            existing.setCategories(categories);
            existing.setStreamings(streamings);

            return movieRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public void deleteById(long id) {
        movieRepository.deleteById(id);
    }
}

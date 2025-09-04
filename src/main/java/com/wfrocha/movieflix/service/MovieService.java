package com.wfrocha.movieflix.service;

import com.wfrocha.movieflix.controller.request.MovieRequest;
import com.wfrocha.movieflix.entity.Category;
import com.wfrocha.movieflix.entity.Movie;
import com.wfrocha.movieflix.entity.Streaming;
import com.wfrocha.movieflix.repository.CategoryRepository;
import com.wfrocha.movieflix.repository.MovieRepository;
import com.wfrocha.movieflix.repository.StreamingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Movie save(Movie request) {
        List<Category> categories = request.getCategories() != null
                ? request.getCategories().stream()
                .map(category -> categoryRepository.findById(category.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        List<Streaming> streamings = request.getStreamings() != null
                ? request.getStreamings().stream()
                .map(streaming -> streamingRepository.findById(streaming.getId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toCollection(ArrayList::new))
                : new ArrayList<>();

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .releaseDate(request.getReleaseDate())
                .rating(request.getRating())
                .categories(categories)
                .streamings(streamings)
                .build();

        return movieRepository.save(movie);
    }

    public Movie update(long id, MovieRequest request) {
        return movieRepository.findById(id).map(existing -> {

            List<Category> categories = request.categories().stream()
                    .map(categoryId -> categoryRepository.findById(categoryId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toCollection(ArrayList::new));

            List<Streaming> streamings = request.streamings().stream()
                    .map(streamingId -> streamingRepository.findById(streamingId))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toCollection(ArrayList::new));

            existing.setTitle(request.title());
            existing.setDescription(request.description());
            existing.setReleaseDate(request.releaseDate());
            existing.setRating(request.rating());
            existing.setCategories(categories);  // agora mutável
            existing.setStreamings(streamings);  // agora mutável

            return movieRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public void deleteById(long id) {
        movieRepository.deleteById(id);
    }
}

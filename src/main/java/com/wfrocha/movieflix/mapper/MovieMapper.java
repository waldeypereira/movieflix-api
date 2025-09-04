package com.wfrocha.movieflix.mapper;

import com.wfrocha.movieflix.controller.request.MovieRequest;
import com.wfrocha.movieflix.controller.response.MovieResponse;
import com.wfrocha.movieflix.entity.Category;
import com.wfrocha.movieflix.entity.Movie;
import com.wfrocha.movieflix.entity.Streaming;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class MovieMapper {

    public static Movie toMovie(MovieRequest request) {
        return Movie.builder()
                .title(request.title())
                .description(request.description())
                .releaseDate(request.releaseDate())
                .rating(request.rating())
                .categories(
                        request.categories().stream()
                                .map(categoryId -> Category.builder().id(categoryId).build())
                                .collect(Collectors.toList())
                )
                .streamings(
                        request.streamings().stream()
                                .map(streamingId -> Streaming.builder().id(streamingId).build())
                                .collect(Collectors.toList())
                )
                .build();
    }

    public static MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseDate(movie.getReleaseDate())
                .rating(movie.getRating())
                .categories(CategoryMapper.toCategoryResponseList(movie.getCategories()))
                .streamings(StreamingMapper.toStreamingResponseList(movie.getStreamings()))
                .build();
    }

    public static List<MovieResponse> toMovieResponseList(List<Movie> movies) {
        return movies.stream()
                .map(MovieMapper::toMovieResponse)
                .toList();
    }
}

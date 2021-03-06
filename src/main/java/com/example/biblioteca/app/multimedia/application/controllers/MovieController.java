package com.example.biblioteca.app.multimedia.application.controllers;

import com.example.biblioteca.app.multimedia.application.requests.MovieRequestVM;
import com.example.biblioteca.app.multimedia.application.responses.MovieCreatedResponse;
import com.example.biblioteca.app.multimedia.application.responses.MovieResponseVM;
import com.example.biblioteca.modules.multimedia.movies.application.MovieService;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@AllArgsConstructor
@RequestMapping("v1/movie")
@RestController
public class MovieController {

    private final MovieService service;

    @PostMapping
    public ResponseEntity createMovie(@RequestBody MovieRequestVM requestVM) {
        UUID movieId = service.createMovie(requestVM.name, requestVM.year);
        MovieCreatedResponse response = new MovieCreatedResponse(movieId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseVM>> getAllMovies() {
        List<Movie> fetchedMovies = service.getAllMovies();
        List<MovieResponseVM> movies = fetchedMovies
                .stream()
                .map(this::mapMovieToResponseVM)
                .collect(toList());

        if (movies.size() > 0) {
            return ResponseEntity.ok(movies);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<MovieResponseVM> getMovieById(@PathVariable("id") UUID id) {
        Optional<Movie> movieMaybe = service.getMovieById(id);
        if (movieMaybe.isPresent()) {
            MovieResponseVM movie = mapMovieToResponseVM(movieMaybe.get());
            return ResponseEntity.ok(movie);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteMovie(@PathVariable("id") UUID id) {
        service.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateMovie(@PathVariable("id") UUID id, @RequestBody MovieRequestVM requestVM) {
        service.updateMovie(id, requestVM.name, requestVM.year);
        return ResponseEntity.noContent().build();
    }

    private MovieResponseVM mapMovieToResponseVM(Movie movie) {
        return new MovieResponseVM(
                movie.getId().toString(),
                movie.getName().getValue(),
                movie.getYear().getValue()
        );
    }
}

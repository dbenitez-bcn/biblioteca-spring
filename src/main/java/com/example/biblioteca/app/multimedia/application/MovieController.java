package com.example.biblioteca.app.multimedia.application;

import com.example.biblioteca.app.multimedia.requests.MovieRequestVM;
import com.example.biblioteca.app.multimedia.responses.MovieResponseVM;
import com.example.biblioteca.modules.multimedia.movies.application.MovieService;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@RequestMapping("v1/movie")
public class MovieController {

    private final MovieService service;

    @PostMapping
    public ResponseEntity createMovie(@RequestBody MovieRequestVM requestVM) {
        service.createMovie(requestVM.name, requestVM.year);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MovieResponseVM>> getAllMovies() {
        List<Movie> fetchedMovies = service.getAllMovies();
        List<MovieResponseVM> movies = fetchedMovies
                .stream()
                .map(this::mapMovieToResponseVM)
                .collect(Collectors.toList());

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

    private MovieResponseVM mapMovieToResponseVM(Movie movie) {
        return new MovieResponseVM(
                movie.getId().toString(),
                movie.getName().getValue(),
                movie.getYear().getValue()
        );
    }
}

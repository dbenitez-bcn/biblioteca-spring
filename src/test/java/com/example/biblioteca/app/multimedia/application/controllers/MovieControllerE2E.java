package com.example.biblioteca.app.multimedia.application.controllers;

import com.example.biblioteca.ApplicationTestCase;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.randomMovie;

class MovieControllerE2E extends ApplicationTestCase {

    //@Test
    void happyPath_userCreatesAMovieThatCouldFind() throws Exception {
        Movie movie = randomMovie();

        // User doesn't find the movie is looking for
        assertRequest(
                HttpMethod.GET.toString(),
                "/v1/movie/" + movie.getId().toString(),
                HttpStatus.NOT_FOUND.value()
        );

        // User creates a movie
        JSONObject movieBody = new JSONObject();
        movieBody.put("name", movie.getName().getValue());
        movieBody.put("year", movie.getYear().getValue());

        assertRequestWithBody(
                HttpMethod.POST.toString(),
                "/v1/movie",
                movieBody.toString(),
                HttpStatus.CREATED.value()
        );

        // User search for all movies
        assertRequest(
                HttpMethod.GET.toString(),
                "/v1/movie/" + movie.getId().toString(),
                HttpStatus.OK.value()
        );
    }
}
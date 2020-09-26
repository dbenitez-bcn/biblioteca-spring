package com.example.biblioteca.app.multimedia.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.modules.multimedia.movies.domain.aggregates.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static com.example.biblioteca.app.multimedia.infrastructure.postgresql.fixtures.MovieEntityFixture.customMovieEntity;
import static com.example.biblioteca.modules.multimedia.movies.domain.fixtures.MovieFixture.randomMovie;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostgresMovieRepositoryIT {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private  MoviesRepositoryJPA moviesRepositoryJPA;

    @Autowired
    private PostgresMovieRepository sut;

    @Test
    public void upsert_whenMoviesDoesNotExist_shouldCreateANewMovie() {
        Movie movieToSave = randomMovie();
        MovieEntity expectedMovieFound = customMovieEntity(movieToSave);

        MovieEntity movieFoundBeforeSave = entityManager.find(MovieEntity.class, movieToSave.getId().toString());
        sut.upsert(movieToSave);
        MovieEntity movieFoundAfterSave = entityManager.find(MovieEntity.class, expectedMovieFound.getId());

        assertThat(movieFoundBeforeSave).isNull();
        assertThat(movieFoundAfterSave).isEqualTo(expectedMovieFound);
    }
}
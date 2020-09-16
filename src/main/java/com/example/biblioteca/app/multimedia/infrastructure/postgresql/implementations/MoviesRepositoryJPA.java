package com.example.biblioteca.app.multimedia.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MoviesRepositoryJPA extends JpaRepository<MovieEntity, String> {
}

package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.MovieEntity;
import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieNotFound;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.example.biblioteca.app.rentals.infrastructure.fixtures.RentalEntityFixture.customMovieEntity;
import static com.example.biblioteca.app.rentals.infrastructure.fixtures.RentalEntityFixture.defaultRentalEntity;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class PostgresRentalRepositoryTest {

    @Mock
    private RentalRepositoryJPA rentalRepositoryJPA;

    @Mock
    private MovieRepositoryJPA movieRepositoryJPA;

    @InjectMocks
    private PostgresRentalRepository sut;

    @Captor
    private ArgumentCaptor<RentalEntity> rentalCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void save_whenMovieExist_shouldSaveANewRental() {
        Rental rentalToSave = defaultRental();
        MovieEntity movieEntity = customMovieEntity(rentalToSave.getMovieId().getValue(), "MovieName");
        when(movieRepositoryJPA.findById(movieEntity.getId())).thenReturn(Optional.of(movieEntity));

        sut.save(rentalToSave);

        verify(rentalRepositoryJPA).save(rentalCaptor.capture());
        RentalEntity rentalCaptured = rentalCaptor.getValue();
        assertThat(rentalCaptured.getMovieId())
            .isEqualTo(rentalToSave.getMovieId()
                                   .getValue());
        assertThat(rentalCaptured.getUserId())
            .isEqualTo(rentalToSave.getUserId()
                                   .getValue());
        assertThat(rentalCaptured.getMovie()).isEqualTo(movieEntity);
    }

    @Test
    void save_whenMovieNotExist_shouldFail() {
        Rental rentalToSave = defaultRental();
        when(movieRepositoryJPA.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sut.save(rentalToSave))
                .isInstanceOf(MovieNotFound.class);
    }

    @Test
    void findByMovie_whenRentalExist_shouldReturnTheRental() {
        RentalEntity rentalFound = defaultRentalEntity();
        Rental expectedRental = customRental(rentalFound.getMovieId(), rentalFound.getUserId());
        when(rentalRepositoryJPA.findById(rentalFound.getMovieId())).thenReturn(Optional.of(rentalFound));

        Optional<Rental> result = sut.findByMovie(new MovieId(rentalFound.getMovieId()));

        assertThat(result).contains(expectedRental);
    }

    @Test
    void findByMovie_whenRentalNotExist_shouldReturnEmpty() {
        when(rentalRepositoryJPA.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Rental> result = sut.findByMovie(new MovieId(MOVIE_ID));

        assertThat(result).isEmpty();
    }

    @Test
    void removeByMovie_shouldRemoveTheRentalForTheGivenId() {
        sut.removeByMovie(new MovieId(MOVIE_ID));

        verify(rentalRepositoryJPA).deleteById(MOVIE_ID);
    }
}
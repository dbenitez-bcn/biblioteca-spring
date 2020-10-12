package com.example.biblioteca.app.rentals.infrastructure.postgresql.implementations;

import static com.example.biblioteca.app.rentals.infrastructure.fixtures.RentalEntityFixture.defaultRentalEntity;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.MOVIE_ID;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.customRental;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.defaultRental;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.example.biblioteca.app.rentals.infrastructure.postgresql.entities.RentalEntity;
import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class PostgresRentalRepositoryTest {

    @Mock
    private RentalRepositoryJPA repositoryJPA;

    @InjectMocks
    private PostgresRentalRepository sut;

    @Captor
    private ArgumentCaptor<RentalEntity> rentalCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void rent_shouldSaveANewRental() {
        Rental rentalToSave = defaultRental();

        sut.rent(rentalToSave);

        verify(repositoryJPA).save(rentalCaptor.capture());
        RentalEntity rentalCaptured = rentalCaptor.getValue();
        assertThat(rentalCaptured.getMovieId())
            .isEqualTo(rentalToSave.getMovieId()
                                   .getValue());
        assertThat(rentalCaptured.getUserId())
            .isEqualTo(rentalToSave.getUserId()
                                   .getValue());
    }

    @Test
    void findByMovie_whenRentalExist_shouldReturnTheRental() {
        RentalEntity rentalFound = defaultRentalEntity();
        Rental expectedRental = customRental(rentalFound.getMovieId(), rentalFound.getUserId());
        when(repositoryJPA.findById(rentalFound.getMovieId())).thenReturn(Optional.of(rentalFound));

        Optional<Rental> result = sut.findByMovie(new MovieId(rentalFound.getMovieId()));

        assertThat(result).contains(expectedRental);
    }

    @Test
    void findByMovie_whenRentalNotExist_shouldReturnEmpty() {
        when(repositoryJPA.findById(any(UUID.class))).thenReturn(Optional.empty());

        Optional<Rental> result = sut.findByMovie(new MovieId(MOVIE_ID));

        assertThat(result).isEmpty();
    }
}
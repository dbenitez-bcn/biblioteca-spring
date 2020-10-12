package com.example.biblioteca.modules.rentals.application;

import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.MOVIE_ID;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.USER_ID;
import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.customRental;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieAlreadyRented;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class RentalsServiceTest {

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private RentalsService sut;

    @Captor
    private ArgumentCaptor<Rental> rentalCaptor;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void rent_shouldRentAMovie() {
        sut.rent(MOVIE_ID, USER_ID);

        verify(rentalRepository).rent(rentalCaptor.capture());
        assertThat(rentalCaptor.getValue().getMovieId().getValue()).isEqualTo(MOVIE_ID);
        assertThat(rentalCaptor.getValue().getUserId().getValue()).isEqualTo(USER_ID);
    }

    @Test
    void rent_whenMovieAlreadyRented_shouldThrowMovieAlreadyRented() {
        when(rentalRepository.findByMovie(new MovieId(MOVIE_ID)))
            .thenReturn(Optional.of(customRental(MOVIE_ID, USER_ID)));

        assertThatThrownBy(() -> sut.rent(MOVIE_ID, USER_ID))
            .isInstanceOf(MovieAlreadyRented.class);
    }
}
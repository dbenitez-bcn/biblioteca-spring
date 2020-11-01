package com.example.biblioteca.modules.rentals.application;

import com.example.biblioteca.modules.rentals.domain.aggregates.Rental;
import com.example.biblioteca.modules.rentals.domain.exceptions.CheckoutNotAllowed;
import com.example.biblioteca.modules.rentals.domain.exceptions.MovieAlreadyRented;
import com.example.biblioteca.modules.rentals.domain.valueObjects.MovieId;
import com.example.biblioteca.modules.rentals.repositories.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static com.example.biblioteca.modules.rentals.domain.fixtures.RentalFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

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

        verify(rentalRepository).save(rentalCaptor.capture());
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

    @Test
    void checkout_whenRentalIsFound_shouldCheckoutAMovie() {
        when(rentalRepository.findByMovie(new MovieId(MOVIE_ID)))
                .thenReturn(Optional.of(customRental(MOVIE_ID, USER_ID)));

        sut.checkout(MOVIE_ID, USER_ID);

        verify(rentalRepository).removeByMovie(new MovieId(MOVIE_ID));
    }

    @Test
    void checkout_whenMovieDoesntBelongToUser_shouldThrowCheckoutNotAllowed() {
        when(rentalRepository.findByMovie(new MovieId(MOVIE_ID)))
                .thenReturn(Optional.of(customRental(MOVIE_ID, USER_ID)));

        assertThatThrownBy(() -> sut.checkout(MOVIE_ID, UUID.randomUUID()))
                .isInstanceOf(CheckoutNotAllowed.class);

        verify(rentalRepository, never()).removeByMovie(any(MovieId.class));
    }

    @Test
    void checkout_whenNoRentalIsFound_shouldDoNothing() {
        when(rentalRepository.findByMovie(new MovieId(MOVIE_ID))).thenReturn(Optional.empty());

        sut.checkout(MOVIE_ID, USER_ID);

        verify(rentalRepository, never()).removeByMovie(any(MovieId.class));
    }
}
package com.example.biblioteca.app.shared.exceptions;

import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler sut = new GlobalExceptionHandler();

    @Test
    void invalidNameForMovie_shouldHandleInvalidNameForMovie() {
        ResponseEntity<ErrorDetails> result = sut.invalidNameForMovie();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid movie name");
    }

    @Test
    void invalidYearForMovie_shouldHandleInvalidYearForMovie() {
        ResponseEntity<ErrorDetails> result = sut.invalidYearForMovie();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid movie year");
    }

    @Test
    void emailAlreadyInUse_shouldHandleEmailAlreadyInUse() {
        ResponseEntity<ErrorDetails> result = sut.emailAlreadyInUse();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Email is already in use");
    }

    @Test
    void invalidEmailAddress_shouldHandleInvalidEmailAddress() {
        ResponseEntity<ErrorDetails> result = sut.invalidEmailAddress();

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid email address");
    }

    @Test
    void invalidPasswordFormat_shouldHandleInvalidPasswordFormat() {
        InvalidPasswordFormat exception = new InvalidPasswordFormat();

        ResponseEntity<ErrorDetails> result = sut.invalidPasswordFormat(exception);

        assertThat(result.getStatusCodeValue()).isEqualTo(422);
        assertThat(result.getBody().message).isEqualTo("Invalid password format: " + exception.getMessage());
    }

    @Test
    void loginFailed_shouldHandleLoginFailed() {
        ResponseEntity<ErrorDetails> result = sut.loginFailed();

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody().message).isEqualTo("Invalid email or password");
    }

    @Test
    void movieAlreadyRented_shouldHandleMovieAlreadyRented() {
        ResponseEntity<ErrorDetails> result = sut.movieAlreadyRented();

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody().message).isEqualTo("Movie is already rented");
    }

    @Test
    void checkoutNotAllowed_shouldHandleCheckoutNotAllowed() {
        ResponseEntity<ErrorDetails> result = sut.checkoutNotAllowed();

        assertThat(result.getStatusCodeValue()).isEqualTo(403);
        assertThat(result.getBody().message).isEqualTo("This movie is not rented by you");
    }

    @Test
    void movieNotFound_shouldMovieNotFound() {
        ResponseEntity<ErrorDetails> result = sut.movieNotFound();

        assertThat(result.getStatusCodeValue()).isEqualTo(404);
        assertThat(result.getBody().message).isEqualTo("This movie is not in biblioteca");
    }
}
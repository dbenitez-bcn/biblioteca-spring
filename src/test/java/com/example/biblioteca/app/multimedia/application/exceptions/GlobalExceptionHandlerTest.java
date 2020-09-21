package com.example.biblioteca.app.multimedia.application.exceptions;

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
}
package com.example.biblioteca.app.shared.exceptions;

import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidNameForMovie;
import com.example.biblioteca.modules.multimedia.movies.domain.exceptions.InvalidYearForMovie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidNameForMovie.class)
    public ResponseEntity<ErrorDetails> invalidNameForMovie() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid movie name");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidYearForMovie.class)
    public ResponseEntity<ErrorDetails> invalidYearForMovie() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid movie year");
        return ResponseEntity.status(422).body(errorDetails);
    }
}

package com.example.biblioteca.app.shared.exceptions;

import com.example.biblioteca.modules.accounts.domain.exceptions.EmailAlreadyInUse;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidEmailAddress;
import com.example.biblioteca.modules.accounts.domain.exceptions.InvalidPasswordFormat;
import com.example.biblioteca.modules.accounts.domain.exceptions.LoginFailed;
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

    @ExceptionHandler(EmailAlreadyInUse.class)
    public ResponseEntity<ErrorDetails> emailAlreadyInUse() {
        ErrorDetails errorDetails = new ErrorDetails("Email is already in use");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidEmailAddress.class)
    public ResponseEntity<ErrorDetails> invalidEmailAddress() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid email address");
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(InvalidPasswordFormat.class)
    public ResponseEntity<ErrorDetails> invalidPasswordFormat(InvalidPasswordFormat exception) {
        ErrorDetails errorDetails = new ErrorDetails("Invalid password format: " + exception.getMessage());
        return ResponseEntity.status(422).body(errorDetails);
    }

    @ExceptionHandler(LoginFailed.class)
    public ResponseEntity<ErrorDetails> loginFailed() {
        ErrorDetails errorDetails = new ErrorDetails("Invalid email or password");
        return ResponseEntity.status(403).body(errorDetails);
    }
}

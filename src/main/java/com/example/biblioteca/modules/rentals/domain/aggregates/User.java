package com.example.biblioteca.modules.rentals.domain.aggregates;

import com.example.biblioteca.modules.rentals.domain.valueObjects.UserEmail;
import com.example.biblioteca.modules.rentals.domain.valueObjects.UserId;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;


@Getter
@EqualsAndHashCode
public class User {
    private UserId id;
    private UserEmail email;


    public User(UUID id, String email) {
        this.id = new UserId(id);
        this.email = new UserEmail(email);
    }
}

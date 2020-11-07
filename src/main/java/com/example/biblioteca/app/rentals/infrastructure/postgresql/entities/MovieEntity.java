package com.example.biblioteca.app.rentals.infrastructure.postgresql.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity(name = "movies_info")
@Table(name = "movies_info")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    private UUID id;
    private String name;
}

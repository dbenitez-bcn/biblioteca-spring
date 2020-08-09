package com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    private Long id;
    private String name;
    private Integer year;
}

package com.example.biblioteca.app.multimedia.infrastructure.postgresql.entities;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MovieEntity {
    @Id
    private String id;
    private String name;
    private Integer year;
}

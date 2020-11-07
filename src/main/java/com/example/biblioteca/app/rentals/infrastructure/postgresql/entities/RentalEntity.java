package com.example.biblioteca.app.rentals.infrastructure.postgresql.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "rentals")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {
    @Id
    private UUID movieId;

    private UUID userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private MovieEntity movie;
}

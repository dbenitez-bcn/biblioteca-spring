package com.example.biblioteca.app.rentals.infrastructure.postgresql.entities;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rentals")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {
    @Id
    private UUID movieId;
    private UUID userId;
}

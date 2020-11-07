ALTER TABLE rentals
    ADD CONSTRAINT fk__rentals__movie_id__movie_info__id
        FOREIGN KEY (movie_id)
            REFERENCES movies_info (id)
            ON DELETE CASCADE;
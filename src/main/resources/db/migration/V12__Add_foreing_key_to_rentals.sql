ALTER TABLE rentals
    ADD CONSTRAINT fk__rentals__user_id__users__id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;
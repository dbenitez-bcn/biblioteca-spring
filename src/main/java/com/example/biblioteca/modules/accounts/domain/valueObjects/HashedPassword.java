package com.example.biblioteca.modules.accounts.domain.valueObjects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@EqualsAndHashCode
@RequiredArgsConstructor
public class HashedPassword {
    private final String value;
}

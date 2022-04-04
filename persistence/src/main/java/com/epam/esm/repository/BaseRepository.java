package com.epam.esm.repository;

import java.util.Optional;
import java.util.Set;

public interface BaseRepository<T> {
    long create(T t);

    void delete(long id);

    Set<T> findAll();

    Optional<T> findById(long id);
}

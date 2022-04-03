package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    long create(T t);

    boolean delete(long id);

    List<T> findAll();

    Optional<T> findById(long id);
}

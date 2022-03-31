package com.epam.esm.repository;

import java.util.List;
import java.util.Optional;

public interface MainRepository<T> {
    boolean create(T t);

    boolean delete(long id);

    List<T> findAll();

    Optional<T> findById(long id);
}

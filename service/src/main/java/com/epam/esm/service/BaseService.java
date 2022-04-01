package com.epam.esm.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    boolean delete(long id);

    List<T> findAll();

    Optional<T> findById(long id);
}

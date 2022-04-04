package com.epam.esm.service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface BaseService<T> {
    boolean create(Map<String, Object> data);

    boolean delete(long id);

    Set<T> findAll();

    Optional<T> findById(long id);
}

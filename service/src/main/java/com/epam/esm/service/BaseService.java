package com.epam.esm.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BaseService<T> {
    boolean create(Map<String, String> data);

    boolean delete(long id);

    List<T> findAll();

    Optional<T> findById(long id);
}

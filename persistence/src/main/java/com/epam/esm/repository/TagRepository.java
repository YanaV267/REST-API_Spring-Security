package com.epam.esm.repository;

import com.epam.esm.repository.entity.Tag;

import java.util.Optional;

public interface TagRepository extends MainRepository<Tag> {
    Optional<Tag> findByName(String name);
}

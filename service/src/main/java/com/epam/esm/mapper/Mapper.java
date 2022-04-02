package com.epam.esm.mapper;

import org.springframework.stereotype.Component;

@Component
public interface Mapper<T, D> {
    D mapToDto(T t);

    T mapToEntity(D d);
}

package com.epam.esm.service.mapper;

public interface Mapper<T, D> {
    D mapToDto(T t);

    T mapToEntity(D d);
}

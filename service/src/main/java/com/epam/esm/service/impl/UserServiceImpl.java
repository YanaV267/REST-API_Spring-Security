package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * The type User service.
 *
 * @author YanaV
 * @project GiftCertificate
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    /**
     * Instantiates a new User service.
     *
     * @param repository the repository
     * @param mapper     the mapper
     */
    @Autowired
    public UserServiceImpl(UserRepository repository, @Qualifier("userServiceMapper") UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            repository.delete(user.get());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<UserDto> findAll(int page) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<User> users = repository.findAll(firstElementNumber);
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findAllWithOrders(int page) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<User> users = repository.findAllWithOrders(firstElementNumber);
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<UserDto> findById(long id) {
        Optional<User> user = repository.findById(id);
        if (user.isPresent()) {
            UserDto userDto = mapper.mapToDto(user.get());
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Set<UserDto> findWithHighestOrderCost(int page) {
        int firstElementNumber = getFirstElementNumber(page);
        Set<User> orders = repository.findWithHighestOrderCost(firstElementNumber);
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }
}

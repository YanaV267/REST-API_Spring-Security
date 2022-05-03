package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import com.epam.esm.util.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserServiceImpl implements UserService, UserDetailsService {
    @Value("${max.result.amount}")
    private int maxResultAmount;
    private int lastPage;
    @Autowired
    private UserRepository repository;
    @Autowired
    @Qualifier("userServiceMapper")
    private UserMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findUserByLogin(username);
        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }
        throw new UsernameNotFoundException("Couldn't find user with username " + username);
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
        int firstElementNumber = getFirstElementNumber(page, maxResultAmount);
        Set<User> users = repository.findAll(firstElementNumber);
        lastPage = repository.getLastPage();
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findAllWithOrders(int page) {
        int firstElementNumber = getFirstElementNumber(page, maxResultAmount);
        Set<UserDto> users = findAll(firstElementNumber);
        return users.stream()
                .filter(u -> !u.getOrders().isEmpty())
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
        int firstElementNumber = getFirstElementNumber(page, maxResultAmount);
        Set<User> orders = repository.findWithHighestOrderCost(firstElementNumber);
        lastPage = repository.getLastPage();
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findWithHighestOrderCostMostUsedTag(int page) {
        int firstElementNumber = getFirstElementNumber(page, maxResultAmount);
        Set<User> orders = repository.findWithHighestOrderCostMostUsedTag(firstElementNumber);
        lastPage = repository.getLastPage();
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}

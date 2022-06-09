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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Optional<User> user = repository.findByLogin(username);
        if (user.isPresent()) {
            return new CustomUserDetails(user.get());
        }
        throw new UsernameNotFoundException("Couldn't find user with username " + username);
    }

    @Override
    @Transactional
    public boolean create(UserDto userDto) {
        User user = mapper.mapToEntity(userDto);
        Optional<User> foundUser = repository.findByLogin(userDto.getLogin());
        if (!foundUser.isPresent()) {
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Set<UserDto> findAll(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<User> users = repository.findAll(pageable).toSet();
        lastPage = repository.findAll(pageable).getTotalPages();
        return users.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findAllWithOrders(int page) {
        Set<UserDto> users = findAll(page);
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
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<User> orders = repository.findByHighestOrderCost(pageable).toSet();
        lastPage = repository.findByHighestOrderCost(pageable).getTotalPages();
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<UserDto> findWithHighestOrderCostMostUsedTag(int page) {
        Pageable pageable = PageRequest.of(page, maxResultAmount);
        Set<User> orders = repository.findByHighestOrderCostMostUsedTag(pageable).toSet();
        lastPage = repository.findByHighestOrderCostMostUsedTag(pageable).getTotalPages();
        return orders.stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toSet());
    }

    @Override
    public int getLastPage() {
        return lastPage;
    }
}

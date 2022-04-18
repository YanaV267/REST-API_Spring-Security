package test.epam.esm.service;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 7, 2})
    void delete(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(User.class));

        boolean actual = service.delete(id);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 45})
    void findAll(int page) {
        when(repository.findAll(page)).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 9})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        Optional<UserDto> user = service.findById(id);
        Assertions.assertFalse(user.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void findAllWithOrders(int page) {
        when(repository.findAll(page)).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {4})
    void findWithHighestOrderCost(int page) {
        when(repository.findAll(page)).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }
}

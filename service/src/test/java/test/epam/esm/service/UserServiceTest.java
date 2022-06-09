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
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    @MethodSource("provideUserData")
    void create(UserDto user) {
        when(repository.save(any(User.class))).thenReturn(new User());

        boolean actual = service.create(user);
        Assertions.assertTrue(actual);
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
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
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
        when(repository.findAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {4})
    void findWithHighestOrderCost(int page) {
        when(repository.findByHighestOrderCost(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {6, 19})
    void findWithHighestOrderCostMostUsedTag(int page) {
        when(repository.findByHighestOrderCostMostUsedTag(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 4;
        Set<UserDto> users = service.findAll(page);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideUserData() {
        return new Object[][]{
                {UserDto.builder()
                        .login("sky_09")
                        .password("1234567890")
                        .surname("Вавилова")
                        .name("Ульяна")
                        .build()}
        };
    }
}

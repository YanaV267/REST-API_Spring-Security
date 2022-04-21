package test.epam.esm.service;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository repository;
    @Mock
    private GiftCertificateRepository certificateRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderMapper mapper;
    @InjectMocks
    private OrderService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void create(OrderDto order) {
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User(7)));
        when(repository.create(any(Order.class))).thenReturn(anyLong());

        boolean actual = service.create(order);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void update(OrderDto order) {
        doNothing().when(repository).update(any(Order.class));

        boolean actual = service.update(order);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void delete(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(Order.class));

        boolean actual = service.delete(id);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 30})
    void findAll(int startElementNumber) {
        when(repository.findAll(startElementNumber)).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());

        int expected = 9;
        Set<OrderDto> tags = service.findAll(startElementNumber);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());

        Optional<OrderDto> tag = service.findById(id);
        Assertions.assertFalse(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {8, 25, 14})
    void findAllByUser(long userId) {
        when(repository.findAllByUser(anyInt(), anyLong())).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());

        int expected = 9;
        int startElementNumber = 15;
        Set<OrderDto> tags = service.findAllByUser(startElementNumber, userId);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findBySeveralParameters(int startElementNumber, OrderDto order, List<Integer> certificateIds,
                                 List<Integer> userIds) {
        when(repository.findBySeveralParameters(anyInt(), any(Order.class), anyList()))
                .thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());

        int expected = 1;
        Set<OrderDto> orders = service.findBySeveralParameters(startElementNumber, order, certificateIds, userIds);
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideOrderData() {
        return new Object[][]{
                {Order.builder()
                        .user(new User(8))
                        .certificates(new LinkedHashSet<>(2))
                        .build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {15, Order.builder()
                        .user(new User(8))
                        .certificates(new LinkedHashSet<>(2))
                        .build(), new ArrayList<Integer>() {
                    {
                        add(1);
                    }
                }, new ArrayList<Integer>() {
                    {
                        add(10);
                    }
                }},
                {10, Order.builder()
                        .user(new User(4))
                        .build(), new ArrayList<Integer>() {
                    {
                        add(5);
                    }
                }, new ArrayList<Integer>()},
        };
    }
}

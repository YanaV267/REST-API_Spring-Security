package test.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.GiftCertificatePurchaseDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.mapper.impl.OrderMapper;
import com.epam.esm.mapper.impl.UserMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private OrderServiceImpl service;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(service, "maxResultAmount", 15);
    }

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void create(OrderDto order) {
        User user = new User(7);
        user.setBalance(BigDecimal.valueOf(100));
        when(certificateRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).updateBalance(any(BigDecimal.class), anyLong());
        when(repository.save(any(Order.class))).thenReturn(new Order());

        boolean actual = service.create(order);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void update(OrderDto order) {
        when(mapper.mapToEntity(any(OrderDto.class))).thenReturn(new Order());
        when(repository.save(any(Order.class))).thenReturn(new Order());

        boolean actual = service.update(order);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void delete(long id) {
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        boolean actual = service.delete(id);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 30})
    void findAll(int page) {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.nCopies(3, new Order())));
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());
        when(userMapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 1;
        Set<OrderDto> tags = service.findAll(page);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Order()));
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());

        Optional<OrderDto> tag = service.findById(id);
        Assertions.assertTrue(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {8, 25, 14})
    void findAllByUser(long userId) {
        when(repository.findByUser_Id(anyLong(), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.nCopies(3, new Order())));
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());
        when(userMapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 1;
        int page = 15;
        Set<OrderDto> tags = service.findAllByUser(page, userId);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findBySeveralParameters(int page, OrderDto order, long userId, List<Integer> certificateIds) {
        when(repository.findAll(any(Example.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.nCopies(3, new Order())));
        when(mapper.mapToEntity(any(OrderDto.class))).thenReturn(new Order());
        when(mapper.mapToDto(any(Order.class))).thenReturn(new OrderDto());
        when(userMapper.mapToDto(any(User.class))).thenReturn(new UserDto());

        int expected = 1;
        Set<OrderDto> orders = service.findBySeveralParameters(page, order, userId, certificateIds);
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideOrderData() {
        return new Object[][]{
                {OrderDto.builder()
                        .user(UserDto.builder()
                                .id(8)
                                .balance(BigDecimal.valueOf(900))
                                .build())
                        .certificates(new HashSet<>(Collections.nCopies(5,
                                new GiftCertificatePurchaseDto(new GiftCertificateDto(), 7))))
                        .build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {15, OrderDto.builder()
                        .user(UserDto.builder()
                                .id(8)
                                .build())
                        .certificates(new LinkedHashSet<>(2))
                        .build(), 1, new ArrayList<Integer>() {
                    {
                        add(10);
                    }
                }},
                {10, OrderDto.builder()
                        .user(UserDto.builder()
                                .id(14)
                                .build())
                        .build(), 17, new ArrayList<Integer>() {
                    {
                        add(5);
                    }
                }},
        };
    }
}

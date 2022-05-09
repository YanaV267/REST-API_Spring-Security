package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatePurchase;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {OrderRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = "com.epam.esm")
class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @ParameterizedTest
    @MethodSource("provideCreateOrderData")
    void create(Order order) {
        long expected = 8;
        long actual = repository.save(order).getId();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateOrderData")
    void update(Order order) {
        BigDecimal expected = order.getCost();
        repository.save(order);
        Optional<Order> foundOrder = repository.findById(order.getId());
        if (foundOrder.isPresent()) {
            BigDecimal actual = foundOrder.get().getCost();
            Assertions.assertEquals(expected, actual);
        } else {
            Assertions.fail();
        }
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 7})
    void delete(long id) {
        Order giftOrder = new Order();
        giftOrder.setId(id);
        repository.delete(giftOrder);

        Optional<Order> deletedOrder = repository.findById(id);
        Assertions.assertFalse(deletedOrder.isPresent());
    }

    @ParameterizedTest
    @MethodSource("providePageable")
    void findAll(Pageable pageable) {
        long expected = 2;
        Set<Order> orders = repository.findAll(pageable).toSet();
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 6})
    void findById(long id) {
        Optional<Order> order = repository.findById(id);
        Assertions.assertTrue(order.isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {5, 17})
    void findAllOrdersByUser(long userId) {
        Pageable pageable = PageRequest.of(6, 15);
        long expected = 2;
        Set<Order> orders = repository.findByUser_Id(userId, pageable).toSet();
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findOrdersBySeveralParameters(Order order, Pageable pageable) {
        long expected = 6;
        Set<Order> orders = repository.findAll(Example.of(order), pageable).toSet();
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideCreateOrderData() {
        return new Object[][]{
                {Order.builder()
                        .user(new User(3))
                        .certificates(new LinkedHashSet<GiftCertificatePurchase>() {
                            {
                                add(new GiftCertificatePurchase(new GiftCertificate(3), 2));
                            }
                        })
                        .build()}
        };
    }

    private static Object[][] provideUpdateOrderData() {
        return new Object[][]{
                {Order.builder()
                        .id(2)
                        .user(new User(3))
                        .build()},
                {Order.builder()
                        .id(2)
                        .certificates(new LinkedHashSet<GiftCertificatePurchase>() {
                            {
                                add(new GiftCertificatePurchase(new GiftCertificate(14), 2));
                            }
                        })
                        .build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {Order.builder()
                        .cost(BigDecimal.valueOf(150))
                        .certificates(new LinkedHashSet<GiftCertificatePurchase>() {
                            {
                                add(new GiftCertificatePurchase(new GiftCertificate(7), 1));
                            }
                        })
                        .id(5)
                        .build(), PageRequest.of(19, 30)},
                {Order.builder()
                        .id(7)
                        .cost(BigDecimal.valueOf(80)), PageRequest.of(3, 10)}
        };
    }

    private static Object[][] providePageable() {
        return new Object[][]{
                {PageRequest.of(4, 20)},
                {PageRequest.of(1, 25)},
                {PageRequest.of(15, 30)},
                {PageRequest.of(7, 27)}
        };
    }
}

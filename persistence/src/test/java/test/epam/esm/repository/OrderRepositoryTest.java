package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificatePurchase;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.impl.OrderRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {OrderRepositoryImpl.class, EntityManager.class})
@EnableAutoConfiguration
@EntityScan(basePackages = "com.epam.esm")
class OrderRepositoryTest {
    @Autowired
    private OrderRepository repository;

    @ParameterizedTest
    @MethodSource("provideCreateOrderData")
    void create(Order order) {
        long expected = 8;
        long actual = repository.create(order);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateOrderData")
    void update(Order order) {
        BigDecimal expected = order.getCost();
        repository.update(order);
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
    @ValueSource(ints = {0, 45, 30})
    void findAll(int firstElementNumber) {
        long expected = 2;
        Set<Order> orders = repository.findAll(firstElementNumber);
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
        int firstElementNumber = 0;
        long expected = 2;
        Set<Order> orders = repository.findAllByUser(firstElementNumber, userId);
        int actual = orders.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findOrdersBySeveralParameters(int firstElementNumber, Order order, List<Integer> userIds) {
        long expected = 6;
        Set<Order> orders = repository.findBySeveralParameters(firstElementNumber, order, userIds);
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
                {15, Order.builder()
                        .cost(BigDecimal.valueOf(150))
                        .certificates(new LinkedHashSet<GiftCertificatePurchase>() {
                            {
                                add(new GiftCertificatePurchase(new GiftCertificate(7), 1));
                            }
                        })
                        .id(5)
                        .build(), new ArrayList<Integer>() {
                    {
                        add(5);
                    }
                }},
                {0, Order.builder()
                        .id(7)
                        .cost(BigDecimal.valueOf(80)), new ArrayList<Integer>() {
                    {
                        add(12);
                    }
                }}
        };
    }
}

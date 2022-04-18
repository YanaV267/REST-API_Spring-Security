package test.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.repository.impl.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {UserRepositoryImpl.class, EntityManager.class})
@EnableAutoConfiguration
@EntityScan(basePackages = "com.epam.esm")
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @ParameterizedTest
    @ValueSource(ints = {15, 75})
    void findAll(int firstElementNumber) {
        long expected = 17;
        Set<User> users = repository.findAll(firstElementNumber);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 6})
    void findById(long id) {
        Optional<User> user = repository.findById(id);
        Assertions.assertTrue(user.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {14, 22})
    void findAllWithOrders(int firstElementNumber) {
        long expected = 9;
        Set<User> users = repository.findAllWithOrders(firstElementNumber);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 0, 60})
    void findWithHighestOrderCost(int firstElementNumber) {
        long expected = 1;
        Set<User> users = repository.findWithHighestOrderCost(firstElementNumber);
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }
}

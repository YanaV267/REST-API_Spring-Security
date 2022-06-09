package test.epam.esm.repository;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {UserRepository.class})
@EnableAutoConfiguration
@EntityScan(basePackages = "com.epam.esm")
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @ParameterizedTest
    @MethodSource("providePageable")
    void findAll(Pageable pageable) {
        long expected = 17;
        Set<User> users = repository.findAll(pageable).toSet();
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
    @MethodSource("providePageable")
    void findByHighestOrderCost(Pageable pageable) {
        long expected = 1;
        Set<User> users = repository.findByHighestOrderCost(pageable).toSet();
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("providePageable")
    void findByHighestOrderCostMostUsedTag(Pageable pageable) {
        long expected = 1;
        Set<User> users = repository.findByHighestOrderCostMostUsedTag(pageable).toSet();
        int actual = users.size();
        Assertions.assertEquals(expected, actual);
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

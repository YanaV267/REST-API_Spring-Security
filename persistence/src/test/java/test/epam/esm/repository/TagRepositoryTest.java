package test.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.impl.TagRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TagRepositoryImpl.class)
@ExtendWith(SpringExtension.class)
class TagRepositoryTest {
    @Autowired
    private TagRepository repository;

    @ParameterizedTest
    @MethodSource("provideTagData")
    void create(Tag tag) {
        long expected = 5;
        long actual = repository.create(tag);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {4, 1, 7})
    void delete(long id) {
        int expected = 2;
        Tag tag = new Tag();
        tag.setId(id);
        repository.delete(tag);
        Set<Tag> tags = repository.findAll();
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        long expected = 4;
        Set<Tag> tags = repository.findAll();
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 1, 7})
    void findById(long id) {
        Optional<Tag> tag = repository.findById(id);
        Assertions.assertTrue(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"travelling", "cars"})
    void findByName(String name) {
        Optional<Tag> tag = repository.findByName(name);
        Assertions.assertTrue(tag.isPresent());
    }

    private static Object[][] provideTagData() {
        return new Object[][]{
                {new Tag("clock")},
                {new Tag("glass")}
        };
    }
}

package test.epam.esm.repository;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {TagRepository.class})
@EnableAutoConfiguration
class TagRepositoryTest {
    @Autowired
    private TagRepository repository;

    @ParameterizedTest
    @MethodSource("provideTagData")
    void create(Tag tag) {
        long expected = 5;
        long actual = repository.save(tag).getId();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {4, 1, 7})
    void delete(long id) {
        Tag tag = new Tag();
        tag.setId(id);
        repository.delete(tag);

        Optional<Tag> deletedTag = repository.findById(id);
        Assertions.assertFalse(deletedTag.isPresent());
    }

    @ParameterizedTest
    @MethodSource("providePageable")
    void findAll(Pageable pageable) {
        long expected = 4;
        Set<Tag> tags = repository.findAll(pageable).toSet();
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

    private static Object[][] providePageable() {
        return new Object[][]{
                {PageRequest.of(4, 20)},
                {PageRequest.of(1, 25)},
                {PageRequest.of(15, 30)},
                {PageRequest.of(7, 27)}
        };
    }
}

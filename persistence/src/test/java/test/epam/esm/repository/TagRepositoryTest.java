package test.epam.esm.repository;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {RepositoryConfig.class})
public class TagRepositoryTest {
    @Autowired
    private TagRepository repository;

    @ParameterizedTest
    @MethodSource("provideTagData")
    public void create(Tag tag) {
        long expected = 5;
        long actual = repository.create(tag);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {4, 1, 7})
    public void delete(long id) {
        int expected = 2;
        repository.delete(id);
        Set<Tag> tags = repository.findAll();
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        long expected = 4;
        Set<Tag> tags = repository.findAll();
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 1, 7})
    public void findById(long id) {
        Optional<Tag> tag = repository.findById(id);
        Assertions.assertTrue(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"travelling", "cars"})
    public void findByName(String name) {
        Optional<Tag> tag = repository.findByName(name);
        Assertions.assertTrue(tag.isPresent());
    }

    public static Object[][] provideTagData() {
        return new Object[][]{
                {new Tag("clock")},
                {new Tag("glass")}
        };
    }
}

package test.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.impl.TagServiceImpl;
import com.epam.esm.validator.TagValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static com.epam.esm.util.ParameterName.NAME;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TagServiceImpl.class)
@ExtendWith(SpringExtension.class)
class TagServiceTest {
    @Autowired
    private TagService service;
    @Mock
    private TagRepository repository;
    @Mock
    private TagValidator validator;
    @Mock
    private TagMapper mapper;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideTagData")
    void create(Map<String, Object> tagData) {
        when(validator.checkName(anyString())).thenReturn(true);
        when(repository.create(any(Tag.class))).thenReturn(Long.valueOf(7));

        boolean actual = service.create(tagData);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 7, 2})
    void delete(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(anyLong());

        boolean actual = service.delete(id);
        Assertions.assertTrue(actual);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        int expected = 4;
        Set<TagDto> tags = service.findAll();
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 9})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        Optional<TagDto> tag = service.findById(id);
        Assertions.assertFalse(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"food", "travelling"})
    void findByName(String name) {
        when(repository.findByName(anyString())).thenReturn(Optional.of(new Tag()));
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        Optional<TagDto> tag = service.findByName(name);
        Assertions.assertFalse(tag.isPresent());
    }

    private static Object[][] provideTagData() {
        return new Object[][]{
                {new HashMap<String, String>() {
                    {
                        put(NAME, "breakfast");
                    }
                }},
                {new HashMap<String, String>() {
                    {
                        put(NAME, "car_trip");
                    }
                }}
        };
    }
}

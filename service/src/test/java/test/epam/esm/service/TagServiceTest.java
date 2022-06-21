package test.epam.esm.service;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.TagMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {
    @Mock
    private TagRepository repository;
    @Mock
    private TagMapper mapper;
    @InjectMocks
    private TagServiceImpl service;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(service, "maxResultAmount", 15);
    }

    @ParameterizedTest
    @MethodSource("provideTagData")
    void create(TagDto tag) {
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());
        when(repository.save(any(Tag.class))).thenReturn(new Tag());

        boolean actual = service.create(tag);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {3, 7, 2})
    void delete(long id) {
        when(repository.existsById(anyLong())).thenReturn(true);
        doNothing().when(repository).deleteById(anyLong());

        boolean actual = service.delete(id);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {4, 45})
    void findAll(int page) {
        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.nCopies(3, new Tag())));
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        int expected = 1;
        Set<TagDto> tags = service.findAll(page);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 3, 9})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Tag()));
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        Optional<TagDto> tag = service.findById(id);
        Assertions.assertTrue(tag.isPresent());
    }

    @ParameterizedTest
    @ValueSource(strings = {"food", "travelling"})
    void findByName(String name) {
        when(repository.findByName(anyString())).thenReturn(Optional.of(new Tag()));
        when(mapper.mapToDto(any(Tag.class))).thenReturn(new TagDto());

        Optional<TagDto> tag = service.findByName(name);
        Assertions.assertTrue(tag.isPresent());
    }

    private static Object[][] provideTagData() {
        return new Object[][]{
                {TagDto.builder()
                        .name("breakfast")
                        .build()},
                {TagDto.builder()
                        .name("car_trip")
                        .build()}
        };
    }
}

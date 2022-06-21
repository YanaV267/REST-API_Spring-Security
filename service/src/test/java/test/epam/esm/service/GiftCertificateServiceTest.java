package test.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private GiftCertificateMapper mapper;
    @InjectMocks
    private GiftCertificateServiceImpl service;

    @BeforeEach
    void init() {
        ReflectionTestUtils.setField(service, "maxResultAmount", 15);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void create(GiftCertificateDto certificate) {
        when(mapper.mapToEntity(any(GiftCertificateDto.class))).thenReturn(new GiftCertificate());
        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(GiftCertificate.class))).thenReturn(any(GiftCertificate.class));

        boolean actual = service.create(certificate);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void update(GiftCertificateDto certificate) {
        when(mapper.mapToEntity(any(GiftCertificateDto.class))).thenReturn(new GiftCertificate());
        when(repository.findById(anyLong())).thenReturn(Optional.of(new GiftCertificate()));
        when(repository.save(any(GiftCertificate.class))).thenReturn(any(GiftCertificate.class));

        boolean actual = service.update(certificate);
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
        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(getCertificateList(9)));
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 1;
        Set<GiftCertificateDto> certificates = service.findAll(page);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new GiftCertificate()));
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        Optional<GiftCertificateDto> tag = service.findById(id);
        Assertions.assertTrue(tag.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findBySeveralParameters(int page, GiftCertificateDto certificate,
                                 List<String> tagNames, List<String> sortTypes) {
        when(mapper.mapToEntity(any(GiftCertificateDto.class))).thenReturn(new GiftCertificate());
        when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(new PageImpl<>(getCertificateList(5)));
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 1;
        Set<GiftCertificateDto> certificates = service.findBySeveralParameters(page,
                certificate, tagNames, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    private List<GiftCertificate> getCertificateList(int amount) {
        return IntStream.range(0, amount)
                .mapToObj(GiftCertificate::new)
                .collect(Collectors.toList());
    }

    private static Object[][] provideCertificateData() {
        return new Object[][]{
                {GiftCertificateDto.builder()
                        .id(4)
                        .name("european countries tours")
                        .description("provides 17% discount for any 1 chosen tour")
                        .price(BigDecimal.valueOf(70))
                        .duration(15)
                        .tags(new LinkedHashSet<TagDto>() {{
                            add(TagDto.builder()
                                    .name("cars")
                                    .build());
                        }}).build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {3, GiftCertificateDto.builder()
                        .name("european countries tours")
                        .duration(15).build(), new ArrayList<String>() {
                    {
                        add("car");
                    }
                }, new ArrayList<String>() {
                    {
                        add("name_asc");
                        add("price_desc");
                    }
                }},
                {5, GiftCertificateDto.builder()
                        .price(BigDecimal.valueOf(100)).build(), new ArrayList<String>() {
                    {
                        add("travelling");
                        add("car");
                    }
                }, new ArrayList<String>() {
                    {
                        add("name_asc");
                    }
                }}
        };
    }
}

package test.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private GiftCertificateMapper mapper;
    @InjectMocks
    private GiftCertificateService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void create(GiftCertificateDto certificate) {
        when(repository.create(any(GiftCertificate.class))).thenReturn(anyLong());

        boolean actual = service.create(certificate);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void update(GiftCertificateDto certificate) {
        doNothing().when(repository).update(any(GiftCertificate.class));

        boolean actual = service.update(certificate);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void delete(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(any(GiftCertificate.class));

        boolean actual = service.delete(id);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @ValueSource(ints = {7, 30})
    void findAll(int startElementNumber) {
        when(repository.findAll(startElementNumber)).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 9;
        Set<GiftCertificateDto> tags = service.findAll(startElementNumber);
        int actual = tags.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void findById(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.of(new GiftCertificate()));
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        Optional<GiftCertificateDto> tag = service.findById(id);
        Assertions.assertFalse(tag.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findBySeveralParameters(int page, GiftCertificateDto certificate,
                                 List<String> tagNames, List<String> sortTypes) {
        when(repository.findBySeveralParameters(anyInt(), any(GiftCertificate.class), anyList()))
                .thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 1;
        Set<GiftCertificateDto> certificates = service.findBySeveralParameters(page,
                certificate, tagNames, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideCertificateData() {
        return new Object[][]{
                {GiftCertificate.builder()
                        .id(4)
                        .name("european countries tours")
                        .description("provides 17% discount for any 1 chosen tour")
                        .price(BigDecimal.valueOf(70))
                        .duration(15)
                        .tags(new LinkedHashSet<Tag>() {{
                    add(new Tag("cars"));
                }})}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {3, GiftCertificate.builder()
                        .name("european countries tours")
                        .duration(15), new ArrayList<String>() {
                    {
                        add("car");
                    }
                }, new ArrayList<String>() {
                    {
                        add("name_asc");
                        add("price_desc");
                    }
                }},
                {5, GiftCertificate.builder()
                        .price(BigDecimal.valueOf(100)), new ArrayList<String>() {
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

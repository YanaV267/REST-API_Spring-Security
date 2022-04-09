package test.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.mapper.impl.GiftCertificateMapper;
import com.epam.esm.repository.CertificatePurchaseRepository;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.util.CertificateDateFormatter;
import com.epam.esm.validator.GiftCertificateValidator;
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

import java.time.LocalDateTime;
import java.util.*;

import static com.epam.esm.util.ParameterName.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = GiftCertificateServiceImpl.class)
@ExtendWith(SpringExtension.class)
class GiftCertificateServiceTest {
    @Autowired
    private GiftCertificateService service;
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private CertificatePurchaseRepository purchaseRepository;
    @Mock
    private GiftCertificateValidator validator;
    @Mock
    private GiftCertificateMapper mapper;
    @Mock
    private CertificateDateFormatter dateFormatter;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void create(Map<String, Object> certificateData) {
        when(validator.checkAllCertificateData(anyMap())).thenReturn(false);
        when(dateFormatter.format(anyString())).thenReturn(LocalDateTime.now());
        when(purchaseRepository.create(any(GiftCertificate.class))).thenReturn(true);

        boolean actual = service.create(certificateData);
        Assertions.assertFalse(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void update(Map<String, Object> certificateData) {
        when(validator.checkAllCertificateData(anyMap())).thenReturn(false);
        when(dateFormatter.format(anyString())).thenReturn(LocalDateTime.now());
        when(purchaseRepository.update(any(GiftCertificate.class))).thenReturn(true);

        boolean actual = service.update(certificateData);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 4, 14, 5})
    void delete(long id) {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        doNothing().when(repository).delete(anyLong());

        boolean actual = service.delete(id);
        Assertions.assertFalse(actual);
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 9;
        Set<GiftCertificateDto> tags = service.findAll();
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
    void findBySeveralParameters(Map<String, Object> certificateData, List<String> sortTypes) {
        when(validator.checkAllCertificateData(anyMap())).thenReturn(true);
        when(dateFormatter.format(anyString())).thenReturn(LocalDateTime.now());
        when(repository.findBySeveralParameters(any(GiftCertificate.class), anyString(), anyList()))
                .thenReturn(new LinkedHashSet<>());
        when(mapper.mapToDto(any(GiftCertificate.class))).thenReturn(new GiftCertificateDto());

        int expected = 1;
        Set<GiftCertificateDto> certificates = service.findBySeveralParameters(certificateData, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideCertificateData() {
        return new Object[][]{
                {new HashMap<String, Object>() {
                    {
                        put(ID, "4");
                        put(NAME, "european countries tours");
                        put(DESCRIPTION, "provides 17% discount for any 1 chosen tour");
                        put(PRICE, "70");
                        put(DURATION, "15");
                        put(LAST_UPDATE_DATE, "2022-03-31T19:04:55");
                        put(TAGS, "{'cars', 'food'}");
                    }
                }},
                {new HashMap<String, Object>() {
                    {
                        put(NAME, "furniture purchase");
                        put(DESCRIPTION, "7% discount on all goods");
                        put(PRICE, "10");
                        put(DURATION, "365");
                        put(LAST_UPDATE_DATE, "2022-04-02 19:04:55");
                    }
                }}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {new HashMap<String, Object>() {
                    {
                        put(PRICE, "100");
                        put(LAST_UPDATE_DATE, "2022-03-31T19:04:55");
                    }
                }, new ArrayList<String>() {
                    {
                        add("name_asc");
                        add("price_desc");
                    }
                }},
                {new HashMap<String, Object>() {
                    {
                        put(NAME, "european countries tours");
                        put(PRICE, "70");
                        put(DURATION, "15");
                    }
                }, new ArrayList<String>() {
                    {
                        add("name_asc");
                    }
                }}
        };
    }
}

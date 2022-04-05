package test.epam.esm.repository;

import com.epam.esm.config.RepositoryConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {RepositoryConfig.class})
public class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @ParameterizedTest
    @MethodSource("provideCreateCertificateData")
    public void create(GiftCertificate certificate) {
        long expected = 3;
        long actual = repository.create(certificate);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateCertificateData")
    public void update(GiftCertificate certificate) {
        BigDecimal expected = certificate.getPrice();
        repository.update(certificate);
        Optional<GiftCertificate> foundCertificate = repository.findById(certificate.getId());
        BigDecimal actual = foundCertificate.get().getPrice();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 7})
    public void delete(long id) {
        long expected = 1;
        repository.delete(id);
        Set<GiftCertificate> certificates = repository.findAll();
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void findAll() {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findAll();
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 6})
    public void findById(long id) {
        Optional<GiftCertificate> certificate = repository.findById(id);
        Assertions.assertTrue(certificate.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    public void findBySeveralParameters(GiftCertificate certificate, String tagName,
                                        List<String> sortTypes) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findBySeveralParameters(certificate, tagName, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    public static Object[][] provideCreateCertificateData() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setName("12% discount")
                        .setDescription("provides 12% discount for any 1 chosen tour")
                        .setPrice(BigDecimal.valueOf(50))
                        .setDuration(90)
                        .build()}
        };
    }

    public static Object[][] provideUpdateCertificateData() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(1)
                        .setName("15% discount")
                        .setDuration(120)
                        .build()},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setPrice(new BigDecimal("80.00"))
                        .build()}
        };
    }

    public static Object[][] provideSearchParameters() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setName("discount")
                        .setDuration(90)
                        .build(), "travelling", null},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setDescription("all")
                        .setDuration(100)
                        .build(), null, null},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(1)
                        .setPrice(new BigDecimal("30"))
                        .build(), "cars", new ArrayList<String>() {
                    {
                        add("price_desc");
                    }
                }},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(0)
                        .build(), null, new ArrayList<String>() {
                    {
                        add("name_asc");
                        add("price_desc");
                    }
                }}
        };
    }
}
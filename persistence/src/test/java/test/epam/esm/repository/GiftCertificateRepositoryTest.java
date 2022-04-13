package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = GiftCertificateRepositoryImpl.class)
@ExtendWith(SpringExtension.class)
class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @ParameterizedTest
    @MethodSource("provideCreateCertificateData")
    void create(GiftCertificate certificate) {
        long expected = 3;
        long actual = repository.create(certificate);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateCertificateData")
    void update(GiftCertificate certificate) {
        BigDecimal expected = certificate.getPrice();
        repository.update(certificate);
        Optional<GiftCertificate> foundCertificate = repository.findById(certificate.getId());
        if (foundCertificate.isPresent()) {
            BigDecimal actual = foundCertificate.get().getPrice();
            Assertions.assertEquals(expected, actual);
        } else {
            Assertions.fail();
        }
    }

    @ParameterizedTest
    @ValueSource(longs = {2, 7})
    void delete(long id) {
        long expected = 1;
        repository.delete(id);
        Set<GiftCertificate> certificates = repository.findAll();
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAll() {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findAll();
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 6})
    void findById(long id) {
        Optional<GiftCertificate> certificate = repository.findById(id);
        Assertions.assertTrue(certificate.isPresent());
    }

    @ParameterizedTest
    @MethodSource("provideSearchParameters")
    void findBySeveralParameters(GiftCertificate certificate, List<Tag> tags,
                                 List<String> sortTypes) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findBySeveralParameters(certificate, tags, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideCreateCertificateData() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setName("12% discount")
                        .setDescription("provides 12% discount for any 1 chosen tour")
                        .setPrice(BigDecimal.valueOf(50))
                        .setDuration(90)
                        .build()}
        };
    }

    private static Object[][] provideUpdateCertificateData() {
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

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setName("discount")
                        .setDuration(90)
                        .build(), "travelling", new ArrayList<Tag>() {
                    {
                        add(new Tag("travelling"));
                    }
                }},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setDescription("all")
                        .setDuration(100)
                        .build(), null, null},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(1)
                        .setPrice(new BigDecimal("30"))
                        .build(), "cars", null, new ArrayList<String>() {
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
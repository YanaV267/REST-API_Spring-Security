package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.impl.GiftCertificateRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GiftCertificateRepositoryImpl.class, EntityManager.class})
@EnableAutoConfiguration
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
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(id);
        repository.delete(giftCertificate);

        Optional<GiftCertificate> deletedCertificate = repository.findById(id);
        Assertions.assertFalse(deletedCertificate.isPresent());
    }

    @ParameterizedTest
    @ValueSource(ints = {15, 75})
    void findAll(int firstElementNumber) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findAll(firstElementNumber);
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
    void findBySeveralParameters(int firstElementNumber, GiftCertificate certificate,
                                 Set<Tag> tags, List<String> sortTypes) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findBySeveralParameters(firstElementNumber,
                certificate, tags, sortTypes);
        int actual = certificates.size();
        Assertions.assertEquals(expected, actual);
    }

    private static Object[][] provideCreateCertificateData() {
        return new Object[][]{
                {GiftCertificate.builder()
                        .name("12% discount")
                        .description("provides 12% discount for any 1 chosen tour")
                        .price(BigDecimal.valueOf(50))
                        .duration(90)
                        .build()}
        };
    }

    private static Object[][] provideUpdateCertificateData() {
        return new Object[][]{
                {GiftCertificate.builder()
                        .id(1)
                        .name("15% discount")
                        .duration(120)
                        .build()},
                {GiftCertificate.builder()
                        .id(2)
                        .price(new BigDecimal("80.00"))
                        .build()}
        };
    }

    private static Object[][] provideSearchParameters() {
        return new Object[][]{
                {30},
                {GiftCertificate.builder()
                        .id(2)
                        .name("discount")
                        .duration(90)
                        .build(), "travelling", new LinkedHashSet<Tag>() {
                    {
                        add(new Tag("travelling"));
                    }
                }},
                {15},
                {GiftCertificate.builder()
                        .id(2)
                        .description("all")
                        .duration(100)
                        .build(), null, null},
                {GiftCertificate.builder()
                        .id(1)
                        .price(new BigDecimal("30"))
                        .build(), "cars", null, new ArrayList<String>() {
                    {
                        add("price_desc");
                    }
                }},
                {0},
                {GiftCertificate.builder()
                        .id(0)
                        .build(), null, new ArrayList<String>() {
                    {
                        add("name_asc");
                        add("price_desc");
                    }
                }}
        };
    }
}
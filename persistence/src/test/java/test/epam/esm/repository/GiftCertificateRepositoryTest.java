package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GiftCertificateRepository.class})
@EnableAutoConfiguration
class GiftCertificateRepositoryTest {
    @Autowired
    private GiftCertificateRepository repository;

    @ParameterizedTest
    @MethodSource("provideCreateCertificateData")
    void create(GiftCertificate certificate) {
        long expected = 3;
        long actual = repository.save(certificate).getId();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideUpdateCertificateData")
    void update(GiftCertificate certificate) {
        BigDecimal expected = certificate.getPrice();
        repository.save(certificate);
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
    @MethodSource("providePageable")
    void findAll(Pageable pageable) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findAll(pageable).toSet();
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
    void findBySeveralParameters(GiftCertificate certificate, Pageable pageable) {
        long expected = 2;
        Set<GiftCertificate> certificates = repository.findAll(Example.of(certificate), pageable).toSet();
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
                {GiftCertificate.builder()
                        .id(2)
                        .name("discount")
                        .duration(90)
                        .build(), PageRequest.of(16, 30)},
                {GiftCertificate.builder()
                        .id(1)
                        .price(new BigDecimal("30"))
                        .build(), PageRequest.of(5, 25)},
                {GiftCertificate.builder()
                        .id(0)
                        .build(), PageRequest.of(23, 20)}
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
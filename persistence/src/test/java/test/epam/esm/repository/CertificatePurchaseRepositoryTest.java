package test.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.CertificatePurchaseRepository;
import com.epam.esm.repository.impl.CertificatePurchaseRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.HashSet;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CertificatePurchaseRepositoryImpl.class)
@ExtendWith(SpringExtension.class)
class CertificatePurchaseRepositoryTest {
    @Autowired
    private CertificatePurchaseRepository repository;

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void create(GiftCertificate certificate) {
        boolean actual = repository.create(certificate);
        Assertions.assertTrue(actual);
    }

    @ParameterizedTest
    @MethodSource("provideCertificateData")
    void update(GiftCertificate certificate) {
        boolean actual = repository.update(certificate);
        Assertions.assertTrue(actual);
    }

    private static Object[][] provideCertificateData() {
        return new Object[][]{
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(1)
                        .setName("12% discount")
                        .setDescription("provides 12% discount for any 1 chosen tour")
                        .setPrice(BigDecimal.valueOf(50))
                        .setDuration(90)
                        .setTags(new HashSet<Tag>() {
                            {
                                add(new Tag("travelling"));
                                add(new Tag("cars"));
                            }
                        })
                        .build()},
                {new GiftCertificate.GiftCertificateBuilder()
                        .setId(2)
                        .setName("grocery store sales card")
                        .setDescription("7% discount on all goods")
                        .setPrice(BigDecimal.valueOf(7))
                        .setDuration(365)
                        .build()}
        };
    }
}

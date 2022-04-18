package test.epam.esm.util;

import com.epam.esm.util.CertificateDateFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = CertificateDateFormatter.class)
@EnableAutoConfiguration
class CertificateDateFormatterTest {
    @Autowired
    private CertificateDateFormatter dateFormatter;

    @ParameterizedTest
    @ValueSource(strings = {"2022-02-18T19:12:27", "2022-03-03 10:25:10"})
    void format(String date) {
        Assertions.assertThrows(DateTimeParseException.class, () -> dateFormatter.format(date));
    }

    @ParameterizedTest
    @MethodSource("provideDateValues")
    void format(LocalDateTime date) {
        Assertions.assertDoesNotThrow(() -> {
            dateFormatter.format(date);
        });
    }

    private static Object[][] provideDateValues() {
        return new Object[][]{
                {LocalDate.parse("2022-02-18", DateTimeFormatter.ISO_DATE).atStartOfDay()},
                {LocalDateTime.parse("2022-04-02T23:14:23", DateTimeFormatter.ISO_DATE_TIME)},
                {LocalDateTime.parse("2022-03-31T19:04:55", DateTimeFormatter.ISO_DATE_TIME)}
        };
    }
}

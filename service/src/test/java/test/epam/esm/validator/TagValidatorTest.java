package test.epam.esm.validator;

import com.epam.esm.config.ServiceConfig;
import com.epam.esm.validator.GiftCertificateValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("dev")
@ContextConfiguration(classes = {ServiceConfig.class})
public class TagValidatorTest {
    @Autowired
    private GiftCertificateValidator validator;

    @ParameterizedTest
    @ValueSource(strings = {"travelling", "tour17", "918&@*82"})
    public void checkName(String name) {
        boolean actual = validator.checkName(name);
        Assertions.assertTrue(actual);
    }
}

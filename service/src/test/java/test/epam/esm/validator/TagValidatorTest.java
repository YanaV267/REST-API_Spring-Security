package test.epam.esm.validator;

import com.epam.esm.validator.TagValidator;
import com.epam.esm.validator.impl.TagValidatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = TagValidatorImpl.class)
@ExtendWith(SpringExtension.class)
class TagValidatorTest {
    @Autowired
    private TagValidator validator;

    @ParameterizedTest
    @ValueSource(strings = {"travelling", "tour17", "918&@*82"})
    void checkName(String name) {
        boolean actual = validator.checkName(name);
        Assertions.assertTrue(actual);
    }
}

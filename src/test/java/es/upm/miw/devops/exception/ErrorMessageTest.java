package es.upm.miw.devops.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @Test
    void testErrorMessage() {
        ErrorMessage errorMessage = new ErrorMessage(new RuntimeException("Boom"), 500);
        assertThat(errorMessage.getError()).isEqualTo("RuntimeException");
        assertThat(errorMessage.getMessage()).isEqualTo("Boom");
        assertThat(errorMessage.getCode()).isEqualTo(500);
    }

    @Test
    void testToString() {
        ErrorMessage errorMessage = new ErrorMessage(new RuntimeException("Boom"), 500);
        assertThat(errorMessage.toString())
                .contains("error='RuntimeException'", "message='Boom'", "code=500");
    }
}

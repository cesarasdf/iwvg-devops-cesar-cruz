package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @Test
    void testNoResourceFoundRequest() {
        ErrorMessage errorMessage = apiExceptionHandler.noResourceFoundRequest(
                new NoResourceFoundException(org.springframework.http.HttpMethod.GET, "/unknown"));
        assertThat(errorMessage.getCode()).isEqualTo(404);
    }

    @Test
    void testNoResourceFoundRequestFromResponseStatusException() {
        ErrorMessage errorMessage = apiExceptionHandler.noResourceFoundRequest(
                new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND));
        assertThat(errorMessage.getCode()).isEqualTo(404);
    }

    @Test
    void testException() {
        ErrorMessage errorMessage = apiExceptionHandler.exception(new RuntimeException("Boom"));
        assertThat(errorMessage.getCode()).isEqualTo(500);
    }
}

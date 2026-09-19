package es.upm.miw.devops.rest.exceptionshandler;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class ApiExceptionHandlerTest {

    private final ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @Test
    void testNoResourceFoundRequest() {
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.noResourceFoundRequest(
                new NoResourceFoundException(org.springframework.http.HttpMethod.GET, "/unknown"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getCode()).isEqualTo(404);
    }

    @Test
    void testResponseStatusExceptionNotFound() {
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.responseStatusException(
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody().getCode()).isEqualTo(404);
    }

    @Test
    void testResponseStatusExceptionConflict() {
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.responseStatusException(
                new ResponseStatusException(HttpStatus.CONFLICT, "An ADMIN user cannot be deactivated"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(response.getBody().getCode()).isEqualTo(409);
        assertThat(response.getBody().getMessage()).contains("ADMIN");
    }

    @Test
    void testException() {
        ResponseEntity<ErrorMessage> response = apiExceptionHandler.exception(new RuntimeException("Boom"));
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody().getCode()).isEqualTo(500);
    }
}

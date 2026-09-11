package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.upm.miw.devops.rest.UserController.USERS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testReadById() {
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Oscar")
                .jsonPath("$.familyName").isEqualTo("Fernandez");
    }

    @Test
    void testReadByIdNotFound() {
        this.webTestClient.get()
                .uri(USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }
}

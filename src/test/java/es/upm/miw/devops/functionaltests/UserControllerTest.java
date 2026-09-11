package es.upm.miw.devops.functionaltests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static es.upm.miw.devops.rest.UserController.SEARCH;
import static es.upm.miw.devops.rest.UserController.USERS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserControllerTest {

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

    @Test
    void testFindByFilterByBillable() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(USERS + SEARCH)
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[2].id").isEqualTo("4");
    }

    @Test
    void testFindByFilterByNotBillable() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(USERS + SEARCH)
                        .queryParam("billable", false)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[0].id").isEqualTo("3")
                .jsonPath("$[1].id").isEqualTo("5")
                .jsonPath("$[2].id").isEqualTo("6");
    }

    @Test
    void testFindByFilterByNameAndFamilyNameAndBillable() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(USERS + SEARCH)
                        .queryParam("name", "Paula")
                        .queryParam("familyName", "Torres")
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1)
                .jsonPath("$[0].id").isEqualTo("4");
    }

    @Test
    void testFindByFilterWithoutConditions() {
        this.webTestClient.get()
                .uri(USERS + SEARCH)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(6);
    }

    @Test
    void testFindByFilterWithoutMatches() {
        this.webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path(USERS + SEARCH)
                        .queryParam("name", "Antonio")
                        .queryParam("familyName", "Fernandez")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testDeleteById() {
        this.webTestClient.delete()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk();
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteByIdNotFound() {
        this.webTestClient.delete()
                .uri(USERS + "/999")
                .exchange()
                .expectStatus().isNotFound();
    }
}

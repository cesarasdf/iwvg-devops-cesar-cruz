package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.code.Role;
import es.upm.miw.devops.code.User;
import es.upm.miw.devops.code.UserActiveUpdate;
import es.upm.miw.devops.code.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static es.upm.miw.devops.rest.UserController.SEARCH;
import static es.upm.miw.devops.rest.UserController.USERS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private UserRepository userRepository;

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

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateActive() {
        this.webTestClient.put()
                .uri(USERS + "/1/active")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(false)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.active").isEqualTo(false);
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);
    }

    @Test
    void testUpdateActiveNotFound() {
        this.webTestClient.put()
                .uri(USERS + "/999/active")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(false)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateActiveAdminCannotBeDeactivated() {
        this.makeAdmin("1");

        this.webTestClient.put()
                .uri(USERS + "/1/active")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(false)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(true);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdate() {
        this.webTestClient.put()
                .uri(USERS + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new User("1", "Oscar2", "Fernandez2", "oscar2@mail.com", "12345678A",
                        "Address 2", "City 2", "Province 2", "28002", new ArrayList<>()))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.name").isEqualTo("Oscar2")
                .jsonPath("$.familyName").isEqualTo("Fernandez2")
                .jsonPath("$.email").isEqualTo("oscar2@mail.com");
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Oscar2");
    }

    @Test
    void testUpdateNotFound() {
        this.webTestClient.put()
                .uri(USERS + "/999")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new User("999", "Name", "FamilyName", new ArrayList<>()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateAdminCannotBeDeactivated() {
        this.makeAdmin("1");
        User user = new User("1", "Oscar2", "Fernandez2", "oscar2@mail.com", "12345678A",
                "Address 2", "City 2", "Province 2", "28002", new ArrayList<>());
        user.setActive(false);

        this.webTestClient.put()
                .uri(USERS + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateActiveList() {
        this.webTestClient.patch()
                .uri(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(
                        new UserActiveUpdate("1", false),
                        new UserActiveUpdate("2", false)
                ))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].active").isEqualTo(false)
                .jsonPath("$[1].id").isEqualTo("2")
                .jsonPath("$[1].active").isEqualTo(false);
        this.webTestClient.get()
                .uri(USERS + "/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.active").isEqualTo(false);
    }

    @Test
    void testUpdateActiveListEmpty() {
        this.webTestClient.patch()
                .uri(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0);
    }

    @Test
    void testUpdateActiveListNotFound() {
        this.webTestClient.patch()
                .uri(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(new UserActiveUpdate("999", false)))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void testUpdateActiveListAdminCannotBeDeactivated() {
        this.makeAdmin("1");

        this.webTestClient.patch()
                .uri(USERS)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(List.of(
                        new UserActiveUpdate("2", false),
                        new UserActiveUpdate("1", false)
                ))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.CONFLICT);
    }

    private void makeAdmin(String id) {
        User user = this.userRepository.findById(id).orElseThrow();
        user.setRole(Role.ADMIN);
        this.userRepository.save(user);
    }
}

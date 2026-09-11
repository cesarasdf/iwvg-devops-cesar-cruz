package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserServiceTest {

    @Test
    void testReadById() {
        User user = new UserService().readById("1");
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("Oscar");
        assertThat(user.getFamilyName()).isEqualTo("Fernandez");
    }

    @Test
    void testReadByIdNotFound() {
        assertThatThrownBy(() -> new UserService().readById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }
}

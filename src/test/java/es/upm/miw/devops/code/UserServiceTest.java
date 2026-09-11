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

    @Test
    void testFindByFilterWithoutConditions() {
        assertThat(new UserService().findByFilter(null, null, null))
                .extracting(User::getId)
                .containsExactly("1", "2", "3", "4", "5", "6");
    }

    @Test
    void testFindByFilterByBillable() {
        assertThat(new UserService().findByFilter(null, null, true))
                .extracting(User::getId)
                .containsExactly("1", "2", "4");
    }

    @Test
    void testFindByFilterByNotBillable() {
        assertThat(new UserService().findByFilter(null, null, false))
                .extracting(User::getId)
                .containsExactly("3", "5", "6");
    }

    @Test
    void testFindByFilterByNameAndBillable() {
        assertThat(new UserService().findByFilter("Paula", null, true))
                .extracting(User::getId)
                .containsExactly("4");
    }

    @Test
    void testFindByFilterByFamilyNameAndNotBillable() {
        assertThat(new UserService().findByFilter(null, "Torres", false))
                .extracting(User::getId)
                .containsExactly("6");
    }

    @Test
    void testFindByFilterByNameAndFamilyName() {
        assertThat(new UserService().findByFilter("Oscar", "López", null))
                .extracting(User::getId)
                .containsExactly("3");
    }

    @Test
    void testFindByFilterWithoutMatches() {
        assertThat(new UserService().findByFilter("Antonio", "Fernandez", null))
                .isEmpty();
    }

    @Test
    void testDeleteById() {
        UserService userService = new UserService();
        userService.deleteById("1");
        assertThat(userService.findByFilter(null, null, null))
                .extracting(User::getId)
                .containsExactly("2", "3", "4", "5", "6");
        assertThatThrownBy(() -> userService.readById("1"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThatThrownBy(() -> new UserService().deleteById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }

    @Test
    void testUpdateActive() {
        UserService userService = new UserService();
        assertThat(userService.readById("1").isActive()).isTrue();
        User user = userService.updateActive("1", false);
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.isActive()).isFalse();
        assertThat(userService.readById("1").isActive()).isFalse();
    }

    @Test
    void testUpdateActiveRestoresActive() {
        UserService userService = new UserService();
        userService.updateActive("2", false);
        assertThat(userService.updateActive("2", true).isActive()).isTrue();
    }

    @Test
    void testUpdateActiveNotFound() {
        assertThatThrownBy(() -> new UserService().updateActive("999", false))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }
}

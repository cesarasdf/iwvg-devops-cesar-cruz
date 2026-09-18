package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testReadById() {
        User user = this.userService.readById("1");
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.getName()).isEqualTo("Oscar");
        assertThat(user.getFamilyName()).isEqualTo("Fernandez");
    }

    @Test
    void testReadByIdNotFound() {
        assertThatThrownBy(() -> this.userService.readById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }

    @Test
    void testFindByFilterWithoutConditions() {
        assertThat(this.userService.findByFilter(null, null, null))
                .extracting(User::getId)
                .containsExactlyInAnyOrder("1", "2", "3", "4", "5", "6");
    }

    @Test
    void testFindByFilterByBillable() {
        assertThat(this.userService.findByFilter(null, null, true))
                .extracting(User::getId)
                .containsExactlyInAnyOrder("1", "2", "4");
    }

    @Test
    void testFindByFilterByNotBillable() {
        assertThat(this.userService.findByFilter(null, null, false))
                .extracting(User::getId)
                .containsExactlyInAnyOrder("3", "5", "6");
    }

    @Test
    void testFindByFilterByNameAndBillable() {
        assertThat(this.userService.findByFilter("Paula", null, true))
                .extracting(User::getId)
                .containsExactly("4");
    }

    @Test
    void testFindByFilterByFamilyNameAndNotBillable() {
        assertThat(this.userService.findByFilter(null, "Torres", false))
                .extracting(User::getId)
                .containsExactly("6");
    }

    @Test
    void testFindByFilterByNameAndFamilyName() {
        assertThat(this.userService.findByFilter("Oscar", "López", null))
                .extracting(User::getId)
                .containsExactly("3");
    }

    @Test
    void testFindByFilterWithoutMatches() {
        assertThat(this.userService.findByFilter("Antonio", "Fernandez", null))
                .isEmpty();
    }

    @Test
    void testDeleteById() {
        this.userService.deleteById("1");
        assertThat(this.userService.findByFilter(null, null, null))
                .extracting(User::getId)
                .containsExactlyInAnyOrder("2", "3", "4", "5", "6");
        assertThatThrownBy(() -> this.userService.readById("1"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND");
    }

    @Test
    void testDeleteByIdNotFound() {
        assertThatThrownBy(() -> this.userService.deleteById("999"))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }

    @Test
    void testUpdateActive() {
        assertThat(this.userService.readById("1").isActive()).isTrue();
        User user = this.userService.updateActive("1", false);
        assertThat(user.getId()).isEqualTo("1");
        assertThat(user.isActive()).isFalse();
        assertThat(this.userService.readById("1").isActive()).isFalse();
    }

    @Test
    void testUpdateActiveRestoresActive() {
        this.userService.updateActive("2", false);
        assertThat(this.userService.updateActive("2", true).isActive()).isTrue();
    }

    @Test
    void testUpdateActiveNotFound() {
        assertThatThrownBy(() -> this.userService.updateActive("999", false))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }

    @Test
    void testUpdate() {
        User user = new User("1", "Oscar2", "Fernandez2", "oscar2@mail.com", "12345678A",
                "Address 2", "City 2", "Province 2", "28002", new ArrayList<>());
        user.setActive(false);

        User updatedUser = this.userService.update("1", user);

        assertThat(updatedUser.getId()).isEqualTo("1");
        assertThat(updatedUser.getName()).isEqualTo("Oscar2");
        assertThat(updatedUser.getFamilyName()).isEqualTo("Fernandez2");
        assertThat(updatedUser.getEmail()).isEqualTo("oscar2@mail.com");
        assertThat(updatedUser.getIdentity()).isEqualTo("12345678A");
        assertThat(updatedUser.getAddress()).isEqualTo("Address 2");
        assertThat(updatedUser.getCity()).isEqualTo("City 2");
        assertThat(updatedUser.getProvince()).isEqualTo("Province 2");
        assertThat(updatedUser.getPostalCode()).isEqualTo("28002");
        assertThat(updatedUser.isActive()).isFalse();

        User persistedUser = this.userService.readById("1");
        assertThat(persistedUser.getName()).isEqualTo("Oscar2");
        assertThat(persistedUser.isActive()).isFalse();
    }

    @Test
    void testUpdateNotFound() {
        User user = new User("999", "Name", "FamilyName", new ArrayList<>());
        assertThatThrownBy(() -> this.userService.update("999", user))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("404 NOT_FOUND")
                .hasMessageContaining("User not found");
    }
}

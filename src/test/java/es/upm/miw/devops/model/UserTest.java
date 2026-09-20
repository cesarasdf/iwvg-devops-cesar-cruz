package es.upm.miw.devops.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void testFullNameAndInitials() {
        User user = new User("1", "Paula", "Torres");
        assertThat(user.fullName()).isEqualTo("Paula Torres");
        assertThat(user.initials()).isEqualTo("P.");
    }

    @Test
    void testActive() {
        User user = new User();
        user.setActive(false);
        assertThat(user.isActive()).isFalse();
    }

    @Test
    void testBillableWhenAllFieldsPresent() {
        User user = new User("1", "Paula", "Torres", "paula@mail.com", "12345678A", "Address",
                "City", "Province", "28001");
        assertThat(user.isBillable()).isTrue();
    }

    @Test
    void testNotBillableWhenAFieldIsBlank() {
        User user = new User("1", "Paula", "Torres", "", "12345678A", "Address",
                "City", "Province", "28001");
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void testNotBillableWhenAFieldIsNull() {
        User user = new User("1", "Paula", "Torres", "paula@mail.com", "12345678A", "Address",
                "City", "Province", null);
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void testToString() {
        User user = new User("1", "Paula", "Torres");
        assertThat(user.toString()).contains("id='1'", "name='Paula'", "familyName='Torres'");
    }
}

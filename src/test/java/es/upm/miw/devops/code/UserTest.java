package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();
        assertThat(user.getFractions()).isEmpty();
        assertThat(user.isActive()).isTrue();
    }

    @Test
    void testFullNameAndInitials() {
        User user = new User("1", "Paula", "Torres", List.of());
        assertThat(user.fullName()).isEqualTo("Paula Torres");
        assertThat(user.initials()).isEqualTo("P.");
    }

    @Test
    void testAddFractionAndSetFractions() {
        User user = new User();
        Fraction fraction = new Fraction(1, 2);
        user.addFraction(fraction);
        assertThat(user.getFractions()).containsExactly(fraction);

        user.setFractions(List.of());
        assertThat(user.getFractions()).isEmpty();
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
                "City", "Province", "28001", List.of());
        assertThat(user.isBillable()).isTrue();
    }

    @Test
    void testNotBillableWhenAFieldIsBlank() {
        User user = new User("1", "Paula", "Torres", "", "12345678A", "Address",
                "City", "Province", "28001", List.of());
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void testNotBillableWhenAFieldIsNull() {
        User user = new User("1", "Paula", "Torres", "paula@mail.com", "12345678A", "Address",
                "City", "Province", null, List.of());
        assertThat(user.isBillable()).isFalse();
    }

    @Test
    void testToString() {
        User user = new User("1", "Paula", "Torres", List.of());
        assertThat(user.toString()).contains("id='1'", "name='Paula'", "familyName='Torres'");
    }
}

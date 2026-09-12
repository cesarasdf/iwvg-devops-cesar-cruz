package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class FractionTest {

    @Test
    void testDefaultConstructor() {
        Fraction fraction = new Fraction();
        assertThat(fraction.getNumerator()).isEqualTo(1);
        assertThat(fraction.getDenominator()).isEqualTo(1);
    }

    @Test
    void testConstructor() {
        Fraction fraction = new Fraction(1, 2);
        assertThat(fraction.getNumerator()).isEqualTo(1);
        assertThat(fraction.getDenominator()).isEqualTo(2);
    }

    @Test
    void testSetters() {
        Fraction fraction = new Fraction();
        fraction.setNumerator(3);
        fraction.setDenominator(4);
        assertThat(fraction.getNumerator()).isEqualTo(3);
        assertThat(fraction.getDenominator()).isEqualTo(4);
    }

    @Test
    void testDecimal() {
        assertThat(new Fraction(1, 2).decimal()).isCloseTo(0.5, within(1e-9));
    }

    @Test
    void testToString() {
        assertThat(new Fraction(1, 2).toString()).isEqualTo("Fraction{numerator=1, denominator=2}");
    }
}

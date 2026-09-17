package es.upm.miw.devops.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

class PointLackOfPOOTest {
    private PointLackOfPOO point;

    @BeforeEach
    void before() {
        point = new PointLackOfPOO(2, 3);
    }

    @Test
    void testPointIntInt() {
        assertThat(point.getX()).isEqualTo(2);
        assertThat(point.getY()).isEqualTo(3);
    }

    @Test
    void testPointInt() {
        point = new PointLackOfPOO(2);
        assertThat(point.getX()).isEqualTo(2);
        assertThat(point.getY()).isEqualTo(2);
    }

    @Test
    void testPoint() {
        point = new PointLackOfPOO();
        assertThat(point.getX()).isZero();
        assertThat(point.getY()).isZero();
    }

    @Test
    void testModule() {
        assertThat(point.module(point.getX(), point.getY()))
                .isCloseTo(3.60555, within(1e-5));
    }

    @Test
    void testPhase() {
        assertThat(point.phase(point.getX(), point.getY()))
                .isCloseTo(0.98279, within(1e-5));
    }

    @Test
    void testTranslateXOrigin() {
        point.translateXOrigin(1);
        assertThat(point.getX()).isEqualTo(1);
        assertThat(point.getY()).isEqualTo(3);
    }

    @Test
    void testTranslateOrigin() {
        point.translateOrigin(1, 1);
        assertThat(point.getX()).isEqualTo(1);
        assertThat(point.getY()).isEqualTo(2);
    }

    @Test
    void testToString() {
        assertThat(point.toString()).isEqualTo("Point{x=2, y=3}");
    }
}

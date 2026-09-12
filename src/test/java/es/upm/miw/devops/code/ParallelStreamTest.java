package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThatCode;

class ParallelStreamTest {

    /**
     * The private timing methods only differ from each other in *how* they compute a sum (sequential vs.
     * parallel, range vs. array...); their business logic doesn't depend on the input size. A small n keeps
     * the test fast while still exercising every instruction.
     */
    private static final int SMALL_N = 5;

    @Test
    void testSequentialRange() {
        assertThatCode(() -> invokePrivate("sequentialRange", SMALL_N))
                .doesNotThrowAnyException();
    }

    @Test
    void testSequentialCollection() {
        assertThatCode(() -> invokePrivate("sequentialCollection", SMALL_N))
                .doesNotThrowAnyException();
    }

    @Test
    void testParallelRange() {
        assertThatCode(() -> invokePrivate("parallelRange", SMALL_N))
                .doesNotThrowAnyException();
    }

    @Test
    void testParallelIterative() {
        assertThatCode(() -> invokePrivate("parallelIterative", SMALL_N))
                .doesNotThrowAnyException();
    }

    @Test
    void testParallelArray() {
        assertThatCode(() -> invokePrivate("parallelArray", SMALL_N))
                .doesNotThrowAnyException();
    }

    private void invokePrivate(String methodName, int... n) throws ReflectiveOperationException {
        ParallelStream parallelStream = new ParallelStream();
        Method method = n.length == 0
                ? ParallelStream.class.getDeclaredMethod(methodName)
                : ParallelStream.class.getDeclaredMethod(methodName, int.class);
        method.setAccessible(true);
        if (n.length == 0) {
            method.invoke(parallelStream);
        } else {
            method.invoke(parallelStream, n[0]);
        }
    }
}

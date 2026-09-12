package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;

class DateSnippetTest {

    @Test
    void testCreateDate() {
        assertThatCode(() -> new DateSnippet().createDate())
                .doesNotThrowAnyException();
    }

    @Test
    void testCreateTime() {
        assertThatCode(() -> new DateSnippet().createTime())
                .doesNotThrowAnyException();
    }

    @Test
    void testCreateDateTime() {
        assertThatCode(() -> new DateSnippet().createDateTime())
                .doesNotThrowAnyException();
    }

    @Test
    void testInstant() {
        assertThatCode(() -> new DateSnippet().instant())
                .doesNotThrowAnyException();
    }
}

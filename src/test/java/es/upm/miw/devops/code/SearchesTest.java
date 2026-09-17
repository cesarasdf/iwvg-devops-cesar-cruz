package es.upm.miw.devops.code;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchesTest {

    @Test
    void testFindUserFamilyNameByUserNameDistinct() {
        assertThat(new Searches().findUserFamilyNameByUserNameDistinct("Paula").toList())
                .containsExactly("Torres");
    }

    @Test
    void testFindUserFractionNumeratorByFamilyName() {
        assertThat(new Searches().findFractionNumeratorByUserFamilyName("Torres").toList())
                .containsExactly(2, 4, 0, 1, 1);
    }

    @Test
    void testFindFamilyNameByFractionDenominator() {
        assertThat(new Searches().findUserFamilyNameByFractionDenominator(2).toList())
                .containsExactly("López", "Torres");
    }

    @Test
    void testFindUserIdByAnyProperFraction() {
        assertThat(new Searches().findUserIdByAnyProperFraction().toList()).isEmpty();
    }

    @Test
    void testFindUserNameByAnyImproperFraction() {
        assertThat(new Searches().findUserNameByAnyImproperFraction().toList()).isEmpty();
    }

    @Test
    void testFindUserFamilyNameByAllNegativeSignFractionDistinct() {
        assertThat(new Searches().findUserFamilyNameByAllNegativeSignFractionDistinct().toList()).isEmpty();
    }

    @Test
    void testFindDecimalFractionByUserName() {
        assertThat(new Searches().findDecimalFractionByUserName("Paula").toList()).isEmpty();
    }

    @Test
    void testFindDecimalFractionByNegativeSignFraction() {
        assertThat(new Searches().findDecimalFractionByNegativeSignFraction().toList()).isEmpty();
    }

    @Test
    void testFindFractionAdditionByUserId() {
        assertThat(new Searches().findFractionAdditionByUserId("1")).isNull();
    }

    @Test
    void testFindFirstFractionSubtractionByUserName() {
        assertThat(new Searches().findFirstFractionSubtractionByUserName("Paula")).isNull();
    }

    @Test
    void testFindFractionMultiplicationByUserFamilyName() {
        assertThat(new Searches().findFractionMultiplicationByUserFamilyName("Torres")).isNull();
    }

    @Test
    void testFindFirstFractionDivisionByUserId() {
        assertThat(new Searches().findFirstFractionDivisionByUserId("1")).isNull();
    }

    @Test
    void testFindFirstDecimalFractionByUserName() {
        assertThat(new Searches().findFirstDecimalFractionByUserName("Paula")).isNull();
    }

    @Test
    void testFindUserIdByAllProperFraction() {
        assertThat(new Searches().findUserIdByAllProperFraction().toList()).isEmpty();
    }

    @Test
    void testFindUserFamilyNameInitialByAnyProperFraction() {
        assertThat(new Searches().findUserFamilyNameInitialByAnyProperFraction().toList()).isEmpty();
    }

    @Test
    void testFindFirstProperFractionByUserId() {
        assertThat(new Searches().findFirstProperFractionByUserId("1")).isNull();
    }

    @Test
    void testFindUserFamilyNameByImproperFraction() {
        assertThat(new Searches().findUserFamilyNameByImproperFraction().toList()).isEmpty();
    }

    @Test
    void testFindHighestFraction() {
        assertThat(new Searches().findHighestFraction()).isNull();
    }
}

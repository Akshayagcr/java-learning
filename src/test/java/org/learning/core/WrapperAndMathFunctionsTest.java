package org.learning.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WrapperAndMathFunctionsTest {

    @DisplayName("Integer wrapper")
    @Test
    void testInteger(){
        int max = Integer.MAX_VALUE;
        int min = Integer.MIN_VALUE;

        assertThat(Integer.valueOf(1)).isInstanceOf(Integer.class);
        assertThat(Integer.valueOf("1")).isInstanceOf(Integer.class).isEqualTo(1);
        assertThat(Integer.toString(1)).isEqualTo("1");

        assertThat(Integer.min(1, 2)).isEqualTo(1);
        assertThat(Integer.max(1, 2)).isEqualTo(2);
        assertThat(Integer.sum(1, 2)).isEqualTo(3);
    }

    @DisplayName("Character wrapper")
    @Test
    void testCharacter(){
        assertThat(Character.isAlphabetic('a')).isTrue();
        assertThat(Character.isDigit('4')).isTrue();
        assertThat(Character.isUpperCase('A')).isTrue();
        assertThat(Character.isLowerCase('a')).isTrue();

        assertThat(Character.toUpperCase('a')).isEqualTo('A');
        assertThat(Character.toLowerCase('A')).isEqualTo('a');
    }

    @DisplayName("Math functions")
    @Test
    void testMathFunctions(){
        assertThat(Math.min(Math.min(30, 20), Math.min(10, 5))).isEqualTo(5);
        assertThat(Math.max(Math.max(30, 20), Math.max(10, 5))).isEqualTo(30);
    }
}

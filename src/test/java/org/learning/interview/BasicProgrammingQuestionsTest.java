package org.learning.interview;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BasicProgrammingQuestionsTest {

    @DisplayName("Test BasicProgrammingQuestions")
    @Test
    void testBasicProgrammingQuestions(){
        assertThat(BasicProgrammingQuestions.reverse("Akshay")).isEqualTo("yahskA");

        assertThat(BasicProgrammingQuestions.isPalindrome("Akshay")).isFalse();
        assertThat(BasicProgrammingQuestions.isPalindrome("liril")).isTrue();

        assertThat(BasicProgrammingQuestions.characterFrequency("akshay")).contains(
                Map.entry('a', 2), Map.entry('k', 1), Map.entry('s', 1), Map.entry('h', 1), Map.entry('y', 1));

        assertThat(BasicProgrammingQuestions.isAnagram("Akshay", "Rathod")).isFalse();
        assertThat(BasicProgrammingQuestions.isAnagram("liril", "lliir")).isTrue();
    }
}

package org.learning.dsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BasicInterviewProgrammingQuestionsTest {

    @DisplayName("Test BasicProgrammingQuestions")
    @Test
    void testBasicProgrammingQuestions(){
        assertThat(BasicInterviewProgrammingQuestions.reverse("Akshay")).isEqualTo("yahskA");

        assertThat(BasicInterviewProgrammingQuestions.isPalindrome("Akshay")).isFalse();
        assertThat(BasicInterviewProgrammingQuestions.isPalindrome("liril")).isTrue();

        assertThat(BasicInterviewProgrammingQuestions.characterFrequency("akshay")).contains(
                Map.entry('a', 2), Map.entry('k', 1), Map.entry('s', 1), Map.entry('h', 1), Map.entry('y', 1));

        assertThat(BasicInterviewProgrammingQuestions.isAnagram("Akshay", "Rathod")).isFalse();
        assertThat(BasicInterviewProgrammingQuestions.isAnagram("liril", "lliir")).isTrue();

        assertThat(BasicInterviewProgrammingQuestions.insertionSort(new int[]{4, 3, 3, 2, 1})).containsSequence(1, 2, 3, 3, 4);

        assertThat(BasicInterviewProgrammingQuestions.reverserInteger(123)).isEqualTo(321);
    }
}

package org.learning.interview;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.learning.interview.BasicInterviewProgrammingQuestions.*;

class BasicInterviewProgrammingQuestionsTest {

    @DisplayName("Test BasicProgrammingQuestions")
    @Test
    void testBasicProgrammingQuestions(){
        assertThat(reverse("Akshay")).isEqualTo("yahskA");

        assertThat(isPalindrome("Akshay")).isFalse();
        assertThat(isPalindrome("liril")).isTrue();

        assertThat(characterFrequency("akshay")).contains(
                Map.entry('a', 2), Map.entry('k', 1), Map.entry('s', 1), Map.entry('h', 1), Map.entry('y', 1));

        assertThat(isAnagram("Akshay", "Rathod")).isFalse();
        assertThat(isAnagram("liril", "lliir")).isTrue();

        assertThat(insertionSort(new int[]{4, 3, 3, 2, 1})).containsSequence(1, 2, 3, 3, 4);

        assertThat(reverserInteger(123)).isEqualTo(321);
        
        var arr = new int[][]{
                {1, 0, 0},
                {1, 2, 0},
                {1, 2, 3}
        };
        assertThat(getRows(arr)).isEqualTo(List.of(
                List.of(1, 0, 0), List.of(1, 2, 0), List.of(1, 2, 3)
        ));
        assertThat(getColumns(arr)).isEqualTo(List.of(
                List.of(1, 1, 1), List.of(0, 2, 2), List.of(0, 0, 3)
        ));
    }
}

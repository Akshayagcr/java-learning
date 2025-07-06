package org.learning.dsa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SearchingSortingTest {

    @DisplayName("Test binary search")
    @Test
    void testBinarySearch(){

        var arr = new int[]{1, 4, 5, 8, 10, 12, 16};

        assertThat(SearchingSorting.binarySearch(arr, 1)).isEqualTo(0);
        assertThat(SearchingSorting.binarySearch(arr, 16)).isEqualTo(6);
        assertThat(SearchingSorting.binarySearch(arr, 8)).isEqualTo(3);
        assertThat(SearchingSorting.binarySearch(arr, 100)).isEqualTo(-1);
    }
}

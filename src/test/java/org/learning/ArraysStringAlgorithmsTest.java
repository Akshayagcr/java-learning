package org.learning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *  ******* VIMP
 *  For sorting, finding element in unsorted array, minimum/ maximum element, count use steams API.
 */
class ArraysStringAlgorithmsTest {

    @DisplayName("Character & Integer")
    @Test
    void testCharacterInteger(){
        assertThat(Character.isAlphabetic('a')).isTrue();
        assertThat(Character.isDigit('4')).isTrue();
        assertThat(Character.isUpperCase('A')).isTrue();
        assertThat(Character.isLowerCase('a')).isTrue();
        assertThat(Character.toUpperCase('a')).isEqualTo('A');
        assertThat(Character.toLowerCase('A')).isEqualTo('a');

        assertThat(Integer.parseInt("23")).isEqualTo(23);
        assertThat(Integer.toString(23)).isEqualTo("23");
    }

    /**
     * String is immutable, and stored in string pool
     * StringBuilder is mutable and unSynchronized (StringBuffer is Synchronous)
     */
    @DisplayName("String & StringBuilder & Text block")
    @Test
    void testString(){
        String s1 = "Akshay";
        String s2 = new String("Akshay");
        String s3 = "Akshay";
        assertThat(s1 == s2).isFalse();
        assertThat(s1.equals(s2)).isTrue();
        assertThat(s1 == s3).isTrue();

        assertThat(s3.length()).isEqualTo(6);
        assertThat(s1.charAt(0)).isEqualTo('A');

        var yCount = s1.chars()
                .mapToObj(c -> (char)c)
                .filter(c -> c.equals('y'))
                .count();
        assertThat(yCount).isEqualTo(1);

        var sb = new StringBuilder(s1);
        assertThat(sb.charAt(0)).isEqualTo('A');
        sb.setCharAt(0, 'B');
        assertThat(sb.charAt(0)).isEqualTo('B');
        sb.deleteCharAt(0);
        assertThat(sb.toString())
                .isEqualTo("kshay")
                .isInstanceOf(String.class);

        var yCountSb = sb.chars()
                .mapToObj(c -> (char)c)
                .filter(c -> c.equals('y'))
                .count();
        assertThat(yCountSb).isEqualTo(1);

        sb.append(" Ratho"); sb.append('d');
        assertThat(sb.toString())
                .isEqualTo("kshay Rathod");

        /*
            Text block start """ : We cannot have anything on this line, not event a comment
            All spaces that are aligned vertically from """ or the left most non-blank character are included in the text block
            All spaces which are on the right of the non-blank character in the string are not included.
            If space needed then place a replacement char at the end of the string and then later replace it with space
         */
        var tb = """
                {
                    "name":"Akshay"
                }
                """;
    }

    @DisplayName("Arrays")
    @Test
    void testArrays(){
        int[][] arr1 = new int[2][2];
        int[][] arr2 = {{1, 2, 3}, {1, 2}};

        assertThat(arr1.length).isEqualTo(2);
        assertThat(arr2[0].length).isEqualTo(3);
    }

    @DisplayName("Arrays method")
    @Test
    void testArraysMethod(){
        int[] arr = {3, 1, 2};

        /*
            **** Use steams API sort

            sort(obj arr, Comparator)
            sort(obj arr, start index, end index exclusive)
            sort(obj arr, start index, end index exclusive, Comparator)
         */
        Arrays.sort(arr);
        assertThat(arr).containsSequence(1, 2, 3);

        /*
            Array needs to be sorted.
            Element found : return its index.
            Multiple element with same value : Can return index of any element with smae value.
            Element not found : return -ve index (Corresponding +ve index would be its insertion point in sorted array)

            Arrays.binarySearch(obj array, key);
            Arrays.binarySearch(obj array, key, comparator);
            Arrays.binarySearch(obj array, start index, end index exclusive, key);
            Arrays.binarySearch(obj array, start index, end index exclusive, key, comparator);
         */
        assertThat(Arrays.binarySearch(arr, 3)).isEqualTo(2);

        assertThat(Arrays.copyOf(arr, 5)).containsSequence(1, 2, 3, 0, 0); // Remaining size will be filled by default value
        assertThat(Arrays.copyOfRange(arr, 1, 3)).containsSequence(2, 3); // to index is exclusive. Can be outside the array
        assertThat(Arrays.equals(new int[]{1, 2, 3}, new int[]{1, 2, 3})).isTrue();
        Arrays.fill(arr, 1); // Arrays.fill(array, start index, end index exclusive, value)
        assertThat(arr).containsSequence(1, 1, 1);

        /*
            ***** VIMP
         */
        var m = Arrays.stream(new int[]{1, 2, 3})
                .max()
                .getAsInt();
        assertThat(m).isEqualTo(3);
    }

    @DisplayName("Collections algorithms")
    @Test
    void testCollectionsAlgo(){
        var coll = new ArrayList<>(List.of(30, 20, 10));

        Collections.addAll(coll, 50, 60, 70, 80);
        assertThat(coll).containsSequence(30, 20, 10, 50, 60, 70, 80);

        /*
            **** Use steams API sort

            Collections.sort(collections, comparator)
         */
        Collections.sort(coll);
        assertThat(coll).containsSequence(10, 20, 30, 50, 60, 70, 80);

        /*
            Collections.binarySearch(collection, key, comparator)
         */
        assertThat(Collections.binarySearch(coll, 20)).isEqualTo(1);

        Collections.fill(coll, 1);
        assertThat(coll).containsSequence(1, 1, 1, 1, 1, 1, 1);
    }

    @DisplayName("Math functions")
    @Test
    void testMathFunctions(){
        assertThat(Math.min(Math.min(30, 20), Math.min(10, 5))).isEqualTo(5);
        assertThat(Math.max(Math.max(30, 20), Math.max(10, 5))).isEqualTo(30);
    }

}

package org.learning.collectionslearning;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionsTest {

    @DisplayName("Sample test")
    @Test
    void test(){
        List<String> l = List.of("");
        assertThat(l).hasSize(1);
    }

}

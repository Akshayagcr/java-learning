package org.learning.util;

import org.learning.model.Employee;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class TestDataUtil {

    public static List<Employee> getEmployees(){
        return List.of(
                new Employee("Raj", "Marketing", 35000),
                new Employee("Akshay", "IT", 200000),
                new Employee("Suraj", "IT", 150000),
                new Employee("Akshay", "HR", 10000),
                new Employee("Dhaval", "Marketing", 25000)
        );
    }

    public static List<String> getAlphabets(){
        return List.of("alfa", "bravo", "charlie", "delta", "echo",
                "foxtrot", "golf", "hotel", "india", "juliet",
                "kilo", "lima", "mike", "november", "oscar",
                "papa", "quebec", "romeo", "sierra", "tango",
                "uniform", "victor", "whiskey", "x-ray", "yankee",
                "zulu");
    }

    public static List<String> getSonnet(){
        return List.of(
                "From fairest creatures we desire increase,",
                "That thereby beauty's rose might never die,",
                "But as the riper should by time decease,",
                "His tender heir might bear his memory:",
                "But thou contracted to thine own bright eyes,",
                "Feed'st thy light's flame with self-substantial fuel,",
                "Making a famine where abundance lies,",
                "Thy self thy foe, to thy sweet self too cruel:",
                "Thou that art now the world's fresh ornament,",
                "And only herald to the gaudy spring,",
                "Within thine own bud buriest thy content,",
                "And, tender churl, mak'st waste in niggarding:",
                "Pity the world, or else this glutton be,",
                "To eat the world's due, by the grave and thee.");
    }

    public static List<String> expandStringToCharList(String s) {
        return s.codePoints()
                .mapToObj(codePoint -> Character.toString((char) codePoint))
                .collect(toList());
    }
}

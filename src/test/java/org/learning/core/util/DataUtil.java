package org.learning.core.util;

import org.learning.core.model.Employee;

import java.util.List;

public class DataUtil {

    public static List<Employee> getEmployees(){
        return List.of(
                new Employee("Raj", "Marketing", 35000),
                new Employee("Akshay", "IT", 200000),
                new Employee("Suraj", "IT", 150000),
                new Employee("Akshay", "HR", 10000),
                new Employee("Dhaval", "Marketing", 25000)
        );
    }
}

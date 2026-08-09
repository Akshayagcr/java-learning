package org.learning.core;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Ch6LambdaExpression {

    void main(){

        /*
        Lambdas can only access variables which are final or effective final.
        Effective final: Variables which are not marked as final. but act as final as they are assigned only once
        and are not modified

        Base interfaces:
        */

        // 1. Supplier: do not take any argument, return something
        Supplier<LocalDateTime> localDateTimeSupplier = () -> LocalDateTime.now();
        localDateTimeSupplier.get();

        // 2. Consumer: take an argument, do not return anything
        Consumer<String> genericPrinter = (value) -> IO.println(value);
        genericPrinter.accept("hello");

        // 3. Predicate: take an argument, return a boolean
        Predicate<String> isBlankChecker = (value) -> value.isBlank();
        isBlankChecker.test("hello");

        // 4. Function: take an argument, return something
        Function<String, Integer> stringLengthCalculator = (value) -> value.length();
        stringLengthCalculator.apply("hello");


        /*
        Specialized versions
            1. Takes two arguments
                1. biconsumers
                2. bipredicates
                3. bifunctions
            2. Extension of function when all arguments are of same
                1. UnaryOperator
                2. BinaryOperator
            3. Primitive equivalents to avoid auto-boxing/unboxing

        Method reference: Sometimes a lambda expression is just a reference to an existing method.
            In that case you can write it is as a method reference.

            Types:
                1. Static method reference
                    Math::sqrt -> Referencing static method of a class
                2. Unbounded method reference
                    Person::getName, String::length -> method ref definition does not refer to a particular instance.
                3. Bounded method reference
                    IO::println -> method ref definition refers a particular instance.
                4. Constructor
                    ArrayList::new

        Chaining functional interface
            Predicate
                1. p1.and(p2).or(p3.negate())
                2. Factory methods to create predicate
                    1. Predicate<String> isEqualToAkshay = Predicate.isEqual("Akshay");
                    1. Predicate<String> isNotEqualToAkshay = Predicate.not(isEqualToAkshay);
    */
        Predicate<String> isNameEqualToAkshay = Predicate.isEqual("akshay");
        Predicate<String> isLowerCase = (value) -> StringUtils.isAllLowerCase(value);

        Predicate<String> nameChecker = isNameEqualToAkshay.and(isLowerCase);
        nameChecker.test("akshay"); // true


        // Consumer Chaining :- consumerOne.andThen(consumerTwo) -> Object is first passed to consumerOne and then to consumerTwo
        Consumer<String> greatConsumer = (value) -> IO.println("Hello " + value);
        Consumer<String> politeConsumer = (value) -> IO.println("How are you " + value);

        Consumer<String> managerConsumer = greatConsumer.andThen(politeConsumer);
        managerConsumer.accept("Akshay"); // Result = Hello Akshay /n How are you Akshay

    /*
            Function
                1. Chaining
                    f1.andThen(f2) -> object is first passed to f1 and result of f1 is then passed to f2
                2. Composing
                    f2.compose(f1) -> object is first passed to f1 and result of f1 is then passed to f2

                The result of both operations is in fact the same. What is different is the way you write it.
     */
        Function<Integer, Integer> addOne = (value) -> value + 1;
        Function<Integer, Integer> addTwo = (value) -> value + 2;
        Function<Integer, Integer> addThree = (value) -> value + 3;

        Function<Integer, Integer> chained = addOne.andThen(addTwo).andThen(addThree);
        chained.apply(1); // Result = 7

        // Creating identity function (Eg : abc -> abc, 123 -> 123)
        Function.identity();

    }

}

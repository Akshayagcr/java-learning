package org.learning.conferences;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Lambdas and Streams Master Class Part 1 José Paumard, Stuart Marks voxxed : https://www.youtube.com/watch?v=ePXnCezwRuw
 * Lambdas and Streams Master Class Part 2 by José Paumard, Stuart Marks : https://www.youtube.com/watch?v=2c_KNH3s2S0&t=456s
 */
class LambdasStreamMasterClassTest {

    void functionalInterfaces(){
        /*
            1. Functional interface : interface having only a single abstract method.
            (Object class methods, default, static method not counted in single abstract method rule)
            2. Four primary functional interfaces : Consumer, Supplier, Function, Predicate.
            3. Lambda provides implementation of functional interface.
            **** need to only think about number of parameters and return type.
         */
        Consumer<String> c = (String s) -> System.out.println(s);
        c.accept("Hello world");

        Supplier<String> s = () -> "Hello world";
        // assertThat(s.get()).isEqualTo("Hello world");

        Function<String, Integer> f = (String st) -> st.length();
        // assertThat(f.apply("Hello world")).isEqualTo(11);

        Predicate<String> p = (String st) -> st.isBlank();
        // assertThat(p.test("Hello world")).isFalse();

        /*
            Generally we cannot add methods to already defined interface as it breaks contract,
            So in java 8 default methods in interfaces we added to support interface evolution
            i.e. for the existing implementations that does not provide implementation of new method
            it acts as a default implementation

            We can chain functional interface with the default method present on them
            eg .andThen() method on Consumer interface
            (Consumer interface is new interface but still defaults are used because they also provide a richer programming model
            which is the second reason for introducing default methods)

            We can use "this" in interface and default method can act on instance of interface
            default method are instance method on the interface as we call them on an instance like
            Consumer<String> c = (String s) -> System.out.println(s);
            Predicate<String> p = (String st) -> st.isBlank();
            c.andThen() ----- default instance method
            p.negate() ----- default instance method
         */

        var p2 = p.negate();   // default instance method in predicate interface.
        // assertThat(p2.test("Hello world")).isTrue();

        /*
            We can pass null to function interface methods like test()
            but cannot pass to methods that accepts other functional interface like andThen()

            Static methods in interface are used to create factory methods which are introduced in java 8
            like List.of(), Comparator.comparing(keyExtractor) these factory methods are used to create an
            instance of the interface by return an anonymous class or lambda implementation

             when the result of comparison is equal we need to compare by other field by using theComparing() method
             which acts on an instance on interface so it need to be a default method as it acts on instance

             ****** By using lambdas as way to provide implementation, Static method on interface which can be used to create instances
             and then using default method as instance method that works on the instances returned by static factory method of interface
             we can create fluent API !!!!
             See example in lambda-master-class-part-1

                1: CurrencyConverter converter = CurrencyConverter.of(date).from("EUR").to("GBP");
                    CurrencyConverter - interface
                    of() - static method (uses lambda to provide implementation)
                    from(), to() - instance(default) method

                2:Validator<Person> validator = Validator.<Person>firstValidate(p -> p.getLastName() == null, "name is null")
                                                                    .thenValidate(p -> p.getAge() < 0, "age is negative")
                Validator - interface
                firstValidate() - static method (uses lambda to provide implementation)
                thenValidate() - instance(default) method

         */
    }

    void javaStreams(){
        /*
            flatMap :- It converts a single element to none, one or more element, we can represent this output with an array or collection
                        But as it is streams API it is represented as stream of element. A stream can be empty or can have n elements
                        **** So flatmap takes a function which converts a single element to stream of element

            Reduction :- reduce(identity, accumulator) function requires an identity or a seed value, Think of that as the
                            default value that should be returned when the steam is empty
                            ******* i.e. reduction operation on empty stream should always result in identity
         */

        var res = Stream.of(1, 2, 3, 4, 5)
                .reduce(0, (total, ele) -> total + ele);
        System.out.println(res);

        /*
            Function combination
            Problem : If we have n number of predicate how do we combine them ?
            var combinedPredicate = firstPredicate.and(secondPredicate).and(thirdPredicate)....
            above technique can be used if we have a static number of predicate which we need to combine.

            1. To combine arbitrary number of predicates i.e. List<Predicate<CustomObj>> into Predicate<CustomObj>
            we can use reduce() operation. true is the identity value for and operation
            .reduce(CustomObj -> true, Predicate::and)

            2. Combine List of IntUnaryOperator into one
            .reduce(IntUnaryOperator::identity, IntUnaryOperator::thenApply)   --- identity() == i -> i


            Collectors :-
                            .toMap(keyMapper, valueMapper, mergeFunction)
                            .groupingBy(classifier)  ---- fancy way for reducing to map

            ***** Fact : in case of parallel stream input encounter order will be preserved in the output.
                            operation will be done in parallel on different threads but when combining
                            parallel stream will take care to merge it in the proper encounter order

            Set of collectors that need downstream collectors
            1. mapping(mapper, downStreamCollector)
            2. filtering(predicate, downStreamCollector)
            1. flatMapping(mapper, downStreamCollector)
         */

        Pattern pattern = Pattern.compile("/"); // TODO : Learn about Pattern !!

        pattern.splitAsStream("String input") // Breaks a string according to regex provided in stream of element
                .filter(s -> s.length() > 2)
                .forEach(System.out::println);

        /*
            Streaming map :-
            As map is set of key value pair we cannot directly stream over map, but instead we need to use map.entrySet().stream()
            to stream over map.
            We can invert a map using groupingBy.
            TODO: Add example of inverting a frequency count map to, map of count as key and list of values

            Iterating over index:-
            Sometimes we need to process a group of element(window) and usual technique of streaming values does not help
            in these cases we can stream over index to get the group of required indexes use below methods

            IntStream.range(start, end exclusive)
            IntStream.rangeClosed(start, end exclusive)

            IntStream.rangeClosed(start, end exclusive)
                     .mapToObj(index -> collection.subList(starting offset + index, ending offset + index))
                     .toList()
         */
    }

}

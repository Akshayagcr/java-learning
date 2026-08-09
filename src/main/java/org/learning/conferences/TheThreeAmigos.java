package org.learning.conferences;

import java.util.List;

/**
 *  https://www.youtube.com/watch?v=00BoeRkumG8&t=7002s
 */
public class TheThreeAmigos {

    /*
        Three Amigos:
            1. Records
            2. Sealed classes
            3. Pattern matching

            1. Records
                    Used to represent data
                    A record is
                        1. Final class
                        2. Cannot have super or sub class
                        3. Can implement intefaces
                        4. enforces composition over inheritance
                        5. We get following five things for free
                                1. Canonical constructor
                                2. toString
                                3. getters
                                4. equals
                                5. hashCode
                        6. Records are immutable - we need to make sure they are
                                make sure record is made up of String, Primitives or other immutable classes

     */

    record Person(String firstName, String lastName, List<Integer> bankAccountNumbers) {
        public Person {
            bankAccountNumbers = List.copyOf(bankAccountNumbers); // *to enforce list immutability
            /*
                List.copyOf(collection) creates a copy only if passed collection is mutable !!!!
             */
        }
    }

    /*
        Don't write canonical constructor if not required. We need to write constructor for records in following casess
            1. Data cleansing or transformation
            2. Data validation
        instead of canonical constructor write compact constructor reason being that for canonical constructor we again need to write
        field assignments like this.age = age

        compact constructor is an interceptor between our call and canonical constructor call. it not really a constructor.
            It is the middle agent that validates and transforms our data

        Record can be used as tuple.
            Tuple is used to store multiple item in a single variable
            It helps to move data around rather than working with discrete data pices.
     */

    private static void methodOne(){
        record PersonTuple(String firstName, String lastName) {} // ***** record created inside method !!!!!!

        List<PersonTuple> personTupleList = List.of(new PersonTuple("Akshay", "Rathod"));
    }

    /*
        Sealed classes (Sealed interface)
            Used when we want other to use our interfaces but prevent them from extending them, at the same time
            we should be able to extend them i.e. provide implementations

            We need to place all implementation of a sealed interface in same package if we are not using module.
            If we use module then implemntation class can be in any package which belongs to same module.

            Implemntation classes can be marked as
                1. Final - Most popular and sensible option
                2. Sealed - Use if the class serves as base class
                3. non sealed - If we want to create an experimental base class and not have to keep hacking permits clause
                    Recommendation- Make the non sealed class as package-private which prevents other from creating interface at same time
                        allows us to create implementation classes.

            As implementation class can be final, this makes Records classes good candidates for implementing sealed interfaces
     */

    sealed interface TrafficLight permits RedTrafficLight, YellowTrafficLight {}

    final class RedTrafficLight implements TrafficLight {}

    final class YellowTrafficLight implements TrafficLight {}

    /*
        From switch to pattern matching

            With switch as statement needs break, which make It's verbose
            With switch as expression we can directly return value instead of mutation or side effect
     */

    void switchExample(){
        var result = switch(2){
            case int i when i < 10 -> "Got a int less than 10"; // *** Guarded case
            case int j when j >= 10 -> "Got a int greater than 10";
            default -> "No clue";
        };

        /*
            Switch expression can be used with records and sealed classes!!!

            Object oriented programming

                To make a class extensible and respect open-close principle or to make it loosely coupled we need to use
                    dependency inversion principle i.e. instead of a concrete class depending on another concrete class make
                    them depend on abstraction or interface

                **** We can dynamically create handler object based on object type which implements same interface

                    var handlerName = "package where handlers are defined." + interface.getClass().getSimpleName() + "Handler";

                    var handlerInstance = Class.forName(handlerName).getDeclaredConstructor().newInstance();

                    Above is used to create classes which adhers to open close principle. ***** Its factory design principle
                        i.e. we can create new Handlers seprately without modifying already written classes !!!!!
                        Just add new Handler for new Object type in
                            handler -> typeOneHandler, typeTwoHandler, **newTypeHandler

                    Drawback of above approach is that we violate DRY principle i.e. we will repeate code i.e. code for most of the handler
                    will look similar.

                    When a language is powerful we use features rather than design patterns.
                        Design patterns are then when the language lacks feature

                    ***** Data oriented way of doing above task using pattern matching ******

                    In DOP we violate open close principle as for adding new type we have to modify the same code

                    We can take the above DOP approach one step further by making the interface as sealed. By doint this we can remove the default statement

                    Destructuring is taking an object and extracting values out of it
                    stuff(String s, int a) -> We are defining new variables s and a, and then extracting data from stuff

         */

        interface Trade{};

        record BuyTrade(String id, int amount) implements Trade {};
        record SellTrade(String id, int amount) implements Trade {};

        Trade buyTrade = new BuyTrade("c345", 1000);

        var resultOne = switch (buyTrade){
            case BuyTrade buy when buy.amount > 1000 -> "Buy greater than 1000";
            case SellTrade(String id, int i) when i > 1000 -> "Sell greater than 1000"; // *** Destructuring SellTrade and extracting amount into variable i
            case SellTrade(_, var i) when i < 1000 -> "Sell less than 1000"; // *** Using type inference in destructuring and ignoring parameter which we dont require
            case null -> "We can also handle null !!!!!";
            default -> "Invalid";
        };

    }
}
